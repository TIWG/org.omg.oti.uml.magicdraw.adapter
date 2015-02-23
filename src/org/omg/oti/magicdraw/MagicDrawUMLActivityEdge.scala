package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLActivityEdge 
  extends UMLActivityEdge[MagicDrawUML]
  with MagicDrawUMLRedefinableElement {

  override protected def e: Uml#ActivityEdge
  import ops._

}
