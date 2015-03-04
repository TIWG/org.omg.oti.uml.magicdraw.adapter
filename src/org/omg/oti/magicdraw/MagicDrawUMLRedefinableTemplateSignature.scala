package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLRedefinableTemplateSignature 
  extends UMLRedefinableTemplateSignature[MagicDrawUML]
  with MagicDrawUMLRedefinableElement
  with MagicDrawUMLTemplateSignature {

  override protected def e: Uml#RedefinableTemplateSignature
  import ops._

  // BUG: NO FIGURE
  override def inheritedParameter: Set[UMLTemplateParameter[Uml]] = ???
  
}
