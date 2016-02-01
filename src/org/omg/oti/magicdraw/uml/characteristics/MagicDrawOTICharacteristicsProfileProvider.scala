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
package org.omg.oti.magicdraw.uml.characteristics

import java.lang.System

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper

import org.omg.oti.uml.UMLError
import org.omg.oti.uml.characteristics._
import org.omg.oti.magicdraw.uml.read._
import org.omg.oti.uml.read.api._

import scala.{AnyRef,Boolean,Option,None,Some,StringContext}
import scala.Predef.{Map => _, Set => _, _}
import scala.collection.JavaConversions._
import scala.collection.immutable._
import scalaz._, Scalaz._

case class MagicDrawOTISymbols
(oti_specification_root_s: UMLStereotype[MagicDrawUML],
 oti_specification_root_packageuri: UMLProperty[MagicDrawUML],
 oti_specification_root_documenturl: UMLProperty[MagicDrawUML],
 oti_specification_root_uuidprefix: UMLProperty[MagicDrawUML],
 oti_specification_root_artifactkind: UMLProperty[MagicDrawUML],
 oti_artifact_kind_specified_metamodel: UMLEnumerationLiteral[MagicDrawUML],
 oti_artifact_kind_specified_profile: UMLEnumerationLiteral[MagicDrawUML],
 oti_artifact_kind_specified_model_library: UMLEnumerationLiteral[MagicDrawUML],
 oti_artifact_kind_implemented_metamodel: UMLEnumerationLiteral[MagicDrawUML],
 oti_artifact_kind_implemented_profile: UMLEnumerationLiteral[MagicDrawUML],
 oti_artifact_kind_implemented_model_library: UMLEnumerationLiteral[MagicDrawUML],
 oti_identity_s: UMLStereotype[MagicDrawUML],
 oti_identity_xmiid: UMLProperty[MagicDrawUML],
 oti_identity_xmiuuid: UMLProperty[MagicDrawUML])
(implicit umlUtil: MagicDrawUMLUtil) {

  import umlUtil._

  lazy val serializableArtifactKindTagValues =
    Set(
      md_artifact_kind_specified_metamodel,
      md_artifact_kind_specified_profile,
      md_artifact_kind_specified_model_library)

  lazy val builtinArtifactKindTagValues =
    Set(
      md_artifact_kind_implemented_metamodel,
      md_artifact_kind_implemented_profile,
      md_artifact_kind_implemented_model_library)

  def isSerializableArtifactKindTagValue(value: AnyRef)
  : Boolean =
    serializableArtifactKindTagValues.contains(value)

  def isSerializableDocumentPackage(pkg: UMLPackage[Uml]): Boolean = {
    val mdPkg = umlMagicDrawUMLPackage(pkg).getMagicDrawPackage
    if (!StereotypesHelper.hasStereotype(mdPkg, md_specification_root_s))
      false
    else
      Option.apply(
        StereotypesHelper
          .getStereotypePropertyValue(mdPkg, md_specification_root_s, md_specification_root_artifactkind)
      )
        .fold[Boolean](false) { l =>
        l.headOption.fold[Boolean](false) {
          case `md_artifact_kind_specified_metamodel` =>
            true
          case `md_artifact_kind_specified_profile` =>
            true
          case `md_artifact_kind_specified_model_library` =>
            true
          case _ =>
            false
        }
      }
  }

  val md_specification_root_s =
    umlMagicDrawUMLStereotype(oti_specification_root_s).getMagicDrawStereotype

  val md_specification_root_packageuri =
    umlMagicDrawUMLProperty(oti_specification_root_packageuri).getMagicDrawProperty

  val md_specification_root_documenturl =
    umlMagicDrawUMLProperty(oti_specification_root_documenturl).getMagicDrawProperty

  val md_specification_root_uuidprefix =
    umlMagicDrawUMLProperty(oti_specification_root_uuidprefix).getMagicDrawProperty

  val md_specification_root_artifactkind =
    umlMagicDrawUMLProperty(oti_specification_root_artifactkind).getMagicDrawProperty

  val md_artifact_kind_specified_metamodel =
    umlMagicDrawUMLEnumerationLiteral(oti_artifact_kind_specified_metamodel).getMagicDrawEnumerationLiteral

  val md_artifact_kind_specified_profile =
    umlMagicDrawUMLEnumerationLiteral(oti_artifact_kind_specified_profile).getMagicDrawEnumerationLiteral

  val md_artifact_kind_specified_model_library =
    umlMagicDrawUMLEnumerationLiteral(oti_artifact_kind_specified_model_library).getMagicDrawEnumerationLiteral

  val md_artifact_kind_implemented_metamodel =
    umlMagicDrawUMLEnumerationLiteral(oti_artifact_kind_implemented_metamodel).getMagicDrawEnumerationLiteral

  val md_artifact_kind_implemented_profile =
    umlMagicDrawUMLEnumerationLiteral(oti_artifact_kind_implemented_profile).getMagicDrawEnumerationLiteral

  val md_artifact_kind_implemented_model_library =
    umlMagicDrawUMLEnumerationLiteral(oti_artifact_kind_implemented_model_library).getMagicDrawEnumerationLiteral

  val md_identity_s =
    umlMagicDrawUMLStereotype(oti_identity_s).getMagicDrawStereotype

  val md_identity_xmiid =
    umlMagicDrawUMLProperty(oti_identity_xmiid).getMagicDrawProperty

  val md_identity_xmiuuid =
    umlMagicDrawUMLProperty(oti_identity_xmiuuid).getMagicDrawProperty

}

