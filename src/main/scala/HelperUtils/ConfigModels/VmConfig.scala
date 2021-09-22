package HelperUtils.ConfigModels

import scala.beans.BeanProperty

case class VmConfig(@BeanProperty var instanceType: String,
                    @BeanProperty var count: Int) {
  def this() = this("", 0)
}
