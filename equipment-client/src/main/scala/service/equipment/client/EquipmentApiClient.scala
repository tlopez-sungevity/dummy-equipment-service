package service.equipment.client

import service.equipment._
import serialize.equipment.Implicits._

import javax.inject._
import com.typesafe.config._
import com.sungevity.commons.formats.siren._
import com.sungevity.commons.formats.siren.Implicits._
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

  /**
   * Gets information about an item of equipment.
   *
   * @param equipmentId identity of equipment to retrieve
   * @return future option of equipment information
   */
  def getEquipment(equipmentId: EquipmentIdentity): Future[Option[Equipment]] = {
    import scala.language.postfixOps

    val requestUrl = s"${equipmentUrl}/${equipmentId.value}"

    println(s"EquipmentApiClient calling $requestUrl")

    ws.url(requestUrl).get() map {
      response =>   
        response.status match {
          case 200 => {
            response.json.asOpt[SirenEntity] flatMap { sirenResource =>
              (sirenResource.`class`.toList collectFirst {
                case "equipment-inverter" => sirenResource.properties.map { _.as[Inverter] }
                case "equipment-module" => sirenResource.properties.map { _.as[Module] }
              }).flatten
            }
          }
          case 404 => None
          case status => throw new EquipmentException(s"Equipment Service Error HTTP Status: $status ${response.statusText}")
        }
    }
  }

  private def equipmentUrl = config.getString("service.equipment.client.apiUrl")
}  