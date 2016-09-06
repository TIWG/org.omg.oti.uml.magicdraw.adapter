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

import scala.collection.JavaConversions._
import org.omg.oti.uml.read.api._

import scala.StringContext
import scala.Predef.String
import scala.collection.immutable._

trait MagicDrawUMLComponent 
  extends MagicDrawUMLClass
  with UMLComponent[MagicDrawUML] {

  override protected def e: Uml#Component
  def getMagicDrawComponent = e
  import ops._

  override def isIndirectlyInstantiated = e.isIndirectlyInstantiated
  
  override def provided = e.getProvided.to[Set]
  
  override def realization = e.getRealization.to[Set]
  
  override def required = e.getRequired.to[Set]
  
  override def packagedElement: Set[UMLPackageableElement[Uml]] =
    e.getPackagedElement.to[Set]
  
}

case class MagicDrawUMLComponentImpl( val e: MagicDrawUML#Component, ops: MagicDrawUMLUtil )
extends MagicDrawUMLComponent
with sext.PrettyPrinting.TreeString
with sext.PrettyPrinting.ValueTreeString {

  override def toString: String =
    s"MagicDrawUMLComponent(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString: String =
    toString

  override def valueTreeString: String =
    toString
}