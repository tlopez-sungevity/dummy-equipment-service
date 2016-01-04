package service.equipment.client

import org.scalatest._
import org.scalatest.concurrent._
import org.scalamock._
import org.scalamock.scalatest.MockFactory
import org.joda.time.DateTime
import play.api.libs.ws._
import play.api.libs.json._
import com.typesafe.config.Config
import scala.concurrent.Future

import service.equipment._

class EquipmentApiClientSpec extends FlatSpec with Matchers with MockFactory with ScalaFutures {
  private def mockWs(expectedUrl: String, responseStatus: Int, responseStatusText: String, responseJson: JsValue = Json.obj()): WSClient = {
    val mockResponse = stub[WSResponse]
    (mockResponse.status _).when().returns(responseStatus)
    (mockResponse.statusText _).when().returns(responseStatusText)
    (mockResponse.json _).when().returns(responseJson)

    val mockRequest = stub[WSRequestHolder]
    (mockRequest.get _).when().returns(Future.successful(mockResponse))

    val mockWs = mock[WSClient]
    (mockWs.url _).expects(expectedUrl).returning(mockRequest)

    mockWs
  }

  private def mockConfig(serviceUrlKey: String, serviceUrlValue: String): Config = {
    val mockConfig = mock[Config]
    (mockConfig.getString _).expects(serviceUrlKey).returning(serviceUrlValue)    

    mockConfig
  }

  trait ModuleFixture {
    val expectedModule = Module(
      id = new EquipmentIdentity(2004),
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

   val moduleProperties = Json.obj(
      "id" -> 2004,
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
      "medianPmaxMultiplier" -> 1.015d)

    val serializedModule = Json.obj(
      "title" -> "BP Solar BP3170B",
      "class" -> Json.arr("equipment","equipment-module"),
      "properties" -> moduleProperties)
  }

  trait InverterFixture {

    val expectedInverter = Inverter(
      id = new EquipmentIdentity(406),
      modelName = "1501xi",
      manufacturerName = "Kaco",
      description = Some("1.5 kW, 240 Vac, 125-400Vdc Utility Interactive Inverter"),
      modifiedDate = new DateTime(2012,8,14,20,22,7),
      rating = Some(2),
      efficiency = 0.94d,
      outputVoltage = None,
      isThreePhase = None
    )

    val inverterProperties = Json.obj(
      "id" -> 406,
      "modelName" -> "1501xi",
      "manufacturerName" -> "Kaco",
      "description" -> "1.5 kW, 240 Vac, 125-400Vdc Utility Interactive Inverter",
      "modifiedDate" -> "2012-08-14T20:22:07",
      "rating" -> 2,
      "efficiency" -> 0.94)

    val serializedInverter = Json.obj(
      "title" -> "Kaco 1501xi",
      "class" -> Json.arr("equipment","equipment-inverter"),
      "properties" -> inverterProperties)

  }

  private def assertGetEquipment(equipmentId: Int, responseStatus: Int, responseStatusText: String = "", responseJson: JsValue = Json.obj(), expectedResult: Option[Equipment]): Unit = {
    val serviceUrl = "http://foo:9013/equipment"

    val client = new EquipmentApiClient(
      mockWs(s"$serviceUrl/$equipmentId", responseStatus, responseStatusText, responseJson), 
      mockConfig("service.equipment.client.apiUrl", serviceUrl))

    val futureEquipment = client.getEquipment(new EquipmentIdentity(equipmentId)) 

    futureEquipment.futureValue shouldBe expectedResult
  }

  "EquipmentApiClient" should "handle 404 or missing equipment by returning a None" in {
    assertGetEquipment(
      equipmentId = 42,
      responseStatus = 404,
      expectedResult = None)
  }

  it should "return None on a 200 response without valid Siren Entity with the expected siren classes containing either an Inverter or a Module" in new ModuleFixture {
    assertGetEquipment(
      equipmentId = 42,
      responseStatus = 200,
      expectedResult = None)
  } 

  it should "return a module on a 200 OK response containing a siren resource with the equipment-module class" in new ModuleFixture {
    assertGetEquipment(
      equipmentId = 2004,
      responseStatus = 200,
      responseJson = serializedModule,
      expectedResult = Some(expectedModule))
  } 

  it should "return an inverter on a 200 OK response containing a siren resource with the equipment-inverter class" in new InverterFixture {
    assertGetEquipment(
      equipmentId = 406,
      responseStatus = 200,
      responseJson = serializedInverter,
      expectedResult = Some(expectedInverter))
  } 


  it should "throw an exception when another kind of http status is returned" in new InverterFixture {
    val serviceUrl = "http://foo:9013/equipment"
    val equipmentId = 13

    val status = 500
    val statusText = "Something horrible happened!"

    val client = new EquipmentApiClient(
      mockWs(s"$serviceUrl/$equipmentId", responseStatus = status, responseStatusText = statusText), 
      mockConfig("service.equipment.client.apiUrl", serviceUrl))

    val futureEquipment = client.getEquipment(new EquipmentIdentity(equipmentId)) 

    whenReady (futureEquipment.failed) { ex => 
      ex shouldBe a [EquipmentException]
      ex.getMessage shouldBe (s"Equipment Service returned error HTTP Status: $status $statusText")
    }
  } 



}