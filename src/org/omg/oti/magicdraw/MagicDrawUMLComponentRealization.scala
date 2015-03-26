package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._
import scala.collection.JavaConversions._

trait MagicDrawUMLComponentRealization 
  extends UMLComponentRealization[MagicDrawUML]
  with MagicDrawUMLRealization {

  override protected def e: Uml#ComponentRealization
  import ops._

	override def abstraction: Option[UMLComponent[Uml]] =
    Option.apply(e.getAbstraction)
    
  override def realizingClassifier: Set[UMLClassifier[Uml]] =
    umlClassifier( e.getRealizingClassifier.toSet ) 

}
