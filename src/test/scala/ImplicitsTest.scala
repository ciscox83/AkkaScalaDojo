import org.junit.Test

class ImplicitsTest {

  @Test
  def myFirstImplicitsTest(): Unit = {
    implicit val number: Int = 1

    val result = concatStringAndNumber("Foo")

    assert(result == "Foo1", "Something went wrong")
  }

  def concatStringAndNumber(s: String)(implicit i: Int): String = {
    s + i
  }
}
