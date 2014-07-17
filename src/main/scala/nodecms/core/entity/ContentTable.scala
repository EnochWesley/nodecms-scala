package nodecms.core.entity

import java.sql.Timestamp

trait ContentTable {
  this: TableCore with NodeTable =>
  import config.profile.simple._

  case class Content(id: Long, defaultView: String,
    description: String, language: String, owner: String, status: String,
    created: Timestamp, modified: Timestamp, inNavigation: Boolean)
  class Contents(tag: Tag) extends Table[Content](tag, config.tableName("content")) {
    def id = column[Long]("id", O.PrimaryKey)
    def defaultView = column[String]("default_view", O.DBType("VARCHAR(50)"))
    def description = column[String]("description", O.DBType("TEXT"))
    def language = column[String]("language", O.DBType("VARCHAR(10)"))
    def owner = column[String]("owner", O.DBType("VARCHAR(100)"))
    def status = column[String]("status", O.DBType("VARCHAR(50)"))
    def created = column[Timestamp]("created")
    def modified = column[Timestamp]("modified")
    def inNavigation = column[Boolean]("in_navigation")

    def * = (id, defaultView, description, language, owner,
      status, created, modified, inNavigation) <> 
    (Content.tupled, Content.unapply)

    def node = foreignKey("node_fk", id, nodes)(_.id)

  }
  val contents = TableQuery[Contents]
}

