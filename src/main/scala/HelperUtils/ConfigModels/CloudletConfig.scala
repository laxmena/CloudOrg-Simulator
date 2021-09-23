package HelperUtils.ConfigModels

import scala.beans.BeanProperty

case class CloudletConfig(@BeanProperty var count: Int,
                          @BeanProperty var pes: Int,
                          @BeanProperty var length: Long,
                          @BeanProperty var size: Long) {
  def this() = this(0, 0, 0L, 0L)
}
