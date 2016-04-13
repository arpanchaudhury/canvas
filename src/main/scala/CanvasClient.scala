import constants.UserMessages
import factories.CanvasServiceFactory

object CanvasClient extends App {
  override def main(args: Array[String]): Unit = {
    val canvasServiceFactory = new CanvasServiceFactory
    val canvasService = canvasServiceFactory.getService

    println(UserMessages.CanvasLogo)
    println(UserMessages.Welcome)
    canvasService.run()
    println(UserMessages.GoodBye)
  }
}
