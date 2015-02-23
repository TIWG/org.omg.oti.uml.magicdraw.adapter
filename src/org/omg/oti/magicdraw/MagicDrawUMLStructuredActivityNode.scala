package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLStructuredActivityNode 
  extends UMLStructuredActivityNode[MagicDrawUML]
  with MagicDrawUMLAction
  with MagicDrawUMLNamespace
  with MagicDrawUMLActivityGroup {

  override protected def e: Uml#StructuredActivityNode
  import ops._

}
