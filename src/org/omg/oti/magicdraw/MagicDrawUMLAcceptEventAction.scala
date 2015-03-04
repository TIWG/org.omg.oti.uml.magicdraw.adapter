package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLAcceptEventAction 
  extends UMLAcceptEventAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#AcceptEventAction
  import ops._

  override def isUnmarshall = 
    e.isUnmarshall
  
  override def result = 
    e.getResult.toSeq
  
  override def trigger = 
    e.getTrigger.toSet[Uml#Trigger]
  
}
