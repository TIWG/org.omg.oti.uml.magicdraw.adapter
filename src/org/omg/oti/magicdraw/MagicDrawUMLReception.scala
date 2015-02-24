package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLReception 
  extends UMLReception[MagicDrawUML]
  with MagicDrawUMLBehavioralFeature {

  override protected def e: Uml#Reception
  import ops._

  // 10.5
  override def signal: Option[UMLSignal[Uml]] = ??? 
}
