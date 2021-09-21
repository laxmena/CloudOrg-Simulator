package Experimentations

import HelperUtils.{BrokerUtils, CreateLogger, ObtainConfigReference}

class CreateResourcesFromConfig

object CreateResourcesFromConfig {
  val config = ObtainConfigReference("simulation") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  val logger = CreateLogger(classOf[CreateResourcesFromConfig])
  
  def createResources(): Unit = 
    val simConfig = config.getConfig("simulation")
    BrokerUtils.createDataCenters(simConfig)
    
}
