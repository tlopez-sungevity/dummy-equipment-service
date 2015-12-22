package service.equipment

import org.scalatest._
import service.equipment.Implicits._

class EquipmentSpec extends FlatSpec with Matchers {
  "EquipmentIdentity" should "be implicitly convertable from an integer" in {
    val equipmentIdentity: EquipmentIdentity = 10
  }

  "EquipmentIdentity" should "be implicitly convertable to an integer" in {
    val equipmentId: Int = new EquipmentIdentity(10) 
  }
}