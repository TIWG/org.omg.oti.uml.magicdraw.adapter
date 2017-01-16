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

trait MagicDrawUMLCombinedFragment 
  extends MagicDrawUMLInteractionFragment
  with UMLCombinedFragment[MagicDrawUML] {

  override protected def e: Uml#CombinedFragment
  def getMagicDrawCombinedFragment: Uml#CombinedFragment = e

  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  override def cfragmentGate
  : Set[UMLGate[Uml]]
  = e.getCfragmentGate.to[Set]
  
  override def interactionOperator
  : Option[UMLInteractionOperatorKind.Value]
  = Option.apply(e.getInteractionOperator) match {
    case None =>
      None
    case Some(v) => v match {
      case com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.ALT =>
        Some(UMLInteractionOperatorKind.alt)
      case com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.ASSERT =>
        Some(UMLInteractionOperatorKind.assert)
      case com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.BREAK =>
        Some(UMLInteractionOperatorKind.break)
      case com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.CONSIDER =>
        Some(UMLInteractionOperatorKind.consider)
      case com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.CRITICAL =>
        Some(UMLInteractionOperatorKind.critical)
      case com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.IGNORE =>
        Some(UMLInteractionOperatorKind.ignore)
      case com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.LOOP =>
        Some(UMLInteractionOperatorKind.loop)
      case com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.NEG =>
        Some(UMLInteractionOperatorKind.neg)
      case com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.OPT =>
        Some(UMLInteractionOperatorKind.opt)
      case com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.PAR =>
        Some(UMLInteractionOperatorKind.par)
      case com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.SEQ =>
        Some(UMLInteractionOperatorKind.seq)
      case com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.STRICT =>
        Some(UMLInteractionOperatorKind.strict)
    }
  }

  override def operand
  : Seq[UMLInteractionOperand[Uml]]
  = e.getOperand.to[Seq]

}

case class MagicDrawUMLCombinedFragmentImpl
(e: MagicDrawUML#CombinedFragment, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLCombinedFragment
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLCombinedFragmentImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
  }

  override def toString
  : String
  = s"MagicDrawUMLCombinedFragment(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}