package Simulations

import HelperUtils.ObtainConfigReference
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.typesafe.config.{Config, ConfigFactory}

class ConfResourcesTestSuite extends AnyFlatSpec with Matchers {
  behavior of "configuration parameters module"
  val config = ObtainConfigReference("experiment") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the experiment config data.")
  }
  val instanceConfig = config.getConfig("instances")
  val saasCloudletsConfig = config.getConfig("saasCloudlets")

  // VM Instance Config tests
  it should "contain nano instance defenition" in {
    assert(instanceConfig.hasPath("nano"))
  }
  it should "contain medium instance defenition" in {
    assert(instanceConfig.hasPath("medium"))
  }
  it should "contain micro instance defenition" in {
    assert(instanceConfig.hasPath("micro"))
  }
  it should "contain xlarge instance defenition" in {
    assert(instanceConfig.hasPath("xlarge"))
  }

  // SAAS Cloudlets Config Tests
  it should "contain googleSheets SAAS Cloudlet defenition" in {
    assert(saasCloudletsConfig.hasPath("googleSheets"))
  }
  it should "contain googleDocs SAAS Cloudlet defenition" in {
    assert(saasCloudletsConfig.hasPath("googleDocs"))
  }


}
