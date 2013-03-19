package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

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

  def deleteList = {
    DB.withConnection { implicit connection =>
      SQL("delete from UserActions").executeUpdate()
    }
  }

}
