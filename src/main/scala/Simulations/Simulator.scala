package Simulations

import HelperUtils.{ClientUtil, CommonUtil, ObtainConfigReference, OrgUtils}
import com.typesafe.config.Config
import org.cloudbus.cloudsim.brokers.{DatacenterBroker, DatacenterBrokerSimple}
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core
import org.cloudbus.cloudsim.core.events.SimEvent
import org.cloudbus.cloudsim.core.{CloudSim, SimEntity}
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.cloudbus.cloudsim.vms.Vm
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.cloudsimplus.listeners.{DatacenterBrokerEventInfo, EventInfo, EventListener}

import java.{lang, util}
import java.util.{Comparator, function}
import java.util.function.BiFunction

/**
 * Simulator helps abstracting Simulation execution sequence by
 * providing helper methods.
 *
 * Only the configuration files needs to be
 */
object Simulator {

  /**
   * Helper method for simulation. Runs simulation, prints summary of simulation tasks
   * and computes total cost.
   *
   * @param simulation CloudSim instance for the simulation
   * @param broker DatacenterBroker instance for the simulation
   * @param orgConfigStr String Identifier of the Organization Config
   * @param clientConfigStr String Identifier of the Client Config
   *
   * */
  def runSimulation(simulation: CloudSim,
                    broker: DatacenterBroker,
                    orgConfigStr: String,
                    clientConfigStr: String): Unit = {
    // Load Org Config
    val orgConfig = ObtainConfigReference(orgConfigStr) match {
      case Some(config) => config.getConfig(orgConfigStr)
      case None => throw new RuntimeException(s"Cannot obtain a reference to the Org config data. ${orgConfigStr}")
    }

    // Load Client Config
    val clientConfig = ObtainConfigReference(clientConfigStr) match {
      case Some(config) => config.getConfig(clientConfigStr)
      case None => throw new RuntimeException(s"Cannot obtain a reference to the Client config data. ${clientConfigStr}")
    }

    runSimulation(simulation, broker, orgConfig, clientConfig)
  }

  /**
   * Runs simulation based on the Organization and Client Config's
   * provided as input parameters, along with CloudSim object and DatacenterBroker.
   *
   * @param simulation CloudSim instance for the simulation
   * @param broker DatacenterBroker instance for the simulation
   * @param orgConfig Identifier of the Organization Config to simulate
   * @param clientConfig Identifier of the Client Config to simulate
   *
   * */
  def runSimulation(simulation: CloudSim,
                    broker: DatacenterBroker,
                    orgConfig: Config,
                    clientConfig: Config): Unit = {
    // Create Resources from Config
    OrgUtils.createOrgResources(simulation, broker, orgConfig)
    ClientUtil.createClientResources(broker, clientConfig)

    simulation.start()

    // Display summary of completed cloudlets
    val finishedCloudlets = broker.getCloudletFinishedList()
    new CloudletsTableBuilder(finishedCloudlets).build()

    // Compute Cost
    CommonUtil.logTotalVmsCost(broker)

  }

  /**
   * Runs simulation based on the Organization and Client Config's as input parameters.
   * Creates its own CloudSim Simulation object and DatacenterBrokerSimple object.
   *
   * @param simulation CloudSim instance for the simulation
   * @param broker DatacenterBroker instance for the simulation
   * @param orgConfig Identifier of the Organization Config to simulate
   * @param clientConfig Identifier of the Client Config to simulate
   *
   * */
  def runSimulation(orgConfigStr: String,
                    clientConfigStr: String): Unit = {
    // Load Org Config
    val orgConfig = ObtainConfigReference(orgConfigStr) match {
      case Some(config) => config.getConfig(orgConfigStr)
      case None => throw new RuntimeException(s"Cannot obtain a reference to the Org config data. ${orgConfigStr}")
    }

    // Load Client Config
    val clientConfig = ObtainConfigReference(clientConfigStr) match {
      case Some(config) => config.getConfig(clientConfigStr)
      case None => throw new RuntimeException(s"Cannot obtain a reference to the Client config data. ${clientConfigStr}")
    }

    // Create Simulation and DatacenterBroker object
    val simulation = new CloudSim()
    val broker = new DatacenterBrokerSimple(simulation)

    runSimulation(simulation, broker, orgConfig, clientConfig)
  }
}
