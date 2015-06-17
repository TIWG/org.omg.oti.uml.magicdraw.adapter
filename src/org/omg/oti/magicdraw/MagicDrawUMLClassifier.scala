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
    
  override def attribute: Seq[UMLProperty[Uml]] =
    e.getAttribute.toSeq
  
  override def inheritedMember: Set[UMLNamedElement[Uml]] =
    e.getInheritedMember.toSet[Uml#NamedElement]
  
  override def isAbstract: Boolean =
    e.isAbstract
  
  override def isFinalSpecialization: Boolean =
    e.isFinalSpecialization
  
  override def ownedTemplateSignature: Option[UMLRedefinableTemplateSignature[Uml]] =
    Option.apply( e.getOwnedTemplateSignature )
  
  override def powertypeExtent: Set[UMLGeneralizationSet[Uml]] =
    e.getPowertypeExtent.toSet[Uml#GeneralizationSet]
  
  override def representation: Option[UMLCollaborationUse[Uml]] =
    Option.apply(e.getRepresentation)
  
  override def useCase: Set[UMLUseCase[Uml]] =
    e.getUseCase.toSet[Uml#UseCase]
  
  override def classifier_readIsClassifiedObjectAction: Set[UMLReadIsClassifiedObjectAction[Uml]] =
    e.get_readIsClassifiedObjectActionOfClassifier.toSet[Uml#ReadIsClassifiedObjectAction]
  
  override def realizingClassifier_componentRealization: Set[UMLComponentRealization[Uml]] =
    e.get_componentRealizationOfRealizingClassifier.toSet[Uml#ComponentRealization]
  
  override def classifier_readExtentAction: Option[UMLReadExtentAction[Uml]] = {
    val actions = e.get_readExtentActionOfClassifier
    require(actions.size <= 1)
    if (actions.isEmpty) None
    else Some(actions.iterator.next)
  }
  
  override def unmarshallType_unmarshallAction: Set[UMLUnmarshallAction[Uml]] =
    e.get_unmarshallActionOfUnmarshallType.toSet[Uml#UnmarshallAction]
  
  override def represented_representation: Set[UMLInformationItem[Uml]] =
    e.get_informationItemOfRepresented.toSet[Uml#InformationItem]
  
  override def newClassifier_reclassifyObjectAction: Set[UMLReclassifyObjectAction[Uml]] =
    e.get_reclassifyObjectActionOfNewClassifier.toSet[Uml#ReclassifyObjectAction]
  
  override def oldClassifier_reclassifyObjectAction: Set[UMLReclassifyObjectAction[Uml]] =
    e.get_reclassifyObjectActionOfOldClassifier.toSet[Uml#ReclassifyObjectAction]
  
  override def contract_substitution: Set[UMLSubstitution[Uml]] =
    e.get_substitutionOfContract.toSet[Uml#Substitution]
  
  override def redefinitionContext_redefinableElement: Set[UMLRedefinableElement[Uml]] =
    e.get_redefinableElementOfRedefinitionContext.toSet[Uml#RedefinableElement]
  
  override def classifier_instanceSpecification: Set[UMLInstanceSpecification[Uml]] =
    e.get_instanceSpecificationOfClassifier.toSet[Uml#InstanceSpecification]
  
  override def exceptionType_exceptionHandler: Set[UMLExceptionHandler[Uml]] =
    e.get_exceptionHandlerOfExceptionType.toSet[Uml#ExceptionHandler]
  
  override def constrainingClassifier_classifierTemplateParameter: Set[UMLClassifierTemplateParameter[Uml]] =
    e.get_classifierTemplateParameterOfConstrainingClassifier.toSet[Uml#ClassifierTemplateParameter]
  
  override def conveyed_conveyingFlow: Set[UMLInformationFlow[Uml]] =
    e.get_informationFlowOfConveyed.toSet[Uml#InformationFlow]
  
  override def context_action: Set[UMLAction[Uml]] =
    ???
  
  override def classifier_createObjectAction: Set[UMLCreateObjectAction[Uml]] =
    e.get_createObjectActionOfClassifier.toSet[Uml#CreateObjectAction]
  
  override def general_generalization: Set[UMLGeneralization[Uml]] =
    e.get_generalizationOfGeneral.toSet[Uml#Generalization]

}