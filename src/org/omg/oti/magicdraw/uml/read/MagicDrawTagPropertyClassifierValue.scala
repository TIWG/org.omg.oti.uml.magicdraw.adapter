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
import org.omg.oti.uml.UMLError
import org.omg.oti.uml.read._

import scala.{Boolean,Double,Int}
import scala.Predef.{???,String}
import scala.collection.immutable._
import scala.collection.Iterable
import scala.language.{implicitConversions, postfixOps}
import scalaz._

sealed abstract class MagicDrawTagPropertyClassifierValue
  extends TagPropertyClassifierValue[MagicDrawUML] {

  override val property: MagicDrawUMLProperty

  def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : NonEmptyList[java.lang.Throwable] \/ scala.xml.Elem

}

sealed abstract class MagicDrawTagPropertyProfileLifecycleIndependentClassifierValue
  extends MagicDrawTagPropertyClassifierValue
  with TagPropertyProfileLifecycleIndependentClassifierValue[MagicDrawUML]

case class MagicDrawTagPropertyEnumerationLiteralValue
(override val property: MagicDrawUMLProperty,
 override val value: MagicDrawUMLEnumerationLiteral)
  extends MagicDrawTagPropertyProfileLifecycleIndependentClassifierValue
  with TagPropertyEnumerationLiteralValue[MagicDrawUML] {

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : NonEmptyList[java.lang.Throwable] \/ scala.xml.Elem =
    \/-(
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

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : NonEmptyList[java.lang.Throwable] \/ scala.xml.Elem =
    \/-(
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

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : NonEmptyList[java.lang.Throwable] \/ scala.xml.Elem =
    \/-(scala.xml.Elem(
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

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : NonEmptyList[java.lang.Throwable] \/ scala.xml.Elem =
    \/-(scala.xml.Elem(
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

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : NonEmptyList[java.lang.Throwable] \/ scala.xml.Elem =
    \/-(scala.xml.Elem(
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

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : NonEmptyList[java.lang.Throwable] \/ scala.xml.Elem =
    \/-(scala.xml.Elem(
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

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : NonEmptyList[java.lang.Throwable] \/ scala.xml.Elem =
    \/-(scala.xml.Elem(
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

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : NonEmptyList[java.lang.Throwable] \/ scala.xml.Elem =
    ???

  override val tagPropertyValueElementReferences
  : Iterable[MagicDrawUMLElement] =
    Iterable()

}

case class MagicDrawTagPropertyProfileLifecycleDependentClassifierValueObject
(override val property: MagicDrawUMLProperty,
 override val attributeValues: Seq[(MagicDrawUMLProperty, Seq[MagicDrawTagPropertyClassifierValue])])
extends MagicDrawTagPropertyProfileLifecycleDependentClassifierValue
with TagPropertyProfileLifecycleDependentClassifierValueObject[MagicDrawUML] {

  def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding)
  : NonEmptyList[java.lang.Throwable] \/ scala.xml.Elem =
    ???

  override val tagPropertyValueElementReferences
  : Iterable[MagicDrawUMLElement] =
    Iterable()

}