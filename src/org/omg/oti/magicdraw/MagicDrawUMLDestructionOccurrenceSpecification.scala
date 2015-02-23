package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDestructionOccurrenceSpecification 
  extends UMLDestructionOccurrenceSpecification[MagicDrawUML]
  with MagicDrawUMLMessageOccurrenceSpecification {

  override protected def e: Uml#DestructionOccurrenceSpecification
  import ops._

}
