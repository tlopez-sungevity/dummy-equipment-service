package controllers

import scala.concurrent.Future

import service.equipment.{EquipmentService, EquipmentIdentity, Equipment}

import org.scalatest._
import org.scalatestplus.play._
import org.scalamock.scalatest.MockFactory

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
  }
}