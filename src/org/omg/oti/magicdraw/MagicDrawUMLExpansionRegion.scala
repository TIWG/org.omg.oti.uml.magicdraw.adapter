package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLExpansionRegion 
  extends UMLExpansionRegion[MagicDrawUML]
  with MagicDrawUMLStructuredActivityNode {

  override protected def e: Uml#ExpansionRegion
  import ops._

  override def inputElement: Set[UMLExpansionNode[Uml]] = ???
  
  override def mode: UMLExpansionKind.Value = ???
  
  override def outputElement: Set[UMLExpansionNode[Uml]] = ???
  
}
