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
import scala.{Any,Boolean,Int,Option,StringContext}
import scala.Predef.String

trait MagicDrawUMLClassifierTemplateParameter 
  extends MagicDrawUMLTemplateParameter
  with UMLClassifierTemplateParameter[MagicDrawUML] {

  override protected def e: Uml#ClassifierTemplateParameter
  def getMagicDrawClassifierTemplateParameter: Uml#ClassifierTemplateParameter = e
  import ops._

  override def allowSubstitutable: Boolean =
    e.isAllowSubstitutable
    
  override def constrainingClassifier
  : Set[UMLClassifier[Uml]]
  = e.getConstrainingClassifier.to[Set]
 
  override def parameteredElement
  : Option[UMLClassifier[Uml]]
  = for { result <- Option.apply( e.getParameteredElement ) } yield result

}

case class MagicDrawUMLClassifierTemplateParameterImpl
(e: MagicDrawUML#ClassifierTemplateParameter, ops: MagicDrawUMLUtil)
  extends MagicDrawUMLClassifierTemplateParameter
    with sext.PrettyPrinting.TreeString
    with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLClassifierTemplateParameterImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
  }

  override def toString
  : String
  = s"MagicDrawUMLClassifierTemplateParameter(ID=${e.getID})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}