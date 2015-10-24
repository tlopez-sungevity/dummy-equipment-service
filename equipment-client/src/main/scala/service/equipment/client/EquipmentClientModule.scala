package service.equipment.client

import com.google.inject.AbstractModule
import net.codingwell.scalaguice._

import service.equipment.EquipmentService

class EquipmentClientModule extends AbstractModule with ScalaModule {
  def configure: Unit = {
    bind[EquipmentService].to[EquipmentApiClient]
  }
}
