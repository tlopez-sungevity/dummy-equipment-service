package serialize.equipment

import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.joda.time.DateTime

import com.sungevity.commons.formats.siren._
import com.sungevity.commons.formats.siren.Implicits._

import service.equipment._

object Implicits {
  private val iso8061Format = "yyyy-MM-dd'T'HH:mm:ss"

  implicit val equipmentIdentityReads: Reads[EquipmentIdentity] = Reads.of[Int].map( new EquipmentIdentity(_) )
  implicit val equipmentIdentityWrites: Writes[EquipmentIdentity] = Writes { (equipmentId: EquipmentIdentity) => JsNumber(equipmentId.value) }

  implicit val inverterReads: Reads[Inverter] = (
      (JsPath \ "id").read[EquipmentIdentity] and
      (JsPath \ "modelName").read[String] and
      (JsPath \ "manufacturerName").read[String] and
      (JsPath \ "description").readNullable[String] and
      (JsPath \ "modifiedDate").read[DateTime](Reads.jodaDateReads(iso8061Format)) and
      (JsPath \ "rating").readNullable[Double] and
      (JsPath \ "efficiency").read[Double] and
      (JsPath \ "outputVoltage").readNullable[Double] and
      (JsPath \ "isThreePhase").readNullable[Boolean]
  )(Inverter.apply _)

  implicit val inverterWrites: Writes[Inverter] = (
      (JsPath \ "id").write[EquipmentIdentity] and
      (JsPath \ "modelName").write[String] and
      (JsPath \ "manufacturerName").write[String] and
      (JsPath \ "description").writeNullable[String] and
      (JsPath \ "modifiedDate").write[DateTime](Writes.jodaDateWrites(iso8061Format)) and
      (JsPath \ "rating").writeNullable[Double] and
      (JsPath \ "efficiency").write[Double] and
      (JsPath \ "outputVoltage").writeNullable[Double] and
      (JsPath \ "isThreePhase").writeNullable[Boolean]
  )(unlift(Inverter.unapply))

  implicit val moduleReads: Reads[Module] = (
      (JsPath \ "id").read[EquipmentIdentity] and
      (JsPath \ "modelName").read[String] and
      (JsPath \ "manufacturerName").read[String] and
      (JsPath \ "description").readNullable[String] and
      (JsPath \ "modifiedDate").read[DateTime](Reads.jodaDateReads(iso8061Format)) and
      (JsPath \ "kwStc").read[Double] and
      (JsPath \ "kwPtc").read[Double] and
      (JsPath \ "heightMm").read[Double] and
      (JsPath \ "widthMm").read[Double] and
      (JsPath \ "isBipvRated").readNullable[Boolean] and
      (JsPath \ "powerTemperatureCoefficient").read[Double] and
      (JsPath \ "normalOperatingCellTemperature").read[Double] and
      (JsPath \ "medianPmaxMultiplier").readNullable[Double]
  )(Module.apply _)

  implicit val moduleWrites: Writes[Module] = (
      (JsPath \ "id").write[EquipmentIdentity] and
      (JsPath \ "modelName").write[String] and
      (JsPath \ "manufacturerName").write[String] and
      (JsPath \ "description").writeNullable[String] and
      (JsPath \ "modifiedDate").write[DateTime](Writes.jodaDateWrites(iso8061Format)) and
      (JsPath \ "kwStc").write[Double] and
      (JsPath \ "kwPtc").write[Double] and
      (JsPath \ "heightMm").write[Double] and
      (JsPath \ "widthMm").write[Double] and
      (JsPath \ "isBipvRated").writeNullable[Boolean] and
      (JsPath \ "powerTemperatureCoefficient").write[Double] and
      (JsPath \ "normalOperatingCellTemperature").write[Double] and
      (JsPath \ "medianPmaxMultiplier").writeNullable[Double]
  )(unlift(Module.unapply))

  implicit class InverterSerializer(inverter: Inverter) extends SirenEntitySerializer {
    def toSirenEntity: SirenEntity =
      SirenEntity(
        `class`=Set("equipment","equipment-inverter"),
        properties=Some(Json.toJson(inverter)),
        title=Some(s"${inverter.manufacturerName} ${inverter.modelName}"))
  }

  implicit class ModuleSerializer(module: Module) extends SirenEntitySerializer {
    def toSirenEntity: SirenEntity =
      SirenEntity(
        `class`=Set("equipment", "equipment-module"),
        properties=Some(Json.toJson(module)),
        title=Some(s"${module.manufacturerName} ${module.modelName}"))
  }
}
