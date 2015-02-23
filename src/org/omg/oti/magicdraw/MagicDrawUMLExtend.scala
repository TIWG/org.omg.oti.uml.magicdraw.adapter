package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLExtend 
  extends UMLExtend[MagicDrawUML]
  with MagicDrawUMLNamedElement
  with MagicDrawUMLDirectedRelationship {

  override protected def e: Uml#Extend
  import ops._

}
