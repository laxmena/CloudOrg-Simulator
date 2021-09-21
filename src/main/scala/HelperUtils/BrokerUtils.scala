package HelperUtils

import Simulations.BasicFirstExample
import com.typesafe.config.Config

import scala.jdk.CollectionConverters.*

class BrokerUtils

object BrokerUtils {

  val logger = CreateLogger(classOf[BrokerUtils])

  def createResources(config: Config) = {
    createDataCenters(config)
  }

  def createDataCenters(config: Config) = {
    logger.info(s"Preparing to create DataCenters")

    val dcConfigList = config.getConfigList("datacenters").asScala
    logger.info(s"Number of DataCenters: ${dcConfigList.length}")

    val dcList = dcConfigList.map(dcConfig => {
      val dcType = dcConfig.getString("type")

      dcType match {
        case "SAAS" => {
        }
        case "IAAS" => {
        }
        case "PAAS" => {
        }
        case "FAAS" => {
        }
      }
      logger.info(s"Creating $dcType Datacenter #${1}")
    }).toList

    dcList
  }

}
