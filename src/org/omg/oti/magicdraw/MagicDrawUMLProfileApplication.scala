package org.omg.oti.magicdraw

import org.omg.oti.UMLProfileApplication

trait MagicDrawUMLProfileApplication extends UMLProfileApplication[MagicDrawUML] with MagicDrawUMLDirectedRelationship {
  override protected def e: Uml#ProfileApplication
  
  import ops._
  
  override def isStrict = e.isStrict()
}