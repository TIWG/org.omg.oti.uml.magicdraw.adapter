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

import org.omg.oti.uml.read.api._

import scala.{Boolean,Option}
import scala.Predef.{???,require}

trait MagicDrawUMLClassifier 
  extends MagicDrawUMLType
  with MagicDrawUMLNamespace
  with MagicDrawUMLRedefinableElement
  with MagicDrawUMLTemplateableElement
  with UMLClassifier[MagicDrawUML] {

  override protected def e: Uml#Classifier
  def getMagicDrawClassifier = e
  override implicit val umlOps = ops
  import umlOps._
    
  override def templateParameter: Option[UMLClassifierTemplateParameter[Uml]] =
    for { result <- Option.apply( e.getTemplateParameter ) } yield result
    
  override def attribute: Seq[UMLProperty[Uml]] =
    e.getAttribute.to[Seq]

  override def isAbstract: Boolean =
    e.isAbstract
  
  override def isFinalSpecialization: Boolean =
    e.isFinalSpecialization

  override def general_classifier: Set[UMLClassifier[Uml]] =
    for {
      g <- e.get_generalizationOfGeneral.to[Set]
      s <- g.specific
    } yield s
  
  override def powertypeExtent: Set[UMLGeneralizationSet[Uml]] =
    e.getPowertypeExtent.to[Set]

  override def useCase: Set[UMLUseCase[Uml]] =
    e.getUseCase.to[Set]
  
  override def classifier_readIsClassifiedObjectAction: Set[UMLReadIsClassifiedObjectAction[Uml]] =
    e.get_readIsClassifiedObjectActionOfClassifier.to[Set]
  
  override def realizingClassifier_componentRealization: Set[UMLComponentRealization[Uml]] =
    e.get_componentRealizationOfRealizingClassifier.to[Set]
  
  override def classifier_readExtentAction: Option[UMLReadExtentAction[Uml]] = {
    val actions = e.get_readExtentActionOfClassifier
    require(actions.size <= 1)
    for { result <- actions.headOption } yield result
  }
  
  override def unmarshallType_unmarshallAction: Set[UMLUnmarshallAction[Uml]] =
    e.get_unmarshallActionOfUnmarshallType.to[Set]
  
  override def represented_representation: Set[UMLInformationItem[Uml]] =
    e.get_informationItemOfRepresented.to[Set]
  
  override def newClassifier_reclassifyObjectAction: Set[UMLReclassifyObjectAction[Uml]] =
    e.get_reclassifyObjectActionOfNewClassifier.to[Set]
  
  override def oldClassifier_reclassifyObjectAction: Set[UMLReclassifyObjectAction[Uml]] =
    e.get_reclassifyObjectActionOfOldClassifier.to[Set]
  
  override def contract_substitution: Set[UMLSubstitution[Uml]] =
    e.get_substitutionOfContract.to[Set]
  
  override def redefinitionContext_redefinableElement: Set[UMLRedefinableElement[Uml]] =
    e.get_redefinableElementOfRedefinitionContext.to[Set]
  
  override def classifier_instanceSpecification: Set[UMLInstanceSpecification[Uml]] =
    e.get_instanceSpecificationOfClassifier.to[Set]
  
  override def exceptionType_exceptionHandler: Set[UMLExceptionHandler[Uml]] =
    e.get_exceptionHandlerOfExceptionType.to[Set]
  
  override def constrainingClassifier_classifierTemplateParameter: Set[UMLClassifierTemplateParameter[Uml]] =
    e.get_classifierTemplateParameterOfConstrainingClassifier.to[Set]
  
  override def conveyed_conveyingFlow: Set[UMLInformationFlow[Uml]] =
    e.get_informationFlowOfConveyed.to[Set]
  
  override def context_action: Set[UMLAction[Uml]] =
    ???
  
  override def classifier_createObjectAction: Set[UMLCreateObjectAction[Uml]] =
    e.get_createObjectActionOfClassifier.to[Set]

}