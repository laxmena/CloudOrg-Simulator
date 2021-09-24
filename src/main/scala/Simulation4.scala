import HelperUtils.{CreateLogger, ObtainConfigReference}
import Experimentations.{CreateResourcesFromConfig, LoadConfig}
import Simulations.Simulator
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.slf4j.LoggerFactory
import org.cloudbus.cloudsim.core.CloudSim

/**
 * Simulate SAAS, PAAS and IAAS DataCenters together
 *
 * Simulating an actual cloud organization that provides SAAS, PAAS and
 * IAAS services to the clients.
 *
 * Check the Configuration files:
 *   Org Config File: simulation4/org.conf
 *   Client Config File: simulation4/client.conf
 *
 * */
object Simulation4 {
  val logger = CreateLogger(classOf[Simulation4])
  val SIM = "simulation4"
  val config = ObtainConfigReference(SIM) match {
    case Some(value) => value.getConfig(SIM)
    case None => throw new RuntimeException("Cannot obtain a reference to the experiment config data.")
  }

  @main def runSimulation4 =
    logger.info("Simulation 4 - PAAS Model")

    Simulator.runSimulation(
      config.getConfig("org"),
      config.getConfig("client"))

    logger.info("Finished Simulation 4...")
}

class Simulation4
