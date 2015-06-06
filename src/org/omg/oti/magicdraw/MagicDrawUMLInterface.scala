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
package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLInterface 
  extends UMLInterface[MagicDrawUML]
  with MagicDrawUMLClassifier {

  override protected def e: Uml#Interface
  import ops._

	override def nestedClassifier: Seq[UMLClassifier[Uml]] =
    e.getNestedClassifier.toSeq
    
  override def ownedAttribute: Seq[UMLProperty[Uml]] =
    e.getOwnedAttribute.toSeq
    
  override def ownedOperation: Seq[UMLOperation[Uml]] =
    e.getOwnedOperation.toSeq
    

  override def provided_port: Set[UMLPort[Uml]] = ???
  
  override def required_port: Set[UMLPort[Uml]] = ???

  override def required_component: Set[UMLComponent[Uml]] = ???
  
  override def provided_component: Set[UMLComponent[Uml]] = ???
  
  override def protocol: Option[UMLProtocolStateMachine[Uml]] =
    Option.apply( e.getProtocol )
    
  override def contract_interfaceRealization: Set[UMLInterfaceRealization[Uml]] =
    umlInterfaceRealization( e.get_interfaceRealizationOfContract.toSet )
   
  override def redefinedInterface: Set[UMLInterface[Uml]] =
    umlInterface( e.getRedefinedInterface.toSet )

  override def redefinedInterface_interface: Set[UMLInterface[Uml]] =
    umlInterface( e.get_interfaceOfRedefinedInterface.toSet )
}