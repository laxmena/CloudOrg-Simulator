package HelperUtils.ConfigModels

import scala.beans.BeanProperty

 /**
 * Case Class for CloudletConfig Object
 * Object for this instance is created from Config file using
 * the PureConfig library.
 * Example: 
 * { 
 *   count: 3
 *   pes: 2
 *   length: 100
 *   size: 100 
 * } 
 */
case class CloudletConfig(@BeanProperty var count: Int,
                          @BeanProperty var pes: Int,
                          @BeanProperty var length: Long,
                          @BeanProperty var size: Long) {
  def this() = this(0, 0, 0L, 0L)
}
