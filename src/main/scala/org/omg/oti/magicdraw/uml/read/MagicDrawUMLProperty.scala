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
import scala.{Boolean,Option,None,Some,StringContext}
import scala.Predef.String

import org.omg.oti.uml.read.api._

trait MagicDrawUMLProperty 
  extends MagicDrawUMLStructuralFeature
  with MagicDrawUMLConnectableElement
  with MagicDrawUMLDeploymentTarget
  with UMLProperty[MagicDrawUML] {

  override protected def e: Uml#Property
  def getMagicDrawProperty = e
  override implicit val umlOps = ops
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

  override def defaultValue: Option[UMLValueSpecification[Uml]] =
    for { result <- Option( e.getDefaultValue ) } yield result
  
  override def isComposite: Boolean =
    e.isComposite
  
  override def isDerived: Boolean =
    e.isDerived
  
  override def isDerivedUnion: Boolean =
    e.isDerivedUnion
  
  override def isID: Boolean =
    e.isID
    
  override def qualifier: Seq[UMLProperty[Uml]] =
    e.getQualifier.to[Seq]
  
  override def subsettedProperty: Set[UMLProperty[Uml]] =
    e.getSubsettedProperty.to[Set]
  
  override def returnValueRecipient_interactionUse: Set[UMLInteractionUse[Uml]] =
    e.get_interactionUseOfReturnValueRecipient().to[Set]
    
  override def qualifier_readLinkObjectEndQualifierAction: Option[UMLReadLinkObjectEndQualifierAction[Uml]] =
    for {
      result <- e.get_readLinkObjectEndQualifierActionOfQualifier().toList.headOption
    } yield result

  override def qualifier_qualifierValue: Set[UMLQualifierValue[Uml]] =
    e.get_qualifierValueOfQualifier().to[Set]
    
  override def part_structuredClassifier: Option[UMLStructuredClassifier[Uml]] =
    for { result <- Option(e.get_structuredClassifierOfOwnedAttribute()) } yield result
  
  override def end_linkEndData: Set[UMLLinkEndData[Uml]] =
    e.get_linkEndDataOfEnd().to[Set]
  
  override def end_readLinkObjectEndAction: Option[UMLReadLinkObjectEndAction[Uml]] =
    for { result <- e.get_readLinkObjectEndActionOfEnd().toList.headOption } yield result

  override def partWithPort_connectorEnd: Set[UMLConnectorEnd[Uml]] =
    e.get_connectorEndOfPartWithPort().to[Set]
  
  override def definingEnd_connectorEnd: Set[UMLConnectorEnd[Uml]] =
    e.getEnd.to[Set]
  
  override def attribute_classifier: Option[UMLClassifier[Uml]] =
    for { result <- Option(e.getClassifier()) } yield result
  
  override def navigableOwnedEnd_association: Option[UMLAssociation[Uml]] =
    for { result <- Option( e.get_associationOfNavigableOwnedEnd ) } yield result
  
  override def opposite_property: Option[UMLProperty[Uml]] =
    association.fold[Option[UMLProperty[Uml]]](None) { a =>
      a.memberEnd find (_ != this )
    }
  
  override def subsettedProperty_property: Set[UMLProperty[Uml]] = 
    e.get_propertyOfSubsettedProperty.to[Set]
  
}

case class MagicDrawUMLPropertyImpl
( val e: MagicDrawUML#Property, ops: MagicDrawUMLUtil )
extends MagicDrawUMLProperty
with sext.PrettyPrinting.TreeString
with sext.PrettyPrinting.ValueTreeString {

  override def toString: String =
    s"MagicDrawUMLProperty(ID=${e.getID}, qname=${e.getQualifiedName})"

  override def treeString: String =
    toString

  override def valueTreeString: String =
    toString
}