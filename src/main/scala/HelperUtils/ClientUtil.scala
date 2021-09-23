package HelperUtils

import org.cloudbus.cloudsim.brokers.DatacenterBroker
import org.cloudbus.cloudsim.core.CloudSim
import com.typesafe.config.{Config, ConfigBeanFactory, ConfigList}

class ClientUtil

object ClientUtil {
//  val brokerConfig = ObtainConfigReference("instances") match {
//    case Some(value) => value
//    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
//  }
//  val logger = CreateLogger(classOf[ClientUtil])

  def createClientResources(simulation: CloudSim, broker: DatacenterBroker, config: Config): Unit = {
//    logger.info(s"Creating Client Resources")

  }

}