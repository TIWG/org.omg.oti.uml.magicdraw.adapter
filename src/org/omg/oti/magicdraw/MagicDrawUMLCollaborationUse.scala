package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLCollaborationUse 
  extends UMLCollaborationUse[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#CollaborationUse
  import ops._

  override def roleBinding = e.getRoleBinding.toSet[Uml#Dependency]
  
  override def _type = Option.apply( e.getType )
  
  override def representation_classifier = Option.apply( e.get_classifierOfRepresentation )

}
