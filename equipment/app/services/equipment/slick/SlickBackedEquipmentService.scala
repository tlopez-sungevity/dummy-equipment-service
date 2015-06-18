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

	class EquipmentTable(tag: Tag) extends Table[(Int, String)](tag, "equipment") {
		def id = column[Int]("id", O.PrimaryKey) // This is the primary key column
		def model = column[String]("model")

		def * = (id, model)
	}
	val equipment = TableQuery[EquipmentTable]


	def getEquipment(equipmentId: Int): Future[Option[Equipment]] = {
  		val foundEquipment = dbConfig.db.run(equipment.filter(_.id === equipmentId).result)
  		
  		foundEquipment map { results => results.headOption map { case (id, model) => Equipment(id, model)  } }
	}

}