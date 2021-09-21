package Experimentations

import HelperUtils.ConfigModels.{CloudletModel, DataCenterModel}
import HelperUtils.{CreateLogger, ObtainConfigReference}
import Simulations.BasicFirstExample
import com.typesafe.config.{Config, ConfigBeanFactory, ConfigFactory}

import scala.jdk.CollectionConverters.*

class LoadConfig

object LoadConfig:
  val config = ObtainConfigReference("simulation") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }

  val logger = CreateLogger(classOf[BasicFirstExample])
  def start(): Unit =
    val sConfig = config.getConfig("simulation")
    val datacenters = sConfig.getConfigList("datacenters").asScala
    val dcList = datacenters.map {
                  ConfigBeanFactory.create(_, classOf[DataCenterModel])
                }.toList
    logger.info(s"$dcList")
    