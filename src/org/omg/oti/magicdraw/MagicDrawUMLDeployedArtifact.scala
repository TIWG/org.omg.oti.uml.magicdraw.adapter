package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDeployedArtifact 
  extends UMLDeployedArtifact[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#DeployedArtifact
  import ops._

  override def deployedArtifact_deploymentForArtifact: Set[org.omg.oti.UMLDeployment[org.omg.oti.magicdraw.MagicDrawUML]] = 
    e.get_deploymentOfDeployedArtifact.toSet[Uml#Deployment]
}
