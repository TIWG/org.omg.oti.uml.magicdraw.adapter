package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLReadLinkObjectEndAction 
  extends UMLReadLinkObjectEndAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#ReadLinkObjectEndAction
  import ops._

  // 16.35
	override def end: Option[UMLProperty[Uml]] = ??? 
    
  // 16.35
	override def _object: Option[UMLInputPin[Uml]] = ??? 
  
  // 16.35
	override def result: Option[UMLOutputPin[Uml]] = ???
  
}
