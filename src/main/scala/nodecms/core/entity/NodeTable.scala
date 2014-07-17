package nodecms.core.entity

trait NodeTable {
  this: TableCore =>
  import config.profile.simple._

  case class Node(id: Long, ntype: String, parentid: Option[Long], 
    position: Int, name: String, title: String, path:String)
  class Nodes(tag: Tag) extends Table[Node](tag, config.tableName("node")) {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def ntype = column[String]("type", O.DBType("VARCHAR(30)"))
    def parentid = column[Long]("parent_id", O.Nullable)
    def position = column[Int]("position")
    def name = column[String]("name", O.DBType("VARCHAR(50)"))
    def title = column[String]("title", O.DBType("VARCHAR(100)"))
    def path = column[String]("path", O.DBType("TEXT"))

    def * = (id, ntype, parentid.?, position, name, title, path) <>
    (Node.tupled, Node.unapply)

    def parent = foreignKey("node_fk", parentid, nodes)(_.id)
  }
  val nodes = TableQuery[Nodes]

  case class NodeMeta(id: Long, nodeid: Long, key: String, value: String)
  class NodeMetas(tag: Tag) extends Table[NodeMeta](tag, config.tableName("node_meta")) {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def nodeid = column[Long]("node_id")
    def key = column[String]("key")
    def value = column[String]("value", O.DBType("TEXT"))

    def * = (id, nodeid, key, value) <> (NodeMeta.tupled, NodeMeta.unapply)

    def node = foreignKey("node_fk", nodeid, nodes)(_.id)
  }
  val nodemetas = TableQuery[NodeMetas]
}
