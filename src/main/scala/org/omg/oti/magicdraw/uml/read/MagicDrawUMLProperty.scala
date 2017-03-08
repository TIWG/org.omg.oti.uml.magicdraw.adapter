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

trait MagicDrawUMLProperty 
  extends MagicDrawUMLStructuralFeature
  with MagicDrawUMLConnectableElement
  with MagicDrawUMLDeploymentTarget
  with UMLProperty[MagicDrawUML] {

  override protected def e: Uml#Property
  def getMagicDrawProperty: Uml#Property = e
  override implicit val umlOps: MagicDrawUMLUtil = ops
  import umlOps._
  
  override def aggregation: Option[UMLAggregationKind.Value] =
    Option.apply(e.getAggregation)
    .fold[Option[UMLAggregationKind.Value]](None) {
      case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.AggregationKindEnum.COMPOSITE =>
        Some(UMLAggregationKind.composite)
      case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.AggregationKindEnum.NONE =>
        Some(UMLAggregationKind.none)
      case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.AggregationKindEnum.SHARED =>
        Some(UMLAggregationKind.shared)
  }

  override def defaultValue
  : Option[UMLValueSpecification[Uml]]
  = for { result <- Option( e.getDefaultValue ) } yield result
  
  override def isComposite: Boolean = e.isComposite
  
  override def isDerived: Boolean = e.isDerived
  
  override def isDerivedUnion: Boolean = e.isDerivedUnion
  
  override def isID: Boolean = e.isID
    
  override def qualifier
  : Seq[UMLProperty[Uml]]
  = e.getQualifier.to[Seq]
  
  override def subsettedProperty
  : Set[UMLProperty[Uml]]
  = e.getSubsettedProperty.to[Set]
  
  override def returnValueRecipient_interactionUse
  : Set[UMLInteractionUse[Uml]]
  = e.get_interactionUseOfReturnValueRecipient().to[Set]
    
  override def qualifier_readLinkObjectEndQualifierAction
  : Option[UMLReadLinkObjectEndQualifierAction[Uml]]
  = for {
      result <- e.get_readLinkObjectEndQualifierActionOfQualifier().toList.headOption
    } yield result

  override def qualifier_qualifierValue
  : Set[UMLQualifierValue[Uml]]
  = e.get_qualifierValueOfQualifier().to[Set]
    
  override def part_structuredClassifier
  : Option[UMLStructuredClassifier[Uml]]
  = for { result <- Option(e.get_structuredClassifierOfOwnedAttribute()) } yield result
  
  override def end_linkEndData
  : Set[UMLLinkEndData[Uml]]
  = e.get_linkEndDataOfEnd().to[Set]
  
  override def end_readLinkObjectEndAction
  : Option[UMLReadLinkObjectEndAction[Uml]]
  = for { result <- e.get_readLinkObjectEndActionOfEnd().toList.headOption } yield result

  override def partWithPort_connectorEnd
  : Set[UMLConnectorEnd[Uml]]
  = e.get_connectorEndOfPartWithPort().to[Set]
  
  override def definingEnd_connectorEnd
  : Set[UMLConnectorEnd[Uml]]
  = e.getEnd.to[Set]
  
  override def attribute_classifier
  : Option[UMLClassifier[Uml]]
  = for { result <- Option(e.getClassifier) } yield result
  
  override def navigableOwnedEnd_association
  : Option[UMLAssociation[Uml]]
  = for { result <- Option( e.get_associationOfNavigableOwnedEnd ) } yield result
  
  override def opposite_property
  : Option[UMLProperty[Uml]]
  = association.fold[Option[UMLProperty[Uml]]](None) { a =>
      a.memberEnd find (_ != this )
    }
  
  override def subsettedProperty_property: Set[UMLProperty[Uml]] = 
    e.get_propertyOfSubsettedProperty.to[Set]
  
}

case class MagicDrawUMLPropertyImpl
( e: MagicDrawUML#Property, ops: MagicDrawUMLUtil )
extends MagicDrawUMLProperty
with sext.PrettyPrinting.TreeString
with sext.PrettyPrinting.ValueTreeString {

  override val hashCode: Int = (e, ops).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLPropertyImpl =>
      this.hashCode == that.hashCode &&
        this.e == that.e &&
        this.ops == that.ops
    case _ =>
      false
  }

  override def toString
  : String
  = s"MagicDrawUMLProperty(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString
  : String
  = toString

  override def valueTreeString
  : String
  = toString
}