package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLTemplateParameterSubstitution 
  extends UMLTemplateParameterSubstitution[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#TemplateParameterSubstitution
  import ops._

}
