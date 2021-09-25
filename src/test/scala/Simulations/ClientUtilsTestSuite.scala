package Simulations

import HelperUtils.ObtainConfigReference
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ClientUtilsTestSuite extends AnyFlatSpec with Matchers  {
  behavior of "ClientUtilsTestSuite Module"
  val config = ObtainConfigReference("experiment") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the experiment config data.")
  }
  
  
}
