import Experimentations.{CreateResourcesFromConfig, LoadConfig}
import HelperUtils.{CreateLogger, ObtainConfigReference}
import Simulations.Simulator
import com.typesafe.config.{Config, ConfigFactory}
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.slf4j.LoggerFactory

import scala.jdk.CollectionConverters.*

/**
 * Compare VmScheduling Policies with same configrations for VM, Hosts and Cloudlets.
 *
 * TimeShared vs SpaceShared vs Combination of TimeShared & SpaceShared.
 *
 * Check the Configuration files:
 *   TimeShared: simulation6/time.conf
 *   SpaceShared: simulation6/space.conf
 *   Time and Space Shared: simulation6/timeandspace.conf
 *
 * */
object Simulation6 {
  val logger = CreateLogger(classOf[Simulation])
  val config = ObtainConfigReference("simulation6") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the experiment config data.")
  }

  @main def runSimulation6 =
    logger.info("-------- Executing Simulation 6 ---------")
    logger.info("----- Comparing Scheduling Policies -----")

    val simulation6 = config.getConfig("simulation6")
    val versions: List[String] =
      simulation6.getStringList("versions").asScala.toList

    logger.info(s"List of Simulation files: ${versions}")

    versions.map(id => {

      logger.info(s"---------- Simulation File: ${id} ----------")
      logger.info(s"Starting Simulation: ${id}")
      val config = simulation6.getConfig(id)
      Simulator.runSimulation(
        config.getConfig("org"),
        config.getConfig("client")
      )
      logger.info(s"-------- Done Simulating File: ${id} -------")
    })

    logger.info("---- Complete Simulation 6 Execution ----")
}

class Simulation6
