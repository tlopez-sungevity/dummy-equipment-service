package service.equipment.client

import com.google.inject.AbstractModule
import com.typesafe.config.{Config,ConfigFactory}
import net.codingwell.scalaguice._

import service.equipment.EquipmentService

/**
 * Guice Module for Equipment API Client, requires being loaded
 * into a Play application for access to WSClient.
 */
class EquipmentApiClientPlayModule extends AbstractModule with ScalaModule {
  def configure: Unit = {
    bind[Config].toInstance(ConfigFactory.load())
    bind[EquipmentService].to[EquipmentApiClient]
  }
}
