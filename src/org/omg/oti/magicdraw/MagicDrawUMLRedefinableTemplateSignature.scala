package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLRedefinableTemplateSignature 
  extends UMLRedefinableTemplateSignature[MagicDrawUML]
  with MagicDrawUMLRedefinableElement
  with MagicDrawUMLTemplateSignature {

  override protected def e: Uml#RedefinableTemplateSignature
  import ops._

}
