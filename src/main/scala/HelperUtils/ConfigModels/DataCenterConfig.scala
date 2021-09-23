package HelperUtils.ConfigModels

import scala.beans.BeanProperty
import scala.jdk.CollectionConverters.*

case class DataCenterConfig(@BeanProperty var dcType: String,
                            @BeanProperty var allocationPolicy: String,
                            @BeanProperty var costPerSecond: Double,
                            @BeanProperty var costPerMem: Double,
                            @BeanProperty var costPerBw: Double,
                            @BeanProperty var costPerStorage: Double,
                            @BeanProperty var schedulingInterval: Double,
                            var hosts: List[HostConfig],
                            var vms: List[VmConfig],
                            var cloudlets: List[CloudletConfig]) {

  // TODO: Recheck this during implementation
  def getHosts: java.util.List[HostConfig] = hosts.asJava

  def setHosts(hosts: java.util.List[HostConfig]): Unit = {
    this.hosts = hosts.asScala.toList
  }

  def getVms: java.util.List[VmConfig] = vms.asJava

  def setVms(vms: java.util.List[VmConfig]): Unit = {
    this.vms = vms.asScala.toList
  }

  def getCloudlets: java.util.List[CloudletConfig] = cloudlets.asJava

  def setCloudlets(cloudlets: java.util.List[CloudletConfig]): Unit = {
    this.cloudlets = cloudlets.asScala.toList
  }
  def this() = this("", "", 0, 0, 0, 0, 0, Nil, Nil, Nil)
}
