import org.junit.runner._
import org.scalatest._
import org.scalatestplus.play._

import play.api.Play.current
import play.api.libs.ws.WS
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

import play.api.test._
import play.api.test.Helpers._


/**
 * Tests the Equipment Service.
 */
class EquipmentServiceISpec extends PlaySpec with OneServerPerSuite {

  "Equipment Service" must {

    "return 404 on an unknown URL" in {
      val prefix = s"http://localhost:$port"
      val response = await(WS.url(s"$prefix/borked").get) 

      response.status mustBe NOT_FOUND
    }

    "return 404 when the equipment id is not found" in {
      val prefix = s"http://localhost:$port"
      val response = await(WS.url(s"$prefix/equipment/9999999").get)

      response.status mustBe NOT_FOUND
    }

    "return 200 when the equipment (module) with id 32 is found" in {
      val prefix = s"http://localhost:$port"
      val response = await(WS.url(s"$prefix/equipment/32").get)
     
      val expectedJson = Json.obj(
        "class" -> Json.arr("equipment","equipment-module"),
        "properties" -> Json.obj(
          "id" -> 32,
          "modelName" -> "BP175B",
          "manufacturerName" -> "BP Solar",
          "description" -> "175W Polycrystalline Module, Multicontact, Bronze Frame",
          "modifiedDate" -> "2013-06-12T21:38:01",
          "kwStc" -> 0.175,
          "kwPtc" -> 0.1573,
          "heightMm" -> 1580,
          "widthMm" -> 808,
          "isBipvRated" -> false,
          "powerTemperatureCoefficient" -> -0.4525,
          "normalOperatingCellTemperature" -> -0.4525),
        "title" -> "BP Solar BP175B")

      response.status mustBe OK
      response.header("Content-Type") must contain ("application/json; charset=utf-8")
      response.json mustBe expectedJson
    }

    "return 200 when the equipment (inverter) with id 406 is found" in {
      val prefix = s"http://localhost:$port"
      val response = await(WS.url(s"$prefix/equipment/406").get)
     
      val expectedJson = Json.obj(
        "class" -> Json.arr("equipment","equipment-inverter"),
        "properties" -> Json.obj(
          "id" -> 406,
          "modelName" -> "1501xi",
          "manufacturerName" -> "Kaco",
          "description" -> "1.5 kW, 240 Vac, 125-400Vdc Utility Interactive Inverter",
          "modifiedDate" -> "2012-08-14T20:22:07",
          "rating" -> 2,
          "efficiency" -> 0.94),
        "title" -> "Kaco 1501xi")

      response.status mustBe OK
      response.header("Content-Type") must contain ("application/json; charset=utf-8")
      response.json mustBe expectedJson
    }
  }

  //TODO database fixtures
  //TODO test valid response for panel
  //TODO test malforned data to type mapping
  //TODO missing description
}
