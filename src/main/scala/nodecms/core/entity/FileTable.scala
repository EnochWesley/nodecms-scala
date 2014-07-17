package nodecms.core.entity

trait FileTable {
  this: TableCore with ContentTable =>
  import config.profile.simple._

  case class File(id: Long, uri: String, filename: String,
    mimetype: String, size: Long)
  class Files(tag: Tag) extends Table[File](tag, config.tableName("file")) {
    def id = column[Long]("id", O.PrimaryKey)
    def uri = column[String]("uri", O.DBType("TEXT"))
    def filename = column[String]("filename", O.DBType("VARCHAR(100)"))
    def mimetype = column[String]("mimetype", O.DBType("VARCHAR(100)"))
    def size = column[Long]("size")

    def * = (id, uri, filename, mimetype, size) <> 
    (File.tupled, File.unapply)

    def content = foreignKey("content_fk", id, contents)(_.id)
  }
  val files = TableQuery[Files]
}

