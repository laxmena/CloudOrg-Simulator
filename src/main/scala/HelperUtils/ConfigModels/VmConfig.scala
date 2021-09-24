package HelperUtils.ConfigModels

import scala.beans.BeanProperty

// Case Class for VmConfig Object
// Object for this instance is created from Config file using
// the PureConfig library
case class VmConfig(@BeanProperty var instanceType: String,
                    @BeanProperty var count: Int) {
  def this() = this("", 0)
}
