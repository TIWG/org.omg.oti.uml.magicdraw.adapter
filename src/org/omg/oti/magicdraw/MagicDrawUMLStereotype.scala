package org.omg.oti.magicdraw

import org.omg.oti.UMLStereotype
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper

trait MagicDrawUMLStereotype extends UMLStereotype[MagicDrawUML] with MagicDrawUMLClass {
  override protected def e: Uml#Stereotype
  
  implicit val ops: MagicDrawUMLUtil
  import ops._
  
  def isStereotypeApplied( element: Uml#Element ): Boolean = StereotypesHelper.hasStereotype( element, e )
}