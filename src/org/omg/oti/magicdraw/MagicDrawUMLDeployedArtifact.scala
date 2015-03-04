package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLDeployedArtifact 
  extends UMLDeployedArtifact[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#DeployedArtifact
  import ops._

  override def deployedArtifact_deploymentForArtifact: Set[UMLDeployment[MagicDrawUML]] = 
    e.get_deploymentOfDeployedArtifact.toSet[Uml#Deployment]
}
