package nodecms.core.extension

import scala.reflect.ClassTag
import scala.collection.immutable.{Seq => ImmutableSeq}
import akka.actor.{
  Actor,
  Props,
  ExtendedActorSystem,
  Extension,
  ExtensionId,
  ExtensionIdProvider
}
import nodecms.core.injector.{
  NodeInjector,
  NodeActorProducer,
  NodeNamedActorProducer
}

trait InjectorExtension extends Extension {
  def injector: NodeInjector
  def props(actorName: String): Props
  def props(clazz: Class[_]): Props
  def props[T <: Actor : ClassTag]: Props =
    props(implicitly[ClassTag[T]].runtimeClass)
}

object InjectorExtension extends ExtensionId[InjectorExtension]
    with ExtensionIdProvider {
  override def lookup = InjectorExtension
  override def createExtension(system: ExtendedActorSystem) = {
    val injector = getInjector(system)
    new GenericInjectorExtension(system, injector)
  }

  private def getInjector(system: ExtendedActorSystem): NodeInjector = {
    val configPath = "nodecms.injector.module"
    val injectorName = system.settings.config.getString(configPath)
    system.dynamicAccess.createInstanceFor[NodeInjector](injectorName, 
      ImmutableSeq((classOf[ExtendedActorSystem], system))).get
  }
}

class GenericInjectorExtension(system: ExtendedActorSystem,
  val injector: NodeInjector) extends InjectorExtension {

  def props(actorName: String): Props = {
    Props(classOf[NodeNamedActorProducer], injector, actorName)
  }
  def props(clazz: Class[_]): Props = {
    Props(classOf[NodeActorProducer], injector, clazz)
  }
}
