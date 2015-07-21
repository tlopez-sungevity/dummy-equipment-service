package services.equipment.slick

import services.equipment.Module

import org.scalatest._
import org.scalatestplus.play._
import org.scalamock.scalatest.MockFactory
import org.joda.time.DateTime

import slick.driver.JdbcProfile

import play.api.test._
import play.api.test.Helpers._
import play.api.db._
import play.api.db.slick._


class EquipmentServiceISpec extends PlaySpec with MockFactory {
  "A Slick-based implementation of the Equipment Service " must {

    trait ModuleFixture {
    	val moduleId = 42
    	val moduleType = "module"
      val moduleName = "FooBar 42"
    	val manufacturerName = "Initech"
    	val someDescription = Some("A market-trailing design created by under-motivated engineers over the weekend.")
    	val modified = new DateTime()
    	val moduleKwStc = 13d
    	val moduleKwPtc = 14d
    	val moduleHeightMm = 750d
    	val moduleWidthMm = 200d
    	val isBipvRated = Some(false)
    	val powerTemperatureCoefficient = 0.9
    	val normalOperatingCellTemperature = 72d

    	val moduleResultWithDescription = (
    	  moduleId,
    	  moduleType,
    	  moduleName,
    	  manufacturerName,
    	  someDescription,
    	  modified,
    	  Some(moduleKwStc),
    	  Some(moduleKwPtc),
    	  Some(moduleHeightMm),
    	  Some(moduleWidthMm),
    	  isBipvRated,
    	  Some(powerTemperatureCoefficient),
    	  Some(normalOperatingCellTemperature),
        None,
        None,
        None,
        None
    	)

      val moduleWithDescription = Module(
        moduleId,
        moduleName,
        manufacturerName,
        someDescription,
        modified,
        moduleKwStc,
        moduleKwPtc,
        moduleHeightMm,
        moduleWidthMm,
        isBipvRated,
        powerTemperatureCoefficient,
        normalOperatingCellTemperature
      )        

    }

    trait InitializedEquipmentService {
      val dbConfigProviderMock = stub[DatabaseConfigProvider]
      (dbConfigProviderMock.get[JdbcProfile] _).when().returns(null)

      val service = new SlickBackedEquipmentService(dbConfigProviderMock)      
    }

    "convert result for module database query to a Module instance" in new ModuleFixture with InitializedEquipmentService {
      service.toEquipment(moduleResultWithDescription) mustBe moduleWithDescription
    }
  }
}