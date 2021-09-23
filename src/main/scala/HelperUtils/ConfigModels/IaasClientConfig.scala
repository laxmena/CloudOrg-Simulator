package HelperUtils.ConfigModels

import scala.beans.BeanProperty
import scala.jdk.CollectionConverters.*

case class IaasClientConfig(var vms: List[VmConfig],
                            var cloudlets: List[CloudletConfig]) {
  def this() = this(Nil, Nil)

  def getVms: java.util.List[VmConfig] = vms.asJava
  def setVms(vms: java.util.List[VmConfig]): Unit = {
    this.vms = vms.asScala.toList
  }

  def getCloudlets: java.util.List[CloudletConfig] = cloudlets.asJava
  def setCloudlets(cloudlets: java.util.List[CloudletConfig]): Unit = {
    this.cloudlets = cloudlets.asScala.toList
  }
}
