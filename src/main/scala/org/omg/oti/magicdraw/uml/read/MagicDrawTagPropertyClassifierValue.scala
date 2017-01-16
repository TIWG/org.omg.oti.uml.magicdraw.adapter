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
import org.omg.oti.uml.read._

import scala.{Any,Boolean,Double,Int}
import scala.Predef.{???,String}
import scala.collection.immutable._
import scala.collection.Iterable
import scalaz._

sealed abstract class MagicDrawTagPropertyClassifierValue
  extends TagPropertyClassifierValue[MagicDrawUML] {

  override val property: MagicDrawUMLProperty

  def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : Set[java.lang.Throwable] \/ scala.xml.Elem

}

sealed abstract class MagicDrawTagPropertyProfileLifecycleIndependentClassifierValue
  extends MagicDrawTagPropertyClassifierValue
  with TagPropertyProfileLifecycleIndependentClassifierValue[MagicDrawUML]

case class MagicDrawTagPropertyEnumerationLiteralValue
(override val property: MagicDrawUMLProperty,
 override val value: MagicDrawUMLEnumerationLiteral)
  extends MagicDrawTagPropertyProfileLifecycleIndependentClassifierValue
  with TagPropertyEnumerationLiteralValue[MagicDrawUML] {

  override val hashCode
  : Int
  = (property, value).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawTagPropertyEnumerationLiteralValue =>
      this.hashCode == that.hashCode &&
        this.property == that.property &&
        this.value == that.value
    case _ =>
      false
  }

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : Set[java.lang.Throwable] \/ scala.xml.Elem
  = \/-(
      scala.xml.Elem(
        prefix = null,
        label = property.name.get,
        attributes = scala.xml.Null,
        scope = xmiScopes,
        minimizeEmpty = true,
        scala.xml.Text(value.name.get)))

}

case class MagicDrawTagPropertyInstanceSpecificationValue
(override val property: MagicDrawUMLProperty,
 override val value: MagicDrawUMLInstanceSpecification)
  extends MagicDrawTagPropertyProfileLifecycleIndependentClassifierValue
  with TagPropertyInstanceSpecificationValue[MagicDrawUML] {

  override val hashCode
  : Int
  = (property, value).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawTagPropertyInstanceSpecificationValue =>
      this.hashCode == that.hashCode &&
        this.property == that.property &&
        this.value == that.value
    case _ =>
      false
  }

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : Set[java.lang.Throwable] \/ scala.xml.Elem
  = \/-(
      scala.xml.Elem(
        prefix = null,
        label = property.name.get,
        attributes = scala.xml.Null,
        scope = xmiScopes,
        minimizeEmpty = true,
        scala.xml.Text(value.name.get)))

}

sealed abstract class MagicDrawTagPropertyPrimitiveValue
  extends MagicDrawTagPropertyClassifierValue
  with TagPropertyPrimitiveValue[MagicDrawUML]

case class MagicDrawTagPropertyBooleanValue
(override val property: MagicDrawUMLProperty,
 override val value: Boolean)
  extends MagicDrawTagPropertyPrimitiveValue
  with TagPropertyBooleanValue[MagicDrawUML] {

  override val hashCode
  : Int
  = (property, value).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawTagPropertyBooleanValue =>
      this.hashCode == that.hashCode &&
        this.property == that.property &&
        this.value == that.value
    case _ =>
      false
  }

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : Set[java.lang.Throwable] \/ scala.xml.Elem
  = \/-(scala.xml.Elem(
      prefix = null,
      label = property.name.get,
      attributes = scala.xml.Null,
      scope = xmiScopes,
      minimizeEmpty = true,
      scala.xml.Text(value.toString)))

}

case class MagicDrawTagPropertyIntegerValue
(override val property: MagicDrawUMLProperty,
 override val value: Int)
  extends MagicDrawTagPropertyPrimitiveValue
  with TagPropertyIntegerValue[MagicDrawUML] {

  override val hashCode
  : Int
  = (property, value).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawTagPropertyIntegerValue =>
      this.hashCode == that.hashCode &&
        this.property == that.property &&
        this.value == that.value
    case _ =>
      false
  }

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : Set[java.lang.Throwable] \/ scala.xml.Elem
  = \/-(scala.xml.Elem(
      prefix = null,
      label = property.name.get,
      attributes = scala.xml.Null,
      scope = xmiScopes,
      minimizeEmpty = true,
      scala.xml.Text(value.toString)))

}

case class MagicDrawTagPropertyUnlimitedNaturalValue
(override val property: MagicDrawUMLProperty,
 override val value: Int)
  extends MagicDrawTagPropertyPrimitiveValue
  with TagPropertyUnlimitedNaturalValue[MagicDrawUML] {

  override val hashCode
  : Int
  = (property, value).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawTagPropertyUnlimitedNaturalValue =>
      this.hashCode == that.hashCode &&
        this.property == that.property &&
        this.value == that.value
    case _ =>
      false
  }

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : Set[java.lang.Throwable] \/ scala.xml.Elem
  = \/-(scala.xml.Elem(
      prefix = null,
      label = property.name.get,
      attributes = scala.xml.Null,
      scope = xmiScopes,
      minimizeEmpty = true,
      scala.xml.Text(value.toString)))

}

