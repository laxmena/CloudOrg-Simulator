package HelperUtils.Broker

import HelperUtils.ConfigModels.{DataCenterConfig, HostConfig, VmConfig}
import HelperUtils.{CreateLogger, ObtainConfigReference}
import Simulations.BasicFirstExample
import com.typesafe.config.{Config, ConfigBeanFactory, ConfigList}
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.cloudbus.cloudsim.hosts.HostSimple
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicyBestFit, VmAllocationPolicyFirstFit, VmAllocationPolicyRoundRobin, VmAllocationPolicySimple}
import org.cloudbus.cloudsim.datacenters.network.NetworkDatacenter
import org.cloudbus.cloudsim.hosts.network.NetworkHost
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.schedulers.vm.{VmScheduler, VmSchedulerSpaceShared, VmSchedulerTimeShared}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}

import scala.jdk.CollectionConverters.*

class BrokerUtils

object BrokerUtils {
  val brokerConfig = ObtainConfigReference("instances") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  val logger = CreateLogger(classOf[BrokerUtils])

  /**
   * Create Organization Resources from Config Files
   *
   * @param config Configuration files that specifies the Organization
   *               Infrastructure.
   * */
  def createOrgResources(simulation: CloudSim, config: Config) = {
    logger.info("Logging: ", config)
    val datacenters = laodDCsFromConfig(simulation, config)

    logger.info(s"Total DataCenters: ${datacenters}")
  }

  /**
   * Loads DataCenters from Config
   *
   * @param config List of Configuration objects, where each object specify
   *               the specification of the DataCenters, with associatated
   *               hosts and vms.
   * */
  def laodDCsFromConfig(simulation: CloudSim, config: Config) = {
    logger.info(s"Loading DataCenters from Config")
    val configList = config.getConfigList("datacenters").asScala
    logger.info(s"Total DataCenters Count: ${configList.length}")

    val datacenters = configList.map(dcConfig => {
      val dcType: String = dcConfig.getString("dcType")
      logger.info(s"Loading $dcType Datacenter")
      configureDataCenter(
        simulation,
        ConfigBeanFactory.create(dcConfig, classOf[DataCenterConfig])
      )
    }).toList
    datacenters
  }

  def configureDataCenter(simulation: CloudSim, dc: DataCenterConfig): Datacenter = {
    logger.info("Configuring DataCenter")

    val hosts: List[NetworkHost] = configureHosts(dc.hosts)
    val vmList: List[Vm] = dc.vms.flatMap(vmConfig => configureVms(vmConfig))

    logger.info(s"Number of Hosts assigned: ${hosts.length}")
    logger.info(s"Number of VM's configured: ${vmList.length}")

    val allocationPolicy = {
      dc.allocationPolicy match {
        case "Simple" => new VmAllocationPolicySimple()
        case "RoundRobin" => new VmAllocationPolicyRoundRobin()
        case "BestFit" => new VmAllocationPolicyBestFit()
        case "FirstFit" => new VmAllocationPolicyFirstFit()
        case _ => {
          logger.info("Unknown allocation Policy. Setting Default.")
          new VmAllocationPolicyBestFit()
        }
      }
    }
    logger.info(s"VM Allocation Policy: ${dc.allocationPolicy}")

    val datacenter = new NetworkDatacenter(simulation, hosts.asJava, allocationPolicy)

    datacenter
      .getCharacteristics
      .setCostPerBw(dc.costPerBw)
      .setCostPerMem(dc.costPerMem)
      .setCostPerStorage(dc.costPerStorage)
      .setCostPerSecond(dc.costPerSecond)
    logger.info("Setting Costs for Bandwidth, Storage, Memory and Per-Second")

    datacenter
  }

  def configureHosts(hostConfigs: List[HostConfig]): List[NetworkHost] = {
    logger.info(s"Received List of ${hostConfigs.length} Hosts")
    hostConfigs.map(config => configureSingleHost(config)).toList
  }

  def configureSingleHost(hostConfig: HostConfig): NetworkHost = {
    val pes = (1 to hostConfig.pes).map { _ => configurePeSimple(hostConfig.mips) }.toList
    val host = new NetworkHost(hostConfig.ram, hostConfig.bandwidth, hostConfig.storage, pes.asJava)
    val vmScheduler = hostConfig.vmScheduler match {
      case "TimeShared" => new VmSchedulerTimeShared()
      case "SpaceShared" => new VmSchedulerSpaceShared()
      case _ => new VmSchedulerTimeShared()
    }
    host.setVmScheduler(vmScheduler)
    logger.info(s"Configuring Host with ${hostConfig.vmScheduler} Scheduling Policy")
    host
  }

  def configurePeSimple(mips: Double): Pe = {
    new PeSimple(mips).asInstanceOf[Pe]
  }

  def configureVms(config: VmConfig): List[Vm] = {
    val instances = brokerConfig.getConfig("instances")
    val instanceType = config.instanceType
    
    val count = config.count
    val instanceConfig = instances.getConfig(instanceType)
    val vmList = (1 to count).map( _ => {
      val vm = new VmSimple(instanceConfig.getLong("mips"),
                            instanceConfig.getLong("pes"))
      vm.setRam(instanceConfig.getLong("ram"))
        .setBw(instanceConfig.getLong("bw"))
        .setSize(instanceConfig.getLong("size"))
      vm
    }).toList
    vmList
  }
}
