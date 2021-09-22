package HelperUtils.ConfigModels

import scala.beans.BeanProperty

case class HostConfig(@BeanProperty var pes: Int,
                      @BeanProperty var mips: Double,
                      @BeanProperty var ram: Long,
                      @BeanProperty var bandwidth: Long,
                      @BeanProperty var storage: Long,
                      @BeanProperty var vmScheduler: String) {
  def this() = this(0, 0D, 0L, 0L, 0L, "")
}
