package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLVertex 
  extends UMLVertex[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#Vertex
  import ops._

}
