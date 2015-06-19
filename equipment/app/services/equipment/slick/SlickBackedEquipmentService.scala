package services.equipment.slick

import javax.inject._
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.db._
import play.api.db.slick._

import services.equipment._

class SlickBackedEquipmentService @Inject()(@NamedDatabase("equipment") dbConfigProvider: DatabaseConfigProvider) extends EquipmentService {
	val dbConfig = dbConfigProvider.get[JdbcProfile]

	class EquipmentTable(tag: Tag) extends Table[(Int, Int, String, Option[String], Option[Double])](tag, "equipment") {
		def id = column[Int]("id", O.PrimaryKey) // This is the primary key column
		def equipmentTypeId = column[Int]("equipment_type_id")
		def model = column[String]("model")
		def description = column[Option[String]]("description")
		def inverterEfficiency = column[Option[Double]]("inverter_efficiency")

		def * = (id, equipmentTypeId, model, description, inverterEfficiency)
	}
	val equipment = TableQuery[EquipmentTable]


	def getEquipment(equipmentId: Int): Future[Option[Equipment]] = {
  		val foundEquipment = dbConfig.db.run(equipment.filter(_.id === equipmentId).result)
  		
  		foundEquipment map { results => results.headOption map { 
  			case (id, 1, model, description, Some(inverterEfficiency)) => Inverter(id, model, description, inverterEfficiency)
  			case (id, 2, model, description, None) => Module(id, model, description)
  			case _ => throw new Exception("Unable to map result")
  		} }
	}

}