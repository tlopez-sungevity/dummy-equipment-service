package service.equipment.slick

import com.google.inject.AbstractModule
import com.google.inject.name.Names

import service.equipment.EquipmentService

class EquipmentModule extends AbstractModule {
  def configure(): Unit = {
    bind(classOf[EquipmentService]).to(classOf[SlickBackedEquipmentService])
  }
}
