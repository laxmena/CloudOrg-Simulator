package HelperUtils.ConfigModels

import scala.beans.BeanProperty
import scala.jdk.CollectionConverters.*

case class DataCenterModel(
                            @BeanProperty var costPerSecond: Double,
                            @BeanProperty var costPerMem: Double,
                            @BeanProperty var costPerBw: Double,
                            @BeanProperty var costPerStorage: Double,
                            var hosts: List[HostModel],
                            var vms: List[VmModel]) {

  // TODO: Recheck this during implementation
  def getHosts: java.util.List[HostModel] = hosts.asJava

  def setHosts(hosts: java.util.List[HostModel]): Unit = {
    this.hosts = hosts.asScala.toList
  }

  def getVms: java.util.List[VmModel] = vms.asJava

  def setVms(vms: java.util.List[VmModel]): Unit = {
    this.vms = vms.asScala.toList
  }

  def this() = this(0, 0, 0, 0, Nil, Nil)
}
