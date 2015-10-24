package service.equipment

import org.joda.time.DateTime
import scala.concurrent.Future

sealed trait Equipment {
  def id: Int
  def modelName: String
  def manufacturerName: String
  def description: Option[String]
  def modifiedDate: DateTime
}

//TODO what is the unit of measurement for the rating?

case class Inverter (
  id: Int,
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
  id: Int,
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

class EquipmentException() extends Exception()

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
  def getEquipment(equipmentId: Int): Future[Option[Equipment]]
}