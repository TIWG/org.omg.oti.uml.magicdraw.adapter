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

import org.omg.oti.uml.read.api._

import scala.collection.JavaConversions._
import scala.collection.immutable._
import scala.{Any,Boolean,Int,Option,None,Some,StringContext}
import scala.Predef.String


trait MagicDrawUMLExpansionRegion 
  extends MagicDrawUMLStructuredActivityNode
  with UMLExpansionRegion[MagicDrawUML] {

  override protected def e: Uml#ExpansionRegion
  def getMagicDrawExpansionRegion: Uml#ExpansionRegion = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  override def inputElement
  : Set[UMLExpansionNode[Uml]]
  = e.getInputElement.to[Set]
  
  override def mode
  : Option[UMLExpansionKind.Value]
  = Option(e.getMode).fold[Option[UMLExpansionKind.Value]](None) {
    case com.nomagic.uml2.ext.magicdraw.activities.mdextrastructuredactivities.ExpansionKindEnum.ITERATIVE =>
      Some(UMLExpansionKind.iterative)
    case com.nomagic.uml2.ext.magicdraw.activities.mdextrastructuredactivities.ExpansionKindEnum.PARALLEL =>
      Some(UMLExpansionKind.parallel)
    case com.nomagic.uml2.ext.magicdraw.activities.mdextrastructuredactivities.ExpansionKindEnum.STREAM =>
      Some(UMLExpansionKind.stream)
  }

  override def outputElement
  : Set[UMLExpansionNode[Uml]]
  = e.getOutputElement.to[Set]

}

case class MagicDrawUMLExpansionRegionImpl
(e: MagicDrawUML#ExpansionRegion, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLExpansionRegion
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLExpansionRegionImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
  }

  override def toString
  : String
  = s"MagicDrawUMLExpansionRegion(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}