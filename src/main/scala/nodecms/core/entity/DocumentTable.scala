package nodecms.core.entity

trait DocumentTable {
  this: TableCore with ContentTable =>
  import config.profile.simple._

  case class Document(id: Long, body: String, mimetype: String)
  class Documents(tag: Tag) extends Table[Document](tag, config.tableName("document")) {
    def id = column[Long]("id", O.PrimaryKey)
    def body = column[String]("body", O.DBType("TEXT"))
    def mimetype = column[String]("mimetype", O.DBType("VARCHAR(30)"))

    def * = (id, body, mimetype) <> (Document.tupled, Document.unapply)

    def content = foreignKey("content_fk", id, contents)(_.id)
  }
  val documents = TableQuery[Documents]
}

