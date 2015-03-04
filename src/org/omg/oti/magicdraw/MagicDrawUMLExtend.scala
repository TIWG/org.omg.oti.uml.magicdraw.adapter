package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLExtend 
  extends UMLExtend[MagicDrawUML]
  with MagicDrawUMLNamedElement
  with MagicDrawUMLDirectedRelationship {

  override protected def e: Uml#Extend
  import ops._
 
  // 18.1
  override def condition: Option[UMLConstraint[Uml]] = ???
    
  // 18.1
	override def extensionLocation: Seq[UMLExtensionPoint[Uml]] = ??? 
}
