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
package org.omg.oti.magicdraw.uml.read

import scala.collection.JavaConversions._
import scala.collection.immutable._
import scala.collection.Iterable
import scala.{Boolean,Option,None,Some,StringContext}
import scala.Predef.{???,String}

import scala.language.postfixOps
import org.omg.oti.uml.read.api._

trait MagicDrawUMLProperty 
  extends UMLProperty[MagicDrawUML]
  with MagicDrawUMLStructuralFeature
  with MagicDrawUMLConnectableElement
  with MagicDrawUMLDeploymentTarget {

  override protected def e: Uml#Property
  def getMagicDrawProperty = e
  override implicit val umlOps = ops
  import umlOps._
  
  override def aggregation: UMLAggregationKind.Value = 
    e.getAggregation match {
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.AggregationKindEnum.COMPOSITE => UMLAggregationKind.composite
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.AggregationKindEnum.NONE => UMLAggregationKind.none
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.AggregationKindEnum.SHARED => UMLAggregationKind.shared
  }
  
  override def association: Option[UMLAssociation[Uml]] =
    Option.apply( e.getAssociation )
 
  override def owningAssociation: Option[UMLAssociation[Uml]] =
    association.fold[Option[UMLAssociation[Uml]]](None) { a =>
      if (a.ownedEnd.contains(this)) Some(a)
      else None
    }
  
  override def associationEnd = Option.apply( e.getAssociationEnd )
  
  override def defaultValue = Option.apply( e.getDefaultValue )
  
  override def isComposite = e.isComposite
  
  override def isDerived = e.isDerived
  
  override def isDerivedUnion = e.isDerivedUnion
  
  override def isID = e.isID
    
  override def opposite: Option[UMLProperty[Uml]] =
    association.fold[Option[UMLProperty[Uml]]](None) { a =>
      a.memberEnd filter (_ != this) headOption
    }
    
	override def qualifier: Seq[UMLProperty[Uml]] = 
    e.getQualifier.to[Seq]
  
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
  
  override def opposite_property: Option[UMLProperty[Uml]] =
    association.fold[Option[UMLProperty[Uml]]](None) { a =>
      a.memberEnd filter (_ != this) headOption
    }

  override def redefinedProperty_property: Set[UMLProperty[Uml]] =
    e.get_propertyOfRedefinedProperty.to[Set]
  
  override def subsettedProperty_property: Set[UMLProperty[Uml]] = 
    e.get_propertyOfSubsettedProperty.to[Set]
  
}

case class MagicDrawUMLPropertyImpl( val e: MagicDrawUML#Property, ops: MagicDrawUMLUtil )
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