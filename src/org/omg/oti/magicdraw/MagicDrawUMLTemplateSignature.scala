package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLTemplateSignature 
  extends UMLTemplateSignature[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#TemplateSignature
  import ops._

}
