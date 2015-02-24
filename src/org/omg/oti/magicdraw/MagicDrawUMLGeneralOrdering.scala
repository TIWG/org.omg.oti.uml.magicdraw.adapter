package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLGeneralOrdering 
  extends UMLGeneralOrdering[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#GeneralOrdering
  import ops._
  
	override def after: Option[UMLOccurrenceSpecification[Uml]] = ???
  
  override def before: Option[UMLOccurrenceSpecification[Uml]] = ???
  
}
