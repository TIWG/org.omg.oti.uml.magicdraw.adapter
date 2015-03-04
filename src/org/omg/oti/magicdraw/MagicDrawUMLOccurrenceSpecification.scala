package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLOccurrenceSpecification 
  extends UMLOccurrenceSpecification[MagicDrawUML]
  with MagicDrawUMLInteractionFragment {

  override protected def e: Uml#OccurrenceSpecification
  import ops._

	override def covered: Iterable[UMLLifeline[Uml]] = ???
  
  override def toAfter: Set[UMLGeneralOrdering[Uml]] = ???
  
  override def toBefore: Set[UMLGeneralOrdering[Uml]] = ???
  
  override def finish_executionSpecification: Set[UMLExecutionSpecification[Uml]] = ???
  
  override def start_executionSpecification: Set[UMLExecutionSpecification[Uml]] = ???
  
}
