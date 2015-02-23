package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLGeneralization 
  extends UMLGeneralization[MagicDrawUML]
  with MagicDrawUMLDirectedRelationship {

  override protected def e: Uml#Generalization
  import ops._
  
}