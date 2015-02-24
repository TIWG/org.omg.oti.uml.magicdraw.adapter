package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInteraction 
  extends UMLInteraction[MagicDrawUML]
  with MagicDrawUMLBehavior
  with MagicDrawUMLInteractionFragment {

  override protected def e: Uml#Interaction
  import ops._

  // 17.1
	override def formalGate: Set[UMLGate[Uml]] = ???
  
  // 17.1
  override def fragment: Seq[UMLInteractionFragment[Uml]] = ???
  
  // 17.18
  override def refersTo_interactionUse: Set[UMLInteractionUse[Uml]] = ???
}