case class MagicDrawOTICharacteristicsProfileProvider
()
(override implicit val otiCharacterizations: Option[Map[UMLPackage[MagicDrawUML], UMLComment[MagicDrawUML]]] = None,
 implicit val umlOps: MagicDrawUMLUtil)
extends OTICharacteristicsProfileProvider[MagicDrawUML] {

  import umlOps._

  lazy val resolvedMagicDrawOTISymbols
  : NonEmptyList[java.lang.Throwable] \/ MagicDrawOTISymbols
  = for {
    oti_specification_root_s <- OTI_SPECIFICATION_ROOT_S
    oti_specification_root_packageuri <- OTI_SPECIFICATION_ROOT_packageURI
    oti_specification_root_documenturl <- OTI_SPECIFICATION_ROOT_documentURL
    oti_specification_root_uuidprefix <- OTI_SPECIFICATION_ROOT_uuidPrefix
    oti_specification_root_artifactkind <- OTI_SPECIFICATION_ROOT_artifactKind
    oti_artifact_kind_specified_metamodel <- OTI_ARTIFACT_KIND_SPECIFIED_METAMODEL
    oti_artifact_kind_specified_profile <- OTI_ARTIFACT_KIND_SPECIFIED_PROFILE
    oti_artifact_kind_specified_model_library <- OTI_ARTIFACT_KIND_SPECIFIED_MODEL_LIBRARY
    oti_artifact_kind_implemented_metamodel <- OTI_ARTIFACT_KIND_IMPLEMENTED_METAMODEL
    oti_artifact_kind_implemented_profile <- OTI_ARTIFACT_KIND_IMPLEMENTED_PROFILE
    oti_artifact_kind_implemented_model_library <- OTI_ARTIFACT_KIND_IMPLEMENTED_MODEL_LIBRARY
    oti_identity_s <- OTI_IDENTITY_S
    oti_identity_xmiid <- OTI_IDENTITY_xmiID
    oti_identity_xmiuuid <- OTI_IDENTITY_xmiUUID
  } yield {
    MagicDrawOTISymbols(
      oti_specification_root_s,
      oti_specification_root_packageuri,
      oti_specification_root_documenturl,
      oti_specification_root_uuidprefix,
      oti_specification_root_artifactkind,
      oti_artifact_kind_specified_metamodel,
      oti_artifact_kind_specified_profile,
      oti_artifact_kind_specified_model_library,
      oti_artifact_kind_implemented_metamodel,
      oti_artifact_kind_implemented_profile,
      oti_artifact_kind_implemented_model_library,
      oti_identity_s,
      oti_identity_xmiid,
      oti_identity_xmiuuid)
  }

  override def getAllOTIBuiltInDocumentPackages
  : NonEmptyList[java.lang.Throwable] \&/ Map[UMLPackage[Uml], OTISpecificationRootCharacteristics] =
    resolvedMagicDrawOTISymbols.toThese.flatMap { otiProfile =>

      type Package_OtionalOTICharacteristicsOrError_Tuple =
      (UMLPackage[MagicDrawUML], NonEmptyList[java.lang.Throwable] \/ Option[OTISpecificationRootCharacteristics])

      val results: Set[Package_OtionalOTICharacteristicsOrError_Tuple] =
        StereotypesHelper
        .getExtendedElements(otiProfile.md_specification_root_s)
        .to[Set]
        .flatMap {
          case p: MagicDrawUML#Package =>

            val umlP = umlPackage(p)

            Option.apply(StereotypesHelper
              .getStereotypePropertyValue(
                p,
                otiProfile.md_specification_root_s,
                otiProfile.md_specification_root_artifactkind))
              .fold[Option[Package_OtionalOTICharacteristicsOrError_Tuple]](None) { tagValueList: java.util.List[_] =>

              tagValueList
                .to[List]
                .headOption
                .fold[Option[Package_OtionalOTICharacteristicsOrError_Tuple]](None) { artifactKindValue =>

                if (otiProfile.builtinArtifactKindTagValues.contains(artifactKindValue))
                  Some(umlP -> getSpecificationRootCharacteristics(umlP))
                else
                  None
              }
            }
        }

      val r0: NonEmptyList[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)] =
        \&/.That(Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]())

      val rN: NonEmptyList[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)] =
        (r0 /: results) { case (ri, (pkg, characteristicsOrError)) =>
          characteristicsOrError
            .fold[NonEmptyList[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]](
            l = (nels: NonEmptyList[java.lang.Throwable]) =>
              \&/.This(nels),
            r = (ch: Option[OTISpecificationRootCharacteristics]) =>
              ch
              .fold[NonEmptyList[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]](
                \&/.That(Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]())
              ){ _ch =>
                \&/.That(Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]((pkg, _ch)))
              }
          )
        }

      rN.map(_.toMap)
    }

  override def getAllOTISerializableDocumentPackages
  : NonEmptyList[java.lang.Throwable] \&/ Map[UMLPackage[Uml], OTISpecificationRootCharacteristics] =
    resolvedMagicDrawOTISymbols.toThese.flatMap { otiProfile =>

      type Package_OtionalOTICharacteristicsOrError_Tuple =
      (UMLPackage[MagicDrawUML], NonEmptyList[java.lang.Throwable] \/ Option[OTISpecificationRootCharacteristics])

      val results: Set[Package_OtionalOTICharacteristicsOrError_Tuple] =
        StereotypesHelper
          .getExtendedElements(otiProfile.md_specification_root_s)
          .to[Set]
          .flatMap {
            case p: MagicDrawUML#Package =>

              val umlP = umlPackage(p)

              Option.apply(StereotypesHelper
                .getStereotypePropertyValue(
                  p,
                  otiProfile.md_specification_root_s,
                  otiProfile.md_specification_root_artifactkind))
                .fold[Option[Package_OtionalOTICharacteristicsOrError_Tuple]](None) { tagValueList: java.util.List[_] =>

                tagValueList
                  .to[List]
                  .headOption
                  .fold[Option[Package_OtionalOTICharacteristicsOrError_Tuple]](None) { artifactKindValue =>

                  if (otiProfile.serializableArtifactKindTagValues.contains(artifactKindValue))
                    Some(umlP -> getSpecificationRootCharacteristics(umlP))
                  else
                    None
                }
              }
          }

      val r0: NonEmptyList[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)] =
        \&/.That(Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]())

      val rN: NonEmptyList[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)] =
        (r0 /: results) { case (ri, (pkg, characteristicsOrError)) =>
          characteristicsOrError
            .fold[NonEmptyList[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]](
            l = (nels: NonEmptyList[java.lang.Throwable]) =>
              \&/.This(nels),
            r = (ch: Option[OTISpecificationRootCharacteristics]) =>
              ch
                .fold[NonEmptyList[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]](
                \&/.That(Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]())
              ){ _ch =>
                \&/.That(Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]((pkg, _ch)))
              }
          )
        }

      rN.map(_.toMap)
    }

  override val OTI_PROFILE
  : NonEmptyList[java.lang.Throwable] \/ UMLProfile[MagicDrawUML] =
    Option.apply(StereotypesHelper.getProfile(project, "OTI"))
      .fold[NonEmptyList[java.lang.Throwable] \/ UMLProfile[MagicDrawUML]](
      NonEmptyList(
        UMLError
          .umlOpsError(umlOps, s"Cannot find the MD adaptation of the OMG OTI profile")
      ).left
    ){ pf =>
      umlProfile(pf).right
    }

  override val OTI_SPECIFICATION_ROOT_S
  : NonEmptyList[java.lang.Throwable] \/ UMLStereotype[MagicDrawUML] =
    OTI_PROFILE
      .flatMap { pf =>
        Option.apply(
          StereotypesHelper.getStereotype(project, "SpecificationRoot", umlMagicDrawUMLProfile(pf).getMagicDrawProfile))
          .fold[NonEmptyList[java.lang.Throwable] \/ UMLStereotype[MagicDrawUML]](
          NonEmptyList(
            UMLError
              .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRoot stereotype in the MD adaptation of the OMG OTI profile")
          ).left
        ){ s =>
          umlStereotype(s).right
        }
      }

  override val OTI_SPECIFICATION_ROOT_packageURI
  : NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML] =
    OTI_SPECIFICATION_ROOT_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "packageURI"))
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRoot::packageURI stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_documentURL
  : NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML] =
    OTI_SPECIFICATION_ROOT_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "documentURL"))
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRoot::documentURL stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_nsPrefix
  : NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML] =
    OTI_SPECIFICATION_ROOT_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "nsPrefix"))
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRoot::nsPrefix stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_uuidPrefix
  : NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML] =
    OTI_SPECIFICATION_ROOT_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "uuidPrefix"))
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRoot::uuidPrefix stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_artifactKind
  : NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML] =
    OTI_SPECIFICATION_ROOT_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "artifactKind"))
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRoot::artifactKind stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_CHARACTERIZATION_S
  : NonEmptyList[java.lang.Throwable] \/ UMLStereotype[MagicDrawUML] =
    OTI_PROFILE
      .flatMap { pf =>
        Option.apply(
          StereotypesHelper.getStereotype(project, "SpecificationRootCharacteristics", umlMagicDrawUMLProfile(pf).getMagicDrawProfile))
          .fold[NonEmptyList[java.lang.Throwable] \/ UMLStereotype[MagicDrawUML]](
          NonEmptyList(
            UMLError
              .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRootCharacteristics stereotype in the MD adaptation of the OMG OTI profile")
          ).left
        ){ s =>
          umlStereotype(s).right
        }
      }

  override val OTI_SPECIFICATION_ROOT_CHARACTERIZATION_packageURI
  : NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML] =
    OTI_SPECIFICATION_ROOT_CHARACTERIZATION_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "packageURI"))
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRootCharacteristics::packageURI stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_CHARACTERIZATION_documentURL
  : NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML] =
    OTI_SPECIFICATION_ROOT_CHARACTERIZATION_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "documentURL"))
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRootCharacteristics::documentURL stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_CHARACTERIZATION_nsPrefix
  : NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML] =
    OTI_SPECIFICATION_ROOT_CHARACTERIZATION_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "nsPrefix"))
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRootCharacteristics::nsPrefix stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_CHARACTERIZATION_uuidPrefix
  : NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML] =
    OTI_SPECIFICATION_ROOT_CHARACTERIZATION_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "uuidPrefix"))
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRootCharacteristics::uuidPrefix stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_CHARACTERIZATION_artifactKind
  : NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML] =
    OTI_SPECIFICATION_ROOT_CHARACTERIZATION_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "artifactKind"))
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRootCharacteristics::artifactKind stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_ARTIFACT_KIND
  : NonEmptyList[java.lang.Throwable] \/ UMLEnumeration[MagicDrawUML] =
    OTI_PROFILE
      .flatMap { pf =>
        val enums = pf.ownedType.selectByKindOf { case e: UMLEnumeration[MagicDrawUML] => e }
        enums
          .find { e => e.name.get == "OMG ArtifactKind" }
          .fold[NonEmptyList[java.lang.Throwable] \/ UMLEnumeration[MagicDrawUML]](
          NonEmptyList(
            UMLError
              .umlOpsError(umlOps, s"Cannot find the OTI::OMG ArtifactKind enum in the MD adaptation of the OMG OTI profile")
          ).left
        ){ e =>
          e.right
        }
      }

  override val OTI_ARTIFACT_KIND_SPECIFIED_METAMODEL
  : NonEmptyList[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML] =
    OTI_ARTIFACT_KIND.flatMap { k =>
      k.ownedLiteral
        .find (_.name.get == "SPECIFIED_METAMODEL")
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::OMG ArtifactKind::SPECIFIED_METAMODEL enumeration literal in the MD adaptation of the OMG OTI profile")
        ).left
      ){ e =>
        e.right
      }
    }

  override val OTI_ARTIFACT_KIND_SPECIFIED_PROFILE
  : NonEmptyList[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML] =
    OTI_ARTIFACT_KIND.flatMap { k =>
      k.ownedLiteral.find (_.name.get == "SPECIFIED_PROFILE")
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::OMG ArtifactKind::SPECIFIED_PROFILE enumeration literal in the MD adaptation of the OMG OTI profile")
        ).left
      ){ e =>
        e.right
      }
    }

  override val OTI_ARTIFACT_KIND_SPECIFIED_MODEL_LIBRARY
  : NonEmptyList[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML] =
    OTI_ARTIFACT_KIND.flatMap { k =>
      k.ownedLiteral.find (_.name.get == "SPECIFIED_MODEL_LIBRARY")
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::OMG ArtifactKind::SPECIFIED_MODEL_LIBRARY enumeration literal in the MD adaptation of the OMG OTI profile")
        ).left
      ){ e =>
        e.right
      }
    }

  override val OTI_ARTIFACT_KIND_IMPLEMENTED_METAMODEL
  : NonEmptyList[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML] =
    OTI_ARTIFACT_KIND.flatMap { k =>
      k.ownedLiteral.find (_.name.get == "IMPLEMENTED_METAMODEL")
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::OMG ArtifactKind::IMPLEMENTED_METAMODEL enumeration literal in the MD adaptation of the OMG OTI profile")
        ).left
      ){ e =>
        e.right
      }
    }

  override val OTI_ARTIFACT_KIND_IMPLEMENTED_PROFILE
  : NonEmptyList[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML] =
    OTI_ARTIFACT_KIND.flatMap { k =>
      k.ownedLiteral.find (_.name.get == "IMPLEMENTED_PROFILE")
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::OMG ArtifactKind::IMPLEMENTED_PROFILE enumeration literal in the MD adaptation of the OMG OTI profile")
        ).left
      ){ e =>
        e.right
      }
    }

  override val OTI_ARTIFACT_KIND_IMPLEMENTED_MODEL_LIBRARY
  : NonEmptyList[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML] =
    OTI_ARTIFACT_KIND.flatMap { k =>
      k.ownedLiteral.find (_.name.get == "IMPLEMENTED_MODEL_LIBRARY")
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::OMG ArtifactKind::IMPLEMENTED_MODEL_LIBRARY enumeration literal in the MD adaptation of the OMG OTI profile")
        ).left
      ){ e =>
        e.right
      }
    }

  override val OTI_IDENTITY_S
  : NonEmptyList[java.lang.Throwable] \/ UMLStereotype[MagicDrawUML] =
    OTI_PROFILE
      .flatMap { pf =>
        Option.apply(
          StereotypesHelper.getStereotype(project, "Identity", umlMagicDrawUMLProfile(pf).getMagicDrawProfile))
          .fold[NonEmptyList[java.lang.Throwable] \/ UMLStereotype[MagicDrawUML]](
          NonEmptyList(
            UMLError
              .umlOpsError(umlOps, s"Cannot find the OTI::Identity stereotype in the MD adaptation of the OMG OTI profile")
          ).left
        ){ s =>
          umlStereotype(s).right
        }
      }

  override val OTI_IDENTITY_xmiID
  : NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML] =
    OTI_IDENTITY_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "xmiID"))
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::Identity::xmiID stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_IDENTITY_xmiUUID
  : NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML] =
    OTI_IDENTITY_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "xmiUUID"))
        .fold[NonEmptyList[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        NonEmptyList(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::Identity::xmiUUID stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

}