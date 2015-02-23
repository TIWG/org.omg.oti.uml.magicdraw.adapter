package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLClause 
  extends UMLClause[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#Clause
  import ops._

}
