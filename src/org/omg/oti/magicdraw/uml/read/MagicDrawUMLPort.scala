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
import org.omg.oti.uml._
import org.omg.oti.uml.read.api._
import org.omg.oti.uml.read.operations._

import scala.reflect.runtime.universe
import scala.reflect._

trait MagicDrawUMLPort 
  extends UMLPort[MagicDrawUML]
  with MagicDrawUMLProperty {

  override protected def e: Uml#Port
  def getMagicDrawPort = e
  import ops._

  // 11.10
	override def isBehavior: Boolean =
    e.isBehavior
  
  // 11.10
  override def isConjugated: Boolean =
    e.isConjugated
  
  // 11.10
  override def isService: Boolean =
    e.isService

  override def ownedPort_encapsulatedClassifier: Option[UMLEncapsulatedClassifier[Uml]] =
    Option.apply(e.getClassifier) match {
      case Some(c) => umlClassifier(c) match {
        case ec: UMLEncapsulatedClassifier[Uml] =>
          Some(ec)
        case _ =>
          None
      }
      case _ => None
    }

  // 11.10
  override def protocol: Option[UMLProtocolStateMachine[Uml]] = 
    Option.apply( e.getProtocol )
  
  // 11.10
  override def provided: Set[UMLInterface[Uml]] =
    e.getProvided.toSet[Uml#Interface]
  
  // 11.10
  override def required: Set[UMLInterface[Uml]] =
    e.getRequired.toSet[Uml#Interface]
  
  // 13.2
	override def port_trigger: Set[UMLTrigger[Uml]] =
    e.get_triggerOfPort.toSet[Uml#Trigger]
  
  // 16.13
  override def onPort_invocationAction: Set[UMLInvocationAction[Uml]] =
    e.get_invocationActionOfOnPort.toSet[Uml#InvocationAction]
  
  override def redefinedPort: Set[UMLPort[Uml]] =
    umlPort( e.getRedefinedPort.toSet )
    
  override def redefinedPort_port: Set[UMLPort[Uml]] =
    umlPort( e.get_portOfRedefinedPort.toSet )
}