package service.equipment

import scala.concurrent.Future

trait EquipmentService {
  def getEquipment(equipmentId: Int): Future[Option[Equipment]]
}
