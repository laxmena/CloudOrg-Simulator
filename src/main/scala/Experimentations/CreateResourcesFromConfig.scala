package Experimentations

import HelperUtils.{ClientUtil, CommonUtil, CreateLogger, ObtainConfigReference, OrgUtils}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.cloudsimplus.builders.tables.CloudletsTableBuilder

class CreateResourcesFromConfig

object CreateResourcesFromConfig {
  val config = ObtainConfigReference("simulation") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  val logger = CreateLogger(classOf[CreateResourcesFromConfig])
  
  def createResources(configName: String): Unit = {
    val simulation = new CloudSim()

    val orgConfig = config.getConfig("simulation")
    val clientConfig = config.getConfig("client")

    val broker = new DatacenterBrokerSimple(simulation)
    // Create Organization Resources metioned in the Org conifg
    OrgUtils.createOrgResources(simulation, broker, orgConfig)

    // Create Client Request Resources
    ClientUtil.createClientResources(broker, clientConfig)

    // Start Simulation
    simulation.start()

    val finishedCloudlets = broker.getCloudletFinishedList()
    new CloudletsTableBuilder(finishedCloudlets).build()
    CommonUtil.logTotalVmsCost(broker)
  }
    
}
