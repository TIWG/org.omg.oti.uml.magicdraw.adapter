package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLClearAssociationAction 
  extends UMLClearAssociationAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ClearAssociationAction
  import ops._

  override def association: Option[UMLAssociation[Uml]] =
    Option.apply( e.getAssociation )
    
  override def _object: Option[UMLInputPin[Uml]] =
    Option.apply( e.getObject )
    
}
