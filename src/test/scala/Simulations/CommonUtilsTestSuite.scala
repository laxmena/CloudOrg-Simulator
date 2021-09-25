package Simulations

import HelperUtils.ConfigModels.CloudletConfig
import HelperUtils.{CommonUtil, ObtainConfigReference}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.cloudbus.cloudsim.resources.Pe
import com.typesafe.config.ConfigBeanFactory
import org.cloudbus.cloudsim.cloudlets.{Cloudlet}

class CommonUtilsTestSuite  extends AnyFlatSpec with Matchers {
  behavior of "CommonUtilsTestSuite Module"
  val config = ObtainConfigReference("experiment") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the experiment config data.")
  }

  val simulation = new CloudSim()
  val broker = new DatacenterBrokerSimple(simulation)

  it should "Create PE" in {
    val pe = CommonUtil.configurePe(200)
    assert(pe.isInstanceOf[Pe])
  }

  val cloudletConf = config.getConfig("cloudletConf-test")

  it should "Configure Single Cloudlet from conf" in {
    val cloudlet = ConfigBeanFactory.create(cloudletConf, classOf[CloudletConfig])
    val obj = CommonUtil.configureCloudlet(cloudlet)
    assert(obj.isInstanceOf[List[Cloudlet]])
  }

  it should "Number of cloudlets should be consistant" in {
    val cloudlet = ConfigBeanFactory.create(cloudletConf, classOf[CloudletConfig])
    val count = cloudlet.count
    val obj = CommonUtil.configureCloudlet(cloudlet)

    assert(obj.length == count)
  }

}
