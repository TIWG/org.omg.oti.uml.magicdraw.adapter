package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDuration 
  extends UMLDuration[MagicDrawUML]
  with MagicDrawUMLValueSpecification {

  override protected def e: Uml#Duration
  import ops._

}
