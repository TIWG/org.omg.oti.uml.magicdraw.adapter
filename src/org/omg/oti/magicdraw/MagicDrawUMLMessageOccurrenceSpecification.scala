package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLMessageOccurrenceSpecification 
  extends UMLMessageOccurrenceSpecification[MagicDrawUML]
  with MagicDrawUMLOccurrenceSpecification
  with MagicDrawUMLMessageEnd {

  override protected def e: Uml#MessageOccurrenceSpecification
  import ops._

}
