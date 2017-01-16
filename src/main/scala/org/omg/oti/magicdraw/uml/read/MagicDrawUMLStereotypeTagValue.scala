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

import com.nomagic.uml2.ext.jmi.helpers.{ModelHelper, StereotypesHelper}

import org.omg.oti.json.common.OTIPrimitiveTypes._
import org.omg.oti.uml.read._
import org.omg.oti.uml.read.api._
import org.omg.oti.uml.xmi._

import scala.collection.JavaConversions._
import scala.collection.immutable._
import scala.collection.Iterable

import scala.Predef.{Map => _, Set => _, _}
import scala.{Any,Boolean,Int,Option,None,Ordering,Some,Tuple2}
import scalaz._, Scalaz._

sealed abstract class MagicDrawUMLStereotypeTagValue
  extends UMLStereotypeTagValue[MagicDrawUML]

case class MagicDrawUMLStereotypeTagExtendedMetaclassPropertyElementReference
(override val extendedElement: MagicDrawUMLElement,
 override val appliedStereotype: MagicDrawUMLStereotype,
 override val stereotypeTagProperty: MagicDrawUMLProperty,
 override val stereotypeTagPropertyType: MagicDrawUMLClass)
  extends MagicDrawUMLStereotypeTagValue
  with UMLStereotypeTagExtendedMetaclassPropertyElementReference[MagicDrawUML] {

  override val hashCode
  : Int
  = (extendedElement, appliedStereotype, stereotypeTagProperty, stereotypeTagPropertyType).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLStereotypeTagExtendedMetaclassPropertyElementReference =>
      this.hashCode == that.hashCode &&
        this.extendedElement == that.extendedElement &&
        this.appliedStereotype == that.appliedStereotype &&
        this.stereotypeTagProperty == that.stereotypeTagProperty &&
        this.stereotypeTagPropertyType == that.stereotypeTagPropertyType
    case _ =>
      false
  }

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding, idg: IDGenerator[MagicDrawUML])
  : Set[java.lang.Throwable] \/ Iterable[scala.xml.Elem]
  = Iterable(
      scala.xml.Elem(
        prefix = null,
        label = stereotypeTagProperty.name.get,
        attributes = new scala.xml.PrefixedAttribute(
          pre = "xmi",
          key = "idref",
          value = TOOL_SPECIFIC_ID.unwrap(extendedElement.toolSpecific_id),
          next = scala.xml.Null),
        scope = xmiScopes,
        minimizeEmpty = true))
    .right

}

case class MagicDrawUMLStereotypeTagPropertyMetaclassElementReference
(override val extendedElement: MagicDrawUMLElement,
 override val appliedStereotype: MagicDrawUMLStereotype,
 override val stereotypeTagProperty: MagicDrawUMLProperty,
 override val stereotypeTagPropertyType: MagicDrawUMLClass,
 override val tagPropertyValueElementReferences: Iterable[MagicDrawUMLElement])
  extends MagicDrawUMLStereotypeTagValue
  with UMLStereotypeTagPropertyMetaclassElementReference[MagicDrawUML] {

  override val hashCode
  : Int
  = (extendedElement, appliedStereotype, stereotypeTagProperty, stereotypeTagPropertyType, tagPropertyValueElementReferences).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLStereotypeTagPropertyMetaclassElementReference =>
      this.hashCode == that.hashCode &&
        this.extendedElement == that.extendedElement &&
        this.appliedStereotype == that.appliedStereotype &&
        this.stereotypeTagProperty == that.stereotypeTagProperty &&
        this.stereotypeTagPropertyType == that.stereotypeTagPropertyType &&
        this.tagPropertyValueElementReferences == that.tagPropertyValueElementReferences
    case _ =>
      false
  }

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding, idg: IDGenerator[MagicDrawUML])
  : Set[java.lang.Throwable] \/ Iterable[scala.xml.Elem] =
    \/-(for {
        elit <- tagPropertyValueElementReferences
      } yield scala.xml.Elem(
        prefix = null,
        label = stereotypeTagProperty.name.get,
        attributes = new scala.xml.PrefixedAttribute(
          pre = "xmi",
          key = "idref",
          value = TOOL_SPECIFIC_ID.unwrap(extendedElement.toolSpecific_id),
          next = scala.xml.Null),
        scope = xmiScopes,
        minimizeEmpty = true))

}

case class MagicDrawUMLStereotypeTagStereotypeInstanceValue
(override val extendedElement: MagicDrawUMLElement,
 override val appliedStereotype: MagicDrawUMLStereotype,
 override val stereotypeTagProperty: MagicDrawUMLProperty,
 override val stereotypeTagPropertyType: MagicDrawUMLStereotype,
 tagPropertyValueAppliedStereotypeAndElementReferences: Iterable[(MagicDrawUMLStereotype, MagicDrawUMLElement)])
  extends MagicDrawUMLStereotypeTagValue
  with UMLStereotypeTagStereotypeInstanceValue[MagicDrawUML] {

  override val hashCode
  : Int
  = (extendedElement, appliedStereotype, stereotypeTagProperty, stereotypeTagPropertyType).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLStereotypeTagStereotypeInstanceValue =>
      this.hashCode == that.hashCode &&
        this.extendedElement == that.extendedElement &&
        this.appliedStereotype == that.appliedStereotype &&
        this.stereotypeTagProperty == that.stereotypeTagProperty &&
        this.stereotypeTagPropertyType == that.stereotypeTagPropertyType
    case _ =>
      false
  }

  override val tagPropertyValueElementReferences
  : Iterable[MagicDrawUMLElement]
  = tagPropertyValueAppliedStereotypeAndElementReferences.map(_._2)

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding, idg: IDGenerator[MagicDrawUML])
  : Set[java.lang.Throwable] \/ Iterable[scala.xml.Elem]
  = {
    val s0: Set[java.lang.Throwable] \/ Vector[scala.xml.Elem] = Vector[scala.xml.Elem]().right
    val sN
    : Set[java.lang.Throwable] \/ Vector[scala.xml.Elem]
    = (s0 /: tagPropertyValueAppliedStereotypeAndElementReferences) { case (si, (s, e)) =>

      for {
        vi <- si
        eID <- e.xmiID()
        sID <- e.xmiID()
      } yield
        vi :+
          scala.xml.Elem(
            prefix = null,
            label = stereotypeTagProperty.name.get,
            attributes = new scala.xml.PrefixedAttribute(
              pre = "xmi",
              key = "idref",
              value = OTI_ID.unwrap(IDGenerator.computeStereotypeApplicationOTI_ID(eID, sID)),
              scala.xml.Null),
            scope = xmiScopes,
            minimizeEmpty = true)
    }
    sN
  }

}

case class MagicDrawUMLStereotypeTagPropertyClassifierValue
(override val extendedElement: MagicDrawUMLElement,
 override val appliedStereotype: MagicDrawUMLStereotype,
 override val stereotypeTagProperty: MagicDrawUMLProperty,
 override val stereotypeTagPropertyType: MagicDrawUMLClassifier,
 override val values: Iterable[MagicDrawTagPropertyClassifierValue])
  extends MagicDrawUMLStereotypeTagValue
  with UMLStereotypeTagPropertyClassifierValue[MagicDrawUML] {

  override val hashCode
  : Int
  = (extendedElement, appliedStereotype, stereotypeTagProperty, stereotypeTagPropertyType, values).##

  override def equals(other: Any): Boolean = other match {
    case that: MagicDrawUMLStereotypeTagPropertyClassifierValue =>
      this.hashCode == that.hashCode &&
        this.extendedElement == that.extendedElement &&
        this.appliedStereotype == that.appliedStereotype &&
        this.stereotypeTagProperty == that.stereotypeTagProperty &&
        this.stereotypeTagPropertyType == that.stereotypeTagPropertyType &&
        this.values == that.values
    case _ =>
      false
  }

  override def serialize
  (implicit xmiScopes: scala.xml.NamespaceBinding, idg: IDGenerator[MagicDrawUML])
  : Set[java.lang.Throwable] \/ Iterable[scala.xml.Elem] = {

    val s0: Set[java.lang.Throwable] \/ Seq[scala.xml.Elem] = Seq().right

    val sn = ( s0 /: values ) { (si, value) =>
      si +++ value.serialize.map(Seq(_))
    }

    sn
  }
}


object MagicDrawUMLStereotypeTagValue {

  def getElementTagValues
  (e: MagicDrawUMLElement)
  (implicit ops: MagicDrawUMLUtil)
  : Set[java.lang.Throwable] \/ Seq[MagicDrawUMLStereotypeTagValue] = {

    import ops._

    type Uml = MagicDrawUML


    val stereotypeOrdering = new Ordering[Uml#Stereotype] {

      def compare(x: Uml#Stereotype, y: Uml#Stereotype): Int = {
        (ModelHelper.getDerivedClassifiers(x).contains(y),
          ModelHelper.getDerivedClassifiers(y).contains(x),
          x.getQualifiedName.compareTo(y.getQualifiedName)) match {
          // x is a generalization parent of y
          case (true, _, _) => -1
          // y is a generalization parent of x
          case (false, true, _) => 1
          // x and y are unrelated
          case (false, false, c) => c
        }

      }
    }

    val mdE = e.getMagicDrawElement
    val mdEMetaclass = mdE.getClassType

    val appliedStereotypes =
      StereotypesHelper.getStereotypes(mdE).to[Set].toList.sorted(stereotypeOrdering)

    val extendedMetaclassTagValues =
      for {
        s <- appliedStereotypes
        metaProperties = StereotypesHelper.getExtensionMetaProperty(s, true) filter { p =>
          val mdPMetaclass = StereotypesHelper.getClassOfMetaClass(p.getType.asInstanceOf[Uml#Class])
          mdEMetaclass == mdPMetaclass || StereotypesHelper.isSubtypeOf(mdPMetaclass, mdEMetaclass)
        }
        mdS = ops.umlMagicDrawUMLStereotype(ops.umlStereotype(s))
        baseProperty <- metaProperties.headOption
        mdBaseProperty = ops.umlMagicDrawUMLProperty(ops.umlProperty(baseProperty))
        t <- mdBaseProperty._type
        mdExtendedMC <- t match {
          case c: UMLClass[Uml] => Some(ops.umlMagicDrawUMLClass(c))
          case _ => None
        }
      } yield
      MagicDrawUMLStereotypeTagExtendedMetaclassPropertyElementReference(
        extendedElement = e,
        appliedStereotype = mdS,
        stereotypeTagProperty = mdBaseProperty,
        stereotypeTagPropertyType = mdExtendedMC)

    Option.apply(e.getMagicDrawElement.getAppliedStereotypeInstance) match {
      case None =>
        extendedMetaclassTagValues.right
      case Some(is) =>
        val tagValues = for {
          s <- is.getSlot
          f <- Option.apply(s.getDefiningFeature) match {
            case Some(df) =>
              df match {
                case p: Uml#Property => Some(ops.umlMagicDrawUMLProperty(ops.umlProperty(p)))
                case _ => None
              }
            case None => None
          }
          fs: UMLStereotype[Uml] <- f.owningStereotype
          mdS = ops.umlMagicDrawUMLStereotype(fs)
          t: UMLType[Uml] <- f._type
          v <- t match {
            case ts: UMLStereotype[Uml] =>

              val mdTS = ops.umlMagicDrawUMLStereotype(ts).getMagicDrawStereotype
              val mdTSDerived = ModelHelper.getDerivedClassifiers(mdTS).toSet
              def getAppliedStereotype_ElementReference_pair(_mdE: MagicDrawUMLElement)
              : Option[(MagicDrawUMLStereotype, MagicDrawUMLElement)]
              = {
                val r0 = StereotypesHelper.getStereotypes(_mdE.getMagicDrawElement).toList

                val r1 = r0 find { _s =>
                  _s == mdTS || mdTSDerived.contains(_s)
                }

                val r2 = r1 match {
                  case None => None
                  case Some(_s) => Some(Tuple2(ops.umlMagicDrawUMLStereotype(_s), _mdE))
                }
//                System.out.println(
//                  s"""### e: ${mdE.getHumanName} -
//                     |TS (fs=${fs.qualifiedName.get},t=${t.qualifiedName.get})
//                     |r0? ${r0.size}
//                     |r1? ${r1.isDefined}
//                     |r2? ${r2.isDefined}
//                     |###""".stripMargin)
                r2
              }

              Some(
                MagicDrawUMLStereotypeTagStereotypeInstanceValue(
                  extendedElement = e,
                  appliedStereotype = mdS,
                  stereotypeTagProperty = f,
                  stereotypeTagPropertyType = ops.umlMagicDrawUMLStereotype(ts),
                  tagPropertyValueAppliedStereotypeAndElementReferences = for {
                    v <- s.getValue.to[scala.collection.immutable.Iterable]
                    e <- v match {
                      case ev: Uml#ElementValue =>
                        ev.element match {
                          case Some(ref) =>
                            getAppliedStereotype_ElementReference_pair(ops.umlMagicDrawUMLElement(ref))
                          case None =>
                            None
                        }
                      case iv: Uml#InstanceValue =>
                        iv.instance match {
                          case Some(ref) =>
                            getAppliedStereotype_ElementReference_pair(ops.umlMagicDrawUMLElement(ref))
                          case None =>
                            None
                        }
                    }
                  } yield e))
            case tc: UMLClass[Uml] =>
              if (StereotypesHelper.isUML2MetaClass(ops.umlMagicDrawUMLClass(tc).getMagicDrawClass))
                Some(
                  MagicDrawUMLStereotypeTagPropertyMetaclassElementReference(
                    extendedElement = e,
                    appliedStereotype = mdS,
                    stereotypeTagProperty = f,
                    stereotypeTagPropertyType = ops.umlMagicDrawUMLClass(tc),
                    tagPropertyValueElementReferences = for {
                      v <- s.getValue.to[scala.collection.immutable.Iterable]
                      e <- v match {
                        case ev: Uml#ElementValue =>
                          ev.element match {
                            case Some(ref) =>
                              Some(ops.umlMagicDrawUMLElement(ref))
                            case None =>
                              None
                          }
                        case iv: Uml#InstanceValue =>
                          iv.instance match {
                            case Some(ref) =>
                              Some(ops.umlMagicDrawUMLElement(ref))
                            case None =>
                              None
                          }
                      }
                    } yield e))
              else
                Some(
                  MagicDrawUMLStereotypeTagPropertyClassifierValue(
                    extendedElement = e,
                    appliedStereotype = mdS,
                    stereotypeTagProperty = f,
                    stereotypeTagPropertyType = ops.umlMagicDrawUMLClass(tc),
                    values = for {
                      v <- s.getValue.to[scala.collection.immutable.Iterable]
                      e <- v match {
                        case iv: Uml#InstanceValue =>
                          iv.instance match {
                            case Some(is) =>
                              Some(MagicDrawTagPropertyInstanceSpecificationValue(
                                property=f,
                                value=ops.umlMagicDrawUMLInstanceSpecification(is)))
                            case None =>
                              None
                          }
                        case _ =>
                          None
                      }
                    } yield e))
            case te: UMLEnumeration[Uml] =>
              Some(
                MagicDrawUMLStereotypeTagPropertyClassifierValue(
                  extendedElement = e,
                  appliedStereotype = mdS,
                  stereotypeTagProperty = f,
                  stereotypeTagPropertyType = ops.umlMagicDrawUMLEnumeration(te),
                  values = for {
                    v <- s.getValue.to[scala.collection.immutable.Iterable]
                    e <- v match {
                      case iv: Uml#InstanceValue =>
                        Option.apply(iv.getInstance) match {
                          case Some(is) =>
                            is match {
                              case elit: Uml#EnumerationLiteral =>
                                Some(MagicDrawTagPropertyEnumerationLiteralValue(
                                  property=f,
                                  value=ops.umlMagicDrawUMLEnumerationLiteral(elit)))
                              case _ =>
                                None
                            }
                          case None =>
                            None
                        }
                      case _ =>
                        None
                    }
                  } yield e))

            case tp: UMLPrimitiveType[Uml] =>
              Some(
                MagicDrawUMLStereotypeTagPropertyClassifierValue(
                  extendedElement = e,
                  appliedStereotype = mdS,
                  stereotypeTagProperty = f,
                  stereotypeTagPropertyType = ops.umlMagicDrawUMLPrimitiveType(tp),
                  values = for {
                    v <- s.getValue.to[scala.collection.immutable.Iterable]
                    e <- v match {
                      case ev: Uml#LiteralBoolean =>
                        Some(MagicDrawTagPropertyBooleanValue(property=f, value=ev.value))
                      case ev: Uml#LiteralInteger =>
                        Some(MagicDrawTagPropertyIntegerValue(property=f, value=ev.value))
                      case ev: Uml#LiteralReal =>
                        Some(MagicDrawTagPropertyRealValue(property=f, value=ev.value))
                      case ev: Uml#LiteralString =>
                        Some(MagicDrawTagPropertyStringValue(property=f, value=ev.value.getOrElse("")))
                      case ev: Uml#LiteralUnlimitedNatural =>
                        Some(MagicDrawTagPropertyUnlimitedNaturalValue(property=f, value=ev.value))
                      case _ =>
                        None
                    }
                  } yield e))
            case td: UMLDataType[Uml] =>

              // Must cover all the MagicDraw specializations of ValueSpecification

              val (slotOwned, modelOwned) = s.getValue partition {
                case _: Uml#LiteralSpecification =>
                  true
                case _: Uml#ElementValue =>
                  false
                case _: Uml#InstanceValue =>
                  false
              }

              if (slotOwned.nonEmpty & modelOwned.isEmpty)
                td match {
                  case tdp: UMLPrimitiveType[Uml] =>
                    Some(
                      MagicDrawUMLStereotypeTagPropertyClassifierValue(
                        extendedElement = e,
                        appliedStereotype = mdS,
                        stereotypeTagProperty = f,
                        stereotypeTagPropertyType = ops.umlMagicDrawUMLPrimitiveType(tdp),
                        values = for {
                          v <- s.getValue.to[scala.collection.immutable.Iterable]
                          e <- v match {
                            case ev: Uml#LiteralBoolean =>
                              Some(MagicDrawTagPropertyBooleanValue(property=f, value=ev.value))
                            case ev: Uml#LiteralInteger =>
                              Some(MagicDrawTagPropertyIntegerValue(property=f, value=ev.value))
                            case ev: Uml#LiteralReal =>
                              Some(MagicDrawTagPropertyRealValue(property=f, value=ev.value))
                            case ev: Uml#LiteralString =>
                              Some(MagicDrawTagPropertyStringValue(property=f, value=ev.value.getOrElse("")))
                            case ev: Uml#LiteralUnlimitedNatural =>
                              Some(MagicDrawTagPropertyUnlimitedNaturalValue(property=f, value=ev.value))
                            case _ =>
                              None
                          }
                        } yield e))
                  case _ =>
                    None
                }

              else if (slotOwned.isEmpty && modelOwned.nonEmpty)
                Some(
                  MagicDrawUMLStereotypeTagPropertyClassifierValue(
                    extendedElement = e,
                    appliedStereotype = mdS,
                    stereotypeTagProperty = f,
                    stereotypeTagPropertyType = ops.umlMagicDrawUMLDataType(td),
                    values = for {
                      v <- s.getValue.to[scala.collection.immutable.Iterable]
                      e <- v match {
                        case iv: Uml#InstanceValue =>
                          Option.apply(iv.getInstance) match {
                            case Some(is) =>
                              Some(MagicDrawTagPropertyInstanceSpecificationValue(
                                property=f,
                                value=ops.umlMagicDrawUMLInstanceSpecification(is)))
                            case None =>
                              None
                          }
                        case _ =>
                          None
                      }
                    } yield e))

              else
                None

            case ta: UMLAssociation[Uml] =>
              Some(
                MagicDrawUMLStereotypeTagPropertyClassifierValue(
                  extendedElement = e,
                  appliedStereotype = mdS,
                  stereotypeTagProperty = f,
                  stereotypeTagPropertyType = ops.umlMagicDrawUMLAssociation(ta),
                  values = for {
                    v <- s.getValue.to[scala.collection.immutable.Iterable]
                    e <- v match {
                      case iv: Uml#InstanceValue =>
                        Option.apply(iv.getInstance) match {
                          case Some(is) =>
                            Some(MagicDrawTagPropertyInstanceSpecificationValue(
                              property=f,
                              value=ops.umlMagicDrawUMLInstanceSpecification(is)))
                          case None =>
                            None
                        }
                    }
                  } yield e))
            case _ =>
              None
          }
        } yield v

        (extendedMetaclassTagValues ++ tagValues).right
    }
  }

}