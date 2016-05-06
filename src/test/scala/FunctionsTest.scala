import org.junit.Test

class FunctionsTest {

  @Test
  def myFirstFunctionTest(): Unit = {
    assert(f("Foo")(1) == "Foo1")
  }

  def f(s: String): Int => String = {
    def nested(i: Int): String = {
      s + i
    }
    nested
  }
}
