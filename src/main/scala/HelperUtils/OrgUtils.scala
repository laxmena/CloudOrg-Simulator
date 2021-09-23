package HelperUtils

import HelperUtils.ConfigModels.{DataCenterConfig, HostConfig, VmConfig}
import HelperUtils.{CommonUtil, CreateLogger, ObtainConfigReference}
import Simulations.BasicFirstExample
import com.typesafe.config.{Config, ConfigBeanFactory, ConfigList}
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicyBestFit, VmAllocationPolicyFirstFit, VmAllocationPolicyRoundRobin, VmAllocationPolicySimple}
import org.cloudbus.cloudsim.brokers.{DatacenterBroker, DatacenterBrokerSimple}
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.cloudbus.cloudsim.datacenters.network.NetworkDatacenter
import org.cloudbus.cloudsim.hosts.HostSimple
import org.cloudbus.cloudsim.hosts.network.NetworkHost
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.schedulers.vm.{VmScheduler, VmSchedulerSpaceShared, VmSchedulerTimeShared}
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}

import scala.jdk.CollectionConverters.*

class OrgUtils

object OrgUtils {
  val brokerConfig = ObtainConfigReference("instances") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  val logger = CreateLogger(classOf[OrgUtils])

  /**
   * Create Organization Resources from Config Files
   *
   * @param config Configuration files that specifies the Organization
   *               Infrastructure.
   * */
  def createOrgResources(simulation: CloudSim, broker: DatacenterBroker,  config: Config) = {
    logger.info("Logging: ", config)
    val datacenters = laodDCsFromConfig(simulation, broker, config)
    broker.setVmDestructionDelay(20.0)

    logger.info(s"Total DataCenters: ${datacenters}")
  }

  /**
   * Loads DataCenters from Config
   *
   * @param config List of Configuration objects, where each object specify
   *               the specification of the DataCenters, with associatated
   *               hosts and vms.
   * */
  def laodDCsFromConfig(simulation: CloudSim, broker: DatacenterBroker, config: Config) = {
    logger.info(s"Loading DataCenters from Config")
    val configList = config.getConfigList("datacenters").asScala
    logger.info(s"Total DataCenters Count: ${configList.length}")

    val datacenters = configList.map(dcConfig => {
      val dcType: String = dcConfig.getString("dcType")
      logger.info(s"Loading $dcType Datacenter")
      createDataCenter(
        simulation,
        broker,
        ConfigBeanFactory.create(dcConfig, classOf[DataCenterConfig])
      )
    }).toList

    datacenters
  }

  /**
   * Creates NetworkDataCenter Objects for the given Cofiguration
   *
   * @param simulation CloudSim object
   * @param dc DataCenter Configuration Object
   * @return DataCenter NetworkDataCenter object created with provided config
   *
   * */
  def createDataCenter(simulation: CloudSim, broker: DatacenterBroker, dc: DataCenterConfig): Datacenter = {
    logger.info("Configuring DataCenter")

    val hostList: List[NetworkHost] = configureHosts(dc.hosts)
    logger.info(s"Number of Hosts assigned: ${hostList.length}")

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

    val datacenter = new NetworkDatacenter(simulation, hostList.asJava, allocationPolicy)
    datacenter.setSchedulingInterval(dc.schedulingInterval)

    datacenter
      .getCharacteristics
      .setCostPerBw(dc.costPerBw)
      .setCostPerMem(dc.costPerMem)
      .setCostPerStorage(dc.costPerStorage)
      .setCostPerSecond(dc.costPerSecond)
    logger.info("Setting Costs for Bandwidth, Storage, Memory and Per-Second")

    dc.dcType match {
      case "IAAS" => Nil
      case "PAAS" => {
        val vmList: List[Vm] =
          dc.vms.flatMap(vmConfig => CommonUtil.configureVms(vmConfig))
        if(vmList.length != 0) {
          broker.submitVmList(vmList.asJava)
          logger.info(s"Broker Submitted ${vmList.length} VMs")
        }
      }
      case "SAAS" => {
        val vmList: List[Vm] =
          dc.vms.flatMap(vmConfig => CommonUtil.configureVms(vmConfig))
        if(vmList.length != 0) {
          broker.submitVmList(vmList.asJava)
          logger.info(s"Broker Submitted ${vmList.length} VMs")
        }
        val cloudletList: List[Cloudlet] = CommonUtil.configureCloudlets(dc.cloudlets)
        if(cloudletList.length != 0) {
          broker.submitCloudletList(cloudletList.asJava)
          logger.info(s"Broker submitted ${cloudletList.length} Cloudlets")
          logger.info(s"${cloudletList.head}")
        }
      }
    }

    datacenter
  }

  /**
   * Create List of NetworkHosts for the Given Host Configurations
   *
   * @param hostConfigs: List of HostConfig objects
   * @return List[NetworkHost]: List of Network Hosts for the corresponding
   *         HostConfig Objects
   * */
  def configureHosts(hostConfigs: List[HostConfig]): List[NetworkHost] = {
    logger.info(s"Received List of ${hostConfigs.length} Hosts")
    hostConfigs.map(config => configureSingleHost(config)).toList
  }

  /**
   * Creates Single Network Host from the HostConfig object
   *
   * @param hostConfig HostConfig instance
   * @return NetworkHost NetworkHost object with configurations from
   *         HostConfig object
   * */
  def configureSingleHost(hostConfig: HostConfig): NetworkHost = {
    val pes = (1 to hostConfig.pes).map { _ => CommonUtil.configurePe(hostConfig.mips) }.toList
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

}
