package nodecms.core.injector

import scala.reflect.ClassTag
import akka.actor.{Actor, IndirectActorProducer}

class NodeActorProducer(injector: NodeInjector, actor: Class[_ <: Actor])
    extends IndirectActorProducer {
  override def actorClass: Class[_ <: Actor] = actor
  override def produce: Actor = injector.getInstance(actor)
}

class NodeNamedActorProducer(injector: NodeInjector, actorName: String)
    extends IndirectActorProducer {
  override def actorClass: Class[_ <: Actor] = 
    injector.getType[Actor](actorName).asInstanceOf[Class[_ <: Actor]]
  override def produce: Actor = 
    injector.getInstance[Actor](actorName)
}

