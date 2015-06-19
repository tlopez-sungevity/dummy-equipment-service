package services.equipment 

sealed trait Equipment {
	def id: Int
	def model: String
	def description: Option[String]
}

case class Inverter (id: Int, model: String, description: Option[String], efficiency: Double) extends Equipment

case class Module (id: Int, model: String, description: Option[String]) extends Equipment