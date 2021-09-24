package HelperUtils.ConfigModels

import scala.beans.BeanProperty
import scala.jdk.CollectionConverters._

/**
 * Case Class for PaasClientConfig.
 * This is a Template for the Client Config file for PAAS client request.
 *
 * Example:
 * {
 *   cloudlets: [{** Refer CloudletConfig **}],
 * }
 *
 * Refer CloudletConfig to know the structure of that input.
 */
case class PaasClientConfig(var cloudlets: List[CloudletConfig]) {
  def this() = this(Nil)
  
  // Getters and Setters for custom parameter
  def getCloudlets: java.util.List[CloudletConfig] = cloudlets.asJava
  def setCloudlets(cloudlets: java.util.List[CloudletConfig]): Unit = {
    this.cloudlets = cloudlets.asScala.toList
  }
}
