package controllers

import scala.concurrent.Future

import services.equipment.EquipmentService

import org.scalatest._
import org.scalatestplus.play._
import org.scalamock.scalatest.MockFactory

import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json.Json

class EquipmentControllerSpec extends PlaySpec with Results with MockFactory {
  "Equipment Controller" should {

    "return 500 status code and an error response body when found equipment cannot be mapped to a known equipment type" in {
      val equipmentId = 42

      val equipmentServiceMock = stub[EquipmentService]
      (equipmentServiceMock.getEquipment _).when(equipmentId).returns( 
        Future.failed(new IllegalStateException("Failed to map")) 
      )

      val controller = new EquipmentController(equipmentServiceMock)

      val expectedResultBody = Json.obj("message" -> s"Unable to map result for Equipment ID $equipmentId to known type.")

      val result = controller.getEquipment(equipmentId).apply(FakeRequest())

      contentAsJson(result) mustBe expectedResultBody 
      status(result) mustBe 500
    }
  }
}