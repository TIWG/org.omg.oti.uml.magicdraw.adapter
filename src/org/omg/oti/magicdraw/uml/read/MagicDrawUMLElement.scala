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

import com.nomagic.magicdraw.uml.UUIDRegistry
import com.nomagic.magicdraw.uml.actions.SelectInContainmentTreeRunnable
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper
import org.omg.oti.uml.UMLError
import org.omg.oti.uml.read.api._

import scala.annotation
import scala.deprecated
import scala.{Boolean,Option,None,Some,StringBuilder}
import scala.Predef.String
import scala.collection.JavaConversions._
import scala.collection.immutable._
import scala.collection.Iterable

import java.lang.Runnable

import scala.language.{implicitConversions, postfixOps}
import scalaz.{\/, \/-, NonEmptyList}

trait MagicDrawUMLElement extends UMLElement[MagicDrawUML] {

  type Uml = MagicDrawUML

  implicit val ops: MagicDrawUMLUtil

  import ops._

  protected def e: Uml#Element

  def getMagicDrawElement = e

  // Element

  override def ownedElement: Set[UMLElement[Uml]] =
    e.getOwnedElement.toSet[Uml#Element] - umlElement(e.getAppliedStereotypeInstance)

  override def owner: Option[UMLElement[Uml]] =
    Option.apply(e.getOwner)

  override def constrainedElement_constraint: Set[UMLConstraint[Uml]] =
    e.get_constraintOfConstrainedElement.toSet[Uml#Constraint]

  override def annotatedElement_comment: Set[UMLComment[Uml]] =
    e.get_commentOfAnnotatedElement.toSet[Uml#Comment]

  override def represents_activityPartition: Set[UMLActivityPartition[Uml]] =
    e.get_activityPartitionOfRepresents.toSet[Uml#ActivityPartition]

  override def relatedElement_relationship: Set[UMLRelationship[Uml]] =
    e.get_relationshipOfRelatedElement.toSet[Uml#Relationship]

  override def target_directedRelationship: Set[UMLDirectedRelationship[Uml]] =
    e.get_directedRelationshipOfTarget.toSet[Uml#DirectedRelationship]

  override def source_directedRelationship: Set[UMLDirectedRelationship[Uml]] =
    e.get_directedRelationshipOfSource.toSet[Uml#DirectedRelationship]

  // ElementOps

  /**
   * @return The transitive closure of `ownedElement` without MagicDraw's AppliedStereotypeInstances.
   */
  override def allOwnedElements: Set[UMLElement[Uml]] =
    closure[UMLElement[Uml], UMLElement[Uml]](this, _.ownedElement)

  override def mofMetaclassName: String =
    StereotypesHelper.getBaseClass(e).getName

  override def tagValues
  : NonEmptyList[UMLError.UException] \/ Seq[MagicDrawUMLStereotypeTagValue] =
    MagicDrawUMLStereotypeTagValue.getElementTagValues(this)

  override def toolSpecific_id: Option[String] =
    Some(e.getID)

  override def toolSpecific_uuid: Option[String] =
    Some(UUIDRegistry.getUUID(e))

  override def hasStereotype(s: UMLStereotype[Uml])
  : NonEmptyList[UMLError.UException] \/ Boolean =
    \/-(umlMagicDrawUMLStereotype(s).isStereotypeApplied(e))

  override def getAppliedStereotypesWithoutMetaclassProperties
  : NonEmptyList[UMLError.UException] \/ Set[UMLStereotype[Uml]] = {
    val eMetaclass = e.getClassType
    \/-(StereotypesHelper.getStereotypes(e).toSet[Uml#Stereotype] flatMap { s =>
      val metaProperties = StereotypesHelper.getExtensionMetaProperty(s, true) filter { p =>
        val pMetaclass = StereotypesHelper.getClassOfMetaClass(p.getType.asInstanceOf[Uml#Class])
        eMetaclass == pMetaclass || StereotypesHelper.isSubtypeOf(pMetaclass, eMetaclass)
      }
      if (metaProperties.isEmpty)
        Some(umlStereotype(s))
      else
        None
    })
  }

  override def isAncestorOf(other: UMLElement[Uml])
  : NonEmptyList[UMLError.UException] \/ Boolean =
    if (e == umlMagicDrawUMLElement(other).getMagicDrawElement)
      \/-(true)
    else
      other
      .owner
      .fold[NonEmptyList[UMLError.UException] \/ Boolean](\/-(false)){ parent =>
        isAncestorOf(parent)
      }

  override def toWrappedObjectString: String = {

    @annotation.tailrec def describe(context: Option[UMLElement[Uml]], path: Seq[String]): String =
      context match {
        case None =>

          def prefixStream(prefix: String): Stream[String] = prefix #:: prefixStream(prefix + "  ")
          val prefixes: List[String] = prefixStream("").take(path.length).toList

          val result = new StringBuilder(500)
          for {
            (prefix, segment) <- prefixes.zip(path.reverse)
          } result.append("\n" + prefix + segment)
          result.result()

        case Some(e) =>
          describe(e.owner, path :+ (e.xmiType.head + " {id=" + e.toolSpecific_id + "}"))
      }

    describe(Some(this), Seq())
  }

  // MagicDraw-specific

  def selectInContainmentTreeRunnable: Runnable = MagicDrawSelectInContainmentTree(e).makeRunnable
}

// Workaround to MD's deprecated API: com.nomagic.magicdraw.uml.actions.SelectInContainmentTreeRunnable
// see https://issues.scala-lang.org/browse/SI-7934
@deprecated("", "")
case class MagicDrawSelectInContainmentTree(e: com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element) {

  def makeRunnable: Runnable =
    new SelectInContainmentTreeRunnable(e)
}