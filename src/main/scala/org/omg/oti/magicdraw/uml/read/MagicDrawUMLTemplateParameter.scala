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

import scala.Option
import scala.Predef.???
import scala.collection.JavaConversions._
import scala.collection.immutable._
import scala.{Any,Boolean,Int,Option,StringContext}
import scala.Predef.String

trait MagicDrawUMLTemplateParameter 
  extends MagicDrawUMLElement
  with UMLTemplateParameter[MagicDrawUML] {

  override protected def e: Uml#TemplateParameter
  def getMagicDrawTemplateParameter: Uml#TemplateParameter = e
  import ops._

  override def default
  : Option[UMLParameterableElement[Uml]]
  = for { result <- Option( e.getDefault ) } yield result
  
  override def ownedParameteredElement
  : Option[UMLParameterableElement[Uml]]
  = for { result <- Option( e.getOwnedParameteredElement ) } yield result

  override def parameteredElement
  : Option[UMLParameterableElement[Uml]]
  = for { result <- Option(e.getParameteredElement) } yield result
  
  // 7.3
  override def parameter_templateSignature
  : Set[UMLTemplateSignature[Uml]]
  = e.get_templateSignatureOfParameter.to[Set]
  
  // 7.4
  override def formal_templateParameterSubstitution
  : Set[UMLTemplateParameterSubstitution[Uml]]
  = e.get_templateParameterSubstitutionOfFormal.to[Set]
   
  // 9.4
  override def inheritedParameter_redefinableTemplateSignature
  : Set[UMLRedefinableTemplateSignature[Uml]]
  = ???

}

case class MagicDrawUMLTemplateParameterImpl
(e: MagicDrawUML#TemplateParameter, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLTemplateParameter
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLTemplateParameterImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
    case _ =>
      false
  }

  override def toString
  : String
  = s"MagicDrawUMLTemplateParameter(ID=${e.getID})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}