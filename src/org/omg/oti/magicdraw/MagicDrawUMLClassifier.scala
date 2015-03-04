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
  
  override def classifier_readExtentAction = Option.apply(e.get_readExtentActionOfClassifier)
  
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