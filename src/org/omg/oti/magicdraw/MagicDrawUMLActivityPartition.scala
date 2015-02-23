package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLActivityPartition 
  extends UMLActivityPartition[MagicDrawUML]
  with MagicDrawUMLActivityGroup {

  override protected def e: Uml#ActivityPartition
  import ops._

}
