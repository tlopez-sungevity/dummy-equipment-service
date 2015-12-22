package service.equipment

import org.joda.time.DateTime
import scala.concurrent.Future
import language.implicitConversions

/**
 * Value-class to uniquely identify an item of equipment in a type-safe manner.
 * @param value the underlying value for the identifier
 */ 
class EquipmentIdentity(val value: Int) extends AnyVal

/**
 * Implicit conversions in the Equipment domain.
 */
object Implicits {
  implicit def intToEquipmentIdentity(id: Int): EquipmentIdentity = new EquipmentIdentity(id)
  implicit def equipmentIdentityToInt(id: EquipmentIdentity): Int = id.value
}

/**
 * Equipment super-type providing common attributes for all types of equipment.
 */
sealed trait Equipment {
  def id: EquipmentIdentity
  def modelName: String
  def manufacturerName: String
  def description: Option[String]
  def modifiedDate: DateTime
}


/**
 * Inverter.
 */
case class Inverter (
  id: EquipmentIdentity,
  modelName: String,
  manufacturerName: String,
  description: Option[String],
  modifiedDate: DateTime,
  rating: Option[Double], //TODO what is the unit of measurement for the rating?
  efficiency: Double,
  outputVoltage: Option[Double],
  isThreePhase: Option[Boolean]) extends Equipment

/**
 * Module.
 */
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
  powerTemperatureCoefficient: Double, //TOOD should module isBipvRated be optional?
  normalOperatingCellTemperature: Double) extends Equipment

/**
 * Thrown for general problemns dealing with equipment.
 */
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
