package org.drc

import org.scalatest.funsuite.AnyFunSuite

class HelloWorldSpec extends AnyFunSuite {
  test("Hello world should start with H") {
    assert("Hello".startsWith("H"))
  }

}
