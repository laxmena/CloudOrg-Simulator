package HelperUtils

import HelperUtils.ConfigModels.{CloudletConfig, VmConfig}
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.vms.Vm
import org.cloudbus.cloudsim.vms.network.NetworkVm
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.cloudlets.network.NetworkCloudlet
import org.cloudbus.cloudsim.utilizationmodels.{UtilizationModel, UtilizationModelDynamic}

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

    // TODO: Replace strings with constants
    val instanceConfig = instances.getConfig(instanceType)
    val vmList = (1 to count).map( _ => {
      val vm = new NetworkVm(instanceConfig.getLong("mips"),
        instanceConfig.getInt("pes"))
      vm.setRam(instanceConfig.getLong("ram"))
        .setBw(instanceConfig.getLong("bw"))
        .setSize(instanceConfig.getLong("size"))
      vm
    }).toList
    vmList
  }
  
  def configureCloudlet(config: CloudletConfig): List[Cloudlet] = {
    (1 to config.count).map(_ => {
      val cloudlet = new CloudletSimple(config.length, config.pes)
      cloudlet.setSizes(config.size)
      cloudlet
    }).toList
  }
  
  def configureMultipleCloudlets(config: List[CloudletConfig]): List[Cloudlet] = {
    logger.info("Configuring Cloudlets")
//    val utilizationModel: UtilizationModel = new UtilizationModel()
    config.map(cloudletConfig => configureCloudlet(cloudletConfig)).flatten
  }


}
