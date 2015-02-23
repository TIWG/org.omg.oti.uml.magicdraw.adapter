package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLTemplateParameter 
  extends UMLTemplateParameter[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#TemplateParameter
  import ops._

}
