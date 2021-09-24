package Experimentations

import Experimentations.CreateResourcesFromConfig.config
import HelperUtils.{CommonUtil, CreateLogger, ObtainConfigReference, OrgUtils}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudsimplus.builders.tables.CloudletsTableBuilder

class BrokerWorkflow

object BrokerWorkflow {

  val config = ObtainConfigReference("simulation") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  val logger = CreateLogger(classOf[CreateResourcesFromConfig])

  def createResources(configName: String): Unit = {
    val simulation = new CloudSim()
    val simConfig = config.getConfig(configName)
    val broker = new DatacenterBrokerSimple(simulation)
    val datacenters = OrgUtils.laodDCsFromConfig(simulation, broker, simConfig)

    simulation.start()

    val finishedCloudlets = broker.getCloudletFinishedList()
    new CloudletsTableBuilder(finishedCloudlets).build()
  }
}
