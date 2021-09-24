package Experimentations

import HelperUtils.{CreateLogger, ObtainConfigReference}
import org.cloudbus.cloudsim.brokers.{DatacenterBroker, DatacenterBrokerSimple}
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.{Datacenter, DatacenterSimple}
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
import org.cloudbus.cloudsim.vms.{Vm, VmCost, VmSimple}
import org.cloudsimplus.builders.tables.CloudletsTableBuilder

import java.util.Comparator
import scala.jdk.CollectionConverters.*

class CostExample

object CostExample {
  val config = ObtainConfigReference("costExample") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  val logger = CreateLogger(classOf[CostExample])
  val utilizationModel: UtilizationModelDynamic = new UtilizationModelDynamic(config.getDouble("costExample.utilizationRatio"))

  def Start() = {
    val cloudsim = new CloudSim()
    val broker = new DatacenterBrokerSimple(cloudsim)
    val datacenter = createDataCenter(cloudsim)
    broker.setVmDestructionDelay(0.2)

    val vmList = createVms(config.getLong("costExample.vm.count"))
    broker.submitVmList(vmList.asJava)

    val cloudletList = createCloudlets(config.getLong("costExample.cloudlet.count"))
    broker.submitCloudletList(cloudletList.asJava)

    cloudsim.start()

    val finishedCloudlets = broker.getCloudletFinishedList()
    new CloudletsTableBuilder(finishedCloudlets).build()
    printTotalVmsCost(broker, datacenter)
  }

  def printTotalVmsCost(broker: DatacenterBroker, datacenter: Datacenter) = {
    val vmCreatedList: List[Vm] = broker.getVmCreatedList().asScala.toList

    def computeTotalCost(f: (VmCost) => Double, vmList: List[Vm]): Double = {
      def compute(f: (VmCost) => Double, vmList: List[Vm], sum: Double): Double =
        return vmList match {
          case vm :: tail => compute(f, tail, sum + f(VmCost(vm)))
          case _ => sum
        }

      compute(f, vmList, 0)
  }
    def computeNonIdleVms(vmList: List[Vm]): Int = {
      def compute(vmList: List[Vm], count: Int): Int = {
        vmList match {
          case vm :: tail => compute(tail, if (vm.getTotalExecutionTime() > 0) count + 1 else count)
          case _ => count
        }
      }
      compute(vmList, 0)
    }
    val processingTotalCost: Double = computeTotalCost(x => x.getProcessingCost(), vmCreatedList)
    val memoryTotalCost = computeTotalCost(x => x.getMemoryCost(), vmCreatedList)
    val storageTotalCost = computeTotalCost(x => x.getStorageCost(), vmCreatedList)
    val bwTotalCost = computeTotalCost(x => x.getBwCost(), vmCreatedList)
    val totalCost = computeTotalCost(x => x.getTotalCost(), vmCreatedList)
    val nonIdleVms = computeNonIdleVms(vmCreatedList)

    println(f"Total cost ($$) for $nonIdleVms%3d created VMs from ${broker.getVmsNumber()}%3d in DC " +
      f"${datacenter.getId()}: $processingTotalCost%8.2f $$ $memoryTotalCost%13.2f $$ " +
      f"$storageTotalCost%17.2f $$ $bwTotalCost%12.2f $$ $totalCost%15.2f $$%n")
  }

  def createDataCenter(cloudsim: CloudSim): Datacenter = {
    val hostList = getHostList(config.getLong("costExample.host.count"))
    val dc = new DatacenterSimple(cloudsim, hostList.asJava)
      .setSchedulingInterval(config.getDouble("costExample.schedulingInterval"))
    dc.getCharacteristics()
      .setCostPerSecond(0.01)
      .setCostPerMem(0.02)
      .setCostPerBw(0.005)
      .setCostPerStorage(0.001)
    dc
  }
  def getHostList(count: Long): List[Host] = {
    if(count < 1) return Nil

    val peList: List[Pe] = createPe(config.getLong("costExample.host.pes"))

    new HostSimple(config.getLong("costExample.host.RAMInMBs"),
      config.getLong("costExample.host.BandwidthInMBps"),
      config.getLong("costExample.host.StorageInMBs"),
      peList.asJava) :: getHostList(count - 1)
  }

  def createPe(count: Long): List[Pe] = {
    if (count < 1) return Nil
    val pe = new PeSimple(config.getLong("costExample.host.mipsCapacity")) // HOST_MIPS
    return pe :: createPe(count - 1)
  }

  def createVms(count: Long): List[Vm] = {
    if (count < 1) return Nil
    val vm = new VmSimple(config.getLong("costExample.vm.mipsCapacity"), config.getLong("costExample.vm.pes"))
    vm.setRam(config.getLong("costExample.vm.RAMInMBs"))
      .setBw(config.getLong("costExample.vm.BandwidthInMBps"))
      .setSize(config.getLong("costExample.vm.StorageInMBs")) :: createVms(count - 1)
  }
  def createCloudlets(count: Long): List[Cloudlet] = {
    if (count < 1) return Nil
    val cloudlet = new CloudletSimple(config.getLong("costExample.cloudlet.length"),
      config.getInt("costExample.cloudlet.pes"),
      utilizationModel)
    cloudlet.setSizes(config.getLong("costExample.cloudlet.size"))
    cloudlet :: createCloudlets(count - 1)
  }
}
