package HelperUtils.ConfigModels

import scala.beans.BeanProperty

// Case Class for SwitchConfig Object
// Object for this instance is created from Config file using 
// the PureConfig library  
case class SwitchConfig(@BeanProperty var count: Int,
                        @BeanProperty var ports: Int,
                        @BeanProperty var bw: Long,
                        @BeanProperty var delay: Long) {
  def this() = this(0, 0, 0L, 0L)
}
