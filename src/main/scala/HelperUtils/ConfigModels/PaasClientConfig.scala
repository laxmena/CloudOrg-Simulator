package HelperUtils.ConfigModels

import scala.beans.BeanProperty
import scala.jdk.CollectionConverters.*

case class PaasClientConfig(var cloudlets: List[CloudletConfig]) {
  def this() = this(Nil)

  def getCloudlets: java.util.List[CloudletConfig] = cloudlets.asJava
  def setCloudlets(cloudlets: java.util.List[CloudletConfig]): Unit = {
    this.cloudlets = cloudlets.asScala.toList
  }
}
