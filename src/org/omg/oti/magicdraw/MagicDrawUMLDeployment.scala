package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLDeployment 
  extends UMLDeployment[MagicDrawUML]
  with MagicDrawUMLDependency {

  override protected def e: Uml#Deployment
  import ops._

}
