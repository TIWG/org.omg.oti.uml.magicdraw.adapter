package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLTemplateParameter 
  extends UMLTemplateParameter[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#TemplateParameter
  import ops._

  override def default: Option[UMLParameterableElement[Uml]] = ???
  
  override def ownedParameteredElement: Option[UMLParameterableElement[Uml]] = 
    Option.apply( e.getOwnedParameteredElement )
  
  override def ownedDefault: Option[UMLParameterableElement[Uml]] = ???
  
  override def parameteredElement: Option[UMLParameterableElement[Uml]] = ???
  
  // 7.3
  override def parameter_templateSignature: Set[UMLTemplateSignature[Uml]] = ???
  
  // 7.4
  override def formal_templateParameterSubstitution: Set[UMLTemplateParameterSubstitution[Uml]] = ???
   
  // 9.4
  override def inheritedParameter_redefinableTemplateSignature: Set[UMLRedefinableTemplateSignature[Uml]] = ???
} 
