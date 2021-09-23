package Experimentations

import HelperUtils.{ClientUtil, CreateLogger, ObtainConfigReference, OrgUtils}
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
  
  def createResources(configName: String): Unit =
    val simulation = new CloudSim()

    val simConfig = config.getConfig(configName)
    val clientConfig = config.getConfig("client")

    val broker = new DatacenterBrokerSimple(simulation)

    OrgUtils.createOrgResources(simulation, broker, simConfig)
    ClientUtil.createClientResources(broker, clientConfig)

    simulation.start()

    val finishedCloudlets = broker.getCloudletFinishedList()
    new CloudletsTableBuilder(finishedCloudlets).build()

    
}
