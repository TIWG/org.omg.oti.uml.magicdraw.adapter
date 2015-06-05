/*
 *
 * License Terms
 *
 * Copyright (c) 2014-2015, California Institute of Technology ("Caltech").
 * U.S. Government sponsorship acknowledged.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * *   Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * *   Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the
 *    distribution.
 *
 * *   Neither the name of Caltech nor its operating division, the Jet
 *    Propulsion Laboratory, nor the names of its contributors may be
 *    used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import scala.language.postfixOps

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLOperation 
  extends UMLOperation[MagicDrawUML]
  with MagicDrawUMLBehavioralFeature
  with MagicDrawUMLParameterableElement
  with MagicDrawUMLTemplateableElement {

  override protected def e: Uml#Operation
  import ops._
  
  override def bodyCondition = Option.apply( e.getBodyCondition )
  
  override def isOrdered = e.isOrdered
  
  override def isQuery = e.isQuery
  
  override def isUnique = e.isQuery
  
  override def lower = Some( e.getLower )
    
  override def ownedParameter = e.getOwnedParameter.toSeq
  
  override def postcondition = e.getPostcondition.toSet[Uml#Constraint]

  override def precondition = e.getPrecondition.toSet[Uml#Constraint]
  
  override def raisedException = e.getRaisedException.toSet[Uml#Type]
  
  override def templateParameter = Option.apply( e.getTemplateParameter )
  
  override def _type = Option.apply( e.getType )
  
  override def upper = Some( e.getUpper )
  
  override def operation_callOperationAction = e.get_callOperationActionOfOperation.toSet[Uml#CallOperationAction]
  
  override def referred_protocolTransition = ???
  
  override def operation_callEvent = e.get_callEventOfOperation.toSet[Uml#CallEvent]
}