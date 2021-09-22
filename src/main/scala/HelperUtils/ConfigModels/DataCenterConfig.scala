package HelperUtils.ConfigModels

import scala.beans.BeanProperty
import scala.jdk.CollectionConverters.*

case class DataCenterConfig(@BeanProperty var dcType: String,
                            @BeanProperty var allocationPolicy: String,
                            @BeanProperty var costPerSecond: Double,
                            @BeanProperty var costPerMem: Double,
                            @BeanProperty var costPerBw: Double,
                            @BeanProperty var costPerStorage: Double,
                            var hosts: List[HostConfig],
                            var vms: List[VmConfig]) {

  // TODO: Recheck this during implementation
  def getHosts: java.util.List[HostConfig] = hosts.asJava

  def setHosts(hosts: java.util.List[HostConfig]): Unit = {
    this.hosts = hosts.asScala.toList
  }

  def getVms: java.util.List[VmConfig] = vms.asJava

  def setVms(vms: java.util.List[VmConfig]): Unit = {
    this.vms = vms.asScala.toList
  }

  def this() = this("", "", 0, 0, 0, 0, Nil, Nil)
}
