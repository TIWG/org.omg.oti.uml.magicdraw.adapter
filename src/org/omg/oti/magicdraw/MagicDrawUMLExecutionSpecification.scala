package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLExecutionSpecification 
  extends UMLExecutionSpecification[MagicDrawUML]
  with MagicDrawUMLInteractionFragment {

  override protected def e: Uml#ExecutionSpecification
  import ops._

  override def finish: Option[UMLOccurrenceSpecification[Uml]] =
    Option.apply( e.getFinish )
    
  override def start: Option[UMLOccurrenceSpecification[Uml]] =
    Option.apply( e.getStart )

  override def execution_executionOccurrenceSpecification: Set[UMLExecutionOccurrenceSpecification[Uml]] =
    e.get_executionOccurrenceSpecificationOfExecution.toSet[Uml#ExecutionOccurrenceSpecification]
  
}
