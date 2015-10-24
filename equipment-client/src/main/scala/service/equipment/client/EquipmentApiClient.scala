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
  */
class EquipmentApiClient @Inject() (ws: WSClient, config: Config) extends EquipmentService {

  /**
   * Gets information about an item of equipment.
   *
   * @param equipmentId identity of equipment to retrieve
   * @return future option of equipment information
   */
  def getEquipment(equipmentId: Int): Future[Option[Equipment]] = {
    import scala.language.postfixOps

    ws.url(s"${equipmentUrl}/${equipmentId}").get() map {
      response =>   
        response.status match {
          case 200 => {
            val sirenResource = response.json.as[SirenEntity]

            sirenResource.`class`.toList collectFirst {
              case "inverter" => sirenResource.properties.map { _.as[Inverter] }
              case "module" => sirenResource.properties.map { _.as[Module] }
            } flatten

          }
          case 404 => None
          case _ => throw new EquipmentException()
        }
    }
  }

  private def equipmentUrl = {
    "http://localhost:9000/equipment"
  }
}  