import HelperUtils.{CreateLogger, ObtainConfigReference}
import Experimentations.{CreateResourcesFromConfig, LoadConfig}
import Simulations.Simulator
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.slf4j.LoggerFactory
import org.cloudbus.cloudsim.core.CloudSim

/**
 * Simulate SAAS DataCenter
 * 
 * Client has control over which Software Service to use, and how many instances.
 * Example: A client can use Google Sheets Software, and he can use 
 * three instances of Google Sheets. 
 * 
 * Check the Configuration files:
 *   Org Config File: simulation3/org.conf
 *   Client Config File: simulation3/client.conf
 *
 * */
class Simulation3
object Simulation3 {
  val logger = CreateLogger(classOf[Simulation3])
  val SIM = "simulation3"
  val config = ObtainConfigReference(SIM) match {
    case Some(value) => value.getConfig(SIM)
    case None => throw new RuntimeException("Cannot obtain a reference to the experiment config data.")
  }

  @main def runSimulation3 =
    logger.info("Simulation 3 - PAAS Model")

    Simulator.runSimulation(
      config.getConfig("org"),
      config.getConfig("client"))

    logger.info("Finished Simulation 3...")
}

