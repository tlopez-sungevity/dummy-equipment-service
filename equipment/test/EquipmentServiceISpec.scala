import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * Tests the Equipment Service.
 */
@RunWith(classOf[JUnitRunner])
class EquipmentServiceISpec extends Specification {

  "Equipment Service" should {

    "return 404 on an unknown URL" in new WithApplication {
      route(FakeRequest(GET, "/borked")) must beSome.which (status(_) == NOT_FOUND)
    }

    "return 404 when the equipment id is not found" in new WithApplication {
      route(FakeRequest(GET, "/equipment/9999999")) must beSome.which (status(_) == NOT_FOUND)
    }

    "return 200 when the equipment (module) with id is found" in new WithApplication {
      val response = route(FakeRequest(GET, "/equipment/32")).get

      status(response) must equalTo(OK)
      contentType(response) must beSome.which(_ == "application/json")
      //TODO verify response is expected module
    }



    "return 200 when the equipment (inverter) with id is found" in new WithApplication {
      val response = route(FakeRequest(GET, "/equipment/10019")).get

      status(response) must equalTo(OK)
      contentType(response) must beSome.which(_ == "application/json")
      //TODO verify response is expected inverter

    }

    "return 500 when the equipment cannot be mapped to a known type" in new WithApplication {
      val response = route(FakeRequest(GET, "/equipment/406")).get

      status(response) must equalTo(INTERNAL_SERVER_ERROR)
      //FIXME 406 is probably valid anyway
    }


    //TODO database fixtures
    //TODO test valid response for panel
    //TODO test malforned data to type mapping

  }
}
