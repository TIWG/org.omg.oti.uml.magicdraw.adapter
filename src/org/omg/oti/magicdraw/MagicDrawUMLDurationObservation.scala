package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDurationObservation 
  extends UMLDurationObservation[MagicDrawUML]
  with MagicDrawUMLObservation {

  override protected def e: Uml#DurationObservation
  import ops._

}
