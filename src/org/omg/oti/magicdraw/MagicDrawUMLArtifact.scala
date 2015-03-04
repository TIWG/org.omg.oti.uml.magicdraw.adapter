package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLArtifact 
  extends UMLArtifact[MagicDrawUML]
  with MagicDrawUMLClassifier
  with MagicDrawUMLDeployedArtifact {

  override protected def e: Uml#Artifact
  import ops._

	override def fileName: Option[String] =
    e.getFileName match {
    case null => None
    case "" => None
    case s => Some( s )
  }
  
  override def ownedAttribute: Seq[UMLProperty[Uml]] =
    e.getOwnedAttribute.toSeq
    
  override def ownedOperation: Seq[UMLOperation[Uml]] =
    e.getOwnedOperation.toSeq

}
