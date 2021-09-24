import HelperUtils.{CreateLogger, ObtainConfigReference}
import Experimentations.{CreateResourcesFromConfig, LoadConfig}
import Simulations.Simulator
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.slf4j.LoggerFactory
import org.cloudbus.cloudsim.core.CloudSim
import com.typesafe.config.{Config, ConfigFactory}

import scala.jdk.CollectionConverters._
/**
 * Compare VM Allocation Policies with same configrations for VM, Hosts and Cloudlets.
 *
 * BestFit vs RoundRobin vs FirstFit vs Simple.
 *
 * Check the Configuration files in resources:
 *   Bestfit: simulation5/v1.conf
 *   RoundRobin: simulation5/v2.conf
 *   FirstFit: simulation5/v3.conf
 *   Simple: simulation5/v4.conf
 *
 * */
class Simulation5
object Simulation5 {
  val logger = CreateLogger(classOf[Simulation5])
  val config = ObtainConfigReference("simulation5") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the experiment config data.")
  }

  @main def runSimulation5 =
    logger.info("-------- Executing Simulation 5 ---------")
    logger.info("----- Comparing Allocation Policies -----")

    val simulation5 = config.getConfig("simulation5")
    val versions: List[String] =
      simulation5.getStringList("versions").asScala.toList

    logger.info(s"List of Simulation files: ${versions}")

    versions.map(id => {

      logger.info(s"---------- Simulation File: ${id} ----------")
      logger.info(s"Starting Simulation: ${id}")
      val config = simulation5.getConfig(id)
      Simulator.runSimulation(
        config.getConfig("org"),
        config.getConfig("client")
      )
      logger.info(s"-------- Done Simulating File: ${id} -------")
    })

    logger.info("---- Complete Simulation 5 Execution ----")
}

