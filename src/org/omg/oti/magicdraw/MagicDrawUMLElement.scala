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
import scala.language.implicitConversions
import scala.language.postfixOps
import com.nomagic.magicdraw.uml.actions.SelectInContainmentTreeRunnable
import org.eclipse.emf.ecore.EStructuralFeature
import org.omg.oti.api._
import org.omg.oti.operations._
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import com.nomagic.magicdraw.uml.UUIDRegistry

import scala.util.Failure
import scala.util.Success
import scala.util.Try

trait MagicDrawUMLElement extends UMLElement[MagicDrawUML] {

  type Uml = MagicDrawUML

  implicit val ops: MagicDrawUMLUtil
  import ops._

  protected def e: Uml#Element

  def getMagicDrawElement = e

  // Element

  override def ownedComment = e.getOwnedComment.toSet[Uml#Comment]

  override def ownedElement: Set[UMLElement[Uml]] =
    e.getOwnedElement.toSet[Uml#Element] - umlElement( e.getAppliedStereotypeInstance )

  override def owner = Option.apply( e.getOwner )

  override def constrainedElement_constraint = e.get_constraintOfConstrainedElement.toSet[Uml#Constraint]

  override def annotatedElement_comment = e.get_commentOfAnnotatedElement.toSet[Uml#Comment]

  override def represents_activityPartition = e.get_activityPartitionOfRepresents.toSet[Uml#ActivityPartition]

  override def relatedElement_relationship = e.get_relationshipOfRelatedElement.toSet[Uml#Relationship]

  override def target_directedRelationship = e.get_directedRelationshipOfTarget.toSet[Uml#DirectedRelationship]

  override def source_directedRelationship = e.get_directedRelationshipOfSource.toSet[Uml#DirectedRelationship]

  // ElementOps

  override def allOwnedElements =
    e.eAllContents.toSet.selectByKindOf { case e: Uml#Element => umlElement( e ) }

  override def mofMetaclassName = StereotypesHelper.getBaseClass( e ).getName

  override def tagValues: Map[UMLProperty[Uml], Seq[UMLValueSpecification[Uml]]] =
    Option.apply( e.getAppliedStereotypeInstance ) match {
      case None => Map()
      case Some( is ) =>
        val tv = for {
          s <- is.getSlot
          p = s.getDefiningFeature match { case p: Uml#Property => umlProperty( p ) }
          v = umlValueSpecification( s.getValue )
        } yield p -> v
        tv.toMap
    }

  override def id: String = e.getID

  override def uuid: Option[String] = Some( UUIDRegistry.getUUID( e ) )

  override def hasStereotype( s: UMLStereotype[Uml] ) = s.isStereotypeApplied( e )

  /**
   * MagicDraw's representation of applied stereotypes on an element E involves a special InstanceSpecification IS owned by E.
   * The applied stereotypes are the classifiers of IS.
   * Values of stereotype properties other than ends of the stereotype's extension are represented in slots of IS.
   * There is no slot corresponding to the "base_<metaclass>" properties of the stereotype's extensions.
   * This means that we have to calculate what these "base_<metaclass>" properties are for each applied stereotype (i.e., classifier of E's IS).
   */
  override def getAppliedStereotypes: Map[UMLStereotype[Uml], UMLProperty[Uml]] = {
    val eMetaclass = e.getClassType
    StereotypesHelper.getStereotypes( e ).toSet[Uml#Stereotype] flatMap { s =>
      val metaProperties = StereotypesHelper.getExtensionMetaProperty( s, true ) filter { p =>
        val pMetaclass = StereotypesHelper.getClassOfMetaClass( p.getType.asInstanceOf[Uml#Class] )
        eMetaclass == pMetaclass || StereotypesHelper.isSubtypeOf( pMetaclass, eMetaclass )
      }
      if ( metaProperties.isEmpty ) {
        None
      } else
        Some( umlStereotype( s ) -> umlProperty( metaProperties.head ) )
    } toMap
  }

  override def getAppliedStereotypesWithoutMetaclassProperties: Set[UMLStereotype[Uml]] = {
    val eMetaclass = e.getClassType
    StereotypesHelper.getStereotypes( e ).toSet[Uml#Stereotype] flatMap { s =>
      val metaProperties = StereotypesHelper.getExtensionMetaProperty( s, true ) filter { p =>
        val pMetaclass = StereotypesHelper.getClassOfMetaClass( p.getType.asInstanceOf[Uml#Class] )
        eMetaclass == pMetaclass || StereotypesHelper.isSubtypeOf( pMetaclass, eMetaclass )
      }
      if ( metaProperties.isEmpty )
        Some( umlStereotype( s ) )
      else
        None
    }
  }

  override def isAncestorOf( other: UMLElement[Uml] ) =
    ( e == other.e ) ||
      ( other.owner match {
        case None           => false
        case Some( parent ) => isAncestorOf( parent )
      } )

  // MagicDraw-specific

  def selectInContainmentTreeRunnable: Runnable = new SelectInContainmentTreeRunnable( e )
}