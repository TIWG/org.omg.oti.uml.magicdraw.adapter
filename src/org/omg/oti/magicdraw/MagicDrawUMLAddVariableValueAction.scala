package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLAddVariableValueAction 
  extends UMLAddVariableValueAction[MagicDrawUML]
  with MagicDrawUMLWriteVariableAction {

  override protected def e: Uml#AddVariableValueAction
  import ops._

  override def insertAt: Option[UMLInputPin[Uml]] =
    Option.apply( e.getInsertAt )
    
  override def isReplaceAll: Boolean =
    e.isReplaceAll
  
}
