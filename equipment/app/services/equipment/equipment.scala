package services.equipment 

sealed trait Equipment {
	def id: Int
	def model: String
	def description: Option[String]
}

case class Inverter (id: Int, model: String, description: Option[String], efficiency: Double, outputVoltage: Option[Double], isThreePhase: Option[Boolean]) extends Equipment

case class Module (id: Int, model: String, description: Option[String], kwStc: Double, kwPtc: Double, heightMm: Double, widthMm: Double) extends Equipment