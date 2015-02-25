package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLGeneralization 
  extends UMLGeneralization[MagicDrawUML]
  with MagicDrawUMLDirectedRelationship {

  override protected def e: Uml#Generalization
  import ops._
  
  /**
   * BUG: in UML 2.5, isSubstituable:Boolean[0..1] = true
   * there should be 3 values: None, Some(true), Some(false)
   * but the MD API only gives 2: true, false
   */
  override def isSubstitutable: Option[Boolean] =
    e.isSubstitutable match {
    case true => None
    case false => Some( false )
  }
    
  // 9.14
  override def generalizationSet: Set[UMLGeneralizationSet[Uml]] = 
    e.getGeneralizationSet.toSet[Uml#GeneralizationSet]
  
}