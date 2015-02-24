package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLLifeline 
  extends UMLLifeline[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#Lifeline
  import ops._

  // 17.6
	override def coveredBy: Set[UMLInteractionFragment[Uml]] = ???
  
  // 17.6
	def decomposedAs: Option[UMLPartDecomposition[Uml]] = ???
  
  // 17.6  
	def represents: Option[UMLConnectableElement[Uml]] = ???
  
  // 17.6
	def selector: Option[UMLValueSpecification[Uml]] = ???
  
  // 17.6
	def covered_events: Seq[UMLOccurrenceSpecification[Uml]] = ???
  
  // 17.6
	def covered_stateInvariant: Set[UMLStateInvariant[Uml]] = ???
}
