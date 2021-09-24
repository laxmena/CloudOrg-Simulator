package HelperUtils.ConfigModels

import java.beans.BeanProperty
import scala.jdk.CollectionConverters._

/**
 * Case Class for ClientConfig Object.
 * This is the input file structure of the client requests,
 * which will be processed and simulated.
 *
 * Example:
 * {
 *   saas: [{** Refer SaasClientConfig **}],
 *   paas: [{** Refer PaasClientConfig **}, {** Refer PaasClientConfig **}],
 *   iaas: [{** Refer IaasClientConfig **}]
 * }
 *
 * Refer SaasClientConfig, PaasClientConfig and IaasClientConfig to know
 * the structure of input of those values
 */
case class ClientConfig(@BeanProperty var saas: List[SaasClientConfig],
                        @BeanProperty var paas: List[PaasClientConfig],
                        @BeanProperty var iaas: List[IaasClientConfig]) {
  def this() = this(Nil, Nil, Nil)
  
  // Getters and Setters for custom DataTypes
  def getSaas: java.util.List[SaasClientConfig] = saas.asJava
  def setSaas(saas: java.util.List[SaasClientConfig]): Unit = {
    this.saas = saas.asScala.toList
  }

  def getPaas: java.util.List[PaasClientConfig] = paas.asJava
  def setPaas(paas: java.util.List[PaasClientConfig]): Unit = {
    this.paas = paas.asScala.toList
  }

  def getIaas: java.util.List[IaasClientConfig] = iaas.asJava
  def setIaas(iaas: java.util.List[IaasClientConfig]): Unit = {
    this.iaas = iaas.asScala.toList
  }
}
