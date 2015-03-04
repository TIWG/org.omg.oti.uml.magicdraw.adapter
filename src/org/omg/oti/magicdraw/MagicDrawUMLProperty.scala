package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import scala.language.postfixOps
import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLProperty 
  extends UMLProperty[MagicDrawUML]
  with MagicDrawUMLStructuralFeature
  with MagicDrawUMLConnectableElement
  with MagicDrawUMLDeploymentTarget {

  override protected def e: Uml#Property
  import ops._
  
  override def aggregation: UMLAggregationKind.Value = 
    e.getAggregation match {
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.AggregationKindEnum.COMPOSITE => UMLAggregationKind.composite
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.AggregationKindEnum.NONE => UMLAggregationKind.none
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.AggregationKindEnum.SHARED => UMLAggregationKind.shared
  }
  
  override def association = Option.apply( e.getAssociation )
  
  override def associationEnd = Option.apply( e.getAssociationEnd )
  
  override def defaultValue = Option.apply( e.getDefaultValue )
  
  override def isComposite = e.isComposite
  
  override def isDerived = e.isDerived
  
  override def isDerivedUnion = e.isDerivedUnion
  
  override def isID = e.isID
    
  override def opposite = association match {
    case None => None
    case Some( a ) => a.memberEnd filter (_ != this) headOption
  }
    
	override def qualifier: Seq[UMLProperty[Uml]] = 
    e.getQualifier.toSeq
  
  override def subsettedProperty = e.getSubsettedProperty.toSet[Uml#Property]
  
	override def returnValueRecipient_interactionUse: Set[UMLInteractionUse[Uml]] = ???
    
	override def qualifier_readLinkObjectEndQualifierAction: Option[UMLReadLinkObjectEndQualifierAction[Uml]] = ???

	override def qualifier_qualifierValue: Set[UMLQualifierValue[Uml]] = ???
    
	override def part_structuredClassifier: Option[UMLStructuredClassifier[Uml]] = ???
  
	override def end_linkEndData: Set[UMLLinkEndData[Uml]] = ???
  
	override def end_readLinkObjectEndAction: Option[UMLReadLinkObjectEndAction[Uml]] = ???

	override def partWithPort_connectorEnd: Set[UMLConnectorEnd[Uml]] = ???
  
	override def definingEnd_connectorEnd: Set[UMLConnectorEnd[Uml]] = ???
  
	override def attribute_classifier: Option[UMLClassifier[Uml]] = ???
  
  override def navigableOwnedEnd_association = Option.apply( e.get_associationOfNavigableOwnedEnd )
  
  override def opposite_property: Option[UMLProperty[Uml]] = association match {
    case None => None
    case Some( a ) => a.memberEnd filter (_ != this) headOption
  }

  override def redefinedProperty_property: Set[UMLProperty[Uml]] = ???
  
  override def subsettedProperty_property: Set[UMLProperty[Uml]] = ???
  
}