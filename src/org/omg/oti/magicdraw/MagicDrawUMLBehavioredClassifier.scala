package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLBehavioredClassifier 
  extends UMLBehavioredClassifier[MagicDrawUML]
  with MagicDrawUMLClassifier {

  override protected def e: Uml#BehavioredClassifier
  import ops._

  override def classifierBehavior = Option.apply( e.getClassifierBehavior )
  
  override def interfaceRealization = e.getInterfaceRealization.toSet[Uml#InterfaceRealization]
  
  override def context_behavior = ???
  
}
