package HelperUtils

import HelperUtils.ConfigModels.{CloudletConfig, VmConfig}
import org.cloudbus.cloudsim.brokers.DatacenterBroker
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.vms.{Vm, VmCost}
import org.cloudbus.cloudsim.vms.network.NetworkVm
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.cloudlets.network.NetworkCloudlet
import org.cloudbus.cloudsim.datacenters.Datacenter
import org.cloudbus.cloudsim.utilizationmodels.{UtilizationModel, UtilizationModelDynamic}

import scala.jdk.CollectionConverters._

class CommonUtil

object CommonUtil {
  val brokerConfig = ObtainConfigReference("instances") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  val logger = CreateLogger(classOf[OrgUtils])

  /**
   * Create Pe (CPU) instance
   *
   * @param mips Million Instructions per second
   * @return Pe PeSimple instance with provided mips value
   * */
  def configurePe(mips: Double): Pe = {
    logger.debug("Creating PE")
    new PeSimple(mips).asInstanceOf[Pe]
  }

  /**
   * Configures multiple VM's from list of VmConfig objects
   *
   * @param configList List of VmConfig Objects
   * @result List[Vm] List of Created VM Objects
   * */
  def configureMultipleVms(configList: List[VmConfig]): List[Vm] = {
    configList.map(config => configureVms(config)).flatten
  }

  /**
   * Configures List of VM instances from VmConfig object
   *
   * @param config instance of VmConfig class with number of VMs and instance types.
   * @return List[Vm] List of VMs created based on VmConfig parameter.
   *
   * */
  def configureVms(config: VmConfig): List[Vm] = {
    val instances = brokerConfig.getConfig("instances")
    val instanceType = config.instanceType
    val count = config.count
    logger.info(s"Configuring $count VMs of instance type: $instanceType")

    val instanceConfig = instances.getConfig(instanceType)
    val vmList = (1 to count).map(_ => {
      val vm = new NetworkVm(instanceConfig.getLong("mips"),
        instanceConfig.getInt("pes"))
      vm.setRam(instanceConfig.getLong("ram"))
        .setBw(instanceConfig.getLong("bw"))
        .setSize(instanceConfig.getLong("size"))
      vm
    }).toList
    vmList
  }

  /**
   * Creates Cloudlets from single CloudletConfig
   *
   * @param config CloudletConfig instance that specifies number of cloudlets, length and PEs
   * @return List[Cloudlet] Returns List of Cloudlets
   * */
  def configureCloudlet(config: CloudletConfig): List[Cloudlet] = {
    (1 to config.count).map(_ => {
      val cloudlet = new CloudletSimple(config.length, config.pes)
      cloudlet.setSizes(config.size)
      cloudlet
    }).toList
  }

  /**
   * Creates Cloudlets with Utilization Ration from single CloudletConfig
   *
   * @param config CloudletConfig instance that specifies number of cloudlets, length and PEs
   * @param uRatio Utilization Ratio
   * @return List[Cloudlet] Returns List of Cloudlets
   * */
  def configureCloudletWithURatio(config: CloudletConfig, uRatio: Double): List[Cloudlet] = {
    (1 to config.count).map(_ => {
      val cloudlet = new CloudletSimple(config.length, config.pes)
      cloudlet.setSizes(config.size)
      cloudlet.setUtilizationModel(new UtilizationModelDynamic(uRatio))
      cloudlet
    }).toList
  }

  /**
   * Creates Cloudlets from a List of CloudletConfig
   *
   * @param config List of CloudletConfig objects
   * @return List[Cloudlet]
   * */
  def configureMultipleCloudlets(config: List[CloudletConfig]): List[Cloudlet] = {
    logger.info("Configuring Cloudlets")
    config.map(cloudletConfig => {
      configureCloudlet(cloudletConfig)
    }).flatten
  }

  /**
   * Creates Cloudlets with Utilization Ratio from a List of CloudletConfig
   *
   * @param config List of CloudletConfig objects
   * @param uRatio Utilization Ratio
   * @return List[Cloudlet]
   * */
  def configureMultipleCloudletsWithURatio(config: List[CloudletConfig], uRatio: Double): List[Cloudlet] = {
    config.map(cloudletConfig => {
      configureCloudletWithURatio(cloudletConfig, uRatio)
    }).flatten
  }

  /**
   * Computes and Logs Total Cost for the VMs and cloudlets managed by the broker.
   *
   * @param broker DataCenter Broker used in the Simulation
   * */
  def logTotalVmsCost(broker: DatacenterBroker): Double = {
    val vmCreatedList: List[Vm] = broker.getVmCreatedList().asScala.toList

    // Helper method to compute cost. Makes use of Scala's Higher Order Function
    // priciple to compute different types of costs using the same logic
    def computeCostSum(f: (VmCost) => Double): Double = {
      def compute(f: (VmCost) => Double, vmList: List[Vm], sum: Double): Double =
        return vmList match {
          case vm :: tail => compute(f, tail, sum + f(VmCost(vm)))
          case _ => sum
        }
      compute(f, vmCreatedList, 0)
    }

    // Helper method. Iterates through the list and finds number of Non Idle VMs
    def computeNonIdleVms(): Int = {
      def compute(vmCreatedList: List[Vm], count: Int): Int = {
        vmCreatedList match {
          case vm :: tail => compute(tail, if (vm.getTotalExecutionTime() > 0) count + 1 else count)
          case _ => count
        }
      }
      compute(vmCreatedList, 0)
    }

    // Compute Different Costs. Uses higher order functions with Lambda.
    val processingTotalCost: Double = computeCostSum(x => x.getProcessingCost())
    val memoryTotalCost = computeCostSum(x => x.getMemoryCost())
    val storageTotalCost = computeCostSum(x => x.getStorageCost())
    val bwTotalCost = computeCostSum(x => x.getBwCost())
    val totalCost = computeCostSum(x => x.getTotalCost())
    val nonIdleVms = computeNonIdleVms()

    logger.info(f"Total cost ($$) for $nonIdleVms%3d created VMs from ${broker.getVmsNumber()}%3d " +
      f": $processingTotalCost%8.2f $$ $memoryTotalCost%13.2f $$ " +
      f"$storageTotalCost%17.2f $$ $bwTotalCost%12.2f $$ $totalCost%15.2f $$%n")

    // Return totalCost for Unit testing
    totalCost
  }
}
