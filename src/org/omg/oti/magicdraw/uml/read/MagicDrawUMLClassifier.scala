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
import scala.collection.Iterable

import org.omg.oti.uml.read.api._

import scala.{Boolean,Option,None,Some}
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