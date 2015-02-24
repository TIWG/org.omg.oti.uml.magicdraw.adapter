package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLTemplateSignature 
  extends UMLTemplateSignature[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#TemplateSignature
  import ops._

  override def ownedParameter: Seq[UMLTemplateParameter[Uml]] =
    e.getOwnedParameter.toSeq
    
  override def parameter: Seq[UMLTemplateParameter[Uml]] =
    e.getParameter.toSeq

}
