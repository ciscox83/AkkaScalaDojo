import java.util.concurrent.TimeUnit

import ActorsTest.{FAILURE_MESSAGE, FEELING_GOOD, HOW_DO_YOU_FEEL, PLEASE_FAIL}
import _root_.akka.util.Timeout
import akka.actor.{Actor, ActorSystem, Props, Status}
import akka.pattern.ask
import org.junit.Test

import _root_.scala.concurrent.Await
import _root_.scala.concurrent.duration.{Duration, FiniteDuration}

object ActorsTest {
  val HOW_DO_YOU_FEEL = "HOW DO YOU FEEL?"
  val FEELING_GOOD = "I am good thanks"

  val PLEASE_FAIL = "FAIL"
  val FAILURE_MESSAGE = "Some failure message"
}

class ActorsTest {

  val actorSystem = ActorSystem("myActorSystem")
  val myActorRef = actorSystem.actorOf(Props(classOf[MyActor]), "MyActorName")

  val duration: FiniteDuration = Duration(1000, TimeUnit.MILLISECONDS)

  implicit val timeout = Timeout(duration)

  @Test
  def successMessageTest(): Unit = {
    val askFeeling = myActorRef ? HOW_DO_YOU_FEEL
    val responseFeeling = Await.result(askFeeling, duration)

    assert(responseFeeling == FEELING_GOOD)
  }

  @Test
  def failureMessageTest(): Unit = {
    val askFailure = myActorRef ? PLEASE_FAIL

    try {
      Await.result(askFailure, duration)
    } catch {
      case e: Exception => {
        assert(FAILURE_MESSAGE == e.getMessage)
      }
    }
  }
}

/**
  * An actor that performs some sort of operation
  */
class MyActor extends Actor {
  override def receive: Receive = {
    case HOW_DO_YOU_FEEL => {
      sender ! FEELING_GOOD
    }
    case PLEASE_FAIL => {
      sender ! Status.Failure(new Exception(FAILURE_MESSAGE))
    }
  }
}

