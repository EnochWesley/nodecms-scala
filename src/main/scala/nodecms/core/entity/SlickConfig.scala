package nodecms.core.entity

import scala.reflect.ClassTag
import akka.actor.DynamicAccess
import scala.slick.driver.JdbcProfile
import javax.sql.DataSource

class SlickConfig(dynamicAccess: DynamicAccess,
  profileClass: String, dataSource: DataSource,
  tablePrefix: String) {

  lazy val profile:JdbcProfile = 
    dynamicAccess.getObjectFor[JdbcProfile](profileClass).get
  import profile.simple._
  lazy val database: Database = Database.forDataSource(dataSource)

  def tableName(table: String): String = {
    s"${tablePrefix}${table}"
  }
}

