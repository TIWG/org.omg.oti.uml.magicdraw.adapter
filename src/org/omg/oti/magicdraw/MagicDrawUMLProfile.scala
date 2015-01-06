package org.omg.oti.magicdraw

import org.omg.oti.UMLProfile

trait MagicDrawUMLProfile extends UMLProfile[MagicDrawUML] with MagicDrawUMLPackage {
  override protected def e: Uml#Profile
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
}