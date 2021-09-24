package HelperUtils.ConfigModels

import scala.beans.BeanProperty
import scala.jdk.CollectionConverters._

/**
 * Case Class for IaasClientConfig.
 * This is a Template for the Client Config file for IAAS client request.
 *
 * Example:
 * {
 *   vms: [{** Refer VmConfig **}],
 *   cloudlets: [{** Refer CloudletConfig **}],
 * }
 *
 * Refer VmConfig and CloudletConfig to know
 * the structure of input of those values.
 */
case class IaasClientConfig(var vms: List[VmConfig],
                            var cloudlets: List[CloudletConfig]) {
  def this() = this(Nil, Nil)

  // Getters and Setters for custom parameter
  def getVms: java.util.List[VmConfig] = vms.asJava
  def setVms(vms: java.util.List[VmConfig]): Unit = {
    this.vms = vms.asScala.toList
  }

  // Getters and Setters for custom parameter
  def getCloudlets: java.util.List[CloudletConfig] = cloudlets.asJava
  def setCloudlets(cloudlets: java.util.List[CloudletConfig]): Unit = {
    this.cloudlets = cloudlets.asScala.toList
  }
}
