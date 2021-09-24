package HelperUtils.ConfigModels

import scala.beans.BeanProperty

/**
 * Case Class for HostConfig Object
 * Object for this instance is created from Config file using
 * the PureConfig library.
 * Example: 
 * { 
 *   pes: 32
 *   mips: 2000
 *   ram: 64000
 *   bandwidth: 32000
 *   storage: 100000
 *   vmScheduler: SpaceShared
 * } 
 */
case class HostConfig(@BeanProperty var pes: Int,
                      @BeanProperty var mips: Double,
                      @BeanProperty var ram: Long,
                      @BeanProperty var bandwidth: Long,
                      @BeanProperty var storage: Long,
                      @BeanProperty var vmScheduler: String) {
  def this() = this(0, 0D, 0L, 0L, 0L, "")
}
