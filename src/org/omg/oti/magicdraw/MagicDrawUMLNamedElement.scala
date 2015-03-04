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