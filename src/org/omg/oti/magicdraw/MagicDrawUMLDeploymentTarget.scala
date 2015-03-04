package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLDeploymentTarget 
  extends UMLDeploymentTarget[MagicDrawUML]
  with MagicDrawUMLNamedElement {

  override protected def e: Uml#DeploymentTarget
  import ops._

  override def deployment =
    e.getDeployment.toSet[Uml#Deployment]
}
