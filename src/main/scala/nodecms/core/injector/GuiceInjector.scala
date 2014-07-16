package nodecms.core.injector

import scala.reflect.ClassTag
import scala.collection.immutable.{ Seq => ImmutableSeq}
import scala.collection.JavaConverters._
import akka.actor.ExtendedActorSystem
import java.lang.reflect.Type

import com.google.inject.{
  Guice, Injector, Module, Key
}
import com.google.inject.name.Names
import nodecms.core.extension.InjectorExtension

class GuiceInjector(system: ExtendedActorSystem) 
    extends NodeInjector {
  val injector = Guice.createInjector(getGuiceModules:_*)

  override def getInstance[T](clazz: Class[T]): T = {
    injector.getInstance(clazz)
  }
  override def getInstance[T:ClassTag](name: String): T = {
    val clazz = implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[T]]
    injector.getInstance(Key.get(clazz, Names.named(name)))
  }
  override def getType[T:ClassTag](name: String): Type = {
    val clazz = implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[T]]
    injector.getBinding(Key.get(clazz, Names.named(name)))
      .getKey().getTypeLiteral().getType()
  }

  private def getGuiceModules: Seq[Module] = {
    val configPath = "nodecms.injector.guice_modules"
    system.settings.config.getStringList(configPath).asScala
      .map(moduleName => loadGuiceModule(moduleName))
  }
  private def loadGuiceModule(moduleName: String): Module = {
    system.dynamicAccess.createInstanceFor[Module](moduleName,
      ImmutableSeq((classOf[ExtendedActorSystem], system))).get
  }
}
