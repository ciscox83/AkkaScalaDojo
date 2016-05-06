import java.util.concurrent.TimeUnit

import org.junit.Test

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.concurrent.ExecutionContext.Implicits.global;

class FutureTest {
  val duration: FiniteDuration = Duration(5000, TimeUnit.MILLISECONDS)

  @Test
  def myFirstFutureTest(): Unit = {
    def printA = {
      Thread.sleep(4000)
      println("A")
    }
    def printB = {
      Thread.sleep(3000)
      println("B")
    }

    def printC = {
      Thread.sleep(2000)
      println("C")
    }

    def printD = {
      Thread.sleep(1000)
      println("D")
    }

    val threadOne = Future(printA)
    val threadTwo = Future(printB)
    val threadThree = Future(printC)
    val threadFour = Future(printD)

    Await.ready(threadOne, duration)
    Await.ready(threadTwo, duration)
    Await.ready(threadThree, duration)
    Await.ready(threadFour, duration)
  }
}
