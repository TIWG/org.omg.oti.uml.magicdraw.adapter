/*
 *
 *  License Terms
 *
 *  Copyright (c) 2014-2015, California Institute of Technology ("Caltech").
 *  U.S. Government sponsorship acknowledged.
 *
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are
 *  met:
 *
 *
 *   *   Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *   *   Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the
 *       distribution.
 *
 *   *   Neither the name of Caltech nor its operating division, the Jet
 *       Propulsion Laboratory, nor the names of its contributors may be
 *       used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *  IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 *  TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *  PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *  OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.omg.oti.magicdraw.uml.read

import scala.collection.JavaConversions._
import scala.collection.immutable._
import scala.{Boolean,Option,None,Some,StringContext}
import scala.Predef.String

import org.omg.oti.uml.UMLError
import org.omg.oti.uml.read.api._

trait MagicDrawUMLParameter 
  extends UMLParameter[MagicDrawUML]
  with MagicDrawUMLConnectableElement
  with MagicDrawUMLMultiplicityElement {

  override protected def e: Uml#Parameter
  def getMagicDrawParameter = e

  override implicit val umlOps = ops
  import umlOps._
  
	override def default: Option[String] = 
    e.getDefault match {
    case null => None
    case "" => None
    case s => Some( s )
  }

	// 9.9
  override def defaultValue: Option[UMLValueSpecification[Uml]] =
    Option.apply( e.getDefaultValue )
  
  // 9.9
  override def direction: Option[UMLParameterDirectionKind.Value] =
    Option.apply(e.getDirection)
    .fold[Option[UMLParameterDirectionKind.Value]](None) {
      case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ParameterDirectionKindEnum.IN =>
        Some(UMLParameterDirectionKind.in)
      case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ParameterDirectionKindEnum.OUT =>
        Some(UMLParameterDirectionKind.out)
      case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ParameterDirectionKindEnum.INOUT =>
        Some(UMLParameterDirectionKind.inout)
      case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ParameterDirectionKindEnum.RETURN =>
        Some(UMLParameterDirectionKind._return)
  }
  
  // 9.9
  override def effect: Option[UMLParameterEffectKind.Value] =
    Option.apply(e.getEffect)
    .fold[Option[UMLParameterEffectKind.Value]](None) {
      case com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ParameterEffectKindEnum.CREATE =>
        Some( UMLParameterEffectKind.create )
      case com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ParameterEffectKindEnum.READ =>
        Some( UMLParameterEffectKind.read )
      case com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ParameterEffectKindEnum.UPDATE =>
        Some( UMLParameterEffectKind.update )
      case com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ParameterEffectKindEnum.DELETE =>
        Some( UMLParameterEffectKind.delete )
    }
  
  // 9.9
  override def isException: Boolean =
    e.isException
  
  // 9.9
  override def isStream: Boolean =
    e.isStream
  
  // 9.9
  override def operation: Option[UMLOperation[Uml]] =
    Option.apply( e.getOperation )
  
  // 9.9
  override def parameterSet: Set[UMLParameterSet[Uml]] =
    e.getParameterSet.to[Set]
  
  // 9.9
  override def ownedParameter_ownerFormalParam: Option[UMLBehavioralFeature[Uml]] =
    Option.apply(e.getOwnerFormalParam)
  
  // 9.9
  override def parameter_activityParameterNode: Set[UMLActivityParameterNode[Uml]] =
    e.get_activityParameterNodeOfParameter().to[Set]
  
  // 9.9
  override def ownedParameter_behavior: Option[UMLBehavior[Uml]] =
    Option.apply(e.get_behaviorOfOwnedParameter())
  
  // 9.9
  override def result_opaqueExpression: Set[UMLOpaqueExpression[Uml]] =
    throw UMLError.umlAdaptationError(s"MagicDrawUMLParameter.result_opaqueExpression is undefined!")


}

case class MagicDrawUMLParameterImpl(val e: MagicDrawUML#Parameter, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLParameter
  with sext.PrettyPrinting.TreeString
  with sext.PrettyPrinting.ValueTreeString {

  override def toString: String =
    s"MagicDrawUMLParameter(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString: String =
    toString

  override def valueTreeString: String =
    toString
}
