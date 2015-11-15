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

import java.io.File
import java.lang.{Object, Runnable, System}
import java.net.URI
import javax.swing.filechooser.FileFilter
import javax.swing.{JFileChooser, SwingUtilities}

import com.nomagic.magicdraw.core.{Application, ApplicationEnvironment, Project}
import com.nomagic.magicdraw.uml.ClassTypes
import com.nomagic.uml2.ext.jmi.helpers.ModelHelper
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper

import org.omg.oti.magicdraw.uml.canonicalXMI.MagicDrawBuiltInDocument

import org.omg.oti.uml.OTIPrimitiveTypes._
import org.omg.oti.uml.UMLError
import org.omg.oti.uml.canonicalXMI._
import org.omg.oti.uml.characteristics._
import org.omg.oti.uml.read.api._

import scala.reflect.runtime.universe._
import scala.language.implicitConversions
import scala.deprecated
import scala.collection.immutable._
import scala.collection.JavaConversions._
import scala.{AnyRef, Array,Boolean,Int,Option,None,Some,StringContext,Unit}
import scala.Predef.String
import scalaz._, Scalaz._

// workaround to MD's deprecated, internal API:
// com.nomagic.magicdraw.core.ApplicationEnvironment.getInstallRoot
// see https://issues.scala-lang.org/browse/SI-7934
@deprecated("", "")
case class MagicDrawFileChooser(title: String) extends Runnable {

  var result: Option[File] = None

  override def run(): Unit = {

    val ff = new FileFilter() {

      def getDescription: String = "*.catalog.xml"

      def accept(f: File): Boolean =
        f.isDirectory ||
        (f.isFile && f.getName.endsWith(".catalog.xml"))

    }

    val mdInstallDir = new File(ApplicationEnvironment.getInstallRoot)
    val fc = new JFileChooser(mdInstallDir) {

      override def getFileSelectionMode: Int = JFileChooser.FILES_ONLY

      override def getDialogTitle = title
    }

    fc.setFileFilter(ff)
    fc.setFileHidingEnabled(true)
    fc.setAcceptAllFileFilterUsed(false)

    fc.showOpenDialog(Application.getInstance().getMainFrame) match {
      case JFileChooser.APPROVE_OPTION =>
        val migrationFile = fc.getSelectedFile
        result = Some(migrationFile)
      case _ =>
        result = None
    }
  }
}

object MagicDrawFileChooser {

  def chooseCatalogFile(title: String = "Select a *.catalog.xml file"): Option[File] = {

    def chooser = MagicDrawFileChooser(title)
    if (SwingUtilities.isEventDispatchThread) chooser.run
    else SwingUtilities.invokeAndWait(chooser)

    chooser.result
  }

}

