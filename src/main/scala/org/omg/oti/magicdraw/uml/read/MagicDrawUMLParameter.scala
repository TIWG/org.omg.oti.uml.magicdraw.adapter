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
import scala.collection.immutable._
import scala.{Boolean,Option,None,Some,StringContext}
import scala.Predef.String

import org.omg.oti.uml.UMLError
import org.omg.oti.uml.read.api._

trait MagicDrawUMLParameter 
  extends MagicDrawUMLConnectableElement
  with MagicDrawUMLMultiplicityElement
  with UMLParameter[MagicDrawUML] {

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
    for { result <- Option( e.getDefaultValue ) } yield result
  
  // 9.9
  override def direction: Option[UMLParameterDirectionKind.Value] =
    Option(e.getDirection)
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
    Option(e.getEffect)
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
  override def parameterSet: Set[UMLParameterSet[Uml]] =
    e.getParameterSet.to[Set]

  // 9.9
  override def parameter_activityParameterNode: Set[UMLActivityParameterNode[Uml]] =
    e.get_activityParameterNodeOfParameter().to[Set]

  // 9.9
  override def result_opaqueExpression: Set[UMLOpaqueExpression[Uml]] =
    throw UMLError.umlAdaptationError(s"MagicDrawUMLParameter.result_opaqueExpression is undefined!")

}

case class MagicDrawUMLParameterImpl
(e: MagicDrawUML#Parameter, ops: MagicDrawUMLUtil)
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