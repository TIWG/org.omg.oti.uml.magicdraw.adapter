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
import scala.{Any,Boolean,Int,Option,Some,StringContext}
import scala.Predef.String

trait MagicDrawUMLPseudostate 
  extends MagicDrawUMLVertex
  with UMLPseudostate[MagicDrawUML] {

  override protected def e: Uml#Pseudostate
  def getMagicDrawPseudostate: Uml#Pseudostate = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  // 14.1
  override def kind
  : Option[UMLPseudostateKind.Value]
  = Option(e.getKind)
    .fold[Option[UMLPseudostateKind.Value]](Option.empty[UMLPseudostateKind.Value]){
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.INITIAL =>
      Some(UMLPseudostateKind.initial)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.DEEPHISTORY =>
      Some(UMLPseudostateKind.deepHistory)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.SHALLOWHISTORY =>
      Some(UMLPseudostateKind.shallowHistory)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.JOIN =>
      Some(UMLPseudostateKind.join)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.FORK =>
      Some(UMLPseudostateKind.fork)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.JUNCTION =>
      Some(UMLPseudostateKind.junction)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.CHOICE =>
      Some(UMLPseudostateKind.choice)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.ENTRYPOINT =>
      Some(UMLPseudostateKind.entryPoint)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.EXITPOINT =>
      Some(UMLPseudostateKind.exitPoint)
    case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.TERMINATE =>
      Some(UMLPseudostateKind.terminate)
  }
  
  // 14.1
  override def entry_connectionPointReference
  : Option[UMLConnectionPointReference[Uml]]
  = for { result <- e.get_connectionPointReferenceOfEntry.headOption } yield result
  
  // 14.1
  override def exit_connectionPointReference
  : Option[UMLConnectionPointReference[Uml]]
  = for { result <- e.get_connectionPointReferenceOfExit.headOption } yield result

}

case class MagicDrawUMLPseudostateImpl
(e: MagicDrawUML#Pseudostate, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLPseudostate
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLPseudostateImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
  }

  override def toString
  : String
  = s"MagicDrawUMLPseudoState(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}