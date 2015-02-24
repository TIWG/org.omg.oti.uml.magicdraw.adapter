package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLInterface 
  extends UMLInterface[MagicDrawUML]
  with MagicDrawUMLClassifier {

  override protected def e: Uml#Interface
  import ops._

	override def nestedClassifier: Seq[UMLClassifier[Uml]] =
    e.getNestedClassifier.toSeq
    
  override def ownedAttribute: Seq[UMLProperty[Uml]] =
    e.getOwnedAttribute.toSeq
    
  override def ownedOperation: Seq[UMLOperation[Uml]] =
    e.getOwnedOperation.toSeq
    

  override def provided_port: Set[UMLPort[Uml]] = ???
  
  override def required_port: Set[UMLPort[Uml]] = ???

  override def required_component: Set[UMLComponent[Uml]] = ???
  
  override def provided_component: Set[UMLComponent[Uml]] = ???
  
  override def protocol: Option[UMLProtocolStateMachine[Uml]] =
    Option.apply( e.getProtocol )
}
