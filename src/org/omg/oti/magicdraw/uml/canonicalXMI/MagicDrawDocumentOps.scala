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
 package org.omg.oti.magicdraw.uml.canonicalXMI
 
 import java.lang.IllegalArgumentException
 import java.lang.System
 import java.net.{ URI, URL }
 import java.io.InputStream
 
 import com.nomagic.magicdraw.core.{ Application, Project, ProjectUtilities }
 import com.nomagic.ci.persistence.IProject
 import com.nomagic.ci.persistence.local.spi.localproject.{ LocalAttachedProject, LocalPrimaryProject }
 import com.nomagic.magicdraw.teamwork.application.storage.{ TeamworkAttachedProject, TeamworkPrimaryProject }
 
 import org.apache.xml.resolver.CatalogManager
 
 import org.omg.oti.uml.OTIPrimitiveTypes._
 import org.omg.oti.uml.UMLError
 import org.omg.oti.uml.canonicalXMI._
 import org.omg.oti.uml.characteristics._
 import org.omg.oti.uml.read.api._
 import org.omg.oti.uml.read.operations._
 import org.omg.oti.uml.xmi._
 
 import org.omg.oti.magicdraw.uml.read._
 
 import scala.{ deprecated, AnyVal, Option, None, Some, StringContext }
 import scala.collection.immutable._
 import scala.util.control.Exception._
 import scala.Predef.{ Set => _, Map => _, _ }
 import scala.reflect.runtime.universe._
 
 import scalaz._, Scalaz._, syntax.std._
 
 /**
  * MagicDraw-specific adapter for the OTI Canonical XMI DocumentOps
  */
 class MagicDrawDocumentOps
 (val otiInfo: MagicDrawOTIInfo)
     extends DocumentOps[MagicDrawUML] {
 
   override implicit val otiCharacteristicsProvider: OTICharacteristicsProvider[MagicDrawUML] =
     otiInfo.otiCharacteristicsProvider
 
   implicit val umlUtil: MagicDrawUMLUtil = otiInfo.umlOps
   implicit val docOps = this
 
   override def openExternalDocumentStreamForImport(lurl: MagicDrawUML#LoadURL): NonEmptyList[java.lang.Throwable] \/ java.io.InputStream =
     lurl.externalDocumentResourceURL.toURL.openStream().right
 
   override def getExternalDocumentURL(lurl: MagicDrawUML#LoadURL): NonEmptyList[java.lang.Throwable] \/ URI =
     lurl.externalDocumentResourceURL.right
 
   override def addDocument(ds: DocumentSet[MagicDrawUML],
     d: Document[MagicDrawUML]): NonEmptyList[java.lang.Throwable] \/ MagicDrawDocumentSet =
     MagicDrawDocumentSet.addDocument(ds, d)
 
   override def addBuiltInImmutableDocument(ds: DocumentSet[MagicDrawUML],
     info: OTISpecificationRootCharacteristics,
     documentURL: MagicDrawUML#LoadURL,
     root: UMLPackage[MagicDrawUML],
     builtInExtent: Set[UMLElement[MagicDrawUML]]): NonEmptyList[java.lang.Throwable] \/ (BuiltInImmutableDocument[MagicDrawUML], MagicDrawDocumentSet) = {
     val iD: BuiltInImmutableDocument[MagicDrawUML] =
       MagicDrawBuiltInImmutableDocument(info, documentURL, root, builtInExtent)(umlUtil)
     val result = for {
       ds2 <- addDocument(ds, iD)
     } yield (iD, ds2)
     result
   }
 
   override def addBuiltInMutableDocument(ds: DocumentSet[MagicDrawUML],
     info: OTISpecificationRootCharacteristics,
     documentURL: MagicDrawUML#LoadURL,
     root: UMLPackage[MagicDrawUML]): NonEmptyList[java.lang.Throwable] \/ (BuiltInMutableDocument[MagicDrawUML], MagicDrawDocumentSet) = {
     val result = for {
       extent <- ds.allOwnedElementsExcludingAllDocumentScopes(root)
       mD = MagicDrawBuiltInMutableDocument(info, documentURL, root, extent)(umlUtil)
       ds2 <- addDocument(ds, mD)
     } yield (mD, ds2)
     result
   }
 
   override def addLoadingMutableDocument(ds: DocumentSet[MagicDrawUML],
     info: OTISpecificationRootCharacteristics,
     documentURL: MagicDrawUML#LoadURL,
     root: UMLPackage[MagicDrawUML]): NonEmptyList[java.lang.Throwable] \/ (LoadingMutableDocument[MagicDrawUML], MagicDrawDocumentSet) = {
     val mD = MagicDrawLoadingMutableDocument(info, documentURL, root)(umlUtil)
     val result = for {
       ds2 <- addDocument(ds, mD)
     } yield (mD, ds2)
     result
   }
 
   override def addSerializableImmutableDocument(ds: DocumentSet[MagicDrawUML],
     info: OTISpecificationRootCharacteristics,
     documentURL: MagicDrawUML#LoadURL,
     root: UMLPackage[MagicDrawUML]): NonEmptyList[java.lang.Throwable] \/ (SerializableImmutableDocument[MagicDrawUML], MagicDrawDocumentSet) = {
     val result = for {
       extent <- ds.allOwnedElementsExcludingAllDocumentScopes(root)
       iD = MagicDrawSerializableImmutableDocument(info, documentURL, root, extent)(umlUtil)
       ds2 <- addDocument(ds, iD)
     } yield (iD, ds2)
     result
   }
 
   override def addSerializableMutableDocument(ds: DocumentSet[MagicDrawUML],
     info: OTISpecificationRootCharacteristics,
     documentURL: MagicDrawUML#LoadURL,
     root: UMLPackage[MagicDrawUML]): NonEmptyList[java.lang.Throwable] \/ (SerializableMutableDocument[MagicDrawUML], MagicDrawDocumentSet) = {
     val result = for {
       extent <- ds.allOwnedElementsExcludingAllDocumentScopes(root)
       mD = MagicDrawSerializableMutableDocument(info, documentURL, root, extent)(umlUtil)
       ds2 <- addDocument(ds, mD)
     } yield (mD, ds2)
     result
   }
 
   override def createDocumentSet
   ( documents: Set[Document[MagicDrawUML]],
     documentURIMapper: CatalogURIMapper,
     builtInURIMapper: CatalogURIMapper,
     aggregate: MagicDrawUML#DocumentSetAggregate )
   ( implicit 
       ops: UMLOps[MagicDrawUML],
       nodeT: TypeTag[Document[MagicDrawUML]],
       edgeT: TypeTag[DocumentEdge[Document[MagicDrawUML]]])
   : NonEmptyList[java.lang.Throwable] \&/ MagicDrawDocumentSet = {
     \&/.That(
       MagicDrawDocumentSet(
         serializableImmutableDocuments = documents.flatMap {
           case sid: SerializableImmutableDocument[MagicDrawUML] =>
             sid.some
           case _ =>
             None
         },
         serializableMutableDocuments = documents.flatMap {
           case smd: SerializableMutableDocument[MagicDrawUML] =>
             smd.some
           case _ =>
             None
         },
         loadingMutableDocuments = documents.flatMap {
           case lmd: LoadingMutableDocument[MagicDrawUML] =>
             lmd.some
           case _ =>
             None
         },
         builtInImmutableDocuments = documents.flatMap {
           case bid: BuiltInImmutableDocument[MagicDrawUML] =>
             bid.some
           case _ =>
             None
         },
         builtInMutableDocuments = documents.flatMap {
           case bmd: BuiltInMutableDocument[MagicDrawUML] =>
             bmd.some
           case _ =>
             None
         },
         documentURIMapper,
         builtInURIMapper,
         aggregate))
 
   }
 
   def initializeDocumentSet
   ()
   ( implicit 
       nodeT: TypeTag[Document[MagicDrawUML]],
       edgeT: TypeTag[DocumentEdge[Document[MagicDrawUML]]] )
   : NonEmptyList[java.lang.Throwable] \&/ MagicDrawDocumentSet = {
 
     val catalogManager: CatalogManager = new CatalogManager()
     catalogManager.setUseStaticCatalog(false)
 
     val otiCatalog: CatalogURIMapper = new CatalogURIMapper(catalogManager)
     val otiUMLCL = classOf[Document[_]].getClassLoader
     val otiPath1 = "resources/omgCatalog/omg.local.catalog.xml"
     val otiPath2 = "omgCatalog/omg.local.catalog.xml"
 
     val mdCatalog: CatalogURIMapper = new CatalogURIMapper(catalogManager)
     val mdUMLCL = classOf[MagicDrawUML].getClassLoader
     val mdPath1 = "resources/md18Catalog/omg.magicdraw.catalog.xml"
     val mdPath2 = "md18Catalog/omg.magicdraw.catalog.xml"
 
     for {
       otiURI <- Seq(otiPath1, otiPath2)
         .flatMap { path => Option.apply(otiUMLCL.getResource(path)) }
         .headOption
         .fold[NonEmptyList[java.lang.Throwable] \&/ URI] {
           \&/.This(
             NonEmptyList(
               documentOpsException(
                 this,
                 "initializeDocumentSet() failed: Cannot find OTI catalog file!")))
         } { url =>
           catching(nonFatalCatcher)
             .either(url.toURI)
             .fold[NonEmptyList[java.lang.Throwable] \&/ URI](
               (cause: java.lang.Throwable) =>
                 \&/.This(
                   NonEmptyList(
                     documentOpsException(
                       this,
                       s"initializeDocumentSet() failed: ${cause.getMessage}",
                       cause))),
 
               (uri: java.net.URI) =>
                 \&/.That(uri))
         }
 
       _ <- otiCatalog.parseCatalog(otiURI).toThese
 
       mdURI <- Seq(mdPath1, mdPath2)
         .flatMap { path => Option.apply(mdUMLCL.getResource(path)) }
         .headOption
         .fold[NonEmptyList[java.lang.Throwable] \&/ URI] {
           \&/.This(
             NonEmptyList(
               documentOpsException(
                 this,
                 "initializeDocumentSet() failed: Cannot find MagicDraw catalog file!")))
         } { url =>
           catching(nonFatalCatcher)
             .either(url.toURI)
             .fold[NonEmptyList[java.lang.Throwable] \&/ URI](
               (cause: java.lang.Throwable) =>
                 \&/.This(
                   NonEmptyList(
                     documentOpsException(
                       this,
                       s"initializeDocumentSet() failed: ${cause.getMessage}",
                       cause))),
 
               (uri: java.net.URI) =>
                 \&/.That(uri))
         }
 
       _ <- mdCatalog.parseCatalog(mdURI).toThese
 
       ds <- initializeDocumentSet(otiCatalog, mdCatalog)
     } yield ds
 
   }
 
   override def initializeDocumentSet
   ( documentURIMapper: CatalogURIMapper,
     builtInURIMapper: CatalogURIMapper )
   ( implicit 
       nodeT: TypeTag[Document[MagicDrawUML]],
       edgeT: TypeTag[DocumentEdge[Document[MagicDrawUML]]] )
   : NonEmptyList[java.lang.Throwable] \&/ MagicDrawDocumentSet = {
 
     Option.apply(Application.getInstance.getProject)
       .fold[NonEmptyList[java.lang.Throwable] \&/ MagicDrawDocumentSet] {
         \&/.This(
           NonEmptyList(
             documentOpsException(
               this,
               "initializeDocumentSet(documentURIMapper, builtInURIMapper) failed: " +
                 "Cannot initialize a MagicDraw OTI DocumentSet without a current Project")))
       } { p =>
         implicit val umlUtil = MagicDrawUMLUtil(p)
         import umlUtil._
 
           val mdPrimitiveTypesPkg =
             umlPackage(
               p
                 .getElementByID("_12_0EAPbeta_be00301_1157529392394_202602_1")
                 .asInstanceOf[MagicDrawUML#Package])
 
           val mdPrimitiveTypesExtent: Set[UMLElement[MagicDrawUML]] =
             Set(mdPrimitiveTypesPkg) ++ mdPrimitiveTypesPkg.ownedType.toSet
             
           val mdUMLPkg =
             umlPackage(
               p
                 .getElementByID("_9_0_be00301_1108053761194_467635_11463")
                 .asInstanceOf[MagicDrawUML#Package])
 
           val mdUMLExtent: Set[UMLElement[MagicDrawUML]] =
             Set(mdUMLPkg) ++ mdUMLPkg.ownedType.selectByKindOf { case mc: UMLClass[MagicDrawUML] => mc }
 
           val mdStandardProfile =
             umlProfile(
               p
                 .getElementByID("_9_0_be00301_1108050582343_527400_10847")
                 .asInstanceOf[MagicDrawUML#Profile])
 
           val mdStandardProfileExtensions =
             mdStandardProfile.ownedType.selectByKindOf { case e: UMLExtension[MagicDrawUML] => e }
           val mdStandardProfileExtensionFeatures =
             mdStandardProfileExtensions flatMap (_.ownedEnd)
           val mdStandardProfileStereotypes =
             mdStandardProfile.ownedType.selectByKindOf { case s: UMLStereotype[MagicDrawUML] => s }
           val mdStandardProfileStereotypeFeatures =
             mdStandardProfileStereotypes flatMap (_.ownedAttribute)
           val mdStandardProfileExtent: Set[UMLNamedElement[MagicDrawUML]] =
             Set(mdStandardProfile) ++
               mdStandardProfileExtensions ++ mdStandardProfileExtensionFeatures ++
               mdStandardProfileStereotypes ++ mdStandardProfileStereotypeFeatures
 
         for {
           ds0 <- 
           createDocumentSet(
               documents = Set[Document[MagicDrawUML]](),
               documentURIMapper,
               builtInURIMapper,
               aggregate = MagicDrawDocumentSetAggregate())
               
           step1 <- 
           addBuiltInImmutableDocument(
               ds0,
               info =
                 OTISpecificationRootCharacteristics(
                   packageURI = OTI_URI("http://www.omg.org/spec/PrimitiveTypes/20131001"),
                   documentURL = OTI_URL("http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi"),
                   artifactKind=OTIBuiltInProfileArtifactKind,
                   nsPrefix = OTI_NS_PREFIX("PrimitiveTypes"),
                   uuidPrefix = OTI_UUID_PREFIX("org.omg.uml.PrimitiveTypes")),
               documentURL =
                 MagicDrawAttachedLocalModuleBuiltInDocumentLoadURL(
                   new URI("http://www.omg.org/spec/UML/20131001/UML.xmi"),
                   "profiles/UML_Standard_Profile.mdzip"),
               root = mdPrimitiveTypesPkg,
               builtInExtent = mdPrimitiveTypesExtent).toThese
               
           (builtInPrimitiveTypes, ds1) = step1
           
           step2 <-
           addBuiltInImmutableDocument(
               ds1,
               info =
                 OTISpecificationRootCharacteristics(
                   packageURI = OTI_URI("http://www.omg.org/spec/UML/20131001"),
                   documentURL = OTI_URL("http://www.omg.org/spec/UML/20131001/UML.xmi"),
                   artifactKind = OTIBuiltInMetamodelArtifactKind,
                   nsPrefix = OTI_NS_PREFIX("uml"),
                   uuidPrefix = OTI_UUID_PREFIX("org.omg.uml.UML")),
               documentURL =
                 MagicDrawAttachedLocalModuleBuiltInDocumentLoadURL(
                   new URI("http://www.omg.org/spec/UML/20131001/UML.xmi"),
                   "profiles/UML_Standard_Profile.mdzip"),
               root = mdUMLPkg,
               builtInExtent = mdUMLExtent).toThese
             
           (builtInUMLMetamodel, ds2) = step2
           
           step3 <-
           addBuiltInImmutableDocument(
               ds2,
               info = 
                 OTISpecificationRootCharacteristics(
                   packageURI = OTI_URI("http://www.omg.org/spec/UML/20131001/StandardProfile"),
                   documentURL = OTI_URL("http://www.omg.org/spec/UML/20131001/StandardProfile.xmi"),
                   artifactKind = OTIBuiltInProfileArtifactKind,
                   nsPrefix = OTI_NS_PREFIX("StandardProfile"),
                   uuidPrefix = OTI_UUID_PREFIX("org.omg.uml.StandardProfile")),
               documentURL =
                 MagicDrawAttachedLocalModuleBuiltInDocumentLoadURL(
                   new URI("http://www.omg.org/spec/UML/20131001/StandardProfile.xmi"),
                   "profiles/UML_Standard_Profile.mdzip"),                  
               root = mdStandardProfile,
               builtInExtent = mdStandardProfileExtent.toSet[UMLElement[MagicDrawUML]]).toThese
           
           (builtInStandardProfile, ds3) = step3    
               
           ds4 <- 
           otiInfo.otiProfile
           .fold[NonEmptyList[java.lang.Throwable] \&/ MagicDrawDocumentSet](\&/.That(ds3)){ 
             otiProfile =>
                 
               val otiResult =
               for {
                 step <-
                 addBuiltInImmutableDocument(
                     ds3,
                     info = 
                       OTISpecificationRootCharacteristics(
                        packageURI = OTI_URI("http://www.omg.org/TIWG/OTI/20160128/OTI.profile"),
                        documentURL = OTI_URL("http://www.omg.org/TIWG/OTI/20160128/OTI.profile.xmi"),
                        artifactKind=OTIBuiltInProfileArtifactKind,
                        nsPrefix = OTI_NS_PREFIX("StandardProfile"),
                        uuidPrefix = OTI_UUID_PREFIX("org.omg.uml.StandardProfile")),
            
                      documentURL = 
                        MagicDrawAttachedLocalModuleBuiltInDocumentLoadURL(
                          new URI("http://www.omg.org/TIWG/OTI/20160128/OTI.profile"),
                          "profiles/OMG/TIWG/OTI.profile.mdzip"),   
                      root = otiProfile,
                      builtInExtent = {
                        val otiUMLStandardProfileClassifiers = 
                          otiProfile.ownedType.selectByKindOf { case cls: UMLClassifier[MagicDrawUML] => cls }
                        val otiUMLStandardProfileFeatures =
                          otiUMLStandardProfileClassifiers flatMap (_.feature)
                        val otiUMLStandardProfileExtent: Set[UMLElement[MagicDrawUML]] =
                          Set[UMLElement[MagicDrawUML]](otiProfile) ++ 
                          otiUMLStandardProfileClassifiers ++ 
                          otiUMLStandardProfileFeatures
                          otiUMLStandardProfileExtent
                      }
                    ).toThese
          
                 (_, ds) = step
              } yield ds
              
              otiResult
            }
       
         } yield ds4
       }
  }

  def getResultOrError(message: String, e: UMLElement[MagicDrawUML]*)(result: NonEmptyList[java.lang.Throwable] \/ Option[String]): NonEmptyList[java.lang.Throwable] \/ String =
    result.fold[NonEmptyList[java.lang.Throwable] \/ String](
      l = (nels: NonEmptyList[java.lang.Throwable]) =>
        NonEmptyList(
          UMLError.illegalElementException[MagicDrawUML, UMLElement[MagicDrawUML]]("Error while evaluating the " + message, e, nels.head)).left,
      r = (maybe: Option[String]) =>
        maybe.fold[NonEmptyList[java.lang.Throwable] \/ String](
          NonEmptyList(
            UMLError.illegalElementError[MagicDrawUML, UMLElement[MagicDrawUML]]("Error: missing value for the " + message, e)).left) { result =>
            result.right
          })

          /*
           * to be updated...
          
  override def createSerializableDocumentFromExistingRootPackage(info: OTISpecificationRootCharacteristics,
    root: UMLPackage[MagicDrawUML],
    specificationRootPackages: Map[UMLPackage[MagicDrawUML], OTISpecificationRootCharacteristics]): NonEmptyList[java.lang.Throwable] \/ SerializableDocument[MagicDrawUML] =
    \/.fromTryCatchNonFatal(new java.net.URI(OTI_URL.unwrap(info.documentURL)))
      .fold[NonEmptyList[java.lang.Throwable] \/ SerializableDocument[MagicDrawUML]](
        l = (t: java.lang.Throwable) =>
          NonEmptyList(
            UMLError.illegalElementException[MagicDrawUML, UMLPackage[MagicDrawUML]](
              s"createSerializableDocumentFromExistingRootPackage $info failed",
              Iterable(root), t)).left,
        r = (externalDocumentResourceURL: java.net.URI) =>
          createSerializableDocumentFromExistingRootPackage(externalDocumentResourceURL, info, root, specificationRootPackages))

  def createSerializableDocumentFromExistingRootPackage(externalDocumentResourceURL: java.net.URI,
    info: OTISpecificationRootCharacteristics,
    root: UMLPackage[MagicDrawUML],
    specificationRootPackages: Map[UMLPackage[MagicDrawUML], OTISpecificationRootCharacteristics]): NonEmptyList[java.lang.Throwable] \/ SerializableDocument[MagicDrawUML] = {
    val mdPkg = umlUtil.umlMagicDrawUMLPackage(root).getMagicDrawPackage
    import MagicDrawProjectAPIHelper._

    determinePackageExternalDocumentURL(externalDocumentResourceURL, info, root)
      .flatMap { mdDocumentURL =>
        MagicDrawSerializableDocument(info, mdDocumentURL, scope = root, specificationRootPackages).right
      }
  }

  def determinePackageExternalDocumentURL(externalDocumentResourceURL: java.net.URI,
    info: OTISpecificationRootCharacteristics,
    root: UMLPackage[MagicDrawUML]): NonEmptyList[java.lang.Throwable] \/ MagicDrawLoadURL = {
    val mdPkg = umlUtil.umlMagicDrawUMLPackage(root).getMagicDrawPackage
    import MagicDrawProjectAPIHelper._
    val mdLoadURLOrError: NonEmptyList[java.lang.Throwable] \/ MagicDrawLoadURL =
      Option.apply(ProjectUtilities.getProject(mdPkg))
        .fold[NonEmptyList[java.lang.Throwable] \/ MagicDrawLoadURL](
          NonEmptyList(
            documentOpsException(
              docOps,
              s"No MagicDraw project for package '${root.qualifiedName.get}'",
              UMLError
                .illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                  "No active MagicDraw project",
                  Iterable(root))))
            .left) {
            case mdServerProject: TeamworkPrimaryProject =>
              info.artifactKind match {
                case _@ (OTISerializableProfileArtifactKind() |
                  OTISerializableModelLibraryArtifactKind()) =>
                  MagicDrawServerProjectLoadURL(
                    externalDocumentResourceURL,
                    magicDrawServerProjectResource = mdServerProject.getResourceURI).right
                case otherK =>
                  NonEmptyList(
                    UMLError.illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                      s"artifactKind for TeamworkPrimaryProject package ${root.qualifiedName.get}' should be " +
                        s"'${OTISerializableProfileArtifactKind()}' or " +
                        s"'${OTISerializableModelLibraryArtifactKind()}', " +
                        s"not $otherK",
                      Iterable(root))).left
              }
            case mdServerModule: TeamworkAttachedProject =>
              info.artifactKind match {
                case _@ (OTISerializableProfileArtifactKind() |
                  OTISerializableModelLibraryArtifactKind()) =>
                  MagicDrawAttachedServerModuleSerializableDocumentLoadURL(
                    externalDocumentResourceURL,
                    magicDrawAttachedServerModuleResource = mdServerModule.getResourceURI).right
                case otherK =>
                  NonEmptyList(
                    UMLError.illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                      s"artifactKind for TeamworkAttachedProject package ${root.qualifiedName.get}' should be " +
                        s"'${OTISerializableProfileArtifactKind()}' or " +
                        s"'${OTISerializableModelLibraryArtifactKind()}', " +
                        s"not $otherK",
                      Iterable(root))).left
              }
            case mdLocalProject: LocalPrimaryProject =>
              info.artifactKind match {
                case _@ (OTISerializableProfileArtifactKind() |
                  OTISerializableModelLibraryArtifactKind()) =>
                  MagicDrawLocalProjectLoadURL(
                    externalDocumentResourceURL,
                    magicDrawLocalProjectResource = mdLocalProject.getResourceURI).right
                case otherK =>
                  NonEmptyList(
                    UMLError.illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                      s"artifactKind for LocalPrimaryProject package ${root.qualifiedName.get}' should be " +
                        s"'${OTISerializableProfileArtifactKind()}' or " +
                        s"'${OTISerializableModelLibraryArtifactKind()}', " +
                        s"not $otherK",
                      Iterable(root))).left
              }
            case mdLocalModule: LocalAttachedProject =>
              info.artifactKind match {
                case _@ (OTISerializableProfileArtifactKind() |
                  OTISerializableModelLibraryArtifactKind()) =>
                  MagicDrawAttachedLocalModuleSerializableDocumentLoadURL(
                    externalDocumentResourceURL,
                    magicDrawAttachedLocalModuleResource = mdLocalModule.getResourceURI).right
                case otherK =>
                  NonEmptyList(
                    UMLError.illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                      s"artifactKind for LocalAttachedProject package ${root.qualifiedName.get}' should be " +
                        s"'${OTISerializableProfileArtifactKind()}' or " +
                        s"'${OTISerializableModelLibraryArtifactKind()}', " +
                        s"not $otherK",
                      Iterable(root))).left
              }
            case iProject =>
              NonEmptyList(
                UMLError.illegalElementError[MagicDrawUML, UMLPackage[MagicDrawUML]](
                  s"package ${root.qualifiedName.get}' is in an unrecognized kind of MagicDraw IProject: $iProject ",
                  Iterable(root))).left
          }

    mdLoadURLOrError
  }

  override def createSerializableDocumentFromImportedRootPackage(info: OTISpecificationRootCharacteristics,
    documentURL: MagicDrawUML#LoadURL,
    root: UMLPackage[MagicDrawUML])(implicit ds: DocumentSet[MagicDrawUML]): NonEmptyList[java.lang.Throwable] \/ SerializableDocument[MagicDrawUML] =
    \/-(
      MagicDrawSerializableDocument(info, documentURL, root, Map()))

      */
}

/**
 * Helper for accessing MagicDraw's Project APIs
 *
 * Note: In MD 18.0, the Project APIs used below are marked Internal and deprecated.
 * These APIs are marked Open in MD 18.2
 *
 * Strict compilation with Scala (i.e., warn for deprecated APIs & treat warnings as errors)
 * requires selectively hiding intentional use of deprecated APIs.
 * Adapted from: https://issues.scala-lang.org/browse/SI-7934
 *
 * Note: In MD 18.0, the
 *
 * @see http://jdocs.nomagic.com/182/com/nomagic/ci/persistence/IProject.html
 * @see http://jdocs.nomagic.com/182/com/nomagic/ci/persistence/IProject.html#getLocationURI()
 */
@deprecated("", "")
class MagicDrawProjectAPIHelper(val iProject: IProject) extends AnyVal {

  def getResourceURI: URI =
    new URI(iProject.getLocationURI.toString)

}

object MagicDrawProjectAPIHelper {

  implicit def toAPIHelper(iProject: IProject): MagicDrawProjectAPIHelper =
    new MagicDrawProjectAPIHelper(iProject)

}