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

package org.omg.oti.magicdraw.uml.characteristics

import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper

import org.omg.oti.json.common.OTISpecificationRootCharacteristics
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

  lazy val serializableArtifactKindTagValues
  = Set(
      md_artifact_kind_specified_metamodel,
      md_artifact_kind_specified_profile,
      md_artifact_kind_specified_model_library)

  lazy val builtinArtifactKindTagValues
  = Set(
      md_artifact_kind_implemented_metamodel,
      md_artifact_kind_implemented_profile,
      md_artifact_kind_implemented_model_library)

  def isSerializableArtifactKindTagValue(value: AnyRef)
  : Boolean
  = serializableArtifactKindTagValues.contains(value)

  def isSerializableDocumentPackage(pkg: UMLPackage[Uml])
  : Boolean
  = {
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
(override implicit val otiCharacterizations: Option[Map[UMLPackage[MagicDrawUML], UMLComment[MagicDrawUML]]],
 implicit val umlOps: MagicDrawUMLUtil)
extends OTICharacteristicsProfileProvider[MagicDrawUML] {

  import umlOps._

  lazy val resolvedMagicDrawOTISymbols
  : Set[java.lang.Throwable] \/ MagicDrawOTISymbols
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
  : Set[java.lang.Throwable] \&/ Map[UMLPackage[Uml], OTISpecificationRootCharacteristics]
  = resolvedMagicDrawOTISymbols.toThese.flatMap { otiProfile =>

      type Package_OtionalOTICharacteristicsOrError_Tuple =
      (UMLPackage[MagicDrawUML], Set[java.lang.Throwable] \/ Option[OTISpecificationRootCharacteristics])

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

      val r0: Set[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)] =
        \&/.That(Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]())

      val rN: Set[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)] =
        (r0 /: results) { case (ri, (pkg, characteristicsOrError)) =>
          characteristicsOrError
            .fold[Set[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]](
            l = (nels: Set[java.lang.Throwable]) =>
              \&/.This(nels),
            r = (ch: Option[OTISpecificationRootCharacteristics]) =>
              ch
              .fold[Set[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]](
                \&/.That(Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]())
              ){ _ch =>
                \&/.That(Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]((pkg, _ch)))
              }
          )
        }

      rN.map(_.toMap)
    }

  override def getAllOTISerializableDocumentPackages
  : Set[java.lang.Throwable] \&/ Map[UMLPackage[Uml], OTISpecificationRootCharacteristics]
  = resolvedMagicDrawOTISymbols.toThese.flatMap { otiProfile =>

      type Package_OtionalOTICharacteristicsOrError_Tuple =
      (UMLPackage[MagicDrawUML], Set[java.lang.Throwable] \/ Option[OTISpecificationRootCharacteristics])

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

      val r0: Set[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)] =
        \&/.That(Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]())

      val rN: Set[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)] =
        (r0 /: results) { case (ri, (pkg, characteristicsOrError)) =>
          characteristicsOrError
            .fold[Set[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]](
            l = (nels: Set[java.lang.Throwable]) =>
              \&/.This(nels),
            r = (ch: Option[OTISpecificationRootCharacteristics]) =>
              ch
                .fold[Set[java.lang.Throwable] \&/ Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]](
                \&/.That(Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]())
              ){ _ch =>
                \&/.That(Set[(UMLPackage[Uml], OTISpecificationRootCharacteristics)]((pkg, _ch)))
              }
          )
        }

      rN.map(_.toMap)
    }

  override val OTI_PROFILE
  : Set[java.lang.Throwable] \/ UMLProfile[MagicDrawUML]
  = Option.apply(StereotypesHelper.getProfile(project, "OTI"))
      .fold[Set[java.lang.Throwable] \/ UMLProfile[MagicDrawUML]](
      Set(
        UMLError
          .umlOpsError(umlOps, s"Cannot find the MD adaptation of the OMG OTI profile")
      ).left
    ){ pf =>
      umlProfile(pf).right
    }

  override val OTI_SPECIFICATION_ROOT_S
  : Set[java.lang.Throwable] \/ UMLStereotype[MagicDrawUML]
  = OTI_PROFILE
      .flatMap { pf =>
        Option.apply(
          StereotypesHelper.getStereotype(project, "SpecificationRoot", umlMagicDrawUMLProfile(pf).getMagicDrawProfile))
          .fold[Set[java.lang.Throwable] \/ UMLStereotype[MagicDrawUML]](
          Set(
            UMLError
              .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRoot stereotype in the MD adaptation of the OMG OTI profile")
          ).left
        ){ s =>
          umlStereotype(s).right
        }
      }

  override val OTI_SPECIFICATION_ROOT_packageURI
  : Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]
  = OTI_SPECIFICATION_ROOT_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "packageURI"))
        .fold[Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRoot::packageURI stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_documentURL
  : Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]
  = OTI_SPECIFICATION_ROOT_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "documentURL"))
        .fold[Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRoot::documentURL stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_nsPrefix
  : Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]
  = OTI_SPECIFICATION_ROOT_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "nsPrefix"))
        .fold[Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRoot::nsPrefix stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_uuidPrefix
  : Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]
  = OTI_SPECIFICATION_ROOT_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "uuidPrefix"))
        .fold[Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRoot::uuidPrefix stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_artifactKind
  : Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]
  = OTI_SPECIFICATION_ROOT_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "artifactKind"))
        .fold[Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRoot::artifactKind stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_CHARACTERIZATION_S
  : Set[java.lang.Throwable] \/ UMLStereotype[MagicDrawUML]
  = OTI_PROFILE
      .flatMap { pf =>
        Option.apply(
          StereotypesHelper.getStereotype(project, "SpecificationRootCharacteristics", umlMagicDrawUMLProfile(pf).getMagicDrawProfile))
          .fold[Set[java.lang.Throwable] \/ UMLStereotype[MagicDrawUML]](
          Set(
            UMLError
              .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRootCharacteristics stereotype in the MD adaptation of the OMG OTI profile")
          ).left
        ){ s =>
          umlStereotype(s).right
        }
      }

  override val OTI_SPECIFICATION_ROOT_CHARACTERIZATION_packageURI
  : Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]
  = OTI_SPECIFICATION_ROOT_CHARACTERIZATION_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "packageURI"))
        .fold[Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRootCharacteristics::packageURI stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_CHARACTERIZATION_documentURL
  : Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]
  = OTI_SPECIFICATION_ROOT_CHARACTERIZATION_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "documentURL"))
        .fold[Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRootCharacteristics::documentURL stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_CHARACTERIZATION_nsPrefix
  : Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]
  = OTI_SPECIFICATION_ROOT_CHARACTERIZATION_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "nsPrefix"))
        .fold[Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRootCharacteristics::nsPrefix stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_CHARACTERIZATION_uuidPrefix
  : Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]
  = OTI_SPECIFICATION_ROOT_CHARACTERIZATION_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "uuidPrefix"))
        .fold[Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRootCharacteristics::uuidPrefix stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_SPECIFICATION_ROOT_CHARACTERIZATION_artifactKind
  : Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]
  = OTI_SPECIFICATION_ROOT_CHARACTERIZATION_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "artifactKind"))
        .fold[Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::SpecificationRootCharacteristics::artifactKind stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_ARTIFACT_KIND
  : Set[java.lang.Throwable] \/ UMLEnumeration[MagicDrawUML]
  = OTI_PROFILE
      .flatMap { pf =>
        val enums = pf.ownedType.selectByKindOf { case e: UMLEnumeration[MagicDrawUML] => e }
        enums
          .find { e => e.name.get == "OMG ArtifactKind" }
          .fold[Set[java.lang.Throwable] \/ UMLEnumeration[MagicDrawUML]](
          Set(
            UMLError
              .umlOpsError(umlOps, s"Cannot find the OTI::OMG ArtifactKind enum in the MD adaptation of the OMG OTI profile")
          ).left
        ){ e =>
          e.right
        }
      }

  override val OTI_ARTIFACT_KIND_SPECIFIED_METAMODEL
  : Set[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]
  = OTI_ARTIFACT_KIND.flatMap { k =>
      k.ownedLiteral
        .find (_.name.get == "SPECIFIED_METAMODEL")
        .fold[Set[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::OMG ArtifactKind::SPECIFIED_METAMODEL enumeration literal in the MD adaptation of the OMG OTI profile")
        ).left
      ){ e =>
        e.right
      }
    }

  override val OTI_ARTIFACT_KIND_SPECIFIED_PROFILE
  : Set[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]
  = OTI_ARTIFACT_KIND.flatMap { k =>
      k.ownedLiteral.find (_.name.get == "SPECIFIED_PROFILE")
        .fold[Set[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::OMG ArtifactKind::SPECIFIED_PROFILE enumeration literal in the MD adaptation of the OMG OTI profile")
        ).left
      ){ e =>
        e.right
      }
    }

  override val OTI_ARTIFACT_KIND_SPECIFIED_MODEL_LIBRARY
  : Set[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]
  = OTI_ARTIFACT_KIND.flatMap { k =>
      k.ownedLiteral.find (_.name.get == "SPECIFIED_MODEL_LIBRARY")
        .fold[Set[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::OMG ArtifactKind::SPECIFIED_MODEL_LIBRARY enumeration literal in the MD adaptation of the OMG OTI profile")
        ).left
      ){ e =>
        e.right
      }
    }

  override val OTI_ARTIFACT_KIND_IMPLEMENTED_METAMODEL
  : Set[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]
  = OTI_ARTIFACT_KIND.flatMap { k =>
      k.ownedLiteral.find (_.name.get == "IMPLEMENTED_METAMODEL")
        .fold[Set[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::OMG ArtifactKind::IMPLEMENTED_METAMODEL enumeration literal in the MD adaptation of the OMG OTI profile")
        ).left
      ){ e =>
        e.right
      }
    }

  override val OTI_ARTIFACT_KIND_IMPLEMENTED_PROFILE
  : Set[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML] =
    OTI_ARTIFACT_KIND.flatMap { k =>
      k.ownedLiteral.find (_.name.get == "IMPLEMENTED_PROFILE")
        .fold[Set[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::OMG ArtifactKind::IMPLEMENTED_PROFILE enumeration literal in the MD adaptation of the OMG OTI profile")
        ).left
      ){ e =>
        e.right
      }
    }

  override val OTI_ARTIFACT_KIND_IMPLEMENTED_MODEL_LIBRARY
  : Set[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]
  = OTI_ARTIFACT_KIND.flatMap { k =>
      k.ownedLiteral.find (_.name.get == "IMPLEMENTED_MODEL_LIBRARY")
        .fold[Set[java.lang.Throwable] \/ UMLEnumerationLiteral[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::OMG ArtifactKind::IMPLEMENTED_MODEL_LIBRARY enumeration literal in the MD adaptation of the OMG OTI profile")
        ).left
      ){ e =>
        e.right
      }
    }

  override val OTI_IDENTITY_S
  : Set[java.lang.Throwable] \/ UMLStereotype[MagicDrawUML]
  = OTI_PROFILE
      .flatMap { pf =>
        Option.apply(
          StereotypesHelper.getStereotype(project, "Identity", umlMagicDrawUMLProfile(pf).getMagicDrawProfile))
          .fold[Set[java.lang.Throwable] \/ UMLStereotype[MagicDrawUML]](
          Set(
            UMLError
              .umlOpsError(umlOps, s"Cannot find the OTI::Identity stereotype in the MD adaptation of the OMG OTI profile")
          ).left
        ){ s =>
          umlStereotype(s).right
        }
      }

  override val OTI_IDENTITY_xmiID
  : Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]
  = OTI_IDENTITY_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "xmiID"))
        .fold[Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::Identity::xmiID stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

  override val OTI_IDENTITY_xmiUUID
  : Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]
  = OTI_IDENTITY_S.flatMap { s =>
      Option.apply(
        StereotypesHelper.getPropertyByName(umlMagicDrawUMLStereotype(s).getMagicDrawStereotype, "xmiUUID"))
        .fold[Set[java.lang.Throwable] \/ UMLProperty[MagicDrawUML]](
        Set(
          UMLError
            .umlOpsError(umlOps, s"Cannot find the OTI::Identity::xmiUUID stereotype property in the MD adaptation of the OMG OTI profile")
        ).left
      ){ p =>
        umlProperty(p).right
      }
    }

}