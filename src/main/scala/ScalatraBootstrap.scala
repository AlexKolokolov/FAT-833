import javax.servlet.ServletContext

import org.kolokolov.scalatra.controller.{GroupController, UserController}
import org.scalatra.LifeCycle

/**
  * Created by Alexey Kolokolov on 05.04.2017.
  */
class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext): Unit = {
    context.mount(new GroupController, "/webapi/groups")
    context.mount(new UserController, "/webapi/users")
  }
}
