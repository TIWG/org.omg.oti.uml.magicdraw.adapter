package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLParameterSet 
  extends UMLParameterSet[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#ParameterSet
  import ops._

}
