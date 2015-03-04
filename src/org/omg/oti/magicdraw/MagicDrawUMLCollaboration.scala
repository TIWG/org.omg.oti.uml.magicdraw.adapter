package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLCollaboration 
  extends UMLCollaboration[MagicDrawUML]
  with MagicDrawUMLBehavioredClassifier
  with MagicDrawUMLStructuredClassifier {

  override protected def e: Uml#Collaboration
  import ops._

  override def collaborationRole = e.getCollaborationRole.toSet[Uml#ConnectableElement]
  
  override def type_collaborationUse = e.get_collaborationUseOfType.toSet[Uml#CollaborationUse]
  
}
