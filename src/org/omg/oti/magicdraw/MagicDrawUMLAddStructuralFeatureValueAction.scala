package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLAddStructuralFeatureValueAction 
  extends UMLAddStructuralFeatureValueAction[MagicDrawUML]
  with MagicDrawUMLWriteStructuralFeatureAction {

  override protected def e: Uml#AddStructuralFeatureValueAction
  import ops._

  override def insertAt: Option[UMLInputPin[Uml]] =
    Option.apply( e.getInsertAt )
    
  override def isReplaceAll: Boolean =
    e.isReplaceAll
  
}
