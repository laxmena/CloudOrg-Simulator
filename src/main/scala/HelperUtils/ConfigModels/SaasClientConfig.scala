package HelperUtils.ConfigModels

import scala.beans.BeanProperty

case class SaasClientConfig(@BeanProperty var service: String,
                            @BeanProperty var count: Int) {
  def this() = this("", 0)
}
