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

trait MagicDrawUMLNamedElement 
  extends UMLNamedElement[MagicDrawUML]
  with MagicDrawUMLElement {

  override protected def e: Uml#NamedElement
  import ops._
  
  override def clientDependency = e.getClientDependency.toSet[Uml#Dependency]
  
  override def name = e.getName match {
    case null => None
    case "" => None
    case n => Some(n)
  }
  
  override def nameExpression = Option.apply( e.getNameExpression )
  
  override def namespace = Option.apply( e.getNamespace )
  
  override def qualifiedName = Option.apply( e.getQualifiedName )
  
  override def visibility = e.getVisibility match {
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PUBLIC => Some( UMLVisibilityKind.public )
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PRIVATE => Some( UMLVisibilityKind._private )
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PROTECTED => Some( UMLVisibilityKind._protected )
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PACKAGE => Some( UMLVisibilityKind._package )
  }
  
  override def event_durationObservation = e.get_durationObservationOfEvent.toSet[Uml#DurationObservation]
  
  override def event_timeObservation = e.get_timeObservationOfEvent.toSet[Uml#TimeObservation]
  
  override def supplier_supplierDependency = e.getSupplierDependency.toSet[Uml#Dependency]
  
  override def informationSource_informationFlow = e.get_informationFlowOfInformationSource.toSet[Uml#InformationFlow]
  
  override def member_memberNamespace = e.get_namespaceOfMember.toSet[Uml#Namespace]

  override def signature_message = e.get_messageOfSignature.toSet[Uml#Message]
  
  override def informationTarget_informationFlow = e.get_informationFlowOfInformationTarget.toSet[Uml#InformationFlow]
  
  override def message_considerIgnoreFragment = e.get_considerIgnoreFragmentOfMessage.toSet[Uml#ConsiderIgnoreFragment]
  
  override def inheritedMember_inheritingClassifier = 
    member_memberNamespace selectByKindOf 
    { case cls: UMLClassifier[Uml] => cls } filter 
    { case cls => cls.inheritedMember.contains( this ) }

  // MD-specific
  
  def setName( name: String ) = e.setName( name )
  
}