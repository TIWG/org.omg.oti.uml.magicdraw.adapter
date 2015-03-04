package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLInclude 
  extends UMLInclude[MagicDrawUML]
  with MagicDrawUMLNamedElement
  with MagicDrawUMLDirectedRelationship {

  override protected def e: Uml#Include
  import ops._

}
