package nodecms.scalatra

import akka.actor.ActorSystem
import org.scalatra.ScalatraServlet

import nodecms.core.extension.InjectorExtension

class NodeServlet extends ScalatraServlet {
  lazy val system = ActorSystem(getInitParameter("nodeActorSystemName"))

  override def destroy(): Unit = {
    system.shutdown()
    super.destroy()
  }
}

