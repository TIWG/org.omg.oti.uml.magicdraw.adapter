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
import scala.{Any,Boolean,Int,Option,None,Some,StringContext}
import scala.Predef.String

trait MagicDrawUMLTransition 
  extends MagicDrawUMLNamespace
  with MagicDrawUMLRedefinableElement
  with UMLTransition[MagicDrawUML] {

  override protected def e: Uml#Transition
  def getMagicDrawTransition: Uml#Transition = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  override def kind
  : Option[UMLTransitionKind.Value]
  = Option(e.getKind)
    .fold[Option[UMLTransitionKind.Value]](None) {
      case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.TransitionKindEnum.EXTERNAL =>
        Some(UMLTransitionKind.external)
      case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.TransitionKindEnum.INTERNAL =>
        Some(UMLTransitionKind.internal)
      case com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.TransitionKindEnum.LOCAL =>
        Some(UMLTransitionKind.local)
    }

  override def source
  : Option[UMLVertex[Uml]]
  = for { result <- Option(e.getSource) } yield result

  override def target
  : Option[UMLVertex[Uml]]
  = for { result <- Option(e.getTarget) } yield result
  
  override def guard
  : Option[UMLConstraint[Uml]]
  = for { result <- Option(e.getGuard) } yield result

}

case class MagicDrawUMLTransitionImpl
(e: MagicDrawUML#Transition, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLTransition
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLTransitionImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
    case _ =>
      false
  }

  override def toString
  : String
  = s"MagicDrawUMLTransition(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}