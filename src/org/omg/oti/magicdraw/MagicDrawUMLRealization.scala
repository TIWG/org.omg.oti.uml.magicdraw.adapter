package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLRealization 
  extends UMLRealization[MagicDrawUML]
  with MagicDrawUMLAbstraction {

  override protected def e: Uml#Realization
  import ops._

}
