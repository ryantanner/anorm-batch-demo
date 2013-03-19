package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

import scala.concurrent.duration._
import play.api.libs.concurrent.Akka
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.util.Random

object UserActions {

  def getList: List[Long] = {
    DB.withConnection { implicit connection =>
      SQL("""
        select action from UserActions
        """).as(long("action") *)
    }
  }

  def saveList(list: List[Long]) = {
    DB.withConnection { implicit connection =>
      val insertQuery = SQL("insert into UserActions(action) values ({action})")
      val batchInsert = (insertQuery.asBatch /: list)(
        (sql, elem) => sql.addBatchParams(elem)
      )
      batchInsert.execute()
    }
  }

  def deleteList() = {
    DB.withConnection { implicit connection =>
      SQL("delete from UserActions").executeUpdate()
    }
  }

  Akka.system.scheduler.schedule(0.seconds, 10.seconds) {
    deleteList()
    val newList = Seq.fill(10)(Random.nextLong).toList
    saveList(newList)
    play.api.Logger.info("Updated list: " + newList.toString)
  }

}
