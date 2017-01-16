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

import scala.{Boolean,Option,None,Some}
import scala.collection.JavaConversions._
import scala.collection.immutable._

import org.omg.oti.uml.read.api._
import com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ObjectNodeOrderingKindEnum

trait MagicDrawUMLObjectNode
  extends MagicDrawUMLActivityNode
  with MagicDrawUMLTypedElement
  with UMLObjectNode[MagicDrawUML] {

  override protected def e: Uml#ObjectNode
  def getMagicDrawObjectNode: Uml#ObjectNode = e

  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._

  override def inState
  : Set[UMLState[Uml]]
  = e.getInState.to[Set]
  
  override def isControlType: Boolean = e.isControlType
    
  override def exceptionInput_exceptionHandler
  : Set[UMLExceptionHandler[Uml]]
  = e.get_exceptionHandlerOfExceptionInput.to[Set]

  override def selection
  : Option[UMLBehavior[Uml]]
  = for { result <- Option( e.getSelection ) } yield result

  override def ordering
  : Option[UMLObjectNodeOrderingKind.Value]
  = Option(e.getOrdering)
    .fold[Option[UMLObjectNodeOrderingKind.Value]](None) {
      case ObjectNodeOrderingKindEnum.FIFO      => Some(UMLObjectNodeOrderingKind.FIFO)
      case ObjectNodeOrderingKindEnum.LIFO      => Some(UMLObjectNodeOrderingKind.LIFO)
      case ObjectNodeOrderingKindEnum.ORDERED   => Some(UMLObjectNodeOrderingKind.ordered)
      case ObjectNodeOrderingKindEnum.UNORDERED => Some(UMLObjectNodeOrderingKind.unordered)
    }

  override def upperBound
  : Option[UMLValueSpecification[Uml]]
  = for { result <- Option(e.getUpperBound) } yield result

}