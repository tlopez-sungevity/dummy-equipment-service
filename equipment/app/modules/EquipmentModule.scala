package modules

import com.google.inject.AbstractModule
import com.google.inject.name.Names

import services.equipment.EquipmentService
import services.equipment.slick.SlickBackedEquipmentService

class EquipmentModule extends AbstractModule {
  def configure() = {
    bind(classOf[EquipmentService])
      .to(classOf[SlickBackedEquipmentService])
  }
}
