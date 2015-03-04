package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLGeneralizationSet 
  extends UMLGeneralizationSet[MagicDrawUML]
  with MagicDrawUMLPackageableElement {

  override protected def e: Uml#GeneralizationSet
  import ops._

  // 9.14
  override def generalization: Set[UMLGeneralization[Uml]] = ??? 
  
  // 9.14
  override def isCovering: Boolean = ???
  
  // 9.14
  override def isDisjoint: Boolean = ???
  
  // 9.14
  override def powertype: Option[UMLClassifier[Uml]] = ???
  
}
