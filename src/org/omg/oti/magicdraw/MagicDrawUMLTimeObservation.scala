package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLTimeObservation 
  extends UMLTimeObservation[MagicDrawUML]
  with MagicDrawUMLObservation {

  override protected def e: Uml#TimeObservation
  import ops._

}
