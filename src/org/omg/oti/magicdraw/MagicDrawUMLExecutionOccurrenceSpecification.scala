package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLExecutionOccurrenceSpecification 
  extends UMLExecutionOccurrenceSpecification[MagicDrawUML]
  with MagicDrawUMLOccurrenceSpecification {

  override protected def e: Uml#ExecutionOccurrenceSpecification
  import ops._

  override def execution: Option[UMLExecutionSpecification[Uml]] = ???
  
}
