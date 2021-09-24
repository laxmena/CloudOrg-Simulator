package HelperUtils.ConfigModels

import scala.beans.BeanProperty
import scala.jdk.CollectionConverters._
/**
 * Case Class for DatacenterConfig Object
 * Object for this instance is created from Config file using
 * the PureConfig library.
 * 
 * Example: 
 * { 
 *   dcType: SAAS
 *   allocationPolicy: Simple
 *   costPerSecond: 0.01
 *   costPerMem: 0.01 
 *   costPerBw: 0.001
 *   costPerStorage: 0.02
 *   schedulingInterval: 1.0
 *   hosts: [{** Refer HostConfig **}]
 *   vms: [{** Refer VmConfig **}, {** Refer VmConfig **}]
 *   cloudlets: {** Refer CloudletConfig **}
 * }
 */
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

  // Getters and Setters methods for custom DataTypes
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
