package Experimentations

import HelperUtils.ConfigModels.{CloudletConfig, DataCenterConfig}
import HelperUtils.{CreateLogger, ObtainConfigReference}
import com.typesafe.config.{Config, ConfigBeanFactory, ConfigFactory}

import scala.jdk.CollectionConverters._

class LoadConfig

object LoadConfig {
  val config = ObtainConfigReference("simulation") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  val logger = CreateLogger(classOf[LoadConfig])

  def start(): Unit = {
    val sConfig = config.getConfig("simulation")
    val datacenters = sConfig.getConfigList("datacenters").asScala
    val dcList = datacenters.map {
      ConfigBeanFactory.create(_, classOf[DataCenterConfig])
    }.toList
    logger.info(s"$dcList")
  }
}