case class MagicDrawTagPropertyRealValue
(override val property: MagicDrawUMLProperty,
 override val value: Double)
  extends MagicDrawTagPropertyPrimitiveValue
  with TagPropertyRealValue[MagicDrawUML] {

  override val hashCode
  : Int
  = (property, value).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawTagPropertyRealValue =>
      this.hashCode == that.hashCode &&
        this.property == that.property &&
        this.value == that.value
    case _ =>
      false
  }

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : Set[java.lang.Throwable] \/ scala.xml.Elem
  = \/-(scala.xml.Elem(
      prefix = null,
      label = property.name.get,
      attributes = scala.xml.Null,
      scope = xmiScopes,
      minimizeEmpty = true,
      scala.xml.Text(value.toString)))

}

case class MagicDrawTagPropertyStringValue
(override val property: MagicDrawUMLProperty,
 override val value: String)
  extends MagicDrawTagPropertyPrimitiveValue
  with TagPropertyStringValue[MagicDrawUML] {

  override val hashCode
  : Int
  = (property, value).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawTagPropertyStringValue =>
      this.hashCode == that.hashCode &&
        this.property == that.property &&
        this.value == that.value
    case _ =>
      false
  }

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : Set[java.lang.Throwable] \/ scala.xml.Elem
  = \/-(scala.xml.Elem(
      prefix = null,
      label = property.name.get,
      attributes = scala.xml.Null,
      scope = xmiScopes,
      minimizeEmpty = true,
      scala.xml.Text(value.toString)))

}

/**
 * MagicDraw profile mechanism does not support this capability.
 * However, it could be emulated with a MagicDraw-specific OTI profile
 * to annotate the use of MagicDraw UML instances owned by some element in the model
 * as if they were owned directly or indirectly by stereotype instances.
 *
 * In principle, this is feasible but there are lots of tricky details.
 * This would require managing references to these MD-specific OTI-annotated instances
 * such that these OTI-annotated instances would emulate the intent of the lifecycle semantics
 * tied to applying/unapplying a profile even though such OTI-annotated instances would
 * be owned by UML elements and creating/deleting them would entail modifying the model
 * in contradiction to UML 2.5, section 12.3.3
 *
 * Bottom line, it's non-trivial and full of complications.
 *
 * This capability would be necessary if it is necessary to support advanced profile techniques
 * (e.g., see UML 2.5, Figure 12.32 and 12.33) by emulation on top of the MagicDraw 18 API.
 * Alternatively, NoMagic may decide to improve their support for Profiles so that it would
 * not be necessary to emulate UML 2.5.
 *
 * For now, this is not implemented in the OTI/MagicDraw 18 adapter.
 */
sealed abstract class MagicDrawTagPropertyProfileLifecycleDependentClassifierValue
  extends MagicDrawTagPropertyClassifierValue
  with TagPropertyProfileLifecycleDependentClassifierValue[MagicDrawUML]

case class MagicDrawTagPropertyProfileLifecycleDependentClassifierValueReference
(override val property: MagicDrawUMLProperty,
 override val value: MagicDrawTagPropertyProfileLifecycleDependentClassifierValueObject)
extends MagicDrawTagPropertyProfileLifecycleDependentClassifierValue
with TagPropertyProfileLifecycleDependentClassifierValueReference[MagicDrawUML] {

  override val hashCode
  : Int
  = (property, value).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawTagPropertyProfileLifecycleDependentClassifierValueReference =>
      this.hashCode == that.hashCode &&
        this.property == that.property &&
        this.value == that.value
    case _ =>
      false
  }

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : Set[java.lang.Throwable] \/ scala.xml.Elem
  = ???

  override val tagPropertyValueElementReferences
  : Iterable[MagicDrawUMLElement]
  = Iterable()

}

case class MagicDrawTagPropertyProfileLifecycleDependentClassifierValueObject
(override val property: MagicDrawUMLProperty,
 override val attributeValues: Seq[(MagicDrawUMLProperty, Seq[MagicDrawTagPropertyClassifierValue])])
extends MagicDrawTagPropertyProfileLifecycleDependentClassifierValue
with TagPropertyProfileLifecycleDependentClassifierValueObject[MagicDrawUML] {

  override val hashCode
  : Int
  = (property, attributeValues).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawTagPropertyProfileLifecycleDependentClassifierValueObject =>
      this.hashCode == that.hashCode &&
        this.property == that.property &&
        this.attributeValues == that.attributeValues
    case _ =>
      false
  }

  def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : Set[java.lang.Throwable] \/ scala.xml.Elem
  = ???

  override val tagPropertyValueElementReferences
  : Iterable[MagicDrawUMLElement]
  = Iterable()

}