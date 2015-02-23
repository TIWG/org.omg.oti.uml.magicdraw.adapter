package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLOccurrenceSpecification 
  extends UMLOccurrenceSpecification[MagicDrawUML]
  with MagicDrawUMLInteractionFragment {

  override protected def e: Uml#OccurrenceSpecification
  import ops._

}
