package pack

import macrotest.AddMethodMacro

@AddMethodMacro
class TestClass() {
  def aHandWrittenMethod(input: String): Unit = println(s"Here's the handwritten part: $input")
}
