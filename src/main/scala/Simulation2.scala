import HelperUtils.{CreateLogger, ObtainConfigReference}
import Experimentations.{CreateResourcesFromConfig, LoadConfig}
import Simulations.Simulator
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.slf4j.LoggerFactory
import org.cloudbus.cloudsim.core.CloudSim

/**
 * Simulate PAAS DataCenter
 *
 * Implementated: Client has contorl over the cloudlets configrations.
 * Example: If client has a PHP Software and has to deploy that software on a
 * PAAS Server, he will send his software code to the Cloud Organization
 * to run on LAMP stack.
 *
 * Check the Configuration files:
 *   Org Config File: simulation2/org.conf
 *   Client Config File: simulation2/client.conf
 *
 * */
object Simulation2 {
  val logger = CreateLogger(classOf[Simulation2])
  val SIM = "simulation2"
  val config = ObtainConfigReference(SIM) match {
    case Some(value) => value.getConfig(SIM)
    case None => throw new RuntimeException("Cannot obtain a reference to the experiment config data.")
  }

  @main def runSimulation2 =
    logger.info("Simulation 2 - PAAS Model")

    Simulator.runSimulation(
      config.getConfig("org"),
      config.getConfig("client"))

    logger.info("Finished Simulation 2...")
}

class Simulation2
