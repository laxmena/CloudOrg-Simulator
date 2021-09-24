package HelperUtils

import HelperUtils.ConfigModels.{ClientConfig, CloudletConfig, IaasClientConfig, PaasClientConfig, SaasClientConfig}
import org.cloudbus.cloudsim.brokers.DatacenterBroker
import org.cloudbus.cloudsim.core.CloudSim
import com.typesafe.config.{Config, ConfigBeanFactory, ConfigList}

import scala.jdk.CollectionConverters._

class ClientUtil

object ClientUtil {
  val saasCloudletsConfig = ObtainConfigReference("saasCloudlets") match {
    case Some(value) => value
    case None => throw new RuntimeException("Cannot obtain a reference to the config data.")
  }
  val logger = CreateLogger(classOf[ClientUtil])

  /**
   * Creates requested Client Resources from the Client Config file.
   *
   * @param broker DatacenterBroker object used for the simulation
   * @param config Client Config file that has Client requirements
   * @return (Int Int Int) Tuple of three Int files, each representing the number of
   *         resources created for SAAS, PAAS and IAAS Respectively
   * */
  def createClientResources(broker: DatacenterBroker, config: Config): (Int, Int, Int) = {
    logger.info(s"Creating Client Resources")
    logger.info(s"$config")

    // Parse ClientConfig and Load it as a ClientConfig object
    val clientConfig: ClientConfig = ConfigBeanFactory.create(config, classOf[ClientConfig])

    // Create SAAS Client Requests
    logger.info(s"Creating SAAS Client Resources")
    val saasCount = createSaasResources(clientConfig.saas, broker)
    logger.info(s"Finished creating SAAS Client Resources")

    // Create PAAS Client Requests
    logger.info(s"Creating PAAS Client Resources")
    val paasCount = createPaasResources(clientConfig.paas, broker)
    logger.info(s"Finished creating PAAS Client Resources")

    // Create IAAS Client Requests
    logger.info(s"Creating IAAS Client Resources")
    val iaasCount = createIaasResources(clientConfig.iaas, broker)
    logger.info(s"Finished creating IAAS Client Resources")

    // Return number of resources created for each type
    (saasCount, paasCount, iaasCount)
  }

  /**
   * Create SAAS Software Cloudlets from list of SaasClientConfig objects
   *
   * @param configList List of SaasClientConfig objects that has
   *                   the name of the service and the number of instances
   * @param broker     DatacenterBroker to create Cloudlets
   * @return Int Number of SAAS Software cloudlets created
   * */
  def createSaasResources(configList: List[SaasClientConfig], broker: DatacenterBroker): Int = {
    // Get List of SAAS Softwares provided by the Organization
    val saasServices = saasCloudletsConfig.getConfig("saasCloudlets")

    // Parse the SaasClientConfig, parse the number of cloudlets and the software name.
    val cloudletList = configList.map(config => {
      // Get the service that the client requests
      val service = config.service
      // Parse the Saas software from configuration file and load it into CloudletConfig object
      val cloudlet = ConfigBeanFactory.create(saasServices.getConfig(service), classOf[CloudletConfig])

      // Create n instances of requested Saas Software service
      (1 to config.count).map(_ => {
        CommonUtil.configureCloudlet(cloudlet)
      }).flatten
    }).flatten

    if (cloudletList.length != 0)
      broker.submitCloudletList(cloudletList.asJava)

    return cloudletList.length
  }

  /**
   * Create PAAS Software Cloudlets from list of PaasClientConfig objects
   *
   * @param configList List of PaasClientConfig objects
   * @param broker     DatacenterBroker to create Cloudlets
   * @return Int Number of PAAS Software cloudlets created
   * */
  def createPaasResources(configList: List[PaasClientConfig], broker: DatacenterBroker): Int = {
    val cloudletList = configList.map(paasClient => {
      CommonUtil.configureMultipleCloudlets(paasClient.cloudlets)
    }).flatten
    if (cloudletList.length != 0)
      broker.submitCloudletList(cloudletList.asJava)
    cloudletList.length
  }

  /**
   * Create IAAS Software Cloudlets from list of IaasClientConfig objects
   *
   * @param configList List of IaasClientConfig objects
   * @param broker     DatacenterBroker to create Cloudlets
   * @return Int Number of IAAS Software cloudlets created
   * */
  def createIaasResources(iaasClientList: List[IaasClientConfig], broker: DatacenterBroker): Int = {

    // Each item in the iaasClientList is a request from a Client
    // We iterate through the list and create resources for each request.
    val count = iaasClientList.map(iaasClient => {
      logger.info(s"Creating VMs for IAAS Client")

      // Create VM's from IaasClientConfig
      val vmList = CommonUtil.configureMultipleVms(iaasClient.vms)
      if (vmList.length != 0)
        broker.submitVmList(vmList.asJava)
      logger.info(s"Configured VMs count: ${vmList.length}")

      logger.info(s"Creating Cloudlets for IAAS Client")
      // Create Cloudlets from IaasClientConfig
      val cloudletList = CommonUtil.configureMultipleCloudlets(iaasClient.cloudlets)
      if (cloudletList.length != 0)
        broker.submitCloudletList(cloudletList.asJava)
      logger.info(s"Configured Cloudlets count: ${cloudletList.length}")

      // return total number of resources created
      vmList.length + cloudletList.length
    }).toList

    // Total number of resources created
    count.sum
  }
}