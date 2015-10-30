package service.equipment

import org.joda.time.DateTime
import scala.concurrent.Future

class EquipmentIdentity(val value: Int) extends AnyVal

sealed trait Equipment {
  def id: EquipmentIdentity
  def modelName: String
  def manufacturerName: String
  def description: Option[String]
  def modifiedDate: DateTime
}

//TODO what is the unit of measurement for the rating?

case class Inverter (
  id: EquipmentIdentity,
  modelName: String,
  manufacturerName: String,
  description: Option[String],
  modifiedDate: DateTime,
  rating: Option[Double],
  efficiency: Double,
  outputVoltage: Option[Double],
  isThreePhase: Option[Boolean]) extends Equipment

//TOOD should module isBipvRated be optional?

case class Module (
  id: EquipmentIdentity,
  modelName: String,
  manufacturerName: String,
  description: Option[String],
  modifiedDate: DateTime,
  kwStc: Double,
  kwPtc: Double,
  heightMm: Double,
  widthMm: Double,
  isBipvRated: Option[Boolean],
  powerTemperatureCoefficient: Double,
  normalOperatingCellTemperature: Double) extends Equipment

case class EquipmentException(message: String) extends Exception(message)

/**
 * Service for obtaining information about equipment used by Sungevity.
 */
trait EquipmentService {

  /**
   * Gets information about an item of equipment.
   *
   * @param equipmentId identity of equipment to retrieve
   * @return future of option of equipment information:
   *         some when found;
   *         none when no equipment with specified identifier exists.
   */
  def getEquipment(equipmentId: EquipmentIdentity): Future[Option[Equipment]]
}
