/*
 * Copyright 2014 California Institute of Technology ("Caltech").
 * U.S. Government sponsorship acknowledged.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * License Terms
 */

package org.omg.oti.magicdraw.uml.read

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper

import org.omg.oti.uml.read.api._

import scala.{Boolean,Option}

trait MagicDrawUMLStereotype 
  extends MagicDrawUMLClass
  with UMLStereotype[MagicDrawUML] {

  override protected def e: Uml#Stereotype
  def getMagicDrawStereotype = e
  import ops._
  
  def isStereotypeApplied( element: Uml#Element ): Boolean =
    StereotypesHelper.hasStereotype( element, e )

  override def ownedStereotype_owningPackage: Option[UMLPackage[Uml]] =
    for { result <- Option(e.getOwningPackage) } yield result

}

case class MagicDrawUMLStereotypeImpl
(e: MagicDrawUML#Stereotype, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLStereotype