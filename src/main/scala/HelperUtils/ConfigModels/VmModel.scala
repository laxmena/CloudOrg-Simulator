package HelperUtils.ConfigModels

import scala.beans.BeanProperty

case class VmModel(@BeanProperty var instanceType: String,
                   @BeanProperty var count: Int) {
  def this() = this("", 0)
}
