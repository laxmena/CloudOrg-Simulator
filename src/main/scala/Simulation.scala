import HelperUtils.{CreateLogger, ObtainConfigReference}
import Experimentations.{CreateResourcesFromConfig, LoadConfig}
import Simulations.BasicCloudSimPlusExample
import Simulations.BasicFirstExample
import Simulations.CostExample
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

object Simulation:
  val logger = CreateLogger(classOf[Simulation])

  @main def runSimulation =
    logger.info("Constructing a cloud model...")
    CreateResourcesFromConfig.createResources("simulation")
    logger.info("Finished cloud simulation...")

class Simulation