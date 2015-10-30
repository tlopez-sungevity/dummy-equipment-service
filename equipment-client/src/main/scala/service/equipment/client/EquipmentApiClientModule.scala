package service.equipment.client

import com.google.inject.AbstractModule
import com.typesafe.config.{Config,ConfigFactory}
import net.codingwell.scalaguice._

import service.equipment.EquipmentService

class EquipmentApiClientModule extends AbstractModule with ScalaModule {
  def configure: Unit = {
    bind[Config].toInstance(ConfigFactory.load())
    bind[EquipmentService].to[EquipmentApiClient]
  }
}
