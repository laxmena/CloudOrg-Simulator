package HelperUtils.ConfigModels

import java.beans.BeanProperty
import scala.jdk.CollectionConverters.*

case class ClientConfig(@BeanProperty var saas: List[SaasClientConfig],
                        @BeanProperty var paas: List[PaasClientConfig],
                        @BeanProperty var iaas: List[IaasClientConfig]) {
  def this() = this(Nil, Nil, Nil)

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
