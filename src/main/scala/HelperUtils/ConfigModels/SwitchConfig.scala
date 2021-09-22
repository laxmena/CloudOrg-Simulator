package HelperUtils.ConfigModels

import scala.beans.BeanProperty

case class SwitchConfig(@BeanProperty var count: Int,
                        @BeanProperty var ports: Int,
                        @BeanProperty var bw: Long,
                        @BeanProperty var delay: Long) {
  def this() = this(0, 0, 0L, 0L)
}
