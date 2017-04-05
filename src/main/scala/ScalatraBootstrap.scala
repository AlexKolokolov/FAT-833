import javax.servlet.ServletContext

import _root_.akka.actor.ActorSystem
import org.kolokolov.scalatra.controller.{GroupController, UserController}
import org.scalatra.LifeCycle

/**
  * Created by Alexey Kolokolov on 05.04.2017.
  */
class ScalatraBootstrap extends LifeCycle {

  val system = ActorSystem()

  override def init(context: ServletContext): Unit = {
    context.mount(new GroupController(system), "/webapi/groups")
    context.mount(new UserController(system), "/webapi/users")
  }

  override def destroy(context: ServletContext): Unit = {
    system.terminate
  }
}
