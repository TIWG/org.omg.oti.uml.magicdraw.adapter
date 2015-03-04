package org.omg.oti.magicdraw

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLStereotype 
  extends UMLStereotype[MagicDrawUML]
  with MagicDrawUMLClass {

  override protected def e: Uml#Stereotype
  import ops._
  
  def isStereotypeApplied( element: Uml#Element ): Boolean = StereotypesHelper.hasStereotype( element, e )
}