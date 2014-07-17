package nodecms.core.entity

class Tables(val config: SlickConfig) extends TableCore with NodeTable with ContentTable with DocumentTable with FileTable {
  import config.profile.simple._
  val ddl = nodes.ddl ++ nodemetas.ddl ++ contents.ddl ++ documents.ddl ++ files.ddl
}

