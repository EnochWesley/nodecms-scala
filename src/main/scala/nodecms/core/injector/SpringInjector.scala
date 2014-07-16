package nodecms.core.injector

import scala.reflect.ClassTag
import akka.actor.ExtendedActorSystem
import java.lang.reflect.Type
import org.springframework.context.support.{
  GenericXmlApplicationContext
}

class SpringInjector(system: ExtendedActorSystem)
    extends NodeInjector {
  val injector = new GenericXmlApplicationContext()
  private val factory = injector.getBeanFactory()
  factory.registerSingleton("nodeActorSystem", system)
  factory.registerSingleton("nodeDynamicAccess", 
    system.dynamicAccess)
  injector.load(getSpringConfig)
  injector.refresh()

  override def getInstance[T](clazz: Class[T]): T = {
    injector.getBean(clazz)
  }
  override def getInstance[T:ClassTag](name: String): T = {
    val clazz = implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[T]]
    injector.getBean(name, clazz)
  }
  override def getType[T:ClassTag](name: String): Type = {
    injector.getType(name).asInstanceOf[Type]
  }

  private def getSpringConfig: String = {
    system.settings.config.getString("nodecms.injector.spring_config")
  }
}


