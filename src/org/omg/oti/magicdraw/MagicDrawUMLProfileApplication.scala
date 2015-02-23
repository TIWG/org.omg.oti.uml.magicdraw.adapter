package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLProfileApplication 
  extends UMLProfileApplication[MagicDrawUML]
  with MagicDrawUMLDirectedRelationship {

  import ops._
  override protected def e: Uml#ProfileApplication
  
  override def isStrict = e.isStrict()
}