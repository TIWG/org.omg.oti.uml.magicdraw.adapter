package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLExecutionSpecification 
  extends UMLExecutionSpecification[MagicDrawUML]
  with MagicDrawUMLInteractionFragment {

  override protected def e: Uml#ExecutionSpecification
  import ops._

}
