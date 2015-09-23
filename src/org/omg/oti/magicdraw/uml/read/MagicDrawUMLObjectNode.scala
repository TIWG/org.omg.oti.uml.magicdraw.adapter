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

import scala.{Boolean,Option,None,Some}
import scala.collection.JavaConversions._
import scala.collection.immutable._

import org.omg.oti.uml.read.api._
import com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ObjectNodeOrderingKindEnum

trait MagicDrawUMLObjectNode
  extends UMLObjectNode[MagicDrawUML]
  with MagicDrawUMLActivityNode
  with MagicDrawUMLTypedElement {

  override protected def e: Uml#ObjectNode
  def getMagicDrawObjectNode = e

  override implicit val umlOps = ops
  import umlOps._

  override def inState: Set[UMLState[Uml]] =
    e.getInState.toSet[Uml#State]
  
  override def isControlType: Boolean =
    e.isControlType
    
  override def exceptionInput_exceptionHandler: Set[UMLExceptionHandler[Uml]] =
    e.get_exceptionHandlerOfExceptionInput.toSet[Uml#ExceptionHandler]

  override def selection: Option[UMLBehavior[Uml]] =
    Option.apply( e.getSelection )

  override def ordering: Option[UMLObjectNodeOrderingKind.Value] =
    Option.apply(e.getOrdering)
    .fold[Option[UMLObjectNodeOrderingKind.Value]](None) {
      case ObjectNodeOrderingKindEnum.FIFO      => Some(UMLObjectNodeOrderingKind.FIFO)
      case ObjectNodeOrderingKindEnum.LIFO      => Some(UMLObjectNodeOrderingKind.LIFO)
      case ObjectNodeOrderingKindEnum.ORDERED   => Some(UMLObjectNodeOrderingKind.ordered)
      case ObjectNodeOrderingKindEnum.UNORDERED => Some(UMLObjectNodeOrderingKind.unordered)
    }

  override def upperBound: Option[UMLValueSpecification[Uml]] =
    Option.apply(e.getUpperBound)

}