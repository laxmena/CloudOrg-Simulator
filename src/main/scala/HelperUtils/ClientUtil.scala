package HelperUtils

import HelperUtils.ConfigModels.{ClientConfig, CloudletConfig, IaasClientConfig, PaasClientConfig, SaasClientConfig}
import org.cloudbus.cloudsim.brokers.DatacenterBroker
import org.cloudbus.cloudsim.core.CloudSim
import com.typesafe.config.{Config, ConfigBeanFactory, ConfigList}

import scala.jdk.CollectionConverters.*

class ClientUtil

object ClientUtil {
  val saasCloudletsConfig = ObtainConfigReference("saasCloudlets") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  val logger = CreateLogger(classOf[ClientUtil])

  def createClientResources(broker: DatacenterBroker, config: Config): Unit = {
    logger.info(s"Creating Client Resources")
    val clientConfig: ClientConfig = ConfigBeanFactory.create(config, classOf[ClientConfig])

    if(clientConfig.saas.length != 0) {
      logger.info(s"Creating SAAS Client Resources")
      createSaasResources(clientConfig.saas, broker)
      logger.info(s"Finished creating SAAS Client Resources")
    }

    if(clientConfig.paas.length != 0) {
      logger.info(s"Creating PAAS Client Resources")
      createPaasResources(clientConfig.paas, broker)
      logger.info(s"Finished creating PAAS Client Resources")
    }

    if(clientConfig.iaas.length != 0) {
      logger.info(s"Creating IAAS Client Resources")
      createIaasResources(clientConfig.iaas, broker)
      logger.info(s"Finished creating IAAS Client Resources")
    }

  }

  def createSaasResources(configList: List[SaasClientConfig], broker: DatacenterBroker): Unit = {
    val saasServices = saasCloudletsConfig.getConfig("saasCloudlets")
    val cloudletList = configList.map(config => {
      val service = config.service
      val cloudlet = ConfigBeanFactory.create(saasServices.getConfig(service), classOf[CloudletConfig])
      (1 to config.count).map(_ => {
        CommonUtil.configureCloudlet(cloudlet)
      }).flatten
    }).flatten
    if(cloudletList.length != 0)
      broker.submitCloudletList(cloudletList.asJava)
  }

  def createPaasResources(paasClientList: List[PaasClientConfig], broker: DatacenterBroker): Unit = {
    val cloudletList = paasClientList.map(paasClient => {
      CommonUtil.configureMultipleCloudlets(paasClient.cloudlets)
    }).flatten
    if(cloudletList.length != 0)
      broker.submitCloudletList(cloudletList.asJava)
  }

  def createIaasResources(iaasClientList: List[IaasClientConfig], broker: DatacenterBroker): Unit = {
    iaasClientList.map(iaasClient => {
      logger.info(s"Creating VMs for IAAS Client")
      val vmList = CommonUtil.configureMultipleVms(iaasClient.vms)
      if(vmList.length != 0)
        broker.submitVmList(vmList.asJava)
      logger.info(s"Configured VMs count: ${vmList.length}")

      logger.info(s"Creating Cloudlets for IAAS Client")
      val cloudletList = CommonUtil.configureMultipleCloudlets(iaasClient.cloudlets)
      if(cloudletList.length != 0)
        broker.submitCloudletList(cloudletList.asJava)
    })
  }
}