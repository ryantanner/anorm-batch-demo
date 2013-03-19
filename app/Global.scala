import play.api._

import scala.util.Random

import models._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    Logger.info("Inserting dummy data")

    UserActions.deleteList

    val list = Seq.fill(10)(Random.nextLong).toList
    UserActions.saveList(list)
  }

}
