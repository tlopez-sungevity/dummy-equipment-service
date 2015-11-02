package serialize.equipment

import play.api.libs.json._
import org.scalatest._
import org.joda.time.DateTime
import com.sungevity.commons.formats.siren._
import com.sungevity.commons.formats.siren.Implicits._

import service.equipment._
import serialize.equipment.Implicits._

class SerializationSpec extends FlatSpec with Matchers {

  trait InverterFixture {

    val inverter = Inverter(
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

    val inverterSirenEntity = SirenEntity(
      `class` = Set("equipment","equipment-inverter"),
      title = Some("Kaco 1501xi"),
      properties = Some(inverterProperties))

    val serializedInverter = Json.obj(
      "title" -> "Kaco 1501xi",
      "class" -> Json.arr("equipment","equipment-inverter"),
      "properties" -> inverterProperties)

  }

  trait ModuleFixture {
    val module = Module(
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
      normalOperatingCellTemperature = 47.3)

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
        "normalOperatingCellTemperature" -> 47.3)

    val moduleSirenEntity = SirenEntity(
      `class` = Set("equipment","equipment-module"),
      title = Some("BP Solar BP3170B"),
      properties = Some(moduleProperties))

    val serializedModule = Json.obj(
      "title" -> "BP Solar BP3170B",
      "class" -> Json.arr("equipment","equipment-module"),
      "properties" -> moduleProperties)
  }

  "Equipment Serialization" should "serialize an Inverter to Siren" in new InverterFixture {
    val sirenizedInverter = inverter.toSirenEntity
    
    sirenizedInverter should be (inverterSirenEntity)
    
    val jsonInverter = Json.toJson(sirenizedInverter)
   
    jsonInverter shouldBe serializedInverter

  }

  it should "deserialize a Siren Inverter" in new InverterFixture {
    val parsedSiren = serializedInverter.as[SirenEntity]
   
    parsedSiren should be (inverterSirenEntity)

    val parsedInverter = parsedSiren.properties.get.as[Inverter]
   
    parsedInverter should be (inverter)

  }  

  it should "serialize a Module to Siren" in new ModuleFixture {
    val sirenizedModule = module.toSirenEntity
    
    sirenizedModule should be (moduleSirenEntity)
    
    val jsonModule = Json.toJson(sirenizedModule)
   
    jsonModule shouldBe serializedModule
  }  

  it should "deserialize a Siren Module" in new ModuleFixture {
    val parsedSiren = serializedModule.as[SirenEntity]
   
    parsedSiren should be (moduleSirenEntity)

    val parsedModule = parsedSiren.properties.get.as[Module]
   
    parsedModule should be (module)
  }  

}