package services.equipment.slick

import services.equipment.{Inverter, Module}

import org.scalatest._
import Matchers._
import org.scalamock.scalatest.MockFactory

import org.joda.time.DateTime

import slick.driver.JdbcProfile

import play.api.test._
import play.api.test.Helpers._
import play.api.db._
import play.api.db.slick._


class EquipmentServiceISpec extends FlatSpec with Matchers with MockFactory {

  trait ModuleFixture {
  	val moduleId = 42
  	val moduleType = "module"
    val moduleName = "FooBar 42"
  	val manufacturerName = "Initech"
  	val someDescription = Some("A market-trailing module design created by under-motivated engineers over the weekend.")
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
      None, None, None, None
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

  trait InverterFixture {
    val inverterId = 24
    val inverterType = "inverter"
    val inverterName = "FooBartron Series 7"
    val manufacturerName = "Initech"
    val someDescription = Some("A market-trailing inverter design created by under-motivated engineers over the weekend.")
    val modified = new DateTime()
    val someInverterRating = Some(200d)
    val inverterEfficiency = 0.9d
    val someInverterOutputVoltage = Some(120d)
    val someIsThreePhase = Some(false)


    val inverterResultWithDescription = (
      inverterId,
      inverterType,
      inverterName,
      manufacturerName,
      someDescription,
      modified,
      None, None, None, None, None, None, None,
      someInverterRating,
      Some(inverterEfficiency),
      someInverterOutputVoltage,
      someIsThreePhase
    )

    val inverterWithDescription = Inverter(
      inverterId,
      inverterName,
      manufacturerName,
      someDescription,
      modified,
      someInverterRating,
      inverterEfficiency,
      someInverterOutputVoltage,
      someIsThreePhase
    )
  }


  trait InitializedEquipmentService {
    val dbConfigProviderMock = stub[DatabaseConfigProvider]
    (dbConfigProviderMock.get[JdbcProfile] _).when().returns(null)

    val service = new SlickBackedEquipmentService(dbConfigProviderMock)      
  }

 "A Slick-based implementation of the Equipment Service " should "convert result for module db query to a Module instance" in new ModuleFixture with InitializedEquipmentService {
    service.toEquipment(moduleResultWithDescription) shouldBe moduleWithDescription
  }

  it should "convert result for inverter db query to Inverter instance" in new InverterFixture with InitializedEquipmentService {
    service.toEquipment(inverterResultWithDescription) shouldBe inverterWithDescription    
  }

  it should "throw an exception when trying map an unknown equipment type" in new InitializedEquipmentService {
    val badResult = (
      101,
      "banana",
      "Borked",
      "Bork, Inc.",
      None, new DateTime(), None, 
      None, None, None, None, None,
      None, None, None, None, None
    )

    an [IllegalStateException] should be thrownBy {
      service.toEquipment(badResult)
    }
  }
  
}