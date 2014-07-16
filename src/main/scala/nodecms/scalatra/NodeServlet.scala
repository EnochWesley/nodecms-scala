package nodecms.scalatra

import akka.actor.ActorSystem
import org.scalatra.ScalatraServlet


class NodeServlet extends ScalatraServlet {
  lazy val system = ActorSystem(getInitParameter("nodeActorSystemName"))

  override def destroy(): Unit = {
    system.shutdown()
    super.destroy()
  }
}

