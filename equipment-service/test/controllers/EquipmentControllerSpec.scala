package controllers

import scala.concurrent.Future

import service.equipment._

import org.scalatest._
import org.scalatestplus.play._
import org.scalamock.scalatest.MockFactory
import org.joda.time.DateTime

import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json._

class EquipmentControllerSpec extends PlaySpec with Results with MockFactory {
  private def assertGetEquipment(equipmentId: Int, equipmentResult: Future[Option[Equipment]], expectedResultBody: JsValue, expectedStatus: Int): Unit = {
    val equipmentServiceMock = stub[EquipmentService]
    (equipmentServiceMock.getEquipment _).when(new EquipmentIdentity(equipmentId)).returns(equipmentResult)

    val controller = new EquipmentController(equipmentServiceMock)

    val result = controller.getEquipment(equipmentId).apply(FakeRequest())

    contentAsJson(result) mustBe expectedResultBody 
    status(result) mustBe expectedStatus
  }

  "Equipment Controller" should {

    "return 500 status code and an error response body when found equipment cannot be mapped to a known equipment type" in {
      val equipmentId = 42

      assertGetEquipment(
        equipmentId, 
        Future.failed(new IllegalStateException("Failed to map")),
        Json.obj("message" -> s"Unable to map result for Equipment ID $equipmentId to known type."),
        500
      )
    }

    "return 500 status code for all other types of error" in {
      assertGetEquipment(
        13, 
        Future.failed(new Exception("Pretend database was not available")),
        Json.obj("message" -> "Internal Server Error"),
        500
      )
    }

    "return 404 status code for inverter or module not found" in {
      val equipmentId = 0

      assertGetEquipment(
        equipmentId, 
        Future.successful(None),
        Json.obj("message" -> s"Unable to find any equipment for ID $equipmentId."),
        404
      )
    }


    "return Inverter when found" in {
      val equipmentId = 7

      assertGetEquipment(
        equipmentId, 
        Future.successful(Some(
          new Inverter(
            new EquipmentIdentity(7),
            modelName = "1501xi",
            manufacturerName = "Kaco",
            description = Some("1.5 kW, 240 Vac, 125-400Vdc Utility Interactive Inverter"),
            modifiedDate = new DateTime(2012,8,14,20,22,7),
            rating = Some(2),
            efficiency = 0.94d,
            outputVoltage = None,
            isThreePhase = None)
        )),
        Json.obj(
          "title" -> "Kaco 1501xi",
          "class" -> Json.arr("equipment","equipment-inverter"),
          "properties" -> Json.obj(
            "id" -> 7,
            "modelName" -> "1501xi",
            "manufacturerName" -> "Kaco",
            "description" -> "1.5 kW, 240 Vac, 125-400Vdc Utility Interactive Inverter",
            "modifiedDate" -> "2012-08-14T20:22:07",
            "rating" -> 2,
            "efficiency" -> 0.94)),
        200
      )
    }


    "return Module when found" in {
      val equipmentId = 11

      assertGetEquipment(
        equipmentId, 
        Future.successful(Some(
          new Module(
            new EquipmentIdentity(11),
            modelName = "BP3170B",
            manufacturerName = "BP Solar",
            description = Some("170W Polycrystalline Module, MC4, U Frame"),
            modifiedDate = new DateTime(2013,10,22,21,38,8),
            kwStc = 0.17,
            kwPtc = 0.1533,
            heightMm = 1590,
            widthMm = 790,
            isBipvRated = Some(false),
            powerTemperatureCoefficient = -0.409,
            normalOperatingCellTemperature = 47.3,
            medianPmaxMultiplier = Some(1.015d))
        )),
        Json.obj(
          "title" -> "BP Solar BP3170B",
          "class" -> Json.arr("equipment","equipment-module"),
          "properties" -> Json.obj(
            "id" -> 11,
            "modelName" -> "BP3170B",
            "manufacturerName" -> "BP Solar",
            "description" -> "170W Polycrystalline Module, MC4, U Frame",
            "modifiedDate" -> "2013-10-22T21:38:08",
            "kwStc" -> 0.17,
            "kwPtc" -> 0.1533,
            "heightMm" -> 1590,
            "widthMm" -> 790,
            "isBipvRated" -> false,
            "powerTemperatureCoefficient" -> -0.409,
            "normalOperatingCellTemperature" -> 47.3,
            "medianPmaxMultiplier" -> 1.015d)),
        200
      )
    }
  }
}
