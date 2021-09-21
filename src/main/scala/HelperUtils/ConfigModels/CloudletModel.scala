package HelperUtils.ConfigModels

import scala.beans.BeanProperty

// Reference:
// https://index.scala-lang.org/pureconfig/pureconfig/pureconfig/0.16.0?target=_2.13
// https://stackoverflow.com/a/38702348/5589746

case class CloudletModel(@BeanProperty var number: Int,
                         @BeanProperty var pes: Int,
                         @BeanProperty var length: Long,
                         @BeanProperty var size: Long ) {
  def this() = this(0, 0, 0, 0)
}
