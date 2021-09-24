import HelperUtils.{CreateLogger, ObtainConfigReference}
import Experimentations.{CreateResourcesFromConfig, LoadConfig}
import Simulations.Simulator
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.slf4j.LoggerFactory
import org.cloudbus.cloudsim.core.CloudSim

/**
 * Simulate IAAS DataCenter
 *
 * Client has control over VMs and the Cloudlets to be deployed on the machine.
 * Example: A client can create one or multiple VMs and use it flexibly based
 * on their requirements.
 *
 * Check the Configuration files:
 *   Org Config File: simulation1/org.conf
 *   Client Config File: simulation1/client.conf
 *
 * */
class Simulation1
object Simulation1 {
  val logger = CreateLogger(classOf[Simulation1])
  val SIM = "simulation1"
  val config = ObtainConfigReference(SIM) match {
    case Some(value) => value.getConfig(SIM)
    case None => throw new RuntimeException("Cannot obtain a reference to the experiment config data.")
  }
  @main def runSimulation1 =
    logger.info("Simulation 1 - IAAS Model")

    Simulator.runSimulation(
      config.getConfig("org"),
      config.getConfig("client"))

    logger.info("Finished Simulation 1...")
}

