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
import scala.{Any,Boolean,Int,Option,None,Some,StringContext}
import scala.Predef.String
import org.omg.oti.uml.read.api._

trait MagicDrawUMLPort 
  extends MagicDrawUMLProperty
  with UMLPort[MagicDrawUML] {

  override protected def e: Uml#Port
  def getMagicDrawPort: Uml#Port = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
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

  override def ownedPort_encapsulatedClassifier
  : Option[UMLEncapsulatedClassifier[Uml]]
  = for {
      c <- Option(e.getClassifier)
      result <- umlClassifier(c) match {
        case ec: UMLEncapsulatedClassifier[Uml] =>
          Some(ec)
        case _ =>
          None
      }
    } yield result

  // 11.10
  override def protocol
  : Option[UMLProtocolStateMachine[Uml]]
  = for { result <- Option( e.getProtocol ) } yield result
  
  // 11.10
  override def provided
  : Set[UMLInterface[Uml]]
  = e.getProvided.to[Set]
  
  // 11.10
  override def required
  : Set[UMLInterface[Uml]]
  = e.getRequired.to[Set]
  
  // 13.2
	override def port_trigger
  : Set[UMLTrigger[Uml]]
  = e.get_triggerOfPort.to[Set]
  
  // 16.13
  override def onPort_invocationAction
  : Set[UMLInvocationAction[Uml]]
  = e.get_invocationActionOfOnPort.to[Set]
  
  override def redefinedPort
  : Set[UMLPort[Uml]]
  = e.getRedefinedPort.to[Set]
    
  override def redefinedPort_port
  : Set[UMLPort[Uml]]
  = e.get_portOfRedefinedPort.to[Set]
}

case class MagicDrawUMLPortImpl
( e: MagicDrawUML#Port, ops: MagicDrawUMLUtil )
  extends MagicDrawUMLPort
  with sext.PrettyPrinting.TreeString
  with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLPortImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
    case _ =>
      false
  }

  override def toString
  : String
  = s"MagicDrawUMLPort(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}