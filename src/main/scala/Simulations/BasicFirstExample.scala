//package Simulations
//
//import HelperUtils.{CreateLogger, ObtainConfigReference}
//import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
//import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
//import org.cloudbus.cloudsim.core.CloudSim
//import org.cloudbus.cloudsim.datacenters.{Datacenter, DatacenterSimple}
//import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
//import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
//import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
//import org.cloudbus.cloudsim.vms.{Vm, VmSimple}
//import org.cloudsimplus.builders.tables.CloudletsTableBuilder
//
//import scala.jdk.CollectionConverters.*
//class BasicFirstExample
//
//object BasicFirstExample {
//  val config = ObtainConfigReference("implementation-test") match {
//    case Some(value) => value
//    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
//  }
//  val logger = CreateLogger(classOf[BasicFirstExample])
//  val utilizationModel: UtilizationModelDynamic = new UtilizationModelDynamic(config.getDouble("basicFirstExample.utilizationRatio"))
//
//  def Start() =
//    val cloudsim = new CloudSim();
//    val datacenter0 = createDataCenter(cloudsim);
//    val datacenter1 = createDataCenter(cloudsim);
//    val broker0 = new DatacenterBrokerSimple(cloudsim)
//    val vmList = createVms(config.getLong("basicFirstExample.vm.count"))
//    val cloudletList = createCloudlets(config.getLong("basicFirstExample.cloudlet.count"))
//    broker0.submitVmList(vmList.asJava)
//    broker0.submitCloudletList(cloudletList.asJava)
//
//    cloudsim.start()
//    val finishedCloudlets = broker0.getCloudletFinishedList()
//    new CloudletsTableBuilder(finishedCloudlets).build()
//
//  def createDataCenter(cloudsim: CloudSim): Datacenter =
//    val hostList = getHostList(config.getLong("basicFirstExample.host.count"))
//    new DatacenterSimple(cloudsim, hostList.asJava)
//
//  def getHostList(count: Long): List[Host] =
//    if(count < 1) return Nil
//    val peList: List[Pe] = createPe(config.getLong("basicFirstExample.host.pes"))
//
//    new HostSimple(config.getLong("basicFirstExample.host.RAMInMBs"),
//      config.getLong("basicFirstExample.host.BandwidthInMBps"),
//      config.getLong("basicFirstExample.host.StorageInMBs"),
//      peList.asJava) :: getHostList(count - 1)
//
//  def createPe(count: Long): List[Pe] =
//    if(count < 1) return Nil
//    val pe = new PeSimple(config.getLong("basicFirstExample.host.mipsCapacity")) // HOST_MIPS
//    return pe :: createPe(count - 1)
//
//  def createVms(count: Long): List[Vm] =
//    if(count < 1) return Nil
//    val vm = new VmSimple(config.getLong("basicFirstExample.vm.mipsCapacity"), config.getLong("basicFirstExample.vm.pes"))
//    vm.setRam(config.getLong("basicFirstExample.vm.RAMInMBs"))
//      .setBw(config.getLong("basicFirstExample.vm.BandwidthInMBps"))
//      .setSize(config.getLong("basicFirstExample.vm.StorageInMBs")) :: createVms(count - 1)
//
//  def createCloudlets(count: Long): List[Cloudlet] =
//    if(count < 1) return Nil
//    val cloudlet = new CloudletSimple(config.getLong("basicFirstExample.cloudlet.length"), config.getInt("basicFirstExample.cloudlet.pes"), utilizationModel)
//    cloudlet.setSizes(config.getLong("basicFirstExample.cloudlet.size"))
//    cloudlet :: createCloudlets(count - 1)
//}
