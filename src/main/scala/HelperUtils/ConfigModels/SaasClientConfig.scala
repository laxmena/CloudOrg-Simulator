package HelperUtils.ConfigModels

import scala.beans.BeanProperty

/**
 * Case Class for SaasClientConfig.
 * This is a Template for the Client Config file for SAAS client request.
 *
 * Example:
 * {
 *   service: "googleSheets",
 *   count: 4
 * }
 *
 */
case class SaasClientConfig(@BeanProperty var service: String,
                            @BeanProperty var count: Int) {
  def this() = this("", 0)
}
