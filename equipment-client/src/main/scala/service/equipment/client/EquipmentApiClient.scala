package service.equipment.client

import service.equipment._
import serialize.equipment.Implicits._

import javax.inject._
import com.typesafe.config._
import com.typesafe.scalalogging._
import com.sungevity.commons.formats.siren._
import com.sungevity.commons.formats.siren.Implicits._
import org.slf4j.LoggerFactory;
import play.api.libs.ws._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Client library for accessing remote equipment service.
  *
  * @param ws web service client instance
  * @param config configruation service
  */
class EquipmentApiClient @Inject() (ws: WSClient, config: Config) extends EquipmentService {

  private val logger = Logger(LoggerFactory.getLogger(this.getClass))

  /**
   * Gets information about an item of equipment.
   *
   * @param equipmentId identity of equipment to retrieve
   * @return future option of equipment information
   */
  def getEquipment(equipmentId: EquipmentIdentity): Future[Option[Equipment]] = {
    import scala.language.postfixOps

    val requestUrl = s"${equipmentUrl}/${equipmentId.value}"

    logger.info(s"Equipment API Client -> $requestUrl")

    ws.url(requestUrl).get() map {
      response =>
        logger.info(s"Equipment API Client <- ${response.json}")

        response.status match {
          case 200 => {
            response.json.asOpt[SirenEntity] flatMap { sirenResource =>
              (sirenResource.`class`.toList collectFirst {
                case "equipment-inverter" => sirenResource.properties.map { _.as[Inverter] }
                case "equipment-module" => sirenResource.properties.map { _.as[Module] }
              }).flatten
            }
          }
          case 404 =>
            logger.warn(s"Equipment ID ${equipmentId.value} could not be found by the Equipment Service.")
            None
          case status =>
            val message = s"Equipment Service returned error HTTP Status: $status ${response.statusText}"
            logger.error(s"$message -> ${response.body}")

            throw new EquipmentException(message)
        }
    }
  }

  private def equipmentUrl = config.getString("service.equipment.client.apiUrl")
}
