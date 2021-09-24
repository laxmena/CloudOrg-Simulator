package Simulations

import HelperUtils.ConfigModels.{DataCenterConfig, HostConfig}
import HelperUtils.ObtainConfigReference
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import HelperUtils.OrgUtils
import com.typesafe.config.ConfigBeanFactory
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.cloudbus.cloudsim.datacenters.network.NetworkDatacenter
import org.cloudbus.cloudsim.hosts.network.NetworkHost

class OrgUtilsTestSuite  extends AnyFlatSpec with Matchers {
  behavior of "OrgUtils Module"
  val config = ObtainConfigReference("experiment") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the experiment config data.")
  }

  val simulation = new CloudSim()
  val broker = new DatacenterBrokerSimple(simulation)

  // Test IAAS Datacenter
  val instanceConfig = config.getConfig("instances")
  it should "Create IAAS Datacenter" in {
    val dcConfig = config.getConfig("iaasdc")
    val dc = ConfigBeanFactory.create(dcConfig, classOf[DataCenterConfig])
    val obj = OrgUtils.createDataCenter(simulation, broker, dc)
    assert(obj.isInstanceOf[Datacenter])
  }

  // Test Network Host
  it should "Create Host" in {
    val host = config.getConfig("host-test")
    val hostConfig = ConfigBeanFactory.create(host, classOf[HostConfig])
    val obj = OrgUtils.configureSingleHost(hostConfig)
    assert(obj.isInstanceOf[NetworkHost])
  }
  
}
