package Experimentations

import HelperUtils.Broker.BrokerUtils
import HelperUtils.{CreateLogger, ObtainConfigReference}
import org.cloudbus.cloudsim.core.CloudSim

class CreateResourcesFromConfig

object CreateResourcesFromConfig {
  val config = ObtainConfigReference("simulation") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  val logger = CreateLogger(classOf[CreateResourcesFromConfig])
  
  def createResources(): Unit =
    val simulation = new CloudSim()
    val simConfig = config.getConfig("simulation")
    BrokerUtils.createOrgResources(simulation, simConfig)
    simulation.start()
    
}
