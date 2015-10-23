package service.equipment.slick

import com.google.inject.AbstractModule
import net.codingwell.scalaguice._

import service.equipment.EquipmentService

class EquipmentModule extends AbstractModule with ScalaModule {
  def configure: Unit = {
    bind[EquipmentService].to[SlickBackedEquipmentService]
  }
}
