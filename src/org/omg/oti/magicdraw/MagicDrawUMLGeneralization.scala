package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLGeneralization 
  extends UMLGeneralization[MagicDrawUML]
  with MagicDrawUMLDirectedRelationship {

  override protected def e: Uml#Generalization
  import ops._
  
  // 9.14
  override def generalizationSet: Set[UMLGeneralizationSet[Uml]] = ???
}