/*
 *
 * License Terms
 *
 * Copyright (c) 2014-2016, California Institute of Technology ("Caltech").
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
package org.omg.oti.magicdraw.uml.read

import scala.collection.JavaConversions._
import scala.collection.immutable._
import scala.{Boolean,Option,None,Some,StringContext}
import scala.Predef.String
import org.omg.oti.uml.read.api._

trait MagicDrawUMLPort 
  extends MagicDrawUMLProperty
  with UMLPort[MagicDrawUML] {

  override protected def e: Uml#Port
  def getMagicDrawPort = e
  override implicit val umlOps = ops
  import umlOps._

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
    for {
      c <- Option(e.getClassifier)
      result <- umlClassifier(c) match {
        case ec: UMLEncapsulatedClassifier[Uml] =>
          Some(ec)
        case _ =>
          None
      }
    } yield result

  // 11.10
  override def protocol: Option[UMLProtocolStateMachine[Uml]] = 
    for { result <- Option( e.getProtocol ) } yield result
  
  // 11.10
  override def provided: Set[UMLInterface[Uml]] =
    e.getProvided.to[Set]
  
  // 11.10
  override def required: Set[UMLInterface[Uml]] =
    e.getRequired.to[Set]
  
  // 13.2
	override def port_trigger: Set[UMLTrigger[Uml]] =
    e.get_triggerOfPort.to[Set]
  
  // 16.13
  override def onPort_invocationAction: Set[UMLInvocationAction[Uml]] =
    e.get_invocationActionOfOnPort.to[Set]
  
  override def redefinedPort: Set[UMLPort[Uml]] =
    e.getRedefinedPort.to[Set]
    
  override def redefinedPort_port: Set[UMLPort[Uml]] =
    e.get_portOfRedefinedPort.to[Set]
}

case class MagicDrawUMLPortImpl
( val e: MagicDrawUML#Port, ops: MagicDrawUMLUtil )
  extends MagicDrawUMLPort
  with sext.PrettyPrinting.TreeString
  with sext.PrettyPrinting.ValueTreeString {

  override def toString: String =
    s"MagicDrawUMLPort(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString: String =
    toString

  override def valueTreeString: String =
    toString
}