package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLValueSpecificationAction 
  extends UMLValueSpecificationAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ValueSpecificationAction
  import ops._

}
