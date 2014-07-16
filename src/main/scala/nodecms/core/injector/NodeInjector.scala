package nodecms.core.injector

import scala.reflect.ClassTag
import java.lang.reflect.Type

trait NodeInjector {
  def getInstance[T](clazz: Class[T]): T
  def getInstance[T: ClassTag](name: String): T
  def getInstance[T: ClassTag]: T = {
    val clazz = implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[T]]
    getInstance(clazz)
  }
  def getType[T:ClassTag](name: String): Type
}
