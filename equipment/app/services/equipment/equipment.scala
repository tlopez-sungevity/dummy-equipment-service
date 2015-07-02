package services.equipment

import org.joda.time.DateTime

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
  rating: Double,
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
