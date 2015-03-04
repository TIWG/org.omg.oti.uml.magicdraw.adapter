package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLInteractionUse 
  extends UMLInteractionUse[MagicDrawUML]
  with MagicDrawUMLInteractionFragment {

  override protected def e: Uml#InteractionUse
  import ops._

  // 17.18
  override def argument: Seq[UMLValueSpecification[Uml]] = ???
  
  // 17.18
  override def refersTo: Option[UMLInteraction[Uml]] = ???
  
  // 17.18
  override def returnValue: Option[UMLValueSpecification[Uml]] = ???
  
  // BUG: NO FIGURE!
  override def returnValueRecipient: Option[UMLProperty[Uml]] = ??? 
}
