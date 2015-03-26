package org.omg.oti.magicdraw

import org.omg.oti.api._
import org.omg.oti.operations._
import scala.collection.JavaConversions._

trait MagicDrawUMLDeployment 
  extends UMLDeployment[MagicDrawUML]
  with MagicDrawUMLDependency {

  override protected def e: Uml#Deployment
  import ops._

  override def deployedArtifact: Set[UMLDeployedArtifact[Uml]] =
    umlDeployedArtifact( e.getDeployedArtifact.toSet )
}
