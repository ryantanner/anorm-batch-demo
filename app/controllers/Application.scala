package controllers

import play.api._
import play.api.mvc._
import play.api.libs.iteratee._
import play.api.libs.concurrent._
import play.api.libs.Comet
import play.api.libs.json._

import scala.concurrent.duration._
import scala.util.Random

import play.api.libs.concurrent.Execution.Implicits._

import models._

object Application extends Controller {

  val randomList: Enumerator[JsValue] = {

    Enumerator.fromCallback { () =>
      Promise.timeout(Some(Json.toJson(UserActions.getList)), 10 seconds)
    }

  }

  def index = Action {
    val list = UserActions.getList

    Ok(views.html.index(list))
  }

  def list = Action {
    Ok.stream(randomList &> Comet(callback = "parent.newList"))
  }
  
}
