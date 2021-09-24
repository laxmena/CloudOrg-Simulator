package Experimentations

import HelperUtils.{ClientUtil, CommonUtil, CreateLogger, ObtainConfigReference, OrgUtils}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.cloudsimplus.builders.tables.CloudletsTableBuilder

/**
 * Experiments. Trial and Error Practice method for directly 
 * creating objects from config files using PureConfig
 *
 * */
class CreateResourcesFromConfig
object CreateResourcesFromConfig {
  val config = ObtainConfigReference("experiment") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the experiment config data.")
  }

  val logger = CreateLogger(classOf[CreateResourcesFromConfig])
  
  def createResources(configName: String): Unit = {
    val simulation = new CloudSim()

    val orgConfig = config.getConfig("organization1")
    val clientConfig = config.getConfig("client1")

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
