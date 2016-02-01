package controllers

import play.api._
import play.api.mvc._
import play.api.Logger


class TesController extends Controller {

 def inverter  = Action {
    Ok("Inverter!!")
  }

  def module  = Action {
    Ok("Hello module")
  }

  def sirenModule  = Action {
    Ok("Hello siren module")
  }

  def sirenInverter  = Action {
    Ok("Hello siren inverter")
  }

}
