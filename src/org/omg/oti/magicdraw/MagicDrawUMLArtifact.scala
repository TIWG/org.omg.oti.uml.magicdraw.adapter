package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLArtifact 
  extends UMLArtifact[MagicDrawUML]
  with MagicDrawUMLClassifier
  with MagicDrawUMLDeployedArtifact {

  override protected def e: Uml#Artifact
  import ops._

}
