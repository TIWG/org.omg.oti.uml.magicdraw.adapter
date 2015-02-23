package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLMessageEnd 
  extends UMLMessageEnd[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#MessageEnd
  import ops._

}
