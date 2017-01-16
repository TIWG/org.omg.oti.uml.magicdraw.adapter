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

import com.nomagic.magicdraw.uml.UUIDRegistry
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper

import org.omg.oti.json.common.OTIPrimitiveTypes._
import org.omg.oti.uml.read.api._

import scala.annotation
import scala.deprecated
import scala.{Boolean, None, Option, Some, StringBuilder}
import scala.Predef.String
import scala.collection.JavaConversions._
import scala.collection.immutable._
import java.lang.Runnable

import com.nomagic.magicdraw.hyperlinks.HyperlinkUtils

import scalaz.{@@, \/, \/-}

trait MagicDrawUMLElement extends UMLElement[MagicDrawUML] {

  type Uml = MagicDrawUML

  implicit val ops: MagicDrawUMLUtil

  import ops._

  protected def e: Uml#Element

  def getMagicDrawElement: Uml#Element = e

  // Element

  override def context_diagram
  : Set[UMLDiagram[Uml]]
  = e.get_diagramOfContext().to[Set]

  override def element_elementValue
  : Set[UMLElementValue[Uml]]
  = e.get_elementValueOfElement().to[Set]

  override def ownedElement
  : Set[UMLElement[Uml]]
  = e.getOwnedElement.to[Set] - umlElement(e.getAppliedStereotypeInstance)

  override def owner
  : Option[UMLElement[Uml]]
  = for { result <- Option.apply(e.getOwner) } yield result

  override def constrainedElement_constraint
  : Set[UMLConstraint[Uml]]
  = e.get_constraintOfConstrainedElement.to[Set]

  override def annotatedElement_comment
  : Set[UMLComment[Uml]]
  = e.get_commentOfAnnotatedElement.to[Set]

  override def represents_activityPartition
  : Set[UMLActivityPartition[Uml]]
  = e.get_activityPartitionOfRepresents.to[Set]

  override def relatedElement_relationship
  : Set[UMLRelationship[Uml]]
  = e.get_relationshipOfRelatedElement.to[Set]

  override def target_directedRelationship
  : Set[UMLDirectedRelationship[Uml]]
  = e.get_directedRelationshipOfTarget.to[Set]

  override def source_directedRelationship
  : Set[UMLDirectedRelationship[Uml]]
  = e.get_directedRelationshipOfSource.to[Set]

  // ElementOps

  override def mofMetaclassName: String =
    StereotypesHelper.getBaseClass(e).getName

  override def tagValues
  : Set[java.lang.Throwable] \/ Seq[MagicDrawUMLStereotypeTagValue]
  = MagicDrawUMLStereotypeTagValue.getElementTagValues(this)

  override def toolSpecific_id: String @@ TOOL_SPECIFIC_ID
  = TOOL_SPECIFIC_ID(e.getID)

  override def toolSpecific_uuid: Option[String @@ TOOL_SPECIFIC_UUID]
  = Some(TOOL_SPECIFIC_UUID(UUIDRegistry.getUUID(e)))

  override def toolSpecific_url: String @@ TOOL_SPECIFIC_URL
  = TOOL_SPECIFIC_URL(HyperlinkUtils.getURLWithVersion(e))

  override def hasStereotype(s: UMLStereotype[Uml])
  : Set[java.lang.Throwable] \/ Boolean
  = \/-(umlMagicDrawUMLStereotype(s).isStereotypeApplied(e))

  override def getAppliedStereotypesWithoutMetaclassProperties
  : Set[java.lang.Throwable] \/ Set[UMLStereotype[Uml]]
  = {
    val eMetaclass = e.getClassType
    \/-(StereotypesHelper.getStereotypes(e).to[Set] flatMap { s =>
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
  : Set[java.lang.Throwable] \/ Boolean
  = if (e == umlMagicDrawUMLElement(other).getMagicDrawElement)
      \/-(true)
    else
      other
      .owner
      .fold[Set[java.lang.Throwable] \/ Boolean](\/-(false)){ parent =>
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
    new com.nomagic.magicdraw.uml.actions.SelectInContainmentTreeRunnable(e)
}