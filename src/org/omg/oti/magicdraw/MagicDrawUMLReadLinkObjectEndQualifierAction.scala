package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLReadLinkObjectEndQualifierAction 
  extends UMLReadLinkObjectEndQualifierAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReadLinkObjectEndQualifierAction
  import ops._
  
  // 16.35
	override def _object: Option[UMLInputPin[Uml]] = ???
    
  // 16.35
	override def qualifier: Option[UMLProperty[Uml]] = ???
  
  // 16.35
	override def result: Option[UMLOutputPin[Uml]] = ??? 
}
