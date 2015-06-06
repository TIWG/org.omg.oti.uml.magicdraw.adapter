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

trait MagicDrawUMLClassifier 
  extends UMLClassifier[MagicDrawUML]
  with MagicDrawUMLType
  with MagicDrawUMLNamespace
  with MagicDrawUMLRedefinableElement
  with MagicDrawUMLTemplateableElement {

  override protected def e: Uml#Classifier
  import ops._
    
  override def templateParameter: Option[UMLClassifierTemplateParameter[Uml]] = Option.apply( e.getTemplateParameter )
    
  override def attribute = e.getAttribute.toSeq
  
  override def inheritedMember = e.getInheritedMember.toSet[Uml#NamedElement]
  
  override def isAbstract = e.isAbstract
  
  override def isFinalSpecialization = e.isFinalSpecialization
  
  override def ownedTemplateSignature = Option.apply( e.getOwnedTemplateSignature )
  
  override def powertypeExtent = e.getPowertypeExtent.toSet[Uml#GeneralizationSet]
  
  override def representation = Option.apply(e.getRepresentation)
  
  override def useCase = e.getUseCase.toSet[Uml#UseCase]
  
  override def classifier_readIsClassifiedObjectAction = e.get_readIsClassifiedObjectActionOfClassifier.toSet[Uml#ReadIsClassifiedObjectAction]
  
  override def realizingClassifier_componentRealization = e.get_componentRealizationOfRealizingClassifier.toSet[Uml#ComponentRealization]
  
  override def classifier_readExtentAction: Option[UMLReadExtentAction[Uml]] = {
    val actions = e.get_readExtentActionOfClassifier
    require(actions.size <= 1)
    if (actions.isEmpty) None
    else Some(actions.iterator.next)
  }
  
  override def unmarshallType_unmarshallAction = e.get_unmarshallActionOfUnmarshallType.toSet[Uml#UnmarshallAction]
  
  override def represented_representation = e.get_informationItemOfRepresented.toSet[Uml#InformationItem]
  
  override def newClassifier_reclassifyObjectAction = e.get_reclassifyObjectActionOfNewClassifier.toSet[Uml#ReclassifyObjectAction]
  
  override def oldClassifier_reclassifyObjectAction = e.get_reclassifyObjectActionOfOldClassifier.toSet[Uml#ReclassifyObjectAction]
  
  override def contract_substitution = e.get_substitutionOfContract.toSet[Uml#Substitution]
  
  override def redefinitionContext_redefinableElement = e.get_redefinableElementOfRedefinitionContext.toSet[Uml#RedefinableElement]
  
  override def classifier_instanceSpecification = e.get_instanceSpecificationOfClassifier.toSet[Uml#InstanceSpecification]
  
  override def exceptionType_exceptionHandler = e.get_exceptionHandlerOfExceptionType.toSet[Uml#ExceptionHandler]
  
  override def constrainingClassifier_classifierTemplateParameter = e.get_classifierTemplateParameterOfConstrainingClassifier.toSet[Uml#ClassifierTemplateParameter]
  
  override def conveyed_conveyingFlow = e.get_informationFlowOfConveyed.toSet[Uml#InformationFlow]
  
  override def context_action = ???
  
  override def classifier_createObjectAction = e.get_createObjectActionOfClassifier.toSet[Uml#CreateObjectAction]
  
  override def general_generalization = e.get_generalizationOfGeneral.toSet[Uml#Generalization]

}