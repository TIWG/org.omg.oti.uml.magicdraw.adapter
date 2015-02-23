package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLExpansionRegion 
  extends UMLExpansionRegion[MagicDrawUML]
  with MagicDrawUMLStructuredActivityNode {

  override protected def e: Uml#ExpansionRegion
  import ops._

}
