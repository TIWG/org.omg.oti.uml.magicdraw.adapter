package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLOperationTemplateParameter 
  extends UMLOperationTemplateParameter[MagicDrawUML]
  with MagicDrawUMLTemplateParameter {

  override protected def e: Uml#OperationTemplateParameter
  import ops._

}
