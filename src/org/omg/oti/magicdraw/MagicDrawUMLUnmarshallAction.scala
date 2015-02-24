package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLUnmarshallAction 
  extends UMLUnmarshallAction[MagicDrawUML]
  with MagicDrawUMLAction {

  override protected def e: Uml#UnmarshallAction
  import ops._

  override def _object: Option[UMLInputPin[Uml]] =
    Option.apply( e.getObject )
    
  override def result: Seq[UMLOutputPin[Uml]] =
    e.getResult.toSeq
    
  override def unmarshallType: Option[UMLClassifier[Uml]] =
    Option.apply( e.getUnmarshallType )
    
}