case class MagicDrawUMLUtil(project: Project)
  extends MagicDrawUMLOps {
  self =>

  type Uml = MagicDrawUML

  /**
   * The MagicDraw-specific equivalent of OCL `T`.allInstances() for an OTI adapter `T` of an OMG UML metaclass
   *
   * It is unclear what is the proper way to do the OCL equivalent of `T`.allInstances() with the MagicDraw API.
   * The logic below is based on what is available in the MagicDraw Open API.
   * @see https://support.nomagic.com/browse/MDUMLCS-16937
   *
   * @param metaclassType A MagicDraw Open API metaclass corresponding to an OMG UML metaclass
   * @param metaclass2umlOTI The OTI uml<metaclass>() converter method to map a MagicDraw metaclass instance
   *                         to a corresponding MagicDraw-specific OTI adapter instance
   * @param umlOTIType A MagicDraw-specific OTI adapter corresponding to an OMG UML metaclasss
   * @param scope An OTI UML element scoping the result of the instances of the OTI UML metaclasses to look for,
   *              or None for a global query
   * @param umlTag Scala type information about the MagicDraw-specific OTI metaclass adaptation trait
   * @param typ Scala type information about a MagicDraw-specific OTI adapter
   * @tparam T A MagicDraw-specific OTI adapter for an OMG UML metaclass
   * @return The MagicDraw-specific OTI adapter instances corresponding to MagicDraw-specific instances of
   *         a MagicDraw-specific implementation of MagicDraw-specific OTI adapter for an OMG UML metaclass
   */
  override def allInstances[T <: UMLElement[Uml]]
  (metaclassType: scala.reflect.runtime.universe.Type,
   metaclass2umlOTI: scala.reflect.runtime.universe.MethodSymbol,
   umlOTIType: scala.reflect.runtime.universe.Type,
   scope: Option[UMLElement[Uml]])
  (implicit umlTag: TypeTag[Uml], typ: TypeTag[T])
  : Option[Set[T]] = {
    val mdClass: java.lang.Class[_] = umlTag.mirror.runtimeClass(metaclassType.typeSymbol.asClass)
    // lookup the uml<metaclass>(e: Uml#<metaclass>):UML<metaclass>[Uml] implicit conversion method in this class
    Option[java.lang.reflect.Method](this.getClass.getMethod(metaclass2umlOTI.name.toString, mdClass)) match {

      case Some(converter) =>

        val mdTypes: Array[java.lang.Class[_]] = ClassTypes.getSubtypesArray(mdClass)
        val mdScope: Uml#Element = scope match {
            case Some(e) => umlMagicDrawUMLElement(e).getMagicDrawElement
            case None    => project.getModel
          }
        val mdInstances = ModelHelper.getElementsOfType(mdScope, mdTypes, true).toSet[Uml#Element]
        val otiInstances: Set[T] = for {
          mdInstance <- mdInstances
          otiInstance = converter.invoke(this, mdInstance).asInstanceOf[T]
        } yield otiInstance
        Some(otiInstances)

      case _ =>
        None
    }
  }

  override def cacheLookupOrUpdate(e: Uml#Element): UMLElement[Uml] = e match {
    case null => null

    // MagicDraw-specific    
    case _e: Uml#Diagram                             =>
      cache.getOrElseUpdate(_e, MagicDrawUMLDiagramImpl(_e, self))
    case _e: Uml#ElementValue                        =>
      cache.getOrElseUpdate(_e, MagicDrawUMLElementValueImpl(_e, self))
    case _e: Uml#Comment                             =>
      cache.getOrElseUpdate(_e, MagicDrawUMLCommentImpl(_e, self))
    case _e: Uml#RedefinableTemplateSignature        =>
      cache.getOrElseUpdate(_e, MagicDrawUMLRedefinableTemplateSignatureImpl(_e, self))
    case _e: Uml#TemplateSignature                   =>
      cache.getOrElseUpdate(_e, MagicDrawUMLTemplateSignatureImpl(_e, self))
    case _e: Uml#ElementImport                       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLElementImportImpl(_e, self))
    case _e: Uml#PackageImport                       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLPackageImportImpl(_e, self))
    case _e: Uml#ProfileApplication                  =>
      cache.getOrElseUpdate(_e, MagicDrawUMLProfileApplicationImpl(_e, self))
    case _e: Uml#PackageMerge                        =>
      cache.getOrElseUpdate(_e, MagicDrawUMLPackageMergeImpl(_e, self))
    case _e: Uml#Model                               =>
      cache.getOrElseUpdate(_e, MagicDrawUMLModelImpl(_e, self))
    case _e: Uml#Profile                             =>
      cache.getOrElseUpdate(_e, MagicDrawUMLProfileImpl(_e, self))
    case _e: Uml#Package                             =>
      cache.getOrElseUpdate(_e, MagicDrawUMLPackageImpl(_e, self))
    case _e: Uml#TimeConstraint                      =>
      cache.getOrElseUpdate(_e, MagicDrawUMLTimeConstraintImpl(_e, self))
    case _e: Uml#DurationConstraint                  =>
      cache.getOrElseUpdate(_e, MagicDrawUMLDurationConstraintImpl(_e, self))
    case _e: Uml#IntervalConstraint                  =>
      cache.getOrElseUpdate(_e, MagicDrawUMLIntervalConstraintImpl(_e, self))
    case _e: Uml#InteractionConstraint               =>
      cache.getOrElseUpdate(_e, MagicDrawUMLInteractionConstraintImpl(_e, self))
    case _e: Uml#Constraint                          =>
      cache.getOrElseUpdate(_e, MagicDrawUMLConstraintImpl(_e, self))
    case _e: Uml#ClassifierTemplateParameter         =>
      cache.getOrElseUpdate(_e, MagicDrawUMLClassifierTemplateParameterImpl(_e, self))
    case _e: Uml#ConnectableElementTemplateParameter =>
      cache.getOrElseUpdate(_e, MagicDrawUMLConnectableElementTemplateParameterImpl(_e, self))
    case _e: Uml#OperationTemplateParameter          =>
      cache.getOrElseUpdate(_e, MagicDrawUMLOperationTemplateParameterImpl(_e, self))
    case _e: Uml#TemplateParameter                   =>
      cache.getOrElseUpdate(_e, MagicDrawUMLTemplateParameterImpl(_e, self))
    case _e: Uml#Substitution                        =>
      cache.getOrElseUpdate(_e, MagicDrawUMLSubstitutionImpl(_e, self))
    case _e: Uml#ComponentRealization                =>
      cache.getOrElseUpdate(_e, MagicDrawUMLComponentRealizationImpl(_e, self))
    case _e: Uml#InterfaceRealization                =>
      cache.getOrElseUpdate(_e, MagicDrawUMLInterfaceRealizationImpl(_e, self))
    case _e: Uml#Realization                         =>
      cache.getOrElseUpdate(_e, MagicDrawUMLRealizationImpl(_e, self))
    case _e: Uml#Manifestation                       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLManifestationImpl(_e, self))
    case _e: Uml#Abstraction                         =>
      cache.getOrElseUpdate(_e, MagicDrawUMLAbstractionImpl(_e, self))
    case _e: Uml#Usage                               =>
      cache.getOrElseUpdate(_e, MagicDrawUMLUsageImpl(_e, self))
    case _e: Uml#Deployment                          =>
      cache.getOrElseUpdate(_e, MagicDrawUMLDeploymentImpl(_e, self))
    case _e: Uml#Dependency                          =>
      cache.getOrElseUpdate(_e, MagicDrawUMLDependencyImpl(_e, self))
    case _e: Uml#AcceptCallAction                    =>
      cache.getOrElseUpdate(_e, MagicDrawUMLAcceptCallActionImpl(_e, self))
    case _e: Uml#AcceptEventAction                   =>
      cache.getOrElseUpdate(_e, MagicDrawUMLAcceptEventActionImpl(_e, self))
    case _e: Uml#ValueSpecificationAction            =>
      cache.getOrElseUpdate(_e, MagicDrawUMLValueSpecificationActionImpl(_e, self))
    case _e: Uml#AddVariableValueAction              =>
      cache.getOrElseUpdate(_e, MagicDrawUMLAddVariableValueActionImpl(_e, self))
    case _e: Uml#RemoveVariableValueAction           =>
      cache.getOrElseUpdate(_e, MagicDrawUMLRemoveVariableValueActionImpl(_e, self))
    //    case _e: Uml#WriteVariableAction =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLWriteVariableAction() {
    //        override val e = _e
    //        override val ops = self
    //      })
    case _e: Uml#ClearVariableAction       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLClearVariableActionImpl(_e, self))
    case _e: Uml#ReadVariableAction        =>
      cache.getOrElseUpdate(_e, MagicDrawUMLReadVariableActionImpl(_e, self))
    case _e: Uml#VariableAction            =>
      cache.getOrElseUpdate(_e, MagicDrawUMLVariableActionImpl(_e, self))
    case _e: Uml#ClearAssociationAction    =>
      cache.getOrElseUpdate(_e, MagicDrawUMLClearAssociationActionImpl(_e, self))
    case _e: Uml#CreateObjectAction        =>
      cache.getOrElseUpdate(_e, MagicDrawUMLCreateObjectActionImpl(_e, self))
    case _e: Uml#DestroyObjectAction       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLDestroyObjectActionImpl(_e, self))
    case _e: Uml#BroadcastSignalAction     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLBroadcastSignalActionImpl(_e, self))
    case _e: Uml#CallBehaviorAction        =>
      cache.getOrElseUpdate(_e, MagicDrawUMLCallBehaviorActionImpl(_e, self))
    case _e: Uml#CallOperationAction       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLCallOperationActionImpl(_e, self))
    case _e: Uml#StartObjectBehaviorAction =>
      cache.getOrElseUpdate(_e, MagicDrawUMLStartObjectBehaviorActionImpl(_e, self))
    //    case _e: Uml#CallAction =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLCallAction() {
    //        override val e = _e
    //        override val ops = self
    //      })
    case _e: Uml#SendObjectAction =>
      cache.getOrElseUpdate(_e, MagicDrawUMLSendObjectActionImpl(_e, self))
    case _e: Uml#SendSignalAction =>
      cache.getOrElseUpdate(_e, MagicDrawUMLSendSignalActionImpl(_e, self))
    //    case _e: Uml#InvocationAction =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLInvocationAction() {
    //        override val e = _e
    //        override val ops = self
    //      })
    case _e: Uml#CreateLinkObjectAction =>
      cache.getOrElseUpdate(_e, MagicDrawUMLCreateLinkObjectActionImpl(_e, self))
    case _e: Uml#CreateLinkAction       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLCreateLinkActionImpl(_e, self))
    case _e: Uml#DestroyLinkAction      =>
      cache.getOrElseUpdate(_e, MagicDrawUMLDestroyLinkActionImpl(_e, self))
    //    case _e: Uml#WriteLinkAction =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLWriteLinkAction() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#ReadLinkAction                     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLReadLinkActionImpl(_e, self))
    case _e: Uml#OpaqueAction                       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLOpaqueActionImpl(_e, self))
    case _e: Uml#RaiseExceptionAction               =>
      cache.getOrElseUpdate(_e, MagicDrawUMLRaiseExceptionActionImpl(_e, self))
    case _e: Uml#ReadExtentAction                   =>
      cache.getOrElseUpdate(_e, MagicDrawUMLReadExtentActionImpl(_e, self))
    case _e: Uml#ReadIsClassifiedObjectAction       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLReadIsClassifiedObjectActionImpl(_e, self))
    case _e: Uml#ReadLinkObjectEndAction            =>
      cache.getOrElseUpdate(_e, MagicDrawUMLReadLinkObjectEndActionImpl(_e, self))
    case _e: Uml#ReadLinkObjectEndQualifierAction   =>
      cache.getOrElseUpdate(_e, MagicDrawUMLReadLinkObjectEndQualifierActionImpl(_e, self))
    case _e: Uml#ReadSelfAction                     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLReadSelfActionImpl(_e, self))
    case _e: Uml#AddStructuralFeatureValueAction    =>
      cache.getOrElseUpdate(_e, MagicDrawUMLAddStructuralFeatureValueActionImpl(_e, self))
    case _e: Uml#RemoveStructuralFeatureValueAction =>
      cache.getOrElseUpdate(_e, MagicDrawUMLRemoveStructuralFeatureValueActionImpl(_e, self))
    //    case _e: Uml#WriteStructuralFeatureAction =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLWriteStructuralFeatureAction() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#ReadStructuralFeatureAction  =>
      cache.getOrElseUpdate(_e, MagicDrawUMLReadStructuralFeatureActionImpl(_e, self))
    case _e: Uml#ClearStructuralFeatureAction =>
      cache.getOrElseUpdate(_e, MagicDrawUMLClearStructuralFeatureActionImpl(_e, self))
    case _e: Uml#ActionExecutionSpecification =>
      cache.getOrElseUpdate(_e, MagicDrawUMLActionExecutionSpecificationImpl(_e, self))
    //    case _e: Uml#StructuralFeatureAction =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLStructuralFeatureAction() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#StartClassifierBehaviorAction =>
      cache.getOrElseUpdate(_e, MagicDrawUMLStartClassifierBehaviorActionImpl(_e, self))
    case _e: Uml#TestIdentityAction            =>
      cache.getOrElseUpdate(_e, MagicDrawUMLTestIdentityActionImpl(_e, self))
    case _e: Uml#ReplyAction                   =>
      cache.getOrElseUpdate(_e, MagicDrawUMLReplyActionImpl(_e, self))
    case _e: Uml#ReduceAction                  =>
      cache.getOrElseUpdate(_e, MagicDrawUMLReduceActionImpl(_e, self))
    case _e: Uml#ReclassifyObjectAction        =>
      cache.getOrElseUpdate(_e, MagicDrawUMLReclassifyObjectActionImpl(_e, self))
    //    case _e: Uml#LinkAction =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLLinkAction() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#UnmarshallAction       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLUnmarshallActionImpl(_e, self))
    case _e: Uml#ConditionalNode        =>
      cache.getOrElseUpdate(_e, MagicDrawUMLConditionalNodeImpl(_e, self))
    case _e: Uml#ExpansionRegion        =>
      cache.getOrElseUpdate(_e, MagicDrawUMLExpansionRegionImpl(_e, self))
    case _e: Uml#LoopNode               =>
      cache.getOrElseUpdate(_e, MagicDrawUMLLoopNodeImpl(_e, self))
    case _e: Uml#SequenceNode           =>
      cache.getOrElseUpdate(_e, MagicDrawUMLSequenceNodeImpl(_e, self))
    case _e: Uml#StructuredActivityNode =>
      cache.getOrElseUpdate(_e, MagicDrawUMLStructuredActivityNodeImpl(_e, self))
    //    case _e: Uml#Action =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLAction() {
    //      override val e = _e
    //      override val ops = self
    //    })
    //    case _e: Uml#ExecutableNode =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLExecutableNode() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#ActionInputPin =>
      cache.getOrElseUpdate(_e, MagicDrawUMLActionInputPinImpl(_e, self))
    case _e: Uml#ValuePin       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLValuePinImpl(_e, self))
    case _e: Uml#InputPin       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLInputPinImpl(_e, self))
    case _e: Uml#OutputPin      =>
      cache.getOrElseUpdate(_e, MagicDrawUMLOutputPinImpl(_e, self))
    //    case _e: Uml#Pin =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLPin() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#FinalState               =>
      cache.getOrElseUpdate(_e, MagicDrawUMLFinalStateImpl(_e, self))
    case _e: Uml#State                    =>
      cache.getOrElseUpdate(_e, MagicDrawUMLStateImpl(_e, self))
    case _e: Uml#Pseudostate              =>
      cache.getOrElseUpdate(_e, MagicDrawUMLPseudostateImpl(_e, self))
    case _e: Uml#ConnectionPointReference =>
      cache.getOrElseUpdate(_e, MagicDrawUMLConnectionPointReferenceImpl(_e, self))
    case _e: Uml#ProtocolTransition       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLProtocolTransitionImpl(_e, self))
    case _e: Uml#Transition               =>
      cache.getOrElseUpdate(_e, MagicDrawUMLTransitionImpl(_e, self))
    case _e: Uml#ExtensionPoint           =>
      cache.getOrElseUpdate(_e, MagicDrawUMLExtensionPointImpl(_e, self))
    case _e: Uml#Region                   =>
      cache.getOrElseUpdate(_e, MagicDrawUMLRegionImpl(_e, self))
    case _e: Uml#ControlFlow              =>
      cache.getOrElseUpdate(_e, MagicDrawUMLControlFlowImpl(_e, self))
    case _e: Uml#ObjectFlow               =>
      cache.getOrElseUpdate(_e, MagicDrawUMLObjectFlowImpl(_e, self))
    //    case _e: Uml#ActivityEdge =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLActivityEdge() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#ActivityParameterNode =>
      cache.getOrElseUpdate(_e, MagicDrawUMLActivityParameterNodeImpl(_e, self))
    case _e: Uml#ExpansionNode         =>
      cache.getOrElseUpdate(_e, MagicDrawUMLExpansionNodeImpl(_e, self))
    case _e: Uml#DataStoreNode         =>
      cache.getOrElseUpdate(_e, MagicDrawUMLDataStoreNodeImpl(_e, self))
    case _e: Uml#CentralBufferNode     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLCentralBufferNodeImpl(_e, self))
    case _e: Uml#MergeNode             =>
      cache.getOrElseUpdate(_e, MagicDrawUMLMergeNodeImpl(_e, self))
    case _e: Uml#JoinNode              =>
      cache.getOrElseUpdate(_e, MagicDrawUMLJoinNodeImpl(_e, self))
    case _e: Uml#InitialNode           =>
      cache.getOrElseUpdate(_e, MagicDrawUMLInitialNodeImpl(_e, self))
    case _e: Uml#ForkNode              =>
      cache.getOrElseUpdate(_e, MagicDrawUMLForkNodeImpl(_e, self))
    case _e: Uml#ActivityFinalNode     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLActivityFinalNodeImpl(_e, self))
    case _e: Uml#FlowFinalNode         =>
      cache.getOrElseUpdate(_e, MagicDrawUMLFlowFinalNodeImpl(_e, self))
    case _e: Uml#FinalNode             =>
      cache.getOrElseUpdate(_e, MagicDrawUMLFinalNodeImpl(_e, self))
    case _e: Uml#DecisionNode          =>
      cache.getOrElseUpdate(_e, MagicDrawUMLDecisionNodeImpl(_e, self))
    //    case _e: Uml#ControlNode =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLControlNode() {
    //      override val e = _e
    //      override val ops = self
    //    })
    //    case _e: Uml#ObjectNode =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLObjectNode() {
    //      override val e = _e
    //      override val ops = self
    //    })
    //    case _e: Uml#ActivityNode =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLActivityNode() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#AnyReceiveEvent =>
      cache.getOrElseUpdate(_e, MagicDrawUMLAnyReceiveEventImpl(_e, self))
    case _e: Uml#CallEvent   =>
      cache.getOrElseUpdate(_e, MagicDrawUMLCallEventImpl(_e, self))
    case _e: Uml#SignalEvent =>
      cache.getOrElseUpdate(_e, MagicDrawUMLSignalEventImpl(_e, self))
    //    case _e: Uml#MessageEvent =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLMessageEvent() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#TimeEvent =>
      cache.getOrElseUpdate(_e, MagicDrawUMLTimeEventImpl(_e, self))
    case _e: Uml#ChangeEvent =>
      cache.getOrElseUpdate(_e, MagicDrawUMLChangeEventImpl(_e, self))
    //    case _e: Uml#Event =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLEvent() {
    //      override val e = _e
    //      override val ops = self
    //    })
    //    case _e: Uml#Interaction =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLInteraction() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#ExecutionOccurrenceSpecification   =>
      cache.getOrElseUpdate(_e, MagicDrawUMLExecutionOccurrenceSpecificationImpl(_e, self))
    case _e: Uml#DestructionOccurrenceSpecification =>
      cache.getOrElseUpdate(_e, MagicDrawUMLDestructionOccurrenceSpecificationImpl(_e, self))
    case _e: Uml#MessageOccurrenceSpecification     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLMessageOccurrenceSpecificationImpl(_e, self))
    case _e: Uml#OccurrenceSpecification            =>
      cache.getOrElseUpdate(_e, MagicDrawUMLOccurrenceSpecificationImpl(_e, self))
    case _e: Uml#Gate                               =>
      cache.getOrElseUpdate(_e, MagicDrawUMLGateImpl(_e, self))
    //    case _e: Uml#MessageEnd =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLMessageEnd() {
    //        override val e = _e
    //        override val ops = self
    //      })
    case _e: Uml#BehaviorExecutionSpecification =>
      cache.getOrElseUpdate(_e, MagicDrawUMLBehaviorExecutionSpecificationImpl(_e, self))
    //    case _e: Uml#ExecutionSpecification =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLExecutionSpecification() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#StateInvariant         =>
      cache.getOrElseUpdate(_e, MagicDrawUMLStateInvariantImpl(_e, self))
    case _e: Uml#ConsiderIgnoreFragment =>
      cache.getOrElseUpdate(_e, MagicDrawUMLConsiderIgnoreFragmentImpl(_e, self))
    case _e: Uml#CombinedFragment       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLCombinedFragmentImpl(_e, self))
    case _e: Uml#Continuation           =>
      cache.getOrElseUpdate(_e, MagicDrawUMLContinuationImpl(_e, self))
    case _e: Uml#InteractionOperand     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLInteractionOperandImpl(_e, self))
    case _e: Uml#PartDecomposition      =>
      cache.getOrElseUpdate(_e, MagicDrawUMLPartDecompositionImpl(_e, self))
    case _e: Uml#InteractionUse         =>
      cache.getOrElseUpdate(_e, MagicDrawUMLInteractionUseImpl(_e, self))
    //    case _e: Uml#InteractionFragment =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLInteractionFragment() {
    //        override val e = _e
    //        override val ops = self
    //      })
    case _e: Uml#Message                     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLMessageImpl(_e, self))
    case _e: Uml#Enumeration                 =>
      cache.getOrElseUpdate(_e, MagicDrawUMLEnumerationImpl(_e, self))
    case _e: Uml#PrimitiveType               =>
      cache.getOrElseUpdate(_e, MagicDrawUMLPrimitiveTypeImpl(_e, self))
    case _e: Uml#DataType                    =>
      cache.getOrElseUpdate(_e, MagicDrawUMLDataTypeImpl(_e, self))
    case _e: Uml#ActivityPartition           =>
      cache.getOrElseUpdate(_e, MagicDrawUMLActivityPartitionImpl(_e, self))
    case _e: Uml#InterruptibleActivityRegion =>
      cache.getOrElseUpdate(_e, MagicDrawUMLInterruptibleActivityRegionImpl(_e, self))
    //    case _e: Uml#ActivityGroup =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLActivityGroup() {
    //        override val e = _e
    //        override val ops = self
    //      })
    case _e: Uml#StringExpression        =>
      cache.getOrElseUpdate(_e, MagicDrawUMLStringExpressionImpl(_e, self))
    case _e: Uml#Expression              =>
      cache.getOrElseUpdate(_e, MagicDrawUMLExpressionImpl(_e, self))
    case _e: Uml#Duration                =>
      cache.getOrElseUpdate(_e, MagicDrawUMLDurationImpl(_e, self))
    case _e: Uml#DurationInterval        =>
      cache.getOrElseUpdate(_e, MagicDrawUMLDurationIntervalImpl(_e, self))
    case _e: Uml#TimeInterval            =>
      cache.getOrElseUpdate(_e, MagicDrawUMLTimeIntervalImpl(_e, self))
    case _e: Uml#Interval                =>
      cache.getOrElseUpdate(_e, MagicDrawUMLIntervalImpl(_e, self))
    case _e: Uml#LiteralBoolean          =>
      cache.getOrElseUpdate(_e, MagicDrawUMLLiteralBooleanImpl(_e, self))
    case _e: Uml#LiteralInteger          =>
      cache.getOrElseUpdate(_e, MagicDrawUMLLiteralIntegerImpl(_e, self))
    case _e: Uml#LiteralNull             =>
      cache.getOrElseUpdate(_e, MagicDrawUMLLiteralNullImpl(_e, self))
    case _e: Uml#LiteralReal             =>
      cache.getOrElseUpdate(_e, MagicDrawUMLLiteralRealImpl(_e, self))
    case _e: Uml#LiteralString           =>
      cache.getOrElseUpdate(_e, MagicDrawUMLLiteralStringImpl(_e, self))
    case _e: Uml#LiteralUnlimitedNatural =>
      cache.getOrElseUpdate(_e, MagicDrawUMLLiteralUnlimitedNaturalImpl(_e, self))
    //    case _e: Uml#LiteralSpecification =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLLiteralSpecification() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#OpaqueExpression   =>
      cache.getOrElseUpdate(_e, MagicDrawUMLOpaqueExpressionImpl(_e, self))
    case _e: Uml#TimeExpression     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLTimeExpressionImpl(_e, self))
    case _e: Uml#EnumerationLiteral =>
      cache.getOrElseUpdate(_e, MagicDrawUMLEnumerationLiteralImpl(_e, self))
    case _e: Uml#InstanceValue      =>
      cache.getOrElseUpdate(_e, MagicDrawUMLInstanceValueImpl(_e, self))
    //    case _e: Uml#ValueSpecification =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLValueSpecification() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#Variable     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLVariableImpl(_e, self))
    case _e: Uml#Parameter    =>
      cache.getOrElseUpdate(_e, MagicDrawUMLParameterImpl(_e, self))
    case _e: Uml#ExtensionEnd =>
      cache.getOrElseUpdate(_e, MagicDrawUMLExtensionEndImpl(_e, self))
    case _e: Uml#Port         =>
      cache.getOrElseUpdate(_e, MagicDrawUMLPortImpl(_e, self))
    case _e: Uml#Property     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLPropertyImpl(_e, self))
    case _e: Uml#Operation    =>
      cache.getOrElseUpdate(_e, MagicDrawUMLOperationImpl(_e, self))
    case _e: Uml#Reception    =>
      cache.getOrElseUpdate(_e, MagicDrawUMLReceptionImpl(_e, self))
    //    case _e: Uml#BehavioralFeature =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLBehavioralFeature() {
    //      override val e = _e
    //      override val ops = self
    //    })
    //    case _e: Uml#StructuralFeature =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLStructuralFeature() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#Connector =>
      cache.getOrElseUpdate(_e, MagicDrawUMLConnectorImpl(_e, self))
    //    case _e: Uml#Feature =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLFeature() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#ExecutionEnvironment =>
      cache.getOrElseUpdate(_e, MagicDrawUMLExecutionEnvironmentImpl(_e, self))
    case _e: Uml#Device               =>
      cache.getOrElseUpdate(_e, MagicDrawUMLDeviceImpl(_e, self))
    case _e: Uml#AssociationClass     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLAssociationClassImpl(_e, self))
    case _e: Uml#Node                 =>
      cache.getOrElseUpdate(_e, MagicDrawUMLNodeImpl(_e, self))
    case _e: Uml#Stereotype           =>
      cache.getOrElseUpdate(_e, MagicDrawUMLStereotypeImpl(_e, self))
    case _e: Uml#FunctionBehavior     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLFunctionBehaviorImpl(_e, self))
    case _e: Uml#Component            =>
      cache.getOrElseUpdate(_e, MagicDrawUMLComponentImpl(_e, self))
    case _e: Uml#ProtocolStateMachine =>
      cache.getOrElseUpdate(_e, MagicDrawUMLProtocolStateMachineImpl(_e, self))
    case _e: Uml#StateMachine         =>
      cache.getOrElseUpdate(_e, MagicDrawUMLStateMachineImpl(_e, self))
    case _e: Uml#Activity             =>
      cache.getOrElseUpdate(_e, MagicDrawUMLActivityImpl(_e, self))
    case _e: Uml#OpaqueBehavior       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLOpaqueBehaviorImpl(_e, self))
    //    case _e: Uml#Behavior =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLBehavior() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#Class                   =>
      cache.getOrElseUpdate(_e, MagicDrawUMLClassImpl(_e, self))
    case _e: Uml#Actor                   =>
      cache.getOrElseUpdate(_e, MagicDrawUMLActorImpl(_e, self))
    case _e: Uml#UseCase                 =>
      cache.getOrElseUpdate(_e, MagicDrawUMLUseCaseImpl(_e, self))
    case _e: Uml#Collaboration           =>
      cache.getOrElseUpdate(_e, MagicDrawUMLCollaborationImpl(_e, self))
    case _e: Uml#DeploymentSpecification =>
      cache.getOrElseUpdate(_e, MagicDrawUMLDeploymentSpecificationImpl(_e, self))
    case _e: Uml#Artifact                =>
      cache.getOrElseUpdate(_e, MagicDrawUMLArtifactImpl(_e, self))
    case _e: Uml#Signal                  =>
      cache.getOrElseUpdate(_e, MagicDrawUMLSignalImpl(_e, self))
    case _e: Uml#InformationItem         =>
      cache.getOrElseUpdate(_e, MagicDrawUMLInformationItemImpl(_e, self))
    case _e: Uml#Interface               =>
      cache.getOrElseUpdate(_e, MagicDrawUMLInterfaceImpl(_e, self))
    case _e: Uml#Extension               =>
      cache.getOrElseUpdate(_e, MagicDrawUMLExtensionImpl(_e, self))
    case _e: Uml#CommunicationPath       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLCommunicationPathImpl(_e, self))
    case _e: Uml#Association             =>
      cache.getOrElseUpdate(_e, MagicDrawUMLAssociationImpl(_e, self))
    case _e: Uml#TimeObservation         =>
      cache.getOrElseUpdate(_e, MagicDrawUMLTimeObservationImpl(_e, self))
    case _e: Uml#DurationObservation     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLDurationObservationImpl(_e, self))
    //    case _e: Uml#Observation =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLObservation() {
    //        override val e = _e
    //        override val ops = self
    //      })
    case _e: Uml#InstanceSpecification =>
      cache.getOrElseUpdate(_e, MagicDrawUMLInstanceSpecificationImpl(_e, self))
    case _e: Uml#InformationFlow       =>
      cache.getOrElseUpdate(_e, MagicDrawUMLInformationFlowImpl(_e, self))
    case _e: Uml#GeneralizationSet     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLGeneralizationSetImpl(_e, self))
    case _e: Uml#Trigger               =>
      cache.getOrElseUpdate(_e, MagicDrawUMLTriggerImpl(_e, self))
    case _e: Uml#Extend                =>
      cache.getOrElseUpdate(_e, MagicDrawUMLExtendImpl(_e, self))
    case _e: Uml#Include               =>
      cache.getOrElseUpdate(_e, MagicDrawUMLIncludeImpl(_e, self))
    case _e: Uml#CollaborationUse      =>
      cache.getOrElseUpdate(_e, MagicDrawUMLCollaborationUseImpl(_e, self))
    //    case _e: Uml#Vertex =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLVertex() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#GeneralOrdering =>
      cache.getOrElseUpdate(_e, MagicDrawUMLGeneralOrderingImpl(_e, self))
    case _e: Uml#Lifeline        =>
      cache.getOrElseUpdate(_e, MagicDrawUMLLifelineImpl(_e, self))
    //    case _e: Uml#DeployedArtifact =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLDeployedArtifact() {
    //        override val e = _e
    //        override val ops = self
    //      })
    //    case _e: Uml#DeploymentTarget =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLDeploymentTarget() {
    //        override val e = _e
    //        override val ops = self
    //      })
    case _e: Uml#ParameterSet                  =>
      cache.getOrElseUpdate(_e, MagicDrawUMLParameterSetImpl(_e, self))
    case _e: Uml#Clause                        =>
      cache.getOrElseUpdate(_e, MagicDrawUMLClauseImpl(_e, self))
    case _e: Uml#LinkEndCreationData           =>
      cache.getOrElseUpdate(_e, MagicDrawUMLLinkEndCreationDataImpl(_e, self))
    case _e: Uml#LinkEndDestructionData        =>
      cache.getOrElseUpdate(_e, MagicDrawUMLLinkEndDestructionDataImpl(_e, self))
    case _e: Uml#LinkEndData                   =>
      cache.getOrElseUpdate(_e, MagicDrawUMLLinkEndDataImpl(_e, self))
    case _e: Uml#TemplateParameterSubstitution =>
      cache.getOrElseUpdate(_e, MagicDrawUMLTemplateParameterSubstitutionImpl(_e, self))
    //    case _e: Uml#TemplateableElement =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLTemplateableElement() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#Slot             =>
      cache.getOrElseUpdate(_e, MagicDrawUMLSlotImpl(_e, self))
    case _e: Uml#Image            =>
      cache.getOrElseUpdate(_e, MagicDrawUMLImageImpl(_e, self))
    case _e: Uml#QualifierValue   =>
      cache.getOrElseUpdate(_e, MagicDrawUMLQualifierValueImpl(_e, self))
    case _e: Uml#ExceptionHandler =>
      cache.getOrElseUpdate(_e, MagicDrawUMLExceptionHandlerImpl(_e, self))

    // unreachable code
    //case _e: Uml#StructuredClassifier =>
    // cache.getOrElseUpdate( _e, new MagicDrawUMLStructuredClassifier()
    // { override val e = _e; override val ops = self } )
    //case _e: Uml#EncapsulatedClassifier =>
    // cache.getOrElseUpdate( _e, new MagicDrawUMLEncapsulatedClassifier()
    // { override val e = _e; override val ops = self } )
    //case _e: Uml#BehavioredClassifier =>
    // cache.getOrElseUpdate( _e, new MagicDrawUMLBehavioredClassifier()
    // { override val e = _e; override val ops = self } )
    //case _e: Uml#Classifier =>
    // cache.getOrElseUpdate( _e, new MagicDrawUMLClassifier()
    // { override val e = _e; override val ops = self } )
    //    case _e: Uml#RedefinableElement =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLRedefinableElement() {
    //      override val e = _e
    //      override val ops = self
    //    })
    //    case _e: Uml#Type =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLType() {
    //      override val e = _e
    //      override val ops = self
    //    })
    //    case _e: Uml#ConnectableElement =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLConnectableElement() {
    //      override val e = _e
    //      override val ops = self
    //    })
    //    case _e: Uml#ParameterableElement =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLParameterableElement() {
    //      override val e = _e
    //      override val ops = self
    //    })
    //    case _e: Uml#TypedElement =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLTypedElement() {
    //      override val e = _e
    //      override val ops = self
    //    })
    //case _e: Uml#PackageableElement =>
    // cache.getOrElseUpdate( _e, new MagicDrawUMLPackageableElement()
    // { override val e = _e; override val ops = self } )

    case _e: Uml#ConnectorEnd =>
      cache.getOrElseUpdate(_e, MagicDrawUMLConnectorEndImpl(_e, self))
    //    case _e: Uml#MultiplicityElement =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLMultiplicityElement() {
    //      override val e = _e
    //      override val ops = self
    //    })

    //    case _e: Uml#Namespace =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLNamespace() {
    //      override val e = _e
    //      override val ops = self
    //    })
    //    case _e: Uml#NamedElement =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLNamedElement() {
    //      override val e = _e
    //      override val ops = self
    //    })
    case _e: Uml#ProtocolConformance =>
      cache.getOrElseUpdate(_e, MagicDrawUMLProtocolConformanceImpl(_e, self))
    case _e: Uml#Generalization      =>
      cache.getOrElseUpdate(_e, MagicDrawUMLGeneralizationImpl(_e, self))
    case _e: Uml#TemplateBinding     =>
      cache.getOrElseUpdate(_e, MagicDrawUMLTemplateBindingImpl(_e, self))
    //    case _e: Uml#DirectedRelationship =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLDirectedRelationship() {
    //      override val e = _e
    //      override val ops = self
    //    })
    //    case _e: Uml#Relationship =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLRelationship() {
    //      override val e = _e
    //      override val ops = self
    //    })
    //    case _e: Uml#Element =>
    //      cache.getOrElseUpdate(_e, new MagicDrawUMLElement() {
    //      override val e = _e
    //      override val ops = self
    //    })
  }

  implicit def umlAbstraction(_e: Uml#Abstraction): UMLAbstraction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLAbstraction]

  implicit def umlAcceptCallAction(_e: Uml#AcceptCallAction): UMLAcceptCallAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLAcceptCallAction]

  implicit def umlAcceptEventAction(_e: Uml#AcceptEventAction): UMLAcceptEventAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLAcceptEventAction]

  implicit def umlAction(_e: Uml#Action): UMLAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLAction]

  implicit def umlActionExecutionSpecification(_e: Uml#ActionExecutionSpecification): UMLActionExecutionSpecification[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLActionExecutionSpecification]

  implicit def umlActionInputPin(_e: Uml#ActionInputPin): UMLActionInputPin[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLActionInputPin]

  implicit def umlActivity(_e: Uml#Activity): UMLActivity[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLActivity]

  implicit def umlActivityEdge(_e: Uml#ActivityEdge): UMLActivityEdge[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLActivityEdge]

  implicit def umlActivityFinalNode(_e: Uml#ActivityFinalNode): UMLActivityFinalNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLActivityFinalNode]

  implicit def umlActivityGroup(_e: Uml#ActivityGroup): UMLActivityGroup[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLActivityGroup]

  implicit def umlActivityNode(_e: Uml#ActivityNode): UMLActivityNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLActivityNode]

  implicit def umlActivityParameterNode(_e: Uml#ActivityParameterNode): UMLActivityParameterNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLActivityParameterNode]

  implicit def umlActivityPartition(_e: Uml#ActivityPartition): UMLActivityPartition[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLActivityPartition]

  implicit def umlActor(_e: Uml#Actor): UMLActor[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLActor]

  implicit def umlAddStructuralFeatureValueAction(_e: Uml#AddStructuralFeatureValueAction): UMLAddStructuralFeatureValueAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLAddStructuralFeatureValueAction]

  implicit def umlAddVariableValueAction(_e: Uml#AddVariableValueAction): UMLAddVariableValueAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLAddVariableValueAction]

  implicit def umlAnyReceiveEvent(_e: Uml#AnyReceiveEvent): UMLAnyReceiveEvent[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLAnyReceiveEvent]

  implicit def umlArtifact(_e: Uml#Artifact): UMLArtifact[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLArtifact]

  implicit def umlAssociation(_e: Uml#Association): UMLAssociation[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLAssociation]

  implicit def umlAssociationClass(_e: Uml#AssociationClass): UMLAssociationClass[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLAssociationClass]

  implicit def umlBehavior(_e: Uml#Behavior): UMLBehavior[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLBehavior]

  implicit def umlBehaviorExecutionSpecification(_e: Uml#BehaviorExecutionSpecification): UMLBehaviorExecutionSpecification[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLBehaviorExecutionSpecification]

  implicit def umlBehavioralFeature(_e: Uml#BehavioralFeature): UMLBehavioralFeature[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLBehavioralFeature]

  implicit def umlBehavioredClassifier(_e: Uml#BehavioredClassifier): UMLBehavioredClassifier[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLBehavioredClassifier]

  implicit def umlBroadcastSignalAction(_e: Uml#BroadcastSignalAction): UMLBroadcastSignalAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLBroadcastSignalAction]

  implicit def umlCallAction(_e: Uml#CallAction): UMLCallAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLCallAction]

  implicit def umlCallBehaviorAction(_e: Uml#CallBehaviorAction): UMLCallBehaviorAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLCallBehaviorAction]

  implicit def umlCallEvent(_e: Uml#CallEvent): UMLCallEvent[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLCallEvent]

  implicit def umlCallOperationAction(_e: Uml#CallOperationAction): UMLCallOperationAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLCallOperationAction]

  implicit def umlCentralBufferNode(_e: Uml#CentralBufferNode): UMLCentralBufferNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLCentralBufferNode]

  implicit def umlChangeEvent(_e: Uml#ChangeEvent): UMLChangeEvent[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLChangeEvent]

  implicit def umlClass(_e: Uml#Class): UMLClass[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLClass]

  implicit def umlClassifier(_e: Uml#Classifier): UMLClassifier[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLClassifier]

  implicit def umlClassifierTemplateParameter(_e: Uml#ClassifierTemplateParameter): UMLClassifierTemplateParameter[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLClassifierTemplateParameter]

  implicit def umlClause(_e: Uml#Clause): UMLClause[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLClause]

  implicit def umlClearAssociationAction(_e: Uml#ClearAssociationAction): UMLClearAssociationAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLClearAssociationAction]

  implicit def umlClearStructuralFeatureAction(_e: Uml#ClearStructuralFeatureAction): UMLClearStructuralFeatureAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLClearStructuralFeatureAction]

  implicit def umlClearVariableAction(_e: Uml#ClearVariableAction): UMLClearVariableAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLClearVariableAction]

  implicit def umlCollaboration(_e: Uml#Collaboration): UMLCollaboration[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLCollaboration]

  implicit def umlCollaborationUse(_e: Uml#CollaborationUse): UMLCollaborationUse[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLCollaborationUse]

  implicit def umlCombinedFragment(_e: Uml#CombinedFragment): UMLCombinedFragment[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLCombinedFragment]

  implicit def umlComment(_e: Uml#Comment): UMLComment[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLComment]

  implicit def umlCommunicationPath(_e: Uml#CommunicationPath): UMLCommunicationPath[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLCommunicationPath]

  implicit def umlComponent(_e: Uml#Component): UMLComponent[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLComponent]

  implicit def umlComponentRealization(_e: Uml#ComponentRealization): UMLComponentRealization[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLComponentRealization]

  implicit def umlConditionalNode(_e: Uml#ConditionalNode): UMLConditionalNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLConditionalNode]

  implicit def umlConnectableElement(_e: Uml#ConnectableElement): UMLConnectableElement[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLConnectableElement]

  implicit def umlConnectableElementTemplateParameter(_e: Uml#ConnectableElementTemplateParameter): UMLConnectableElementTemplateParameter[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLConnectableElementTemplateParameter]

  implicit def umlConnectionPointReference(_e: Uml#ConnectionPointReference): UMLConnectionPointReference[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLConnectionPointReference]

  implicit def umlConnector(_e: Uml#Connector): UMLConnector[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLConnector]

  implicit def umlConnectorEnd(_e: Uml#ConnectorEnd): UMLConnectorEnd[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLConnectorEnd]

  implicit def umlConsiderIgnoreFragment(_e: Uml#ConsiderIgnoreFragment): UMLConsiderIgnoreFragment[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLConsiderIgnoreFragment]

  implicit def umlConstraint(_e: Uml#Constraint): UMLConstraint[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLConstraint]

  implicit def umlContinuation(_e: Uml#Continuation): UMLContinuation[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLContinuation]

  implicit def umlControlFlow(_e: Uml#ControlFlow): UMLControlFlow[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLControlFlow]

  implicit def umlControlNode(_e: Uml#ControlNode): UMLControlNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLControlNode]

  implicit def umlCreateLinkAction(_e: Uml#CreateLinkAction): UMLCreateLinkAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLCreateLinkAction]

  implicit def umlCreateLinkObjectAction(_e: Uml#CreateLinkObjectAction): UMLCreateLinkObjectAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLCreateLinkObjectAction]

  implicit def umlCreateObjectAction(_e: Uml#CreateObjectAction): UMLCreateObjectAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLCreateObjectAction]

  implicit def umlDataStoreNode(_e: Uml#DataStoreNode): UMLDataStoreNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDataStoreNode]

  implicit def umlDataType(_e: Uml#DataType): UMLDataType[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDataType]

  implicit def umlDecisionNode(_e: Uml#DecisionNode): UMLDecisionNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDecisionNode]

  implicit def umlDependency(_e: Uml#Dependency): UMLDependency[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDependency]

  implicit def umlDeployedArtifact(_e: Uml#DeployedArtifact): UMLDeployedArtifact[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDeployedArtifact]

  implicit def umlDeployment(_e: Uml#Deployment): UMLDeployment[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDeployment]

  implicit def umlDeploymentSpecification(_e: Uml#DeploymentSpecification): UMLDeploymentSpecification[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDeploymentSpecification]

  implicit def umlDeploymentTarget(_e: Uml#DeploymentTarget): UMLDeploymentTarget[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDeploymentTarget]

  implicit def umlDestroyLinkAction(_e: Uml#DestroyLinkAction): UMLDestroyLinkAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDestroyLinkAction]

  implicit def umlDestroyObjectAction(_e: Uml#DestroyObjectAction): UMLDestroyObjectAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDestroyObjectAction]

  implicit def umlDestructionOccurrenceSpecification(_e: Uml#DestructionOccurrenceSpecification): UMLDestructionOccurrenceSpecification[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDestructionOccurrenceSpecification]

  implicit def umlDevice(_e: Uml#Device): UMLDevice[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDevice]

  implicit def umlDirectedRelationship(_e: Uml#DirectedRelationship): UMLDirectedRelationship[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDirectedRelationship]

  implicit def umlDuration(_e: Uml#Duration): UMLDuration[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDuration]

  implicit def umlDurationConstraint(_e: Uml#DurationConstraint): UMLDurationConstraint[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDurationConstraint]

  implicit def umlDurationInterval(_e: Uml#DurationInterval): UMLDurationInterval[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDurationInterval]

  implicit def umlDurationObservation(_e: Uml#DurationObservation): UMLDurationObservation[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDurationObservation]

  implicit def umlElement(_e: Uml#Element): UMLElement[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLElement]

  implicit def umlElementImport(_e: Uml#ElementImport): UMLElementImport[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLElementImport]

  implicit def umlEncapsulatedClassifier(_e: Uml#EncapsulatedClassifier): UMLEncapsulatedClassifier[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLEncapsulatedClassifier]

  implicit def umlEnumeration(_e: Uml#Enumeration): UMLEnumeration[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLEnumeration]

  implicit def umlEnumerationLiteral(_e: Uml#EnumerationLiteral): UMLEnumerationLiteral[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLEnumerationLiteral]

  implicit def umlEvent(_e: Uml#Event): UMLEvent[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLEvent]

  implicit def umlExceptionHandler(_e: Uml#ExceptionHandler): UMLExceptionHandler[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLExceptionHandler]

  implicit def umlExecutableNode(_e: Uml#ExecutableNode): UMLExecutableNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLExecutableNode]

  implicit def umlExecutionEnvironment(_e: Uml#ExecutionEnvironment): UMLExecutionEnvironment[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLExecutionEnvironment]

  implicit def umlExecutionOccurrenceSpecification(_e: Uml#ExecutionOccurrenceSpecification): UMLExecutionOccurrenceSpecification[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLExecutionOccurrenceSpecification]

  implicit def umlExecutionSpecification(_e: Uml#ExecutionSpecification): UMLExecutionSpecification[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLExecutionSpecification]

  implicit def umlExpansionNode(_e: Uml#ExpansionNode): UMLExpansionNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLExpansionNode]

  implicit def umlExpansionRegion(_e: Uml#ExpansionRegion): UMLExpansionRegion[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLExpansionRegion]

  implicit def umlExpression(_e: Uml#Expression): UMLExpression[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLExpression]

  implicit def umlExtend(_e: Uml#Extend): UMLExtend[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLExtend]

  implicit def umlExtension(_e: Uml#Extension): UMLExtension[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLExtension]

  implicit def umlExtensionEnd(_e: Uml#ExtensionEnd): UMLExtensionEnd[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLExtensionEnd]

  implicit def umlExtensionPoint(_e: Uml#ExtensionPoint): UMLExtensionPoint[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLExtensionPoint]

  implicit def umlFeature(_e: Uml#Feature): UMLFeature[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLFeature]

  implicit def umlFinalNode(_e: Uml#FinalNode): UMLFinalNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLFinalNode]

  implicit def umlFinalState(_e: Uml#FinalState): UMLFinalState[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLFinalState]

  implicit def umlFlowFinalNode(_e: Uml#FlowFinalNode): UMLFlowFinalNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLFlowFinalNode]

  implicit def umlForkNode(_e: Uml#ForkNode): UMLForkNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLForkNode]

  implicit def umlFunctionBehavior(_e: Uml#FunctionBehavior): UMLFunctionBehavior[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLFunctionBehavior]

  implicit def umlGate(_e: Uml#Gate): UMLGate[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLGate]

  implicit def umlGeneralOrdering(_e: Uml#GeneralOrdering): UMLGeneralOrdering[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLGeneralOrdering]

  implicit def umlGeneralization(_e: Uml#Generalization): UMLGeneralization[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLGeneralization]

  implicit def umlGeneralizationSet(_e: Uml#GeneralizationSet): UMLGeneralizationSet[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLGeneralizationSet]

  implicit def umlImage(_e: Uml#Image): UMLImage[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLImage]

  implicit def umlInclude(_e: Uml#Include): UMLInclude[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInclude]

  implicit def umlInformationFlow(_e: Uml#InformationFlow): UMLInformationFlow[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInformationFlow]

  implicit def umlInformationItem(_e: Uml#InformationItem): UMLInformationItem[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInformationItem]

  implicit def umlInitialNode(_e: Uml#InitialNode): UMLInitialNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInitialNode]

  implicit def umlInputPin(_e: Uml#InputPin): UMLInputPin[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInputPin]

  implicit def umlInstanceSpecification(_e: Uml#InstanceSpecification): UMLInstanceSpecification[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInstanceSpecification]

  implicit def umlInstanceValue(_e: Uml#InstanceValue): UMLInstanceValue[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInstanceValue]

  implicit def umlInteraction(_e: Uml#Interaction): UMLInteraction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInteraction]

  implicit def umlInteractionConstraint(_e: Uml#InteractionConstraint): UMLInteractionConstraint[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInteractionConstraint]

  implicit def umlInteractionFragment(_e: Uml#InteractionFragment): UMLInteractionFragment[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInteractionFragment]

  implicit def umlInteractionOperand(_e: Uml#InteractionOperand): UMLInteractionOperand[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInteractionOperand]

  implicit def umlInteractionUse(_e: Uml#InteractionUse): UMLInteractionUse[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInteractionUse]

  implicit def umlInterface(_e: Uml#Interface): UMLInterface[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInterface]

  implicit def umlInterfaceRealization(_e: Uml#InterfaceRealization): UMLInterfaceRealization[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInterfaceRealization]

  implicit def umlInterruptibleActivityRegion(_e: Uml#InterruptibleActivityRegion): UMLInterruptibleActivityRegion[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInterruptibleActivityRegion]

  implicit def umlInterval(_e: Uml#Interval): UMLInterval[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInterval]

  implicit def umlIntervalConstraint(_e: Uml#IntervalConstraint): UMLIntervalConstraint[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLIntervalConstraint]

  implicit def umlInvocationAction(_e: Uml#InvocationAction): UMLInvocationAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLInvocationAction]

  implicit def umlJoinNode(_e: Uml#JoinNode): UMLJoinNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLJoinNode]

  implicit def umlLifeline(_e: Uml#Lifeline): UMLLifeline[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLifeline]

  implicit def umlLinkAction(_e: Uml#LinkAction): UMLLinkAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLinkAction]

  implicit def umlLinkEndCreationData(_e: Uml#LinkEndCreationData): UMLLinkEndCreationData[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLinkEndCreationData]

  implicit def umlLinkEndData(_e: Uml#LinkEndData): UMLLinkEndData[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLinkEndData]

  implicit def umlLinkEndDestructionData(_e: Uml#LinkEndDestructionData): UMLLinkEndDestructionData[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLinkEndDestructionData]

  implicit def umlLiteralBoolean(_e: Uml#LiteralBoolean): UMLLiteralBoolean[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLiteralBoolean]

  implicit def umlLiteralInteger(_e: Uml#LiteralInteger): UMLLiteralInteger[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLiteralInteger]

  implicit def umlLiteralNull(_e: Uml#LiteralNull): UMLLiteralNull[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLiteralNull]

  implicit def umlLiteralReal(_e: Uml#LiteralReal): UMLLiteralReal[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLiteralReal]

  implicit def umlLiteralSpecification(_e: Uml#LiteralSpecification): UMLLiteralSpecification[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLiteralSpecification]

  implicit def umlLiteralString(_e: Uml#LiteralString): UMLLiteralString[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLiteralString]

  implicit def umlLiteralUnlimitedNatural(_e: Uml#LiteralUnlimitedNatural): UMLLiteralUnlimitedNatural[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLiteralUnlimitedNatural]

  implicit def umlLoopNode(_e: Uml#LoopNode): UMLLoopNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLLoopNode]

  implicit def umlManifestation(_e: Uml#Manifestation): UMLManifestation[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLManifestation]

  implicit def umlMergeNode(_e: Uml#MergeNode): UMLMergeNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLMergeNode]

  implicit def umlMessage(_e: Uml#Message): UMLMessage[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLMessage]

  implicit def umlMessageEnd(_e: Uml#MessageEnd): UMLMessageEnd[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLMessageEnd]

  implicit def umlMessageEvent(_e: Uml#MessageEvent): UMLMessageEvent[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLMessageEvent]

  implicit def umlMessageOccurrenceSpecification(_e: Uml#MessageOccurrenceSpecification): UMLMessageOccurrenceSpecification[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLMessageOccurrenceSpecification]

  implicit def umlModel(_e: Uml#Model): UMLModel[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLModel]

  implicit def umlMultiplicityElement(_e: Uml#MultiplicityElement): UMLMultiplicityElement[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLMultiplicityElement]

  implicit def umlNamedElement(_e: Uml#NamedElement): UMLNamedElement[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLNamedElement]

  implicit def umlNamespace(_e: Uml#Namespace): UMLNamespace[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLNamespace]

  implicit def umlNode(_e: Uml#Node): UMLNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLNode]

  implicit def umlObjectFlow(_e: Uml#ObjectFlow): UMLObjectFlow[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLObjectFlow]

  implicit def umlObjectNode(_e: Uml#ObjectNode): UMLObjectNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLObjectNode]

  implicit def umlObservation(_e: Uml#Observation): UMLObservation[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLObservation]

  implicit def umlOccurrenceSpecification(_e: Uml#OccurrenceSpecification): UMLOccurrenceSpecification[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLOccurrenceSpecification]

  implicit def umlOpaqueAction(_e: Uml#OpaqueAction): UMLOpaqueAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLOpaqueAction]

  implicit def umlOpaqueBehavior(_e: Uml#OpaqueBehavior): UMLOpaqueBehavior[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLOpaqueBehavior]

  implicit def umlOpaqueExpression(_e: Uml#OpaqueExpression): UMLOpaqueExpression[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLOpaqueExpression]

  implicit def umlOperation(_e: Uml#Operation): UMLOperation[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLOperation]

  implicit def umlOperationTemplateParameter(_e: Uml#OperationTemplateParameter): UMLOperationTemplateParameter[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLOperationTemplateParameter]

  implicit def umlOutputPin(_e: Uml#OutputPin): UMLOutputPin[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLOutputPin]

  implicit def umlPackage(_e: Uml#Package): UMLPackage[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLPackage]

  implicit def umlPackageImport(_e: Uml#PackageImport): UMLPackageImport[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLPackageImport]

  implicit def umlPackageMerge(_e: Uml#PackageMerge): UMLPackageMerge[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLPackageMerge]

  implicit def umlPackageableElement(_e: Uml#PackageableElement): UMLPackageableElement[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLPackageableElement]

  implicit def umlParameter(_e: Uml#Parameter): UMLParameter[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLParameter]

  implicit def umlParameterSet(_e: Uml#ParameterSet): UMLParameterSet[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLParameterSet]

  implicit def umlParameterableElement(_e: Uml#ParameterableElement): UMLParameterableElement[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLParameterableElement]

  implicit def umlPartDecomposition(_e: Uml#PartDecomposition): UMLPartDecomposition[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLPartDecomposition]

  implicit def umlPin(_e: Uml#Pin): UMLPin[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLPin]

  implicit def umlPort(_e: Uml#Port): UMLPort[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLPort]

  implicit def umlPrimitiveType(_e: Uml#PrimitiveType): UMLPrimitiveType[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLPrimitiveType]

  implicit def umlProfile(_e: Uml#Profile): UMLProfile[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLProfile]

  implicit def umlProfileApplication(_e: Uml#ProfileApplication): UMLProfileApplication[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLProfileApplication]

  implicit def umlProperty(_e: Uml#Property): UMLProperty[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLProperty]

  implicit def umlProtocolConformance(_e: Uml#ProtocolConformance): UMLProtocolConformance[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLProtocolConformance]

  implicit def umlProtocolStateMachine(_e: Uml#ProtocolStateMachine): UMLProtocolStateMachine[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLProtocolStateMachine]

  implicit def umlProtocolTransition(_e: Uml#ProtocolTransition): UMLProtocolTransition[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLProtocolTransition]

  implicit def umlPseudostate(_e: Uml#Pseudostate): UMLPseudostate[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLPseudostate]

  implicit def umlQualifierValue(_e: Uml#QualifierValue): UMLQualifierValue[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLQualifierValue]

  implicit def umlRaiseExceptionAction(_e: Uml#RaiseExceptionAction): UMLRaiseExceptionAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLRaiseExceptionAction]

  implicit def umlReadExtentAction(_e: Uml#ReadExtentAction): UMLReadExtentAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLReadExtentAction]

  implicit def umlReadIsClassifiedObjectAction(_e: Uml#ReadIsClassifiedObjectAction): UMLReadIsClassifiedObjectAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLReadIsClassifiedObjectAction]

  implicit def umlReadLinkAction(_e: Uml#ReadLinkAction): UMLReadLinkAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLReadLinkAction]

  implicit def umlReadLinkObjectEndAction(_e: Uml#ReadLinkObjectEndAction): UMLReadLinkObjectEndAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLReadLinkObjectEndAction]

  implicit def umlReadLinkObjectEndQualifierAction(_e: Uml#ReadLinkObjectEndQualifierAction): UMLReadLinkObjectEndQualifierAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLReadLinkObjectEndQualifierAction]

  implicit def umlReadSelfAction(_e: Uml#ReadSelfAction): UMLReadSelfAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLReadSelfAction]

  implicit def umlReadStructuralFeatureAction(_e: Uml#ReadStructuralFeatureAction): UMLReadStructuralFeatureAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLReadStructuralFeatureAction]

  implicit def umlReadVariableAction(_e: Uml#ReadVariableAction): UMLReadVariableAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLReadVariableAction]

  implicit def umlRealization(_e: Uml#Realization): UMLRealization[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLRealization]

  implicit def umlReception(_e: Uml#Reception): UMLReception[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLReception]

  implicit def umlReclassifyObjectAction(_e: Uml#ReclassifyObjectAction): UMLReclassifyObjectAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLReclassifyObjectAction]

  implicit def umlRedefinableElement(_e: Uml#RedefinableElement): UMLRedefinableElement[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLRedefinableElement]

  implicit def umlRedefinableTemplateSignature(_e: Uml#RedefinableTemplateSignature): UMLRedefinableTemplateSignature[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLRedefinableTemplateSignature]

  implicit def umlReduceAction(_e: Uml#ReduceAction): UMLReduceAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLReduceAction]

  implicit def umlRegion(_e: Uml#Region): UMLRegion[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLRegion]

  implicit def umlRelationship(_e: Uml#Relationship): UMLRelationship[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLRelationship]

  implicit def umlRemoveStructuralFeatureValueAction(_e: Uml#RemoveStructuralFeatureValueAction): UMLRemoveStructuralFeatureValueAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLRemoveStructuralFeatureValueAction]

  implicit def umlRemoveVariableValueAction(_e: Uml#RemoveVariableValueAction): UMLRemoveVariableValueAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLRemoveVariableValueAction]

  implicit def umlReplyAction(_e: Uml#ReplyAction): UMLReplyAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLReplyAction]

  implicit def umlSendObjectAction(_e: Uml#SendObjectAction): UMLSendObjectAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLSendObjectAction]

  implicit def umlSendSignalAction(_e: Uml#SendSignalAction): UMLSendSignalAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLSendSignalAction]

  implicit def umlSequenceNode(_e: Uml#SequenceNode): UMLSequenceNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLSequenceNode]

  implicit def umlSignal(_e: Uml#Signal): UMLSignal[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLSignal]

  implicit def umlSignalEvent(_e: Uml#SignalEvent): UMLSignalEvent[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLSignalEvent]

  implicit def umlSlot(_e: Uml#Slot): UMLSlot[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLSlot]

  implicit def umlStartClassifierBehaviorAction(_e: Uml#StartClassifierBehaviorAction): UMLStartClassifierBehaviorAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLStartClassifierBehaviorAction]

  implicit def umlStartObjectBehaviorAction(_e: Uml#StartObjectBehaviorAction): UMLStartObjectBehaviorAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLStartObjectBehaviorAction]

  implicit def umlState(_e: Uml#State): UMLState[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLState]

  implicit def umlStateInvariant(_e: Uml#StateInvariant): UMLStateInvariant[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLStateInvariant]

  implicit def umlStateMachine(_e: Uml#StateMachine): UMLStateMachine[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLStateMachine]

  implicit def umlStereotype(_e: Uml#Stereotype): UMLStereotype[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLStereotype]

  implicit def umlStringExpression(_e: Uml#StringExpression): UMLStringExpression[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLStringExpression]

  implicit def umlStructuralFeature(_e: Uml#StructuralFeature): UMLStructuralFeature[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLStructuralFeature]

  implicit def umlStructuralFeatureAction(_e: Uml#StructuralFeatureAction): UMLStructuralFeatureAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLStructuralFeatureAction]

  implicit def umlStructuredActivityNode(_e: Uml#StructuredActivityNode): UMLStructuredActivityNode[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLStructuredActivityNode]

  implicit def umlStructuredClassifier(_e: Uml#StructuredClassifier): UMLStructuredClassifier[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLStructuredClassifier]

  implicit def umlSubstitution(_e: Uml#Substitution): UMLSubstitution[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLSubstitution]

  implicit def umlTemplateBinding(_e: Uml#TemplateBinding): UMLTemplateBinding[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLTemplateBinding]

  implicit def umlTemplateParameter(_e: Uml#TemplateParameter): UMLTemplateParameter[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLTemplateParameter]

  implicit def umlTemplateParameterSubstitution(_e: Uml#TemplateParameterSubstitution): UMLTemplateParameterSubstitution[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLTemplateParameterSubstitution]

  implicit def umlTemplateSignature(_e: Uml#TemplateSignature): UMLTemplateSignature[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLTemplateSignature]

  implicit def umlTemplateableElement(_e: Uml#TemplateableElement): UMLTemplateableElement[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLTemplateableElement]

  implicit def umlTestIdentityAction(_e: Uml#TestIdentityAction): UMLTestIdentityAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLTestIdentityAction]

  implicit def umlTimeConstraint(_e: Uml#TimeConstraint): UMLTimeConstraint[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLTimeConstraint]

  implicit def umlTimeEvent(_e: Uml#TimeEvent): UMLTimeEvent[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLTimeEvent]

  implicit def umlTimeExpression(_e: Uml#TimeExpression): UMLTimeExpression[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLTimeExpression]

  implicit def umlTimeInterval(_e: Uml#TimeInterval): UMLTimeInterval[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLTimeInterval]

  implicit def umlTimeObservation(_e: Uml#TimeObservation): UMLTimeObservation[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLTimeObservation]

  implicit def umlTransition(_e: Uml#Transition): UMLTransition[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLTransition]

  implicit def umlTrigger(_e: Uml#Trigger): UMLTrigger[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLTrigger]

  implicit def umlType(_e: Uml#Type): UMLType[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLType]

  implicit def umlTypedElement(_e: Uml#TypedElement): UMLTypedElement[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLTypedElement]

  implicit def umlUnmarshallAction(_e: Uml#UnmarshallAction): UMLUnmarshallAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLUnmarshallAction]

  implicit def umlUsage(_e: Uml#Usage): UMLUsage[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLUsage]

  implicit def umlUseCase(_e: Uml#UseCase): UMLUseCase[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLUseCase]

  implicit def umlValuePin(_e: Uml#ValuePin): UMLValuePin[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLValuePin]

  implicit def umlValueSpecification(_e: Uml#ValueSpecification): UMLValueSpecification[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLValueSpecification]

  implicit def umlValueSpecificationAction(_e: Uml#ValueSpecificationAction): UMLValueSpecificationAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLValueSpecificationAction]

  implicit def umlVariable(_e: Uml#Variable): UMLVariable[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLVariable]

  implicit def umlVariableAction(_e: Uml#VariableAction): UMLVariableAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLVariableAction]

  implicit def umlVertex(_e: Uml#Vertex): UMLVertex[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLVertex]

  implicit def umlWriteLinkAction(_e: Uml#WriteLinkAction): UMLWriteLinkAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLWriteLinkAction]

  implicit def umlWriteStructuralFeatureAction(_e: Uml#WriteStructuralFeatureAction): UMLWriteStructuralFeatureAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLWriteStructuralFeatureAction]

  implicit def umlWriteVariableAction(_e: Uml#WriteVariableAction): UMLWriteVariableAction[Uml] =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLWriteVariableAction]

  // MagicDraw-specific

  implicit def umlDiagram(_e: Uml#Diagram): MagicDrawUMLDiagram =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLDiagram]

  implicit def umlElementValue(_e: Uml#ElementValue): MagicDrawUMLElementValue =
    cacheLookupOrUpdate(_e).asInstanceOf[MagicDrawUMLElementValue]

  // caution: these implicit conversions can lead to circularities if used improperly!

  def umlMagicDrawUMLAbstraction(_e: UMLAbstraction[Uml]): MagicDrawUMLAbstraction =
    _e match {
      case mdE: MagicDrawUMLAbstraction => mdE
    }

  def umlMagicDrawUMLAcceptCallAction(_e: UMLAcceptCallAction[Uml]): MagicDrawUMLAcceptCallAction =
    _e match {
      case mdE: MagicDrawUMLAcceptCallAction => mdE
    }

  def umlMagicDrawUMLAcceptEventAction(_e: UMLAcceptEventAction[Uml]): MagicDrawUMLAcceptEventAction =
    _e match {
      case mdE: MagicDrawUMLAcceptEventAction => mdE
    }

  def umlMagicDrawUMLAction(_e: UMLAction[Uml]): MagicDrawUMLAction =
    _e match {
      case mdE: MagicDrawUMLAction => mdE
    }

  def umlMagicDrawUMLActionExecutionSpecification(_e: UMLActionExecutionSpecification[Uml]): MagicDrawUMLActionExecutionSpecification =
    _e match {
      case mdE: MagicDrawUMLActionExecutionSpecification => mdE
    }

  def umlMagicDrawUMLActionInputPin(_e: UMLActionInputPin[Uml]): MagicDrawUMLActionInputPin =
    _e match {
      case mdE: MagicDrawUMLActionInputPin => mdE
    }

  def umlMagicDrawUMLActivity(_e: UMLActivity[Uml]): MagicDrawUMLActivity =
    _e match {
      case mdE: MagicDrawUMLActivity => mdE
    }

  def umlMagicDrawUMLActivityEdge(_e: UMLActivityEdge[Uml]): MagicDrawUMLActivityEdge =
    _e match {
      case mdE: MagicDrawUMLActivityEdge => mdE
    }

  def umlMagicDrawUMLActivityFinalNode(_e: UMLActivityFinalNode[Uml]): MagicDrawUMLActivityFinalNode =
    _e match {
      case mdE: MagicDrawUMLActivityFinalNode => mdE
    }

  def umlMagicDrawUMLActivityGroup(_e: UMLActivityGroup[Uml]): MagicDrawUMLActivityGroup =
    _e match {
      case mdE: MagicDrawUMLActivityGroup => mdE
    }

  def umlMagicDrawUMLActivityNode(_e: UMLActivityNode[Uml]): MagicDrawUMLActivityNode =
    _e match {
      case mdE: MagicDrawUMLActivityNode => mdE
    }

  def umlMagicDrawUMLActivityParameterNode(_e: UMLActivityParameterNode[Uml]): MagicDrawUMLActivityParameterNode =
    _e match {
      case mdE: MagicDrawUMLActivityParameterNode => mdE
    }

  def umlMagicDrawUMLActivityPartition(_e: UMLActivityPartition[Uml]): MagicDrawUMLActivityPartition =
    _e match {
      case mdE: MagicDrawUMLActivityPartition => mdE
    }

  def umlMagicDrawUMLActor(_e: UMLActor[Uml]): MagicDrawUMLActor =
    _e match {
      case mdE: MagicDrawUMLActor => mdE
    }

  def umlMagicDrawUMLAddStructuralFeatureValueAction(_e: UMLAddStructuralFeatureValueAction[Uml]): MagicDrawUMLAddStructuralFeatureValueAction =
    _e match {
      case mdE: MagicDrawUMLAddStructuralFeatureValueAction => mdE
    }

  def umlMagicDrawUMLAddVariableValueAction(_e: UMLAddVariableValueAction[Uml]): MagicDrawUMLAddVariableValueAction =
    _e match {
      case mdE: MagicDrawUMLAddVariableValueAction => mdE
    }

  def umlMagicDrawUMLAnyReceiveEvent(_e: UMLAnyReceiveEvent[Uml]): MagicDrawUMLAnyReceiveEvent =
    _e match {
      case mdE: MagicDrawUMLAnyReceiveEvent => mdE
    }

  def umlMagicDrawUMLArtifact(_e: UMLArtifact[Uml]): MagicDrawUMLArtifact =
    _e match {
      case mdE: MagicDrawUMLArtifact => mdE
    }

  def umlMagicDrawUMLAssociation(_e: UMLAssociation[Uml]): MagicDrawUMLAssociation =
    _e match {
      case mdE: MagicDrawUMLAssociation => mdE
    }

  def umlMagicDrawUMLAssociationClass(_e: UMLAssociationClass[Uml]): MagicDrawUMLAssociationClass =
    _e match {
      case mdE: MagicDrawUMLAssociationClass => mdE
    }

  def umlMagicDrawUMLBehavior(_e: UMLBehavior[Uml]): MagicDrawUMLBehavior =
    _e match {
      case mdE: MagicDrawUMLBehavior => mdE
    }

  def umlMagicDrawUMLBehaviorExecutionSpecification(_e: UMLBehaviorExecutionSpecification[Uml]): MagicDrawUMLBehaviorExecutionSpecification =
    _e match {
      case mdE: MagicDrawUMLBehaviorExecutionSpecification => mdE
    }

  def umlMagicDrawUMLBehavioralFeature(_e: UMLBehavioralFeature[Uml]): MagicDrawUMLBehavioralFeature =
    _e match {
      case mdE: MagicDrawUMLBehavioralFeature => mdE
    }

  def umlMagicDrawUMLBehavioredClassifier(_e: UMLBehavioredClassifier[Uml]): MagicDrawUMLBehavioredClassifier =
    _e match {
      case mdE: MagicDrawUMLBehavioredClassifier => mdE
    }

  def umlMagicDrawUMLBroadcastSignalAction(_e: UMLBroadcastSignalAction[Uml]): MagicDrawUMLBroadcastSignalAction =
    _e match {
      case mdE: MagicDrawUMLBroadcastSignalAction => mdE
    }

  def umlMagicDrawUMLCallAction(_e: UMLCallAction[Uml]): MagicDrawUMLCallAction =
    _e match {
      case mdE: MagicDrawUMLCallAction => mdE
    }

  def umlMagicDrawUMLCallBehaviorAction(_e: UMLCallBehaviorAction[Uml]): MagicDrawUMLCallBehaviorAction =
    _e match {
      case mdE: MagicDrawUMLCallBehaviorAction => mdE
    }

  def umlMagicDrawUMLCallEvent(_e: UMLCallEvent[Uml]): MagicDrawUMLCallEvent =
    _e match {
      case mdE: MagicDrawUMLCallEvent => mdE
    }

  def umlMagicDrawUMLCallOperationAction(_e: UMLCallOperationAction[Uml]): MagicDrawUMLCallOperationAction =
    _e match {
      case mdE: MagicDrawUMLCallOperationAction => mdE
    }

  def umlMagicDrawUMLCentralBufferNode(_e: UMLCentralBufferNode[Uml]): MagicDrawUMLCentralBufferNode =
    _e match {
      case mdE: MagicDrawUMLCentralBufferNode => mdE
    }

  def umlMagicDrawUMLChangeEvent(_e: UMLChangeEvent[Uml]): MagicDrawUMLChangeEvent =
    _e match {
      case mdE: MagicDrawUMLChangeEvent => mdE
    }

  def umlMagicDrawUMLClass(_e: UMLClass[Uml]): MagicDrawUMLClass =
    _e match {
      case mdE: MagicDrawUMLClass => mdE
    }

  def umlMagicDrawUMLClassifier(_e: UMLClassifier[Uml]): MagicDrawUMLClassifier =
    _e match {
      case mdE: MagicDrawUMLClassifier => mdE
    }

  def umlMagicDrawUMLClassifierTemplateParameter(_e: UMLClassifierTemplateParameter[Uml]): MagicDrawUMLClassifierTemplateParameter =
    _e match {
      case mdE: MagicDrawUMLClassifierTemplateParameter => mdE
    }

  def umlMagicDrawUMLClause(_e: UMLClause[Uml]): MagicDrawUMLClause =
    _e match {
      case mdE: MagicDrawUMLClause => mdE
    }

  def umlMagicDrawUMLClearAssociationAction(_e: UMLClearAssociationAction[Uml]): MagicDrawUMLClearAssociationAction =
    _e match {
      case mdE: MagicDrawUMLClearAssociationAction => mdE
    }

  def umlMagicDrawUMLClearStructuralFeatureAction(_e: UMLClearStructuralFeatureAction[Uml]): MagicDrawUMLClearStructuralFeatureAction =
    _e match {
      case mdE: MagicDrawUMLClearStructuralFeatureAction => mdE
    }

  def umlMagicDrawUMLClearVariableAction(_e: UMLClearVariableAction[Uml]): MagicDrawUMLClearVariableAction =
    _e match {
      case mdE: MagicDrawUMLClearVariableAction => mdE
    }

  def umlMagicDrawUMLCollaboration(_e: UMLCollaboration[Uml]): MagicDrawUMLCollaboration =
    _e match {
      case mdE: MagicDrawUMLCollaboration => mdE
    }

  def umlMagicDrawUMLCollaborationUse(_e: UMLCollaborationUse[Uml]): MagicDrawUMLCollaborationUse =
    _e match {
      case mdE: MagicDrawUMLCollaborationUse => mdE
    }

  def umlMagicDrawUMLCombinedFragment(_e: UMLCombinedFragment[Uml]): MagicDrawUMLCombinedFragment =
    _e match {
      case mdE: MagicDrawUMLCombinedFragment => mdE
    }

  def umlMagicDrawUMLComment(_e: UMLComment[Uml]): MagicDrawUMLComment =
    _e match {
      case mdE: MagicDrawUMLComment => mdE
    }

  def umlMagicDrawUMLCommunicationPath(_e: UMLCommunicationPath[Uml]): MagicDrawUMLCommunicationPath =
    _e match {
      case mdE: MagicDrawUMLCommunicationPath => mdE
    }

  def umlMagicDrawUMLComponent(_e: UMLComponent[Uml]): MagicDrawUMLComponent =
    _e match {
      case mdE: MagicDrawUMLComponent => mdE
    }

  def umlMagicDrawUMLComponentRealization(_e: UMLComponentRealization[Uml]): MagicDrawUMLComponentRealization =
    _e match {
      case mdE: MagicDrawUMLComponentRealization => mdE
    }

  def umlMagicDrawUMLConditionalNode(_e: UMLConditionalNode[Uml]): MagicDrawUMLConditionalNode =
    _e match {
      case mdE: MagicDrawUMLConditionalNode => mdE
    }

  def umlMagicDrawUMLConnectableElement(_e: UMLConnectableElement[Uml]): MagicDrawUMLConnectableElement =
    _e match {
      case mdE: MagicDrawUMLConnectableElement => mdE
    }

  def umlMagicDrawUMLConnectableElementTemplateParameter(_e: UMLConnectableElementTemplateParameter[Uml]): MagicDrawUMLConnectableElementTemplateParameter =
    _e match {
      case mdE: MagicDrawUMLConnectableElementTemplateParameter => mdE
    }

  def umlMagicDrawUMLConnectionPointReference(_e: UMLConnectionPointReference[Uml]): MagicDrawUMLConnectionPointReference =
    _e match {
      case mdE: MagicDrawUMLConnectionPointReference => mdE
    }

  def umlMagicDrawUMLConnector(_e: UMLConnector[Uml]): MagicDrawUMLConnector =
    _e match {
      case mdE: MagicDrawUMLConnector => mdE
    }

  def umlMagicDrawUMLConnectorEnd(_e: UMLConnectorEnd[Uml]): MagicDrawUMLConnectorEnd =
    _e match {
      case mdE: MagicDrawUMLConnectorEnd => mdE
    }

  def umlMagicDrawUMLConsiderIgnoreFragment(_e: UMLConsiderIgnoreFragment[Uml]): MagicDrawUMLConsiderIgnoreFragment =
    _e match {
      case mdE: MagicDrawUMLConsiderIgnoreFragment => mdE
    }

  def umlMagicDrawUMLConstraint(_e: UMLConstraint[Uml]): MagicDrawUMLConstraint =
    _e match {
      case mdE: MagicDrawUMLConstraint => mdE
    }

  def umlMagicDrawUMLContinuation(_e: UMLContinuation[Uml]): MagicDrawUMLContinuation =
    _e match {
      case mdE: MagicDrawUMLContinuation => mdE
    }

  def umlMagicDrawUMLControlFlow(_e: UMLControlFlow[Uml]): MagicDrawUMLControlFlow =
    _e match {
      case mdE: MagicDrawUMLControlFlow => mdE
    }

  def umlMagicDrawUMLControlNode(_e: UMLControlNode[Uml]): MagicDrawUMLControlNode =
    _e match {
      case mdE: MagicDrawUMLControlNode => mdE
    }

  def umlMagicDrawUMLCreateLinkAction(_e: UMLCreateLinkAction[Uml]): MagicDrawUMLCreateLinkAction =
    _e match {
      case mdE: MagicDrawUMLCreateLinkAction => mdE
    }

  def umlMagicDrawUMLCreateLinkObjectAction(_e: UMLCreateLinkObjectAction[Uml]): MagicDrawUMLCreateLinkObjectAction =
    _e match {
      case mdE: MagicDrawUMLCreateLinkObjectAction => mdE
    }

  def umlMagicDrawUMLCreateObjectAction(_e: UMLCreateObjectAction[Uml]): MagicDrawUMLCreateObjectAction =
    _e match {
      case mdE: MagicDrawUMLCreateObjectAction => mdE
    }

  def umlMagicDrawUMLDataStoreNode(_e: UMLDataStoreNode[Uml]): MagicDrawUMLDataStoreNode =
    _e match {
      case mdE: MagicDrawUMLDataStoreNode => mdE
    }

  def umlMagicDrawUMLDataType(_e: UMLDataType[Uml]): MagicDrawUMLDataType =
    _e match {
      case mdE: MagicDrawUMLDataType => mdE
    }

  def umlMagicDrawUMLDecisionNode(_e: UMLDecisionNode[Uml]): MagicDrawUMLDecisionNode =
    _e match {
      case mdE: MagicDrawUMLDecisionNode => mdE
    }

  def umlMagicDrawUMLDependency(_e: UMLDependency[Uml]): MagicDrawUMLDependency =
    _e match {
      case mdE: MagicDrawUMLDependency => mdE
    }

  def umlMagicDrawUMLDeployedArtifact(_e: UMLDeployedArtifact[Uml]): MagicDrawUMLDeployedArtifact =
    _e match {
      case mdE: MagicDrawUMLDeployedArtifact => mdE
    }

  def umlMagicDrawUMLDeployment(_e: UMLDeployment[Uml]): MagicDrawUMLDeployment =
    _e match {
      case mdE: MagicDrawUMLDeployment => mdE
    }

  def umlMagicDrawUMLDeploymentSpecification(_e: UMLDeploymentSpecification[Uml]): MagicDrawUMLDeploymentSpecification =
    _e match {
      case mdE: MagicDrawUMLDeploymentSpecification => mdE
    }

  def umlMagicDrawUMLDeploymentTarget(_e: UMLDeploymentTarget[Uml]): MagicDrawUMLDeploymentTarget =
    _e match {
      case mdE: MagicDrawUMLDeploymentTarget => mdE
    }

  def umlMagicDrawUMLDestroyLinkAction(_e: UMLDestroyLinkAction[Uml]): MagicDrawUMLDestroyLinkAction =
    _e match {
      case mdE: MagicDrawUMLDestroyLinkAction => mdE
    }

  def umlMagicDrawUMLDestroyObjectAction(_e: UMLDestroyObjectAction[Uml]): MagicDrawUMLDestroyObjectAction =
    _e match {
      case mdE: MagicDrawUMLDestroyObjectAction => mdE
    }

  def umlMagicDrawUMLDestructionOccurrenceSpecification(_e: UMLDestructionOccurrenceSpecification[Uml]): MagicDrawUMLDestructionOccurrenceSpecification =
    _e match {
      case mdE: MagicDrawUMLDestructionOccurrenceSpecification => mdE
    }

  def umlMagicDrawUMLDevice(_e: UMLDevice[Uml]): MagicDrawUMLDevice =
    _e match {
      case mdE: MagicDrawUMLDevice => mdE
    }

  def umlMagicDrawUMLDirectedRelationship(_e: UMLDirectedRelationship[Uml]): MagicDrawUMLDirectedRelationship =
    _e match {
      case mdE: MagicDrawUMLDirectedRelationship => mdE
    }

  def umlMagicDrawUMLDuration(_e: UMLDuration[Uml]): MagicDrawUMLDuration =
    _e match {
      case mdE: MagicDrawUMLDuration => mdE
    }

  def umlMagicDrawUMLDurationConstraint(_e: UMLDurationConstraint[Uml]): MagicDrawUMLDurationConstraint =
    _e match {
      case mdE: MagicDrawUMLDurationConstraint => mdE
    }

  def umlMagicDrawUMLDurationInterval(_e: UMLDurationInterval[Uml]): MagicDrawUMLDurationInterval =
    _e match {
      case mdE: MagicDrawUMLDurationInterval => mdE
    }

  def umlMagicDrawUMLDurationObservation(_e: UMLDurationObservation[Uml]): MagicDrawUMLDurationObservation =
    _e match {
      case mdE: MagicDrawUMLDurationObservation => mdE
    }

  def umlMagicDrawUMLElement(_e: UMLElement[Uml]): MagicDrawUMLElement =
    _e match {
      case mdE: MagicDrawUMLElement => mdE
    }

  def umlMagicDrawUMLElementImport(_e: UMLElementImport[Uml]): MagicDrawUMLElementImport =
    _e match {
      case mdE: MagicDrawUMLElementImport => mdE
    }

  def umlMagicDrawUMLEncapsulatedClassifier(_e: UMLEncapsulatedClassifier[Uml]): MagicDrawUMLEncapsulatedClassifier =
    _e match {
      case mdE: MagicDrawUMLEncapsulatedClassifier => mdE
    }

  def umlMagicDrawUMLEnumeration(_e: UMLEnumeration[Uml]): MagicDrawUMLEnumeration =
    _e match {
      case mdE: MagicDrawUMLEnumeration => mdE
    }

  def umlMagicDrawUMLEnumerationLiteral(_e: UMLEnumerationLiteral[Uml]): MagicDrawUMLEnumerationLiteral =
    _e match {
      case mdE: MagicDrawUMLEnumerationLiteral => mdE
    }

  def umlMagicDrawUMLEvent(_e: UMLEvent[Uml]): MagicDrawUMLEvent =
    _e match {
      case mdE: MagicDrawUMLEvent => mdE
    }

  def umlMagicDrawUMLExceptionHandler(_e: UMLExceptionHandler[Uml]): MagicDrawUMLExceptionHandler =
    _e match {
      case mdE: MagicDrawUMLExceptionHandler => mdE
    }

  def umlMagicDrawUMLExecutableNode(_e: UMLExecutableNode[Uml]): MagicDrawUMLExecutableNode =
    _e match {
      case mdE: MagicDrawUMLExecutableNode => mdE
    }

  def umlMagicDrawUMLExecutionEnvironment(_e: UMLExecutionEnvironment[Uml]): MagicDrawUMLExecutionEnvironment =
    _e match {
      case mdE: MagicDrawUMLExecutionEnvironment => mdE
    }

  def umlMagicDrawUMLExecutionOccurrenceSpecification(_e: UMLExecutionOccurrenceSpecification[Uml]): MagicDrawUMLExecutionOccurrenceSpecification =
    _e match {
      case mdE: MagicDrawUMLExecutionOccurrenceSpecification => mdE
    }

  def umlMagicDrawUMLExecutionSpecification(_e: UMLExecutionSpecification[Uml]): MagicDrawUMLExecutionSpecification =
    _e match {
      case mdE: MagicDrawUMLExecutionSpecification => mdE
    }

  def umlMagicDrawUMLExpansionNode(_e: UMLExpansionNode[Uml]): MagicDrawUMLExpansionNode =
    _e match {
      case mdE: MagicDrawUMLExpansionNode => mdE
    }

  def umlMagicDrawUMLExpansionRegion(_e: UMLExpansionRegion[Uml]): MagicDrawUMLExpansionRegion =
    _e match {
      case mdE: MagicDrawUMLExpansionRegion => mdE
    }

  def umlMagicDrawUMLExpression(_e: UMLExpression[Uml]): MagicDrawUMLExpression =
    _e match {
      case mdE: MagicDrawUMLExpression => mdE
    }

  def umlMagicDrawUMLExtend(_e: UMLExtend[Uml]): MagicDrawUMLExtend =
    _e match {
      case mdE: MagicDrawUMLExtend => mdE
    }

  def umlMagicDrawUMLExtension(_e: UMLExtension[Uml]): MagicDrawUMLExtension =
    _e match {
      case mdE: MagicDrawUMLExtension => mdE
    }

  def umlMagicDrawUMLExtensionEnd(_e: UMLExtensionEnd[Uml]): MagicDrawUMLExtensionEnd =
    _e match {
      case mdE: MagicDrawUMLExtensionEnd => mdE
    }

  def umlMagicDrawUMLExtensionPoint(_e: UMLExtensionPoint[Uml]): MagicDrawUMLExtensionPoint =
    _e match {
      case mdE: MagicDrawUMLExtensionPoint => mdE
    }

  def umlMagicDrawUMLFeature(_e: UMLFeature[Uml]): MagicDrawUMLFeature =
    _e match {
      case mdE: MagicDrawUMLFeature => mdE
    }

  def umlMagicDrawUMLFinalNode(_e: UMLFinalNode[Uml]): MagicDrawUMLFinalNode =
    _e match {
      case mdE: MagicDrawUMLFinalNode => mdE
    }

  def umlMagicDrawUMLFinalState(_e: UMLFinalState[Uml]): MagicDrawUMLFinalState =
    _e match {
      case mdE: MagicDrawUMLFinalState => mdE
    }

  def umlMagicDrawUMLFlowFinalNode(_e: UMLFlowFinalNode[Uml]): MagicDrawUMLFlowFinalNode =
    _e match {
      case mdE: MagicDrawUMLFlowFinalNode => mdE
    }

  def umlMagicDrawUMLForkNode(_e: UMLForkNode[Uml]): MagicDrawUMLForkNode =
    _e match {
      case mdE: MagicDrawUMLForkNode => mdE
    }

  def umlMagicDrawUMLFunctionBehavior(_e: UMLFunctionBehavior[Uml]): MagicDrawUMLFunctionBehavior =
    _e match {
      case mdE: MagicDrawUMLFunctionBehavior => mdE
    }

  def umlMagicDrawUMLGate(_e: UMLGate[Uml]): MagicDrawUMLGate =
    _e match {
      case mdE: MagicDrawUMLGate => mdE
    }

  def umlMagicDrawUMLGeneralOrdering(_e: UMLGeneralOrdering[Uml]): MagicDrawUMLGeneralOrdering =
    _e match {
      case mdE: MagicDrawUMLGeneralOrdering => mdE
    }

  def umlMagicDrawUMLGeneralization(_e: UMLGeneralization[Uml]): MagicDrawUMLGeneralization =
    _e match {
      case mdE: MagicDrawUMLGeneralization => mdE
    }

  def umlMagicDrawUMLGeneralizationSet(_e: UMLGeneralizationSet[Uml]): MagicDrawUMLGeneralizationSet =
    _e match {
      case mdE: MagicDrawUMLGeneralizationSet => mdE
    }

  def umlMagicDrawUMLImage(_e: UMLImage[Uml]): MagicDrawUMLImage =
    _e match {
      case mdE: MagicDrawUMLImage => mdE
    }

  def umlMagicDrawUMLInclude(_e: UMLInclude[Uml]): MagicDrawUMLInclude =
    _e match {
      case mdE: MagicDrawUMLInclude => mdE
    }

  def umlMagicDrawUMLInformationFlow(_e: UMLInformationFlow[Uml]): MagicDrawUMLInformationFlow =
    _e match {
      case mdE: MagicDrawUMLInformationFlow => mdE
    }

  def umlMagicDrawUMLInformationItem(_e: UMLInformationItem[Uml]): MagicDrawUMLInformationItem =
    _e match {
      case mdE: MagicDrawUMLInformationItem => mdE
    }

  def umlMagicDrawUMLInitialNode(_e: UMLInitialNode[Uml]): MagicDrawUMLInitialNode =
    _e match {
      case mdE: MagicDrawUMLInitialNode => mdE
    }

  def umlMagicDrawUMLInputPin(_e: UMLInputPin[Uml]): MagicDrawUMLInputPin =
    _e match {
      case mdE: MagicDrawUMLInputPin => mdE
    }

  def umlMagicDrawUMLInstanceSpecification(_e: UMLInstanceSpecification[Uml]): MagicDrawUMLInstanceSpecification =
    _e match {
      case mdE: MagicDrawUMLInstanceSpecification => mdE
    }

  def umlMagicDrawUMLInstanceValue(_e: UMLInstanceValue[Uml]): MagicDrawUMLInstanceValue =
    _e match {
      case mdE: MagicDrawUMLInstanceValue => mdE
    }

  def umlMagicDrawUMLInteraction(_e: UMLInteraction[Uml]): MagicDrawUMLInteraction =
    _e match {
      case mdE: MagicDrawUMLInteraction => mdE
    }

  def umlMagicDrawUMLInteractionConstraint(_e: UMLInteractionConstraint[Uml]): MagicDrawUMLInteractionConstraint =
    _e match {
      case mdE: MagicDrawUMLInteractionConstraint => mdE
    }

  def umlMagicDrawUMLInteractionFragment(_e: UMLInteractionFragment[Uml]): MagicDrawUMLInteractionFragment =
    _e match {
      case mdE: MagicDrawUMLInteractionFragment => mdE
    }

  def umlMagicDrawUMLInteractionOperand(_e: UMLInteractionOperand[Uml]): MagicDrawUMLInteractionOperand =
    _e match {
      case mdE: MagicDrawUMLInteractionOperand => mdE
    }

  def umlMagicDrawUMLInteractionUse(_e: UMLInteractionUse[Uml]): MagicDrawUMLInteractionUse =
    _e match {
      case mdE: MagicDrawUMLInteractionUse => mdE
    }

  def umlMagicDrawUMLInterface(_e: UMLInterface[Uml]): MagicDrawUMLInterface =
    _e match {
      case mdE: MagicDrawUMLInterface => mdE
    }

  def umlMagicDrawUMLInterfaceRealization(_e: UMLInterfaceRealization[Uml]): MagicDrawUMLInterfaceRealization =
    _e match {
      case mdE: MagicDrawUMLInterfaceRealization => mdE
    }

  def umlMagicDrawUMLInterruptibleActivityRegion(_e: UMLInterruptibleActivityRegion[Uml]): MagicDrawUMLInterruptibleActivityRegion =
    _e match {
      case mdE: MagicDrawUMLInterruptibleActivityRegion => mdE
    }

  def umlMagicDrawUMLInterval(_e: UMLInterval[Uml]): MagicDrawUMLInterval =
    _e match {
      case mdE: MagicDrawUMLInterval => mdE
    }

  def umlMagicDrawUMLIntervalConstraint(_e: UMLIntervalConstraint[Uml]): MagicDrawUMLIntervalConstraint =
    _e match {
      case mdE: MagicDrawUMLIntervalConstraint => mdE
    }

  def umlMagicDrawUMLInvocationAction(_e: UMLInvocationAction[Uml]): MagicDrawUMLInvocationAction =
    _e match {
      case mdE: MagicDrawUMLInvocationAction => mdE
    }

  def umlMagicDrawUMLJoinNode(_e: UMLJoinNode[Uml]): MagicDrawUMLJoinNode =
    _e match {
      case mdE: MagicDrawUMLJoinNode => mdE
    }

  def umlMagicDrawUMLLifeline(_e: UMLLifeline[Uml]): MagicDrawUMLLifeline =
    _e match {
      case mdE: MagicDrawUMLLifeline => mdE
    }

  def umlMagicDrawUMLLinkAction(_e: UMLLinkAction[Uml]): MagicDrawUMLLinkAction =
    _e match {
      case mdE: MagicDrawUMLLinkAction => mdE
    }

  def umlMagicDrawUMLLinkEndCreationData(_e: UMLLinkEndCreationData[Uml]): MagicDrawUMLLinkEndCreationData =
    _e match {
      case mdE: MagicDrawUMLLinkEndCreationData => mdE
    }

  def umlMagicDrawUMLLinkEndData(_e: UMLLinkEndData[Uml]): MagicDrawUMLLinkEndData =
    _e match {
      case mdE: MagicDrawUMLLinkEndData => mdE
    }

  def umlMagicDrawUMLLinkEndDestructionData(_e: UMLLinkEndDestructionData[Uml]): MagicDrawUMLLinkEndDestructionData =
    _e match {
      case mdE: MagicDrawUMLLinkEndDestructionData => mdE
    }

  def umlMagicDrawUMLLiteralBoolean(_e: UMLLiteralBoolean[Uml]): MagicDrawUMLLiteralBoolean =
    _e match {
      case mdE: MagicDrawUMLLiteralBoolean => mdE
    }

  def umlMagicDrawUMLLiteralInteger(_e: UMLLiteralInteger[Uml]): MagicDrawUMLLiteralInteger =
    _e match {
      case mdE: MagicDrawUMLLiteralInteger => mdE
    }

  def umlMagicDrawUMLLiteralNull(_e: UMLLiteralNull[Uml]): MagicDrawUMLLiteralNull =
    _e match {
      case mdE: MagicDrawUMLLiteralNull => mdE
    }

  def umlMagicDrawUMLLiteralReal(_e: UMLLiteralReal[Uml]): MagicDrawUMLLiteralReal =
    _e match {
      case mdE: MagicDrawUMLLiteralReal => mdE
    }

  def umlMagicDrawUMLLiteralSpecification(_e: UMLLiteralSpecification[Uml]): MagicDrawUMLLiteralSpecification =
    _e match {
      case mdE: MagicDrawUMLLiteralSpecification => mdE
    }

  def umlMagicDrawUMLLiteralString(_e: UMLLiteralString[Uml]): MagicDrawUMLLiteralString =
    _e match {
      case mdE: MagicDrawUMLLiteralString => mdE
    }

  def umlMagicDrawUMLLiteralUnlimitedNatural(_e: UMLLiteralUnlimitedNatural[Uml]): MagicDrawUMLLiteralUnlimitedNatural =
    _e match {
      case mdE: MagicDrawUMLLiteralUnlimitedNatural => mdE
    }

  def umlMagicDrawUMLLoopNode(_e: UMLLoopNode[Uml]): MagicDrawUMLLoopNode =
    _e match {
      case mdE: MagicDrawUMLLoopNode => mdE
    }

  def umlMagicDrawUMLManifestation(_e: UMLManifestation[Uml]): MagicDrawUMLManifestation =
    _e match {
      case mdE: MagicDrawUMLManifestation => mdE
    }

  def umlMagicDrawUMLMergeNode(_e: UMLMergeNode[Uml]): MagicDrawUMLMergeNode =
    _e match {
      case mdE: MagicDrawUMLMergeNode => mdE
    }

  def umlMagicDrawUMLMessage(_e: UMLMessage[Uml]): MagicDrawUMLMessage =
    _e match {
      case mdE: MagicDrawUMLMessage => mdE
    }

  def umlMagicDrawUMLMessageEnd(_e: UMLMessageEnd[Uml]): MagicDrawUMLMessageEnd =
    _e match {
      case mdE: MagicDrawUMLMessageEnd => mdE
    }

  def umlMagicDrawUMLMessageEvent(_e: UMLMessageEvent[Uml]): MagicDrawUMLMessageEvent =
    _e match {
      case mdE: MagicDrawUMLMessageEvent => mdE
    }

  def umlMagicDrawUMLMessageOccurrenceSpecification(_e: UMLMessageOccurrenceSpecification[Uml]): MagicDrawUMLMessageOccurrenceSpecification =
    _e match {
      case mdE: MagicDrawUMLMessageOccurrenceSpecification => mdE
    }

  def umlMagicDrawUMLModel(_e: UMLModel[Uml]): MagicDrawUMLModel =
    _e match {
      case mdE: MagicDrawUMLModel => mdE
    }

  def umlMagicDrawUMLMultiplicityElement(_e: UMLMultiplicityElement[Uml]): MagicDrawUMLMultiplicityElement =
    _e match {
      case mdE: MagicDrawUMLMultiplicityElement => mdE
    }

  def umlMagicDrawUMLNamedElement(_e: UMLNamedElement[Uml]): MagicDrawUMLNamedElement =
    _e match {
      case mdE: MagicDrawUMLNamedElement => mdE
    }

  def umlMagicDrawUMLNamespace(_e: UMLNamespace[Uml]): MagicDrawUMLNamespace =
    _e match {
      case mdE: MagicDrawUMLNamespace => mdE
    }

  def umlMagicDrawUMLNode(_e: UMLNode[Uml]): MagicDrawUMLNode =
    _e match {
      case mdE: MagicDrawUMLNode => mdE
    }

  def umlMagicDrawUMLObjectFlow(_e: UMLObjectFlow[Uml]): MagicDrawUMLObjectFlow =
    _e match {
      case mdE: MagicDrawUMLObjectFlow => mdE
    }

  def umlMagicDrawUMLObjectNode(_e: UMLObjectNode[Uml]): MagicDrawUMLObjectNode =
    _e match {
      case mdE: MagicDrawUMLObjectNode => mdE
    }

  def umlMagicDrawUMLObservation(_e: UMLObservation[Uml]): MagicDrawUMLObservation =
    _e match {
      case mdE: MagicDrawUMLObservation => mdE
    }

  def umlMagicDrawUMLOccurrenceSpecification(_e: UMLOccurrenceSpecification[Uml]): MagicDrawUMLOccurrenceSpecification =
    _e match {
      case mdE: MagicDrawUMLOccurrenceSpecification => mdE
    }

  def umlMagicDrawUMLOpaqueAction(_e: UMLOpaqueAction[Uml]): MagicDrawUMLOpaqueAction =
    _e match {
      case mdE: MagicDrawUMLOpaqueAction => mdE
    }

  def umlMagicDrawUMLOpaqueBehavior(_e: UMLOpaqueBehavior[Uml]): MagicDrawUMLOpaqueBehavior =
    _e match {
      case mdE: MagicDrawUMLOpaqueBehavior => mdE
    }

  def umlMagicDrawUMLOpaqueExpression(_e: UMLOpaqueExpression[Uml]): MagicDrawUMLOpaqueExpression =
    _e match {
      case mdE: MagicDrawUMLOpaqueExpression => mdE
    }

  def umlMagicDrawUMLOperation(_e: UMLOperation[Uml]): MagicDrawUMLOperation =
    _e match {
      case mdE: MagicDrawUMLOperation => mdE
    }

  def umlMagicDrawUMLOperationTemplateParameter(_e: UMLOperationTemplateParameter[Uml]): MagicDrawUMLOperationTemplateParameter =
    _e match {
      case mdE: MagicDrawUMLOperationTemplateParameter => mdE
    }

  def umlMagicDrawUMLOutputPin(_e: UMLOutputPin[Uml]): MagicDrawUMLOutputPin =
    _e match {
      case mdE: MagicDrawUMLOutputPin => mdE
    }

  def umlMagicDrawUMLPackage(_e: UMLPackage[Uml]): MagicDrawUMLPackage =
    _e match {
      case mdE: MagicDrawUMLPackage => mdE
    }

  def umlMagicDrawUMLPackageImport(_e: UMLPackageImport[Uml]): MagicDrawUMLPackageImport =
    _e match {
      case mdE: MagicDrawUMLPackageImport => mdE
    }

  def umlMagicDrawUMLPackageMerge(_e: UMLPackageMerge[Uml]): MagicDrawUMLPackageMerge =
    _e match {
      case mdE: MagicDrawUMLPackageMerge => mdE
    }

  def umlMagicDrawUMLPackageableElement(_e: UMLPackageableElement[Uml]): MagicDrawUMLPackageableElement =
    _e match {
      case mdE: MagicDrawUMLPackageableElement => mdE
    }

  def umlMagicDrawUMLParameter(_e: UMLParameter[Uml]): MagicDrawUMLParameter =
    _e match {
      case mdE: MagicDrawUMLParameter => mdE
    }

  def umlMagicDrawUMLParameterSet(_e: UMLParameterSet[Uml]): MagicDrawUMLParameterSet =
    _e match {
      case mdE: MagicDrawUMLParameterSet => mdE
    }

  def umlMagicDrawUMLParameterableElement(_e: UMLParameterableElement[Uml]): MagicDrawUMLParameterableElement =
    _e match {
      case mdE: MagicDrawUMLParameterableElement => mdE
    }

  def umlMagicDrawUMLPartDecomposition(_e: UMLPartDecomposition[Uml]): MagicDrawUMLPartDecomposition =
    _e match {
      case mdE: MagicDrawUMLPartDecomposition => mdE
    }

  def umlMagicDrawUMLPin(_e: UMLPin[Uml]): MagicDrawUMLPin =
    _e match {
      case mdE: MagicDrawUMLPin => mdE
    }

  def umlMagicDrawUMLPort(_e: UMLPort[Uml]): MagicDrawUMLPort =
    _e match {
      case mdE: MagicDrawUMLPort => mdE
    }

  def umlMagicDrawUMLPrimitiveType(_e: UMLPrimitiveType[Uml]): MagicDrawUMLPrimitiveType =
    _e match {
      case mdE: MagicDrawUMLPrimitiveType => mdE
    }

  def umlMagicDrawUMLProfile(_e: UMLProfile[Uml]): MagicDrawUMLProfile =
    _e match {
      case mdE: MagicDrawUMLProfile => mdE
    }

  def umlMagicDrawUMLProfileApplication(_e: UMLProfileApplication[Uml]): MagicDrawUMLProfileApplication =
    _e match {
      case mdE: MagicDrawUMLProfileApplication => mdE
    }

  def umlMagicDrawUMLProperty(_e: UMLProperty[Uml]): MagicDrawUMLProperty =
    _e match {
      case mdE: MagicDrawUMLProperty => mdE
    }

  def umlMagicDrawUMLProtocolConformance(_e: UMLProtocolConformance[Uml]): MagicDrawUMLProtocolConformance =
    _e match {
      case mdE: MagicDrawUMLProtocolConformance => mdE
    }

  def umlMagicDrawUMLProtocolStateMachine(_e: UMLProtocolStateMachine[Uml]): MagicDrawUMLProtocolStateMachine =
    _e match {
      case mdE: MagicDrawUMLProtocolStateMachine => mdE
    }

  def umlMagicDrawUMLProtocolTransition(_e: UMLProtocolTransition[Uml]): MagicDrawUMLProtocolTransition =
    _e match {
      case mdE: MagicDrawUMLProtocolTransition => mdE
    }

  def umlMagicDrawUMLPseudostate(_e: UMLPseudostate[Uml]): MagicDrawUMLPseudostate =
    _e match {
      case mdE: MagicDrawUMLPseudostate => mdE
    }

  def umlMagicDrawUMLQualifierValue(_e: UMLQualifierValue[Uml]): MagicDrawUMLQualifierValue =
    _e match {
      case mdE: MagicDrawUMLQualifierValue => mdE
    }

  def umlMagicDrawUMLRaiseExceptionAction(_e: UMLRaiseExceptionAction[Uml]): MagicDrawUMLRaiseExceptionAction =
    _e match {
      case mdE: MagicDrawUMLRaiseExceptionAction => mdE
    }

  def umlMagicDrawUMLReadExtentAction(_e: UMLReadExtentAction[Uml]): MagicDrawUMLReadExtentAction =
    _e match {
      case mdE: MagicDrawUMLReadExtentAction => mdE
    }

  def umlMagicDrawUMLReadIsClassifiedObjectAction(_e: UMLReadIsClassifiedObjectAction[Uml]): MagicDrawUMLReadIsClassifiedObjectAction =
    _e match {
      case mdE: MagicDrawUMLReadIsClassifiedObjectAction => mdE
    }

  def umlMagicDrawUMLReadLinkAction(_e: UMLReadLinkAction[Uml]): MagicDrawUMLReadLinkAction =
    _e match {
      case mdE: MagicDrawUMLReadLinkAction => mdE
    }

  def umlMagicDrawUMLReadLinkObjectEndAction(_e: UMLReadLinkObjectEndAction[Uml]): MagicDrawUMLReadLinkObjectEndAction =
    _e match {
      case mdE: MagicDrawUMLReadLinkObjectEndAction => mdE
    }

  def umlMagicDrawUMLReadLinkObjectEndQualifierAction(_e: UMLReadLinkObjectEndQualifierAction[Uml]): MagicDrawUMLReadLinkObjectEndQualifierAction =
    _e match {
      case mdE: MagicDrawUMLReadLinkObjectEndQualifierAction => mdE
    }

  def umlMagicDrawUMLReadSelfAction(_e: UMLReadSelfAction[Uml]): MagicDrawUMLReadSelfAction =
    _e match {
      case mdE: MagicDrawUMLReadSelfAction => mdE
    }

  def umlMagicDrawUMLReadStructuralFeatureAction(_e: UMLReadStructuralFeatureAction[Uml]): MagicDrawUMLReadStructuralFeatureAction =
    _e match {
      case mdE: MagicDrawUMLReadStructuralFeatureAction => mdE
    }

  def umlMagicDrawUMLReadVariableAction(_e: UMLReadVariableAction[Uml]): MagicDrawUMLReadVariableAction =
    _e match {
      case mdE: MagicDrawUMLReadVariableAction => mdE
    }

  def umlMagicDrawUMLRealization(_e: UMLRealization[Uml]): MagicDrawUMLRealization =
    _e match {
      case mdE: MagicDrawUMLRealization => mdE
    }

  def umlMagicDrawUMLReception(_e: UMLReception[Uml]): MagicDrawUMLReception =
    _e match {
      case mdE: MagicDrawUMLReception => mdE
    }

  def umlMagicDrawUMLReclassifyObjectAction(_e: UMLReclassifyObjectAction[Uml]): MagicDrawUMLReclassifyObjectAction =
    _e match {
      case mdE: MagicDrawUMLReclassifyObjectAction => mdE
    }

  def umlMagicDrawUMLRedefinableElement(_e: UMLRedefinableElement[Uml]): MagicDrawUMLRedefinableElement =
    _e match {
      case mdE: MagicDrawUMLRedefinableElement => mdE
    }

  def umlMagicDrawUMLRedefinableTemplateSignature(_e: UMLRedefinableTemplateSignature[Uml]): MagicDrawUMLRedefinableTemplateSignature =
    _e match {
      case mdE: MagicDrawUMLRedefinableTemplateSignature => mdE
    }

  def umlMagicDrawUMLReduceAction(_e: UMLReduceAction[Uml]): MagicDrawUMLReduceAction =
    _e match {
      case mdE: MagicDrawUMLReduceAction => mdE
    }

  def umlMagicDrawUMLRegion(_e: UMLRegion[Uml]): MagicDrawUMLRegion =
    _e match {
      case mdE: MagicDrawUMLRegion => mdE
    }

  def umlMagicDrawUMLRelationship(_e: UMLRelationship[Uml]): MagicDrawUMLRelationship =
    _e match {
      case mdE: MagicDrawUMLRelationship => mdE
    }

  def umlMagicDrawUMLRemoveStructuralFeatureValueAction(_e: UMLRemoveStructuralFeatureValueAction[Uml]): MagicDrawUMLRemoveStructuralFeatureValueAction =
    _e match {
      case mdE: MagicDrawUMLRemoveStructuralFeatureValueAction => mdE
    }

  def umlMagicDrawUMLRemoveVariableValueAction(_e: UMLRemoveVariableValueAction[Uml]): MagicDrawUMLRemoveVariableValueAction =
    _e match {
      case mdE: MagicDrawUMLRemoveVariableValueAction => mdE
    }

  def umlMagicDrawUMLReplyAction(_e: UMLReplyAction[Uml]): MagicDrawUMLReplyAction =
    _e match {
      case mdE: MagicDrawUMLReplyAction => mdE
    }

  def umlMagicDrawUMLSendObjectAction(_e: UMLSendObjectAction[Uml]): MagicDrawUMLSendObjectAction =
    _e match {
      case mdE: MagicDrawUMLSendObjectAction => mdE
    }

  def umlMagicDrawUMLSendSignalAction(_e: UMLSendSignalAction[Uml]): MagicDrawUMLSendSignalAction =
    _e match {
      case mdE: MagicDrawUMLSendSignalAction => mdE
    }

  def umlMagicDrawUMLSequenceNode(_e: UMLSequenceNode[Uml]): MagicDrawUMLSequenceNode =
    _e match {
      case mdE: MagicDrawUMLSequenceNode => mdE
    }

  def umlMagicDrawUMLSignal(_e: UMLSignal[Uml]): MagicDrawUMLSignal =
    _e match {
      case mdE: MagicDrawUMLSignal => mdE
    }

  def umlMagicDrawUMLSignalEvent(_e: UMLSignalEvent[Uml]): MagicDrawUMLSignalEvent =
    _e match {
      case mdE: MagicDrawUMLSignalEvent => mdE
    }

  def umlMagicDrawUMLSlot(_e: UMLSlot[Uml]): MagicDrawUMLSlot =
    _e match {
      case mdE: MagicDrawUMLSlot => mdE
    }

  def umlMagicDrawUMLStartClassifierBehaviorAction(_e: UMLStartClassifierBehaviorAction[Uml]): MagicDrawUMLStartClassifierBehaviorAction =
    _e match {
      case mdE: MagicDrawUMLStartClassifierBehaviorAction => mdE
    }

  def umlMagicDrawUMLStartObjectBehaviorAction(_e: UMLStartObjectBehaviorAction[Uml]): MagicDrawUMLStartObjectBehaviorAction =
    _e match {
      case mdE: MagicDrawUMLStartObjectBehaviorAction => mdE
    }

  def umlMagicDrawUMLState(_e: UMLState[Uml]): MagicDrawUMLState =
    _e match {
      case mdE: MagicDrawUMLState => mdE
    }

  def umlMagicDrawUMLStateInvariant(_e: UMLStateInvariant[Uml]): MagicDrawUMLStateInvariant =
    _e match {
      case mdE: MagicDrawUMLStateInvariant => mdE
    }

  def umlMagicDrawUMLStateMachine(_e: UMLStateMachine[Uml]): MagicDrawUMLStateMachine =
    _e match {
      case mdE: MagicDrawUMLStateMachine => mdE
    }

  def umlMagicDrawUMLStereotype(_e: UMLStereotype[Uml]): MagicDrawUMLStereotype =
    _e match {
      case mdE: MagicDrawUMLStereotype => mdE
    }

  def umlMagicDrawUMLStringExpression(_e: UMLStringExpression[Uml]): MagicDrawUMLStringExpression =
    _e match {
      case mdE: MagicDrawUMLStringExpression => mdE
    }

  def umlMagicDrawUMLStructuralFeature(_e: UMLStructuralFeature[Uml]): MagicDrawUMLStructuralFeature =
    _e match {
      case mdE: MagicDrawUMLStructuralFeature => mdE
    }

  def umlMagicDrawUMLStructuralFeatureAction(_e: UMLStructuralFeatureAction[Uml]): MagicDrawUMLStructuralFeatureAction =
    _e match {
      case mdE: MagicDrawUMLStructuralFeatureAction => mdE
    }

  def umlMagicDrawUMLStructuredActivityNode(_e: UMLStructuredActivityNode[Uml]): MagicDrawUMLStructuredActivityNode =
    _e match {
      case mdE: MagicDrawUMLStructuredActivityNode => mdE
    }

  def umlMagicDrawUMLStructuredClassifier(_e: UMLStructuredClassifier[Uml]): MagicDrawUMLStructuredClassifier =
    _e match {
      case mdE: MagicDrawUMLStructuredClassifier => mdE
    }

  def umlMagicDrawUMLSubstitution(_e: UMLSubstitution[Uml]): MagicDrawUMLSubstitution =
    _e match {
      case mdE: MagicDrawUMLSubstitution => mdE
    }

  def umlMagicDrawUMLTemplateBinding(_e: UMLTemplateBinding[Uml]): MagicDrawUMLTemplateBinding =
    _e match {
      case mdE: MagicDrawUMLTemplateBinding => mdE
    }

  def umlMagicDrawUMLTemplateParameter(_e: UMLTemplateParameter[Uml]): MagicDrawUMLTemplateParameter =
    _e match {
      case mdE: MagicDrawUMLTemplateParameter => mdE
    }

  def umlMagicDrawUMLTemplateParameterSubstitution(_e: UMLTemplateParameterSubstitution[Uml]): MagicDrawUMLTemplateParameterSubstitution =
    _e match {
      case mdE: MagicDrawUMLTemplateParameterSubstitution => mdE
    }

  def umlMagicDrawUMLTemplateSignature(_e: UMLTemplateSignature[Uml]): MagicDrawUMLTemplateSignature =
    _e match {
      case mdE: MagicDrawUMLTemplateSignature => mdE
    }

  def umlMagicDrawUMLTemplateableElement(_e: UMLTemplateableElement[Uml]): MagicDrawUMLTemplateableElement =
    _e match {
      case mdE: MagicDrawUMLTemplateableElement => mdE
    }

  def umlMagicDrawUMLTestIdentityAction(_e: UMLTestIdentityAction[Uml]): MagicDrawUMLTestIdentityAction =
    _e match {
      case mdE: MagicDrawUMLTestIdentityAction => mdE
    }

  def umlMagicDrawUMLTimeConstraint(_e: UMLTimeConstraint[Uml]): MagicDrawUMLTimeConstraint =
    _e match {
      case mdE: MagicDrawUMLTimeConstraint => mdE
    }

  def umlMagicDrawUMLTimeEvent(_e: UMLTimeEvent[Uml]): MagicDrawUMLTimeEvent =
    _e match {
      case mdE: MagicDrawUMLTimeEvent => mdE
    }

  def umlMagicDrawUMLTimeExpression(_e: UMLTimeExpression[Uml]): MagicDrawUMLTimeExpression =
    _e match {
      case mdE: MagicDrawUMLTimeExpression => mdE
    }

  def umlMagicDrawUMLTimeInterval(_e: UMLTimeInterval[Uml]): MagicDrawUMLTimeInterval =
    _e match {
      case mdE: MagicDrawUMLTimeInterval => mdE
    }

  def umlMagicDrawUMLTimeObservation(_e: UMLTimeObservation[Uml]): MagicDrawUMLTimeObservation =
    _e match {
      case mdE: MagicDrawUMLTimeObservation => mdE
    }

  def umlMagicDrawUMLTransition(_e: UMLTransition[Uml]): MagicDrawUMLTransition =
    _e match {
      case mdE: MagicDrawUMLTransition => mdE
    }

  def umlMagicDrawUMLTrigger(_e: UMLTrigger[Uml]): MagicDrawUMLTrigger =
    _e match {
      case mdE: MagicDrawUMLTrigger => mdE
    }

  def umlMagicDrawUMLType(_e: UMLType[Uml]): MagicDrawUMLType =
    _e match {
      case mdE: MagicDrawUMLType => mdE
    }

  def umlMagicDrawUMLTypedElement(_e: UMLTypedElement[Uml]): MagicDrawUMLTypedElement =
    _e match {
      case mdE: MagicDrawUMLTypedElement => mdE
    }

  def umlMagicDrawUMLUnmarshallAction(_e: UMLUnmarshallAction[Uml]): MagicDrawUMLUnmarshallAction =
    _e match {
      case mdE: MagicDrawUMLUnmarshallAction => mdE
    }

  def umlMagicDrawUMLUsage(_e: UMLUsage[Uml]): MagicDrawUMLUsage =
    _e match {
      case mdE: MagicDrawUMLUsage => mdE
    }

  def umlMagicDrawUMLUseCase(_e: UMLUseCase[Uml]): MagicDrawUMLUseCase =
    _e match {
      case mdE: MagicDrawUMLUseCase => mdE
    }

  def umlMagicDrawUMLValuePin(_e: UMLValuePin[Uml]): MagicDrawUMLValuePin =
    _e match {
      case mdE: MagicDrawUMLValuePin => mdE
    }

  def umlMagicDrawUMLValueSpecification(_e: UMLValueSpecification[Uml]): MagicDrawUMLValueSpecification =
    _e match {
      case mdE: MagicDrawUMLValueSpecification => mdE
    }

  def umlMagicDrawUMLValueSpecificationAction(_e: UMLValueSpecificationAction[Uml]): MagicDrawUMLValueSpecificationAction =
    _e match {
      case mdE: MagicDrawUMLValueSpecificationAction => mdE
    }

  def umlMagicDrawUMLVariable(_e: UMLVariable[Uml]): MagicDrawUMLVariable =
    _e match {
      case mdE: MagicDrawUMLVariable => mdE
    }

  def umlMagicDrawUMLVariableAction(_e: UMLVariableAction[Uml]): MagicDrawUMLVariableAction =
    _e match {
      case mdE: MagicDrawUMLVariableAction => mdE
    }

  def umlMagicDrawUMLVertex(_e: UMLVertex[Uml]): MagicDrawUMLVertex =
    _e match {
      case mdE: MagicDrawUMLVertex => mdE
    }

  def umlMagicDrawUMLWriteLinkAction(_e: UMLWriteLinkAction[Uml]): MagicDrawUMLWriteLinkAction =
    _e match {
      case mdE: MagicDrawUMLWriteLinkAction => mdE
    }

  def umlMagicDrawUMLWriteStructuralFeatureAction(_e: UMLWriteStructuralFeatureAction[Uml]): MagicDrawUMLWriteStructuralFeatureAction =
    _e match {
      case mdE: MagicDrawUMLWriteStructuralFeatureAction => mdE
    }

  def umlMagicDrawUMLWriteVariableAction(_e: UMLWriteVariableAction[Uml]): MagicDrawUMLWriteVariableAction =
    _e match {
      case mdE: MagicDrawUMLWriteVariableAction => mdE
    }


  // -------------


  override val UML_PRIMITIVE_TYPES_BOOLEAN: UMLPrimitiveType[Uml] =
    StereotypesHelper.getPrimitiveByName(project, "Boolean")

  override val UML_PRIMITIVE_TYPES_INTEGER: UMLPrimitiveType[Uml] =
    StereotypesHelper.getPrimitiveByName(project, "Integer")

  override val UML_PRIMITIVE_TYPES_UNLIMITED_NATURAL: UMLPrimitiveType[Uml] =
    StereotypesHelper.getPrimitiveByName(project, "UnlimitedNatural")

  override val UML_PRIMITIVE_TYPES_REAL: UMLPrimitiveType[Uml] =
    StereotypesHelper.getPrimitiveByName(project, "Real")

  override val UML_PRIMITIVE_TYPES_STRING: UMLPrimitiveType[Uml] =
    StereotypesHelper.getPrimitiveByName(project, "String")

  // MagicDraw-specific

  implicit val DIAGRAM: TypeTag[MagicDrawUML#Diagram] = typeTag[MagicDrawUML#Diagram]

  implicit val ELEMENT_VALUE: TypeTag[MagicDrawUML#ElementValue] = typeTag[MagicDrawUML#ElementValue]

  // val stdProfile = ModelHelper.getUMLStandardProfile(project)
  lazy val MDBuiltInPrimitiveTypes = {

    val mdPrimitiveTypesPkg =
      umlPackage( project.getElementByID("_12_0EAPbeta_be00301_1157529392394_202602_1").asInstanceOf[MagicDrawUML#Package] )

    val mdPrimitiveTypesExtent: Set[UMLElement[MagicDrawUML]] =
      Set(mdPrimitiveTypesPkg) ++ mdPrimitiveTypesPkg.ownedType.toSet

    MagicDrawBuiltInDocument(
      info=OTISpecificationRootCharacteristics(
        packageURI=OTI_URI("http://www.omg.org/spec/PrimitiveTypes/20100901"),
        documentURL=OTI_URL("http://www.omg.org/spec/PrimitiveTypes/20100901/PrimitiveTypes.xmi"),
        artifactKind=OTIBuiltInProfileArtifactKind(),
        nsPrefix=OTI_NS_PREFIX("PrimitiveTypes"),
        uuidPrefix=OTI_UUID_PREFIX("org.omg.uml.PrimitiveTypes")),
      scope=mdPrimitiveTypesPkg,
      documentURL=
        MagicDrawAttachedLocalModuleBuiltInDocumentLoadURL(
          new URI("http://www.omg.org/spec/UML/20131001/PrimitiveTypes.xmi"),
          "profiles/UML_Standard_Profile.mdzip"),
      builtInExtent=mdPrimitiveTypesExtent )( this )
  }

  lazy val MDBuiltInUML = {

    val mdUMLPkg =
      umlPackage( project.getElementByID("_9_0_be00301_1108053761194_467635_11463").asInstanceOf[MagicDrawUML#Package] )

    val mdUMLExtent: Set[UMLElement[MagicDrawUML]] =
      Set(mdUMLPkg) ++ mdUMLPkg.ownedType.selectByKindOf { case mc: UMLClass[MagicDrawUML] => mc }

    MagicDrawBuiltInDocument(
      info=OTISpecificationRootCharacteristics(
        packageURI=OTI_URI("http://www.omg.org/spec/UML/20131001"),
        documentURL=OTI_URL("http://www.omg.org/spec/UML/20131001/UML.xmi"),
        artifactKind=OTIBuiltInMetamodelArtifactKind(),
        nsPrefix=OTI_NS_PREFIX("uml"),
        uuidPrefix=OTI_UUID_PREFIX("org.omg.uml.UML")),
      scope=mdUMLPkg,
      documentURL=
        MagicDrawAttachedLocalModuleBuiltInDocumentLoadURL(
          new URI("http://www.omg.org/spec/UML/20131001/UML.xmi"),
          "profiles/UML_Standard_Profile.mdzip"),
      builtInExtent=mdUMLExtent )( this )
  }

  lazy val MDBuiltInStandardProfile = {

    val mdStandardProfile =
      umlProfile( project.getElementByID("_9_0_be00301_1108050582343_527400_10847").asInstanceOf[MagicDrawUML#Profile] )

    val mdStandardProfileExtensions = mdStandardProfile.ownedType.selectByKindOf { case e: UMLExtension[MagicDrawUML] => e }
    val mdStandardProfileExtensionFeatures = mdStandardProfileExtensions flatMap (_.ownedEnd)
    val mdStandardProfileStereotypes = mdStandardProfile.ownedType.selectByKindOf { case s: UMLStereotype[MagicDrawUML] => s }
    val mdStandardProfileStereotypeFeatures = mdStandardProfileStereotypes flatMap (_.ownedAttribute)
    val mdStandardProfileExtent: Set[UMLNamedElement[MagicDrawUML]] =
      Set(mdStandardProfile) ++
      mdStandardProfileExtensions ++ mdStandardProfileExtensionFeatures ++
      mdStandardProfileStereotypes ++ mdStandardProfileStereotypeFeatures

    MagicDrawBuiltInDocument(
      info=OTISpecificationRootCharacteristics(
        packageURI=OTI_URI("http://www.omg.org/spec/UML/20131001/StandardProfile"),
        documentURL=OTI_URL("http://www.omg.org/spec/UML/20131001/StandardProfile.xmi"),
        artifactKind=OTIBuiltInProfileArtifactKind(),
        nsPrefix=OTI_NS_PREFIX("StandardProfile"),
        uuidPrefix=OTI_UUID_PREFIX("org.omg.uml.StandardProfile")),
      scope=mdStandardProfile,
      documentURL=
        MagicDrawAttachedLocalModuleBuiltInDocumentLoadURL(
          new URI("http://www.omg.org/spec/UML/20131001/StandardProfile.xmi"),
          "profiles/UML_Standard_Profile.mdzip"),
      builtInExtent=mdStandardProfileExtent.toSet[UMLElement[MagicDrawUML]] )( this )
  }

  lazy val MDBuiltInUML2PrimitiveTypes = DocumentEdge( MDBuiltInUML, MDBuiltInPrimitiveTypes )
  lazy val MDBuiltInStandardProfile2UML = DocumentEdge( MDBuiltInStandardProfile, MDBuiltInUML )

}
