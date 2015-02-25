package org.omg.oti.magicdraw

import scala.collection.JavaConversions._
import com.nomagic.magicdraw.core.Application
import com.nomagic.magicdraw.core.ApplicationEnvironment
import com.nomagic.magicdraw.core.Project
import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileFilter
import scala.language.implicitConversions
import scala.reflect.runtime.universe
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import org.apache.xml.resolver.CatalogManager
import org.omg.oti._
import org.omg.oti.operations._
import com.nomagic.actions.NMAction
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults
import com.nomagic.magicdraw.validation.ValidationRunData
import com.nomagic.magicdraw.annotation.Annotation
import gov.nasa.jpl.dynamicScripts.magicdraw.MagicDrawValidationDataResults.ValidationAnnotationAction
import com.nomagic.task.RunnableWithProgress
import com.nomagic.magicdraw.validation.RuleViolationResult
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import javax.swing.SwingUtilities

case class MagicDrawUMLUtil( val project: Project )
  extends MagicDrawUMLOps { self =>

  type Uml = MagicDrawUML

  import self._

  def chooseCatalogFile( title: String = "Select a *.catalog.xml file" ): Option[File] = {

    var result: Option[File] = None

    SwingUtilities.invokeAndWait( new Runnable {
      def run: Unit = {

        val ff = new FileFilter() {

          def getDescription: String = "*.catalog.xml"

          def accept( f: File ): Boolean =
            f.isDirectory() ||
              ( f.isFile() && f.getName.endsWith( ".catalog.xml" ) )

        }

        val mdInstallDir = new File( ApplicationEnvironment.getInstallRoot )
        val fc = new JFileChooser( mdInstallDir ) {

          override def getFileSelectionMode: Int = JFileChooser.FILES_ONLY

          override def getDialogTitle = title
        }

        fc.setFileFilter( ff )
        fc.setFileHidingEnabled( true )
        fc.setAcceptAllFileFilterUsed( false )

        fc.showOpenDialog( Application.getInstance().getMainFrame ) match {
          case JFileChooser.APPROVE_OPTION =>
            val migrationFile = fc.getSelectedFile
            result = Some( migrationFile )
          case _ =>
            result = None
        }
      }
    } )
    result
  }

  override def cacheLookupOrUpdate( e: Uml#Element ) = e match {
    case null                            => null

    // MagicDraw-specific    
    case _e: Uml#Diagram                 => cache.getOrElseUpdate( _e, new MagicDrawUMLDiagram() { override val e = _e; override val ops = self } )
    case _e: Uml#ElementValue            => cache.getOrElseUpdate( _e, new MagicDrawUMLElementValue() { override val e = _e; override val ops = self } )
    
    case _e: Uml#Comment => cache.getOrElseUpdate( _e, new MagicDrawUMLComment() { override val e = _e; override val ops = self } )
    case _e: Uml#RedefinableTemplateSignature => cache.getOrElseUpdate( _e, new MagicDrawUMLRedefinableTemplateSignature() { override val e = _e; override val ops = self } )
    case _e: Uml#TemplateSignature => cache.getOrElseUpdate( _e, new MagicDrawUMLTemplateSignature() { override val e = _e; override val ops = self } )
    case _e: Uml#ElementImport => cache.getOrElseUpdate( _e, new MagicDrawUMLElementImport() { override val e = _e; override val ops = self } )
    case _e: Uml#PackageImport => cache.getOrElseUpdate( _e, new MagicDrawUMLPackageImport() { override val e = _e; override val ops = self } )
    case _e: Uml#ProfileApplication => cache.getOrElseUpdate( _e, new MagicDrawUMLProfileApplication() { override val e = _e; override val ops = self } )
    case _e: Uml#PackageMerge => cache.getOrElseUpdate( _e, new MagicDrawUMLPackageMerge() { override val e = _e; override val ops = self } )
    case _e: Uml#Model => cache.getOrElseUpdate( _e, new MagicDrawUMLModel() { override val e = _e; override val ops = self } )
    case _e: Uml#Profile => cache.getOrElseUpdate( _e, new MagicDrawUMLProfile() { override val e = _e; override val ops = self } )
    case _e: Uml#Package => cache.getOrElseUpdate( _e, new MagicDrawUMLPackage() { override val e = _e; override val ops = self } )
    case _e: Uml#TimeConstraint => cache.getOrElseUpdate( _e, new MagicDrawUMLTimeConstraint() { override val e = _e; override val ops = self } )
    case _e: Uml#DurationConstraint => cache.getOrElseUpdate( _e, new MagicDrawUMLDurationConstraint() { override val e = _e; override val ops = self } )
    case _e: Uml#IntervalConstraint => cache.getOrElseUpdate( _e, new MagicDrawUMLIntervalConstraint() { override val e = _e; override val ops = self } )
    case _e: Uml#InteractionConstraint => cache.getOrElseUpdate( _e, new MagicDrawUMLInteractionConstraint() { override val e = _e; override val ops = self } )
    case _e: Uml#Constraint => cache.getOrElseUpdate( _e, new MagicDrawUMLConstraint() { override val e = _e; override val ops = self } )
    case _e: Uml#ClassifierTemplateParameter => cache.getOrElseUpdate( _e, new MagicDrawUMLClassifierTemplateParameter() { override val e = _e; override val ops = self } )
    case _e: Uml#ConnectableElementTemplateParameter => cache.getOrElseUpdate( _e, new MagicDrawUMLConnectableElementTemplateParameter() { override val e = _e; override val ops = self } )
    case _e: Uml#OperationTemplateParameter => cache.getOrElseUpdate( _e, new MagicDrawUMLOperationTemplateParameter() { override val e = _e; override val ops = self } )
    case _e: Uml#TemplateParameter => cache.getOrElseUpdate( _e, new MagicDrawUMLTemplateParameter() { override val e = _e; override val ops = self } )
    case _e: Uml#Substitution => cache.getOrElseUpdate( _e, new MagicDrawUMLSubstitution() { override val e = _e; override val ops = self } )
    case _e: Uml#ComponentRealization => cache.getOrElseUpdate( _e, new MagicDrawUMLComponentRealization() { override val e = _e; override val ops = self } )
    case _e: Uml#InterfaceRealization => cache.getOrElseUpdate( _e, new MagicDrawUMLInterfaceRealization() { override val e = _e; override val ops = self } )    
    case _e: Uml#Realization => cache.getOrElseUpdate( _e, new MagicDrawUMLRealization() { override val e = _e; override val ops = self } )    
    case _e: Uml#Manifestation => cache.getOrElseUpdate( _e, new MagicDrawUMLManifestation() { override val e = _e; override val ops = self } )
    case _e: Uml#Abstraction => cache.getOrElseUpdate( _e, new MagicDrawUMLAbstraction() { override val e = _e; override val ops = self } )
    case _e: Uml#Usage => cache.getOrElseUpdate( _e, new MagicDrawUMLUsage() { override val e = _e; override val ops = self } )
    case _e: Uml#Deployment => cache.getOrElseUpdate( _e, new MagicDrawUMLDeployment() { override val e = _e; override val ops = self } )
    case _e: Uml#Dependency => cache.getOrElseUpdate( _e, new MagicDrawUMLDependency() { override val e = _e; override val ops = self } )
    case _e: Uml#AcceptCallAction => cache.getOrElseUpdate( _e, new MagicDrawUMLAcceptCallAction() { override val e = _e; override val ops = self } )
    case _e: Uml#AcceptEventAction => cache.getOrElseUpdate( _e, new MagicDrawUMLAcceptEventAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ValueSpecificationAction => cache.getOrElseUpdate( _e, new MagicDrawUMLValueSpecificationAction() { override val e = _e; override val ops = self } )
    case _e: Uml#AddVariableValueAction => cache.getOrElseUpdate( _e, new MagicDrawUMLAddVariableValueAction() { override val e = _e; override val ops = self } )
    case _e: Uml#RemoveVariableValueAction => cache.getOrElseUpdate( _e, new MagicDrawUMLRemoveVariableValueAction() { override val e = _e; override val ops = self } )
    case _e: Uml#WriteVariableAction => cache.getOrElseUpdate( _e, new MagicDrawUMLWriteVariableAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ClearVariableAction => cache.getOrElseUpdate( _e, new MagicDrawUMLClearVariableAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ReadVariableAction => cache.getOrElseUpdate( _e, new MagicDrawUMLReadVariableAction() { override val e = _e; override val ops = self } )
    case _e: Uml#VariableAction => cache.getOrElseUpdate( _e, new MagicDrawUMLVariableAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ClearAssociationAction => cache.getOrElseUpdate( _e, new MagicDrawUMLClearAssociationAction() { override val e = _e; override val ops = self } )
    case _e: Uml#CreateObjectAction => cache.getOrElseUpdate( _e, new MagicDrawUMLCreateObjectAction() { override val e = _e; override val ops = self } )
    case _e: Uml#DestroyObjectAction => cache.getOrElseUpdate( _e, new MagicDrawUMLDestroyObjectAction() { override val e = _e; override val ops = self } )
    case _e: Uml#BroadcastSignalAction => cache.getOrElseUpdate( _e, new MagicDrawUMLBroadcastSignalAction() { override val e = _e; override val ops = self } )
    case _e: Uml#CallBehaviorAction => cache.getOrElseUpdate( _e, new MagicDrawUMLCallBehaviorAction() { override val e = _e; override val ops = self } )
    case _e: Uml#CallOperationAction => cache.getOrElseUpdate( _e, new MagicDrawUMLCallOperationAction() { override val e = _e; override val ops = self } )
    case _e: Uml#StartObjectBehaviorAction => cache.getOrElseUpdate( _e, new MagicDrawUMLStartObjectBehaviorAction() { override val e = _e; override val ops = self } )
    case _e: Uml#CallAction => cache.getOrElseUpdate( _e, new MagicDrawUMLCallAction() { override val e = _e; override val ops = self } )
    case _e: Uml#SendObjectAction => cache.getOrElseUpdate( _e, new MagicDrawUMLSendObjectAction() { override val e = _e; override val ops = self } )
    case _e: Uml#SendSignalAction => cache.getOrElseUpdate( _e, new MagicDrawUMLSendSignalAction() { override val e = _e; override val ops = self } )
    case _e: Uml#InvocationAction => cache.getOrElseUpdate( _e, new MagicDrawUMLInvocationAction() { override val e = _e; override val ops = self } )
    case _e: Uml#CreateLinkObjectAction => cache.getOrElseUpdate( _e, new MagicDrawUMLCreateLinkObjectAction() { override val e = _e; override val ops = self } )
    case _e: Uml#CreateLinkAction => cache.getOrElseUpdate( _e, new MagicDrawUMLCreateLinkAction() { override val e = _e; override val ops = self } )
    case _e: Uml#DestroyLinkAction => cache.getOrElseUpdate( _e, new MagicDrawUMLDestroyLinkAction() { override val e = _e; override val ops = self } )
    case _e: Uml#WriteLinkAction => cache.getOrElseUpdate( _e, new MagicDrawUMLWriteLinkAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ReadLinkAction => cache.getOrElseUpdate( _e, new MagicDrawUMLReadLinkAction() { override val e = _e; override val ops = self } )
    case _e: Uml#OpaqueAction => cache.getOrElseUpdate( _e, new MagicDrawUMLOpaqueAction() { override val e = _e; override val ops = self } )
    case _e: Uml#RaiseExceptionAction => cache.getOrElseUpdate( _e, new MagicDrawUMLRaiseExceptionAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ReadExtentAction => cache.getOrElseUpdate( _e, new MagicDrawUMLReadExtentAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ReadIsClassifiedObjectAction => cache.getOrElseUpdate( _e, new MagicDrawUMLReadIsClassifiedObjectAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ReadLinkObjectEndAction => cache.getOrElseUpdate( _e, new MagicDrawUMLReadLinkObjectEndAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ReadLinkObjectEndQualifierAction => cache.getOrElseUpdate( _e, new MagicDrawUMLReadLinkObjectEndQualifierAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ReadSelfAction => cache.getOrElseUpdate( _e, new MagicDrawUMLReadSelfAction() { override val e = _e; override val ops = self } )
    case _e: Uml#AddStructuralFeatureValueAction => cache.getOrElseUpdate( _e, new MagicDrawUMLAddStructuralFeatureValueAction() { override val e = _e; override val ops = self } )
    case _e: Uml#RemoveStructuralFeatureValueAction => cache.getOrElseUpdate( _e, new MagicDrawUMLRemoveStructuralFeatureValueAction() { override val e = _e; override val ops = self } )    
    case _e: Uml#WriteStructuralFeatureAction => cache.getOrElseUpdate( _e, new MagicDrawUMLWriteStructuralFeatureAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ReadStructuralFeatureAction => cache.getOrElseUpdate( _e, new MagicDrawUMLReadStructuralFeatureAction() { override val e = _e; override val ops = self } )    
    case _e: Uml#ClearStructuralFeatureAction => cache.getOrElseUpdate( _e, new MagicDrawUMLClearStructuralFeatureAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ActionExecutionSpecification => cache.getOrElseUpdate( _e, new MagicDrawUMLActionExecutionSpecification() { override val e = _e; override val ops = self } )
    case _e: Uml#StructuralFeatureAction => cache.getOrElseUpdate( _e, new MagicDrawUMLStructuralFeatureAction() { override val e = _e; override val ops = self } )    
    case _e: Uml#StartClassifierBehaviorAction => cache.getOrElseUpdate( _e, new MagicDrawUMLStartClassifierBehaviorAction() { override val e = _e; override val ops = self } )
    case _e: Uml#TestIdentityAction => cache.getOrElseUpdate( _e, new MagicDrawUMLTestIdentityAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ReplyAction => cache.getOrElseUpdate( _e, new MagicDrawUMLReplyAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ReduceAction => cache.getOrElseUpdate( _e, new MagicDrawUMLReduceAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ReclassifyObjectAction => cache.getOrElseUpdate( _e, new MagicDrawUMLReclassifyObjectAction() { override val e = _e; override val ops = self } )
    case _e: Uml#LinkAction => cache.getOrElseUpdate( _e, new MagicDrawUMLLinkAction() { override val e = _e; override val ops = self } )
    case _e: Uml#UnmarshallAction => cache.getOrElseUpdate( _e, new MagicDrawUMLUnmarshallAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ConditionalNode => cache.getOrElseUpdate( _e, new MagicDrawUMLConditionalNode() { override val e = _e; override val ops = self } )
    case _e: Uml#ExpansionRegion => cache.getOrElseUpdate( _e, new MagicDrawUMLExpansionRegion() { override val e = _e; override val ops = self } )
    case _e: Uml#LoopNode => cache.getOrElseUpdate( _e, new MagicDrawUMLLoopNode() { override val e = _e; override val ops = self } )
    case _e: Uml#SequenceNode => cache.getOrElseUpdate( _e, new MagicDrawUMLSequenceNode() { override val e = _e; override val ops = self } )
    case _e: Uml#StructuredActivityNode => cache.getOrElseUpdate( _e, new MagicDrawUMLStructuredActivityNode() { override val e = _e; override val ops = self } )
    case _e: Uml#Action => cache.getOrElseUpdate( _e, new MagicDrawUMLAction() { override val e = _e; override val ops = self } )
    case _e: Uml#ExecutableNode => cache.getOrElseUpdate( _e, new MagicDrawUMLExecutableNode() { override val e = _e; override val ops = self } )
    case _e: Uml#ActionInputPin => cache.getOrElseUpdate( _e, new MagicDrawUMLActionInputPin() { override val e = _e; override val ops = self } )
    case _e: Uml#ValuePin => cache.getOrElseUpdate( _e, new MagicDrawUMLValuePin() { override val e = _e; override val ops = self } )
    case _e: Uml#InputPin => cache.getOrElseUpdate( _e, new MagicDrawUMLInputPin() { override val e = _e; override val ops = self } )
    case _e: Uml#OutputPin => cache.getOrElseUpdate( _e, new MagicDrawUMLOutputPin() { override val e = _e; override val ops = self } )
    case _e: Uml#Pin => cache.getOrElseUpdate( _e, new MagicDrawUMLPin() { override val e = _e; override val ops = self } )
    case _e: Uml#FinalState => cache.getOrElseUpdate( _e, new MagicDrawUMLFinalState() { override val e = _e; override val ops = self } )
    case _e: Uml#State => cache.getOrElseUpdate( _e, new MagicDrawUMLState() { override val e = _e; override val ops = self } )
    case _e: Uml#Pseudostate => cache.getOrElseUpdate( _e, new MagicDrawUMLPseudostate() { override val e = _e; override val ops = self } )
    case _e: Uml#ConnectionPointReference => cache.getOrElseUpdate( _e, new MagicDrawUMLConnectionPointReference() { override val e = _e; override val ops = self } )
    case _e: Uml#ProtocolTransition => cache.getOrElseUpdate( _e, new MagicDrawUMLProtocolTransition() { override val e = _e; override val ops = self } )
    case _e: Uml#Transition => cache.getOrElseUpdate( _e, new MagicDrawUMLTransition() { override val e = _e; override val ops = self } )
    case _e: Uml#ExtensionPoint => cache.getOrElseUpdate( _e, new MagicDrawUMLExtensionPoint() { override val e = _e; override val ops = self } )
    case _e: Uml#Region => cache.getOrElseUpdate( _e, new MagicDrawUMLRegion() { override val e = _e; override val ops = self } )
    case _e: Uml#ControlFlow => cache.getOrElseUpdate( _e, new MagicDrawUMLControlFlow() { override val e = _e; override val ops = self } )
    case _e: Uml#ObjectFlow => cache.getOrElseUpdate( _e, new MagicDrawUMLObjectFlow() { override val e = _e; override val ops = self } )
    case _e: Uml#ActivityEdge => cache.getOrElseUpdate( _e, new MagicDrawUMLActivityEdge() { override val e = _e; override val ops = self } )
    case _e: Uml#ActivityParameterNode => cache.getOrElseUpdate( _e, new MagicDrawUMLActivityParameterNode() { override val e = _e; override val ops = self } )
    case _e: Uml#ExpansionNode => cache.getOrElseUpdate( _e, new MagicDrawUMLExpansionNode() { override val e = _e; override val ops = self } )
    case _e: Uml#DataStoreNode => cache.getOrElseUpdate( _e, new MagicDrawUMLDataStoreNode() { override val e = _e; override val ops = self } )
    case _e: Uml#CentralBufferNode => cache.getOrElseUpdate( _e, new MagicDrawUMLCentralBufferNode() { override val e = _e; override val ops = self } )
    case _e: Uml#MergeNode => cache.getOrElseUpdate( _e, new MagicDrawUMLMergeNode() { override val e = _e; override val ops = self } )
    case _e: Uml#JoinNode => cache.getOrElseUpdate( _e, new MagicDrawUMLJoinNode() { override val e = _e; override val ops = self } )
    case _e: Uml#InitialNode => cache.getOrElseUpdate( _e, new MagicDrawUMLInitialNode() { override val e = _e; override val ops = self } )    
    case _e: Uml#ForkNode => cache.getOrElseUpdate( _e, new MagicDrawUMLForkNode() { override val e = _e; override val ops = self } )
    case _e: Uml#ActivityFinalNode => cache.getOrElseUpdate( _e, new MagicDrawUMLActivityFinalNode() { override val e = _e; override val ops = self } )
    case _e: Uml#FlowFinalNode => cache.getOrElseUpdate( _e, new MagicDrawUMLFlowFinalNode() { override val e = _e; override val ops = self } )    
    case _e: Uml#FinalNode => cache.getOrElseUpdate( _e, new MagicDrawUMLFinalNode() { override val e = _e; override val ops = self } )
    case _e: Uml#DecisionNode => cache.getOrElseUpdate( _e, new MagicDrawUMLDecisionNode() { override val e = _e; override val ops = self } )
    case _e: Uml#ControlNode => cache.getOrElseUpdate( _e, new MagicDrawUMLControlNode() { override val e = _e; override val ops = self } )
    case _e: Uml#ObjectNode => cache.getOrElseUpdate( _e, new MagicDrawUMLObjectNode() { override val e = _e; override val ops = self } )
    case _e: Uml#ActivityNode => cache.getOrElseUpdate( _e, new MagicDrawUMLActivityNode() { override val e = _e; override val ops = self } )
    case _e: Uml#AnyReceiveEvent => cache.getOrElseUpdate( _e, new MagicDrawUMLAnyReceiveEvent() { override val e = _e; override val ops = self } )
    case _e: Uml#CallEvent => cache.getOrElseUpdate( _e, new MagicDrawUMLCallEvent() { override val e = _e; override val ops = self } )
    case _e: Uml#SignalEvent => cache.getOrElseUpdate( _e, new MagicDrawUMLSignalEvent() { override val e = _e; override val ops = self } )
    case _e: Uml#MessageEvent => cache.getOrElseUpdate( _e, new MagicDrawUMLMessageEvent() { override val e = _e; override val ops = self } )
    case _e: Uml#TimeEvent => cache.getOrElseUpdate( _e, new MagicDrawUMLTimeEvent() { override val e = _e; override val ops = self } )  
    case _e: Uml#ChangeEvent => cache.getOrElseUpdate( _e, new MagicDrawUMLChangeEvent() { override val e = _e; override val ops = self } )
    case _e: Uml#Event => cache.getOrElseUpdate( _e, new MagicDrawUMLEvent() { override val e = _e; override val ops = self } )
    case _e: Uml#Interaction => cache.getOrElseUpdate( _e, new MagicDrawUMLInteraction() { override val e = _e; override val ops = self } )
    case _e: Uml#ExecutionOccurrenceSpecification => cache.getOrElseUpdate( _e, new MagicDrawUMLExecutionOccurrenceSpecification() { override val e = _e; override val ops = self } )
    case _e: Uml#DestructionOccurrenceSpecification => cache.getOrElseUpdate( _e, new MagicDrawUMLDestructionOccurrenceSpecification() { override val e = _e; override val ops = self } )
    case _e: Uml#MessageOccurrenceSpecification => cache.getOrElseUpdate( _e, new MagicDrawUMLMessageOccurrenceSpecification() { override val e = _e; override val ops = self } )
    case _e: Uml#OccurrenceSpecification => cache.getOrElseUpdate( _e, new MagicDrawUMLOccurrenceSpecification() { override val e = _e; override val ops = self } )
    case _e: Uml#Gate => cache.getOrElseUpdate( _e, new MagicDrawUMLGate() { override val e = _e; override val ops = self } )
    case _e: Uml#MessageEnd => cache.getOrElseUpdate( _e, new MagicDrawUMLMessageEnd() { override val e = _e; override val ops = self } )
    case _e: Uml#BehaviorExecutionSpecification => cache.getOrElseUpdate( _e, new MagicDrawUMLBehaviorExecutionSpecification() { override val e = _e; override val ops = self } )
    case _e: Uml#ExecutionSpecification => cache.getOrElseUpdate( _e, new MagicDrawUMLExecutionSpecification() { override val e = _e; override val ops = self } )
    case _e: Uml#StateInvariant => cache.getOrElseUpdate( _e, new MagicDrawUMLStateInvariant() { override val e = _e; override val ops = self } )
    case _e: Uml#ConsiderIgnoreFragment => cache.getOrElseUpdate( _e, new MagicDrawUMLConsiderIgnoreFragment() { override val e = _e; override val ops = self } )
    case _e: Uml#CombinedFragment => cache.getOrElseUpdate( _e, new MagicDrawUMLCombinedFragment() { override val e = _e; override val ops = self } )
    case _e: Uml#Continuation => cache.getOrElseUpdate( _e, new MagicDrawUMLContinuation() { override val e = _e; override val ops = self } )
    case _e: Uml#InteractionOperand => cache.getOrElseUpdate( _e, new MagicDrawUMLInteractionOperand() { override val e = _e; override val ops = self } )   
    case _e: Uml#PartDecomposition => cache.getOrElseUpdate( _e, new MagicDrawUMLPartDecomposition() { override val e = _e; override val ops = self } )
    case _e: Uml#InteractionUse => cache.getOrElseUpdate( _e, new MagicDrawUMLInteractionUse() { override val e = _e; override val ops = self } )
    case _e: Uml#InteractionFragment => cache.getOrElseUpdate( _e, new MagicDrawUMLInteractionFragment() { override val e = _e; override val ops = self } )
    case _e: Uml#Message => cache.getOrElseUpdate( _e, new MagicDrawUMLMessage() { override val e = _e; override val ops = self } )
    case _e: Uml#Enumeration => cache.getOrElseUpdate( _e, new MagicDrawUMLEnumeration() { override val e = _e; override val ops = self } )
    case _e: Uml#PrimitiveType => cache.getOrElseUpdate( _e, new MagicDrawUMLPrimitiveType() { override val e = _e; override val ops = self } )
    case _e: Uml#DataType => cache.getOrElseUpdate( _e, new MagicDrawUMLDataType() { override val e = _e; override val ops = self } )
    case _e: Uml#ActivityPartition => cache.getOrElseUpdate( _e, new MagicDrawUMLActivityPartition() { override val e = _e; override val ops = self } )
    case _e: Uml#InterruptibleActivityRegion => cache.getOrElseUpdate( _e, new MagicDrawUMLInterruptibleActivityRegion() { override val e = _e; override val ops = self } )
    case _e: Uml#ActivityGroup => cache.getOrElseUpdate( _e, new MagicDrawUMLActivityGroup() { override val e = _e; override val ops = self } )
    case _e: Uml#StringExpression => cache.getOrElseUpdate( _e, new MagicDrawUMLStringExpression() { override val e = _e; override val ops = self } )
    case _e: Uml#Expression => cache.getOrElseUpdate( _e, new MagicDrawUMLExpression() { override val e = _e; override val ops = self } )
    case _e: Uml#Duration => cache.getOrElseUpdate( _e, new MagicDrawUMLDuration() { override val e = _e; override val ops = self } )
    case _e: Uml#DurationInterval => cache.getOrElseUpdate( _e, new MagicDrawUMLDurationInterval() { override val e = _e; override val ops = self } )
    case _e: Uml#TimeInterval => cache.getOrElseUpdate( _e, new MagicDrawUMLTimeInterval() { override val e = _e; override val ops = self } )
    case _e: Uml#Interval => cache.getOrElseUpdate( _e, new MagicDrawUMLInterval() { override val e = _e; override val ops = self } )
    case _e: Uml#LiteralBoolean => cache.getOrElseUpdate( _e, new MagicDrawUMLLiteralBoolean() { override val e = _e; override val ops = self } )
    case _e: Uml#LiteralInteger => cache.getOrElseUpdate( _e, new MagicDrawUMLLiteralInteger() { override val e = _e; override val ops = self } )
    case _e: Uml#LiteralNull => cache.getOrElseUpdate( _e, new MagicDrawUMLLiteralNull() { override val e = _e; override val ops = self } )
    case _e: Uml#LiteralReal => cache.getOrElseUpdate( _e, new MagicDrawUMLLiteralReal() { override val e = _e; override val ops = self } )
    case _e: Uml#LiteralString => cache.getOrElseUpdate( _e, new MagicDrawUMLLiteralString() { override val e = _e; override val ops = self } )
    case _e: Uml#LiteralUnlimitedNatural => cache.getOrElseUpdate( _e, new MagicDrawUMLLiteralUnlimitedNatural() { override val e = _e; override val ops = self } )
    case _e: Uml#LiteralSpecification => cache.getOrElseUpdate( _e, new MagicDrawUMLLiteralSpecification() { override val e = _e; override val ops = self } )
    case _e: Uml#OpaqueExpression => cache.getOrElseUpdate( _e, new MagicDrawUMLOpaqueExpression() { override val e = _e; override val ops = self } )
    case _e: Uml#TimeExpression => cache.getOrElseUpdate( _e, new MagicDrawUMLTimeExpression() { override val e = _e; override val ops = self } )
    case _e: Uml#EnumerationLiteral => cache.getOrElseUpdate( _e, new MagicDrawUMLEnumerationLiteral() { override val e = _e; override val ops = self } )
    case _e: Uml#InstanceValue => cache.getOrElseUpdate( _e, new MagicDrawUMLInstanceValue() { override val e = _e; override val ops = self } )
    case _e: Uml#ValueSpecification => cache.getOrElseUpdate( _e, new MagicDrawUMLValueSpecification() { override val e = _e; override val ops = self } )
    case _e: Uml#Variable => cache.getOrElseUpdate( _e, new MagicDrawUMLVariable() { override val e = _e; override val ops = self } )
    case _e: Uml#Parameter => cache.getOrElseUpdate( _e, new MagicDrawUMLParameter() { override val e = _e; override val ops = self } )
    case _e: Uml#ExtensionEnd => cache.getOrElseUpdate( _e, new MagicDrawUMLExtensionEnd() { override val e = _e; override val ops = self } )
    case _e: Uml#Port => cache.getOrElseUpdate( _e, new MagicDrawUMLPort() { override val e = _e; override val ops = self } )
    case _e: Uml#Property => cache.getOrElseUpdate( _e, new MagicDrawUMLProperty() { override val e = _e; override val ops = self } )
    case _e: Uml#Operation => cache.getOrElseUpdate( _e, new MagicDrawUMLOperation() { override val e = _e; override val ops = self } )
    case _e: Uml#Reception => cache.getOrElseUpdate( _e, new MagicDrawUMLReception() { override val e = _e; override val ops = self } )
    case _e: Uml#BehavioralFeature => cache.getOrElseUpdate( _e, new MagicDrawUMLBehavioralFeature() { override val e = _e; override val ops = self } )
    case _e: Uml#StructuralFeature => cache.getOrElseUpdate( _e, new MagicDrawUMLStructuralFeature() { override val e = _e; override val ops = self } )
    case _e: Uml#Connector => cache.getOrElseUpdate( _e, new MagicDrawUMLConnector() { override val e = _e; override val ops = self } )
    case _e: Uml#Feature => cache.getOrElseUpdate( _e, new MagicDrawUMLFeature() { override val e = _e; override val ops = self } )
    case _e: Uml#ExecutionEnvironment => cache.getOrElseUpdate( _e, new MagicDrawUMLExecutionEnvironment() { override val e = _e; override val ops = self } )
    case _e: Uml#Device => cache.getOrElseUpdate( _e, new MagicDrawUMLDevice() { override val e = _e; override val ops = self } )
    case _e: Uml#AssociationClass => cache.getOrElseUpdate( _e, new MagicDrawUMLAssociationClass() { override val e = _e; override val ops = self } )
    case _e: Uml#Node => cache.getOrElseUpdate( _e, new MagicDrawUMLNode() { override val e = _e; override val ops = self } )
    case _e: Uml#Stereotype => cache.getOrElseUpdate( _e, new MagicDrawUMLStereotype() { override val e = _e; override val ops = self } )
    case _e: Uml#FunctionBehavior => cache.getOrElseUpdate( _e, new MagicDrawUMLFunctionBehavior() { override val e = _e; override val ops = self } )
    case _e: Uml#Component => cache.getOrElseUpdate( _e, new MagicDrawUMLComponent() { override val e = _e; override val ops = self } )
    case _e: Uml#ProtocolStateMachine => cache.getOrElseUpdate( _e, new MagicDrawUMLProtocolStateMachine() { override val e = _e; override val ops = self } )
    case _e: Uml#StateMachine => cache.getOrElseUpdate( _e, new MagicDrawUMLStateMachine() { override val e = _e; override val ops = self } )
    case _e: Uml#Activity => cache.getOrElseUpdate( _e, new MagicDrawUMLActivity() { override val e = _e; override val ops = self } )
    case _e: Uml#OpaqueBehavior => cache.getOrElseUpdate( _e, new MagicDrawUMLOpaqueBehavior() { override val e = _e; override val ops = self } )
    case _e: Uml#Behavior => cache.getOrElseUpdate( _e, new MagicDrawUMLBehavior() { override val e = _e; override val ops = self } )
    case _e: Uml#Class => cache.getOrElseUpdate( _e, new MagicDrawUMLClass() { override val e = _e; override val ops = self } )
    case _e: Uml#Actor => cache.getOrElseUpdate( _e, new MagicDrawUMLActor() { override val e = _e; override val ops = self } )
    case _e: Uml#UseCase => cache.getOrElseUpdate( _e, new MagicDrawUMLUseCase() { override val e = _e; override val ops = self } )
    case _e: Uml#Collaboration => cache.getOrElseUpdate( _e, new MagicDrawUMLCollaboration() { override val e = _e; override val ops = self } )
    case _e: Uml#DeploymentSpecification => cache.getOrElseUpdate( _e, new MagicDrawUMLDeploymentSpecification() { override val e = _e; override val ops = self } )
    case _e: Uml#Artifact => cache.getOrElseUpdate( _e, new MagicDrawUMLArtifact() { override val e = _e; override val ops = self } )
    case _e: Uml#Signal => cache.getOrElseUpdate( _e, new MagicDrawUMLSignal() { override val e = _e; override val ops = self } )
    case _e: Uml#InformationItem => cache.getOrElseUpdate( _e, new MagicDrawUMLInformationItem() { override val e = _e; override val ops = self } )
    case _e: Uml#Interface => cache.getOrElseUpdate( _e, new MagicDrawUMLInterface() { override val e = _e; override val ops = self } )
    case _e: Uml#Extension => cache.getOrElseUpdate( _e, new MagicDrawUMLExtension() { override val e = _e; override val ops = self } )
    case _e: Uml#CommunicationPath => cache.getOrElseUpdate( _e, new MagicDrawUMLCommunicationPath() { override val e = _e; override val ops = self } )
    case _e: Uml#Association => cache.getOrElseUpdate( _e, new MagicDrawUMLAssociation() { override val e = _e; override val ops = self } )
    case _e: Uml#TimeObservation => cache.getOrElseUpdate( _e, new MagicDrawUMLTimeObservation() { override val e = _e; override val ops = self } )
    case _e: Uml#DurationObservation => cache.getOrElseUpdate( _e, new MagicDrawUMLDurationObservation() { override val e = _e; override val ops = self } )
    case _e: Uml#Observation => cache.getOrElseUpdate( _e, new MagicDrawUMLObservation() { override val e = _e; override val ops = self } )
    case _e: Uml#InstanceSpecification => cache.getOrElseUpdate( _e, MagicDrawUMLInstanceSpecificationImpl( _e, self ) )
    case _e: Uml#InformationFlow => cache.getOrElseUpdate( _e, new MagicDrawUMLInformationFlow() { override val e = _e; override val ops = self } )
    case _e: Uml#GeneralizationSet => cache.getOrElseUpdate( _e, new MagicDrawUMLGeneralizationSet() { override val e = _e; override val ops = self } )
    case _e: Uml#Trigger => cache.getOrElseUpdate( _e, new MagicDrawUMLTrigger() { override val e = _e; override val ops = self } )
    case _e: Uml#Extend => cache.getOrElseUpdate( _e, new MagicDrawUMLExtend() { override val e = _e; override val ops = self } )
    case _e: Uml#Include => cache.getOrElseUpdate( _e, new MagicDrawUMLInclude() { override val e = _e; override val ops = self } )
    case _e: Uml#CollaborationUse => cache.getOrElseUpdate( _e, new MagicDrawUMLCollaborationUse() { override val e = _e; override val ops = self } )
    case _e: Uml#Vertex => cache.getOrElseUpdate( _e, new MagicDrawUMLVertex() { override val e = _e; override val ops = self } )
    case _e: Uml#GeneralOrdering => cache.getOrElseUpdate( _e, new MagicDrawUMLGeneralOrdering() { override val e = _e; override val ops = self } )
    case _e: Uml#Lifeline => cache.getOrElseUpdate( _e, new MagicDrawUMLLifeline() { override val e = _e; override val ops = self } )
    case _e: Uml#DeployedArtifact => cache.getOrElseUpdate( _e, new MagicDrawUMLDeployedArtifact() { override val e = _e; override val ops = self } )
    case _e: Uml#DeploymentTarget => cache.getOrElseUpdate( _e, new MagicDrawUMLDeploymentTarget() { override val e = _e; override val ops = self } )
    case _e: Uml#ParameterSet => cache.getOrElseUpdate( _e, new MagicDrawUMLParameterSet() { override val e = _e; override val ops = self } )
    case _e: Uml#Clause => cache.getOrElseUpdate( _e, new MagicDrawUMLClause() { override val e = _e; override val ops = self } )
    case _e: Uml#LinkEndCreationData => cache.getOrElseUpdate( _e, new MagicDrawUMLLinkEndCreationData() { override val e = _e; override val ops = self } )
    case _e: Uml#LinkEndDestructionData => cache.getOrElseUpdate( _e, new MagicDrawUMLLinkEndDestructionData() { override val e = _e; override val ops = self } )
    case _e: Uml#LinkEndData => cache.getOrElseUpdate( _e, new MagicDrawUMLLinkEndData() { override val e = _e; override val ops = self } )
    case _e: Uml#TemplateParameterSubstitution => cache.getOrElseUpdate( _e, new MagicDrawUMLTemplateParameterSubstitution() { override val e = _e; override val ops = self } )
    case _e: Uml#TemplateableElement => cache.getOrElseUpdate( _e, new MagicDrawUMLTemplateableElement() { override val e = _e; override val ops = self } )
    case _e: Uml#Slot => cache.getOrElseUpdate( _e, new MagicDrawUMLSlot() { override val e = _e; override val ops = self } )
    case _e: Uml#Image => cache.getOrElseUpdate( _e, new MagicDrawUMLImage() { override val e = _e; override val ops = self } )
    case _e: Uml#QualifierValue => cache.getOrElseUpdate( _e, new MagicDrawUMLQualifierValue() { override val e = _e; override val ops = self } )
    case _e: Uml#ExceptionHandler => cache.getOrElseUpdate( _e, new MagicDrawUMLExceptionHandler() { override val e = _e; override val ops = self } )

    case _e: Uml#EncapsulatedClassifier => cache.getOrElseUpdate( _e, new MagicDrawUMLEncapsulatedClassifier() { override val e = _e; override val ops = self } )
    case _e: Uml#BehavioredClassifier => cache.getOrElseUpdate( _e, new MagicDrawUMLBehavioredClassifier() { override val e = _e; override val ops = self } )
    case _e: Uml#StructuredClassifier => cache.getOrElseUpdate( _e, new MagicDrawUMLStructuredClassifier() { override val e = _e; override val ops = self } )   
    case _e: Uml#Classifier => cache.getOrElseUpdate( _e, new MagicDrawUMLClassifier() { override val e = _e; override val ops = self } )
    case _e: Uml#RedefinableElement => cache.getOrElseUpdate( _e, new MagicDrawUMLRedefinableElement() { override val e = _e; override val ops = self } )
    case _e: Uml#Type => cache.getOrElseUpdate( _e, new MagicDrawUMLType() { override val e = _e; override val ops = self } )
    case _e: Uml#ConnectableElement => cache.getOrElseUpdate( _e, new MagicDrawUMLConnectableElement() { override val e = _e; override val ops = self } )
    case _e: Uml#ParameterableElement => cache.getOrElseUpdate( _e, new MagicDrawUMLParameterableElement() { override val e = _e; override val ops = self } )
    case _e: Uml#TypedElement => cache.getOrElseUpdate( _e, new MagicDrawUMLTypedElement() { override val e = _e; override val ops = self } )
    case _e: Uml#PackageableElement => cache.getOrElseUpdate( _e, new MagicDrawUMLPackageableElement() { override val e = _e; override val ops = self } )

    case _e: Uml#ConnectorEnd => cache.getOrElseUpdate( _e, new MagicDrawUMLConnectorEnd() { override val e = _e; override val ops = self } )
    case _e: Uml#MultiplicityElement => cache.getOrElseUpdate( _e, new MagicDrawUMLMultiplicityElement() { override val e = _e; override val ops = self } )

    case _e: Uml#Namespace => cache.getOrElseUpdate( _e, new MagicDrawUMLNamespace() { override val e = _e; override val ops = self } )
    case _e: Uml#NamedElement => cache.getOrElseUpdate( _e, new MagicDrawUMLNamedElement() { override val e = _e; override val ops = self } )
    case _e: Uml#ProtocolConformance => cache.getOrElseUpdate( _e, new MagicDrawUMLProtocolConformance() { override val e = _e; override val ops = self } )
    case _e: Uml#Generalization => cache.getOrElseUpdate( _e, new MagicDrawUMLGeneralization() { override val e = _e; override val ops = self } )
    case _e: Uml#TemplateBinding => cache.getOrElseUpdate( _e, new MagicDrawUMLTemplateBinding() { override val e = _e; override val ops = self } )
    case _e: Uml#DirectedRelationship => cache.getOrElseUpdate( _e, new MagicDrawUMLDirectedRelationship() { override val e = _e; override val ops = self } )
    case _e: Uml#Relationship => cache.getOrElseUpdate( _e, new MagicDrawUMLRelationship() { override val e = _e; override val ops = self } )
    case _e: Uml#Element => cache.getOrElseUpdate( _e, new MagicDrawUMLElement() { override val e = _e; override val ops = self } )



    case _e                              => throw new MatchError( s" No case for ${_e.getHumanType}: ${_e.getID}" )

  }

  implicit def umlAbstraction( _e: Uml#Abstraction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLAbstraction]
  implicit def umlAcceptCallAction( _e: Uml#AcceptCallAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLAcceptCallAction]
  implicit def umlAcceptEventAction( _e: Uml#AcceptEventAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLAcceptEventAction]
  implicit def umlAction( _e: Uml#Action ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLAction]
  implicit def umlActionExecutionSpecification( _e: Uml#ActionExecutionSpecification ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLActionExecutionSpecification]
  implicit def umlActionInputPin( _e: Uml#ActionInputPin ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLActionInputPin]
  implicit def umlActivity( _e: Uml#Activity ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLActivity]
  implicit def umlActivityEdge( _e: Uml#ActivityEdge ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLActivityEdge]
  implicit def umlActivityFinalNode( _e: Uml#ActivityFinalNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLActivityFinalNode]
  implicit def umlActivityGroup( _e: Uml#ActivityGroup ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLActivityGroup]
  implicit def umlActivityNode( _e: Uml#ActivityNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLActivityNode]
  implicit def umlActivityParameterNode( _e: Uml#ActivityParameterNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLActivityParameterNode]
  implicit def umlActivityPartition( _e: Uml#ActivityPartition ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLActivityPartition]
  implicit def umlActor( _e: Uml#Actor ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLActor]
  implicit def umlAddStructuralFeatureValueAction( _e: Uml#AddStructuralFeatureValueAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLAddStructuralFeatureValueAction]
  implicit def umlAddVariableValueAction( _e: Uml#AddVariableValueAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLAddVariableValueAction]
  implicit def umlAnyReceiveEvent( _e: Uml#AnyReceiveEvent ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLAnyReceiveEvent]
  implicit def umlArtifact( _e: Uml#Artifact ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLArtifact]
  implicit def umlAssociation( _e: Uml#Association ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLAssociation]
  implicit def umlAssociationClass( _e: Uml#AssociationClass ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLAssociationClass]
  implicit def umlBehavior( _e: Uml#Behavior ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLBehavior]
  implicit def umlBehaviorExecutionSpecification( _e: Uml#BehaviorExecutionSpecification ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLBehaviorExecutionSpecification]
  implicit def umlBehavioralFeature( _e: Uml#BehavioralFeature ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLBehavioralFeature]
  implicit def umlBehavioredClassifier( _e: Uml#BehavioredClassifier ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLBehavioredClassifier]
  implicit def umlBroadcastSignalAction( _e: Uml#BroadcastSignalAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLBroadcastSignalAction]
  implicit def umlCallAction( _e: Uml#CallAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLCallAction]
  implicit def umlCallBehaviorAction( _e: Uml#CallBehaviorAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLCallBehaviorAction]
  implicit def umlCallEvent( _e: Uml#CallEvent ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLCallEvent]
  implicit def umlCallOperationAction( _e: Uml#CallOperationAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLCallOperationAction]
  implicit def umlCentralBufferNode( _e: Uml#CentralBufferNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLCentralBufferNode]
  implicit def umlChangeEvent( _e: Uml#ChangeEvent ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLChangeEvent]
  implicit def umlClass( _e: Uml#Class ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLClass]
  implicit def umlClassifier( _e: Uml#Classifier ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLClassifier]
  implicit def umlClassifierTemplateParameter( _e: Uml#ClassifierTemplateParameter ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLClassifierTemplateParameter]
  implicit def umlClause( _e: Uml#Clause ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLClause]
  implicit def umlClearAssociationAction( _e: Uml#ClearAssociationAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLClearAssociationAction]
  implicit def umlClearStructuralFeatureAction( _e: Uml#ClearStructuralFeatureAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLClearStructuralFeatureAction]
  implicit def umlClearVariableAction( _e: Uml#ClearVariableAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLClearVariableAction]
  implicit def umlCollaboration( _e: Uml#Collaboration ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLCollaboration]
  implicit def umlCollaborationUse( _e: Uml#CollaborationUse ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLCollaborationUse]
  implicit def umlCombinedFragment( _e: Uml#CombinedFragment ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLCombinedFragment]
  implicit def umlComment( _e: Uml#Comment ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLComment]
  implicit def umlCommunicationPath( _e: Uml#CommunicationPath ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLCommunicationPath]
  implicit def umlComponent( _e: Uml#Component ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLComponent]
  implicit def umlComponentRealization( _e: Uml#ComponentRealization ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLComponentRealization]
  implicit def umlConditionalNode( _e: Uml#ConditionalNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLConditionalNode]
  implicit def umlConnectableElement( _e: Uml#ConnectableElement ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLConnectableElement]
  implicit def umlConnectableElementTemplateParameter( _e: Uml#ConnectableElementTemplateParameter ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLConnectableElementTemplateParameter]
  implicit def umlConnectionPointReference( _e: Uml#ConnectionPointReference ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLConnectionPointReference]
  implicit def umlConnector( _e: Uml#Connector ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLConnector]
  implicit def umlConnectorEnd( _e: Uml#ConnectorEnd ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLConnectorEnd]
  implicit def umlConsiderIgnoreFragment( _e: Uml#ConsiderIgnoreFragment ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLConsiderIgnoreFragment]
  implicit def umlConstraint( _e: Uml#Constraint ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLConstraint]
  implicit def umlContinuation( _e: Uml#Continuation ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLContinuation]
  implicit def umlControlFlow( _e: Uml#ControlFlow ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLControlFlow]
  implicit def umlControlNode( _e: Uml#ControlNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLControlNode]
  implicit def umlCreateLinkAction( _e: Uml#CreateLinkAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLCreateLinkAction]
  implicit def umlCreateLinkObjectAction( _e: Uml#CreateLinkObjectAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLCreateLinkObjectAction]
  implicit def umlCreateObjectAction( _e: Uml#CreateObjectAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLCreateObjectAction]
  implicit def umlDataStoreNode( _e: Uml#DataStoreNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDataStoreNode]
  implicit def umlDataType( _e: Uml#DataType ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDataType]
  implicit def umlDecisionNode( _e: Uml#DecisionNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDecisionNode]
  implicit def umlDependency( _e: Uml#Dependency ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDependency]
  implicit def umlDeployedArtifact( _e: Uml#DeployedArtifact ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDeployedArtifact]
  implicit def umlDeployment( _e: Uml#Deployment ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDeployment]
  implicit def umlDeploymentSpecification( _e: Uml#DeploymentSpecification ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDeploymentSpecification]
  implicit def umlDeploymentTarget( _e: Uml#DeploymentTarget ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDeploymentTarget]
  implicit def umlDestroyLinkAction( _e: Uml#DestroyLinkAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDestroyLinkAction]
  implicit def umlDestroyObjectAction( _e: Uml#DestroyObjectAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDestroyObjectAction]
  implicit def umlDestructionOccurrenceSpecification( _e: Uml#DestructionOccurrenceSpecification ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDestructionOccurrenceSpecification]
  implicit def umlDevice( _e: Uml#Device ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDevice]
  implicit def umlDirectedRelationship( _e: Uml#DirectedRelationship ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDirectedRelationship]
  implicit def umlDuration( _e: Uml#Duration ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDuration]
  implicit def umlDurationConstraint( _e: Uml#DurationConstraint ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDurationConstraint]
  implicit def umlDurationInterval( _e: Uml#DurationInterval ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDurationInterval]
  implicit def umlDurationObservation( _e: Uml#DurationObservation ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDurationObservation]
  implicit def umlElement( _e: Uml#Element ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLElement]
  implicit def umlElementImport( _e: Uml#ElementImport ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLElementImport]
  implicit def umlEncapsulatedClassifier( _e: Uml#EncapsulatedClassifier ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLEncapsulatedClassifier]
  implicit def umlEnumeration( _e: Uml#Enumeration ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLEnumeration]
  implicit def umlEnumerationLiteral( _e: Uml#EnumerationLiteral ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLEnumerationLiteral]
  implicit def umlEvent( _e: Uml#Event ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLEvent]
  implicit def umlExceptionHandler( _e: Uml#ExceptionHandler ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLExceptionHandler]
  implicit def umlExecutableNode( _e: Uml#ExecutableNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLExecutableNode]
  implicit def umlExecutionEnvironment( _e: Uml#ExecutionEnvironment ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLExecutionEnvironment]
  implicit def umlExecutionOccurrenceSpecification( _e: Uml#ExecutionOccurrenceSpecification ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLExecutionOccurrenceSpecification]
  implicit def umlExecutionSpecification( _e: Uml#ExecutionSpecification ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLExecutionSpecification]
  implicit def umlExpansionNode( _e: Uml#ExpansionNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLExpansionNode]
  implicit def umlExpansionRegion( _e: Uml#ExpansionRegion ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLExpansionRegion]
  implicit def umlExpression( _e: Uml#Expression ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLExpression]
  implicit def umlExtend( _e: Uml#Extend ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLExtend]
  implicit def umlExtension( _e: Uml#Extension ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLExtension]
  implicit def umlExtensionEnd( _e: Uml#ExtensionEnd ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLExtensionEnd]
  implicit def umlExtensionPoint( _e: Uml#ExtensionPoint ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLExtensionPoint]
  implicit def umlFeature( _e: Uml#Feature ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLFeature]
  implicit def umlFinalNode( _e: Uml#FinalNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLFinalNode]
  implicit def umlFinalState( _e: Uml#FinalState ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLFinalState]
  implicit def umlFlowFinalNode( _e: Uml#FlowFinalNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLFlowFinalNode]
  implicit def umlForkNode( _e: Uml#ForkNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLForkNode]
  implicit def umlFunctionBehavior( _e: Uml#FunctionBehavior ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLFunctionBehavior]
  implicit def umlGate( _e: Uml#Gate ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLGate]
  implicit def umlGeneralOrdering( _e: Uml#GeneralOrdering ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLGeneralOrdering]
  implicit def umlGeneralization( _e: Uml#Generalization ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLGeneralization]
  implicit def umlGeneralizationSet( _e: Uml#GeneralizationSet ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLGeneralizationSet]
  implicit def umlImage( _e: Uml#Image ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLImage]
  implicit def umlInclude( _e: Uml#Include ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInclude]
  implicit def umlInformationFlow( _e: Uml#InformationFlow ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInformationFlow]
  implicit def umlInformationItem( _e: Uml#InformationItem ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInformationItem]
  implicit def umlInitialNode( _e: Uml#InitialNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInitialNode]
  implicit def umlInputPin( _e: Uml#InputPin ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInputPin]
  implicit def umlInstanceSpecification( _e: Uml#InstanceSpecification ) = cacheLookupOrUpdateAs[Uml#InstanceSpecification, MagicDrawUMLInstanceSpecification]( _e )
  implicit def umlInstanceValue( _e: Uml#InstanceValue ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInstanceValue]
  implicit def umlInteraction( _e: Uml#Interaction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInteraction]
  implicit def umlInteractionConstraint( _e: Uml#InteractionConstraint ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInteractionConstraint]
  implicit def umlInteractionFragment( _e: Uml#InteractionFragment ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInteractionFragment]
  implicit def umlInteractionOperand( _e: Uml#InteractionOperand ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInteractionOperand]
  implicit def umlInteractionUse( _e: Uml#InteractionUse ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInteractionUse]
  implicit def umlInterface( _e: Uml#Interface ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInterface]
  implicit def umlInterfaceRealization( _e: Uml#InterfaceRealization ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInterfaceRealization]
  implicit def umlInterruptibleActivityRegion( _e: Uml#InterruptibleActivityRegion ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInterruptibleActivityRegion]
  implicit def umlInterval( _e: Uml#Interval ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInterval]
  implicit def umlIntervalConstraint( _e: Uml#IntervalConstraint ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLIntervalConstraint]
  implicit def umlInvocationAction( _e: Uml#InvocationAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLInvocationAction]
  implicit def umlJoinNode( _e: Uml#JoinNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLJoinNode]
  implicit def umlLifeline( _e: Uml#Lifeline ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLLifeline]
  implicit def umlLinkAction( _e: Uml#LinkAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLLinkAction]
  implicit def umlLinkEndCreationData( _e: Uml#LinkEndCreationData ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLLinkEndCreationData]
  implicit def umlLinkEndData( _e: Uml#LinkEndData ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLLinkEndData]
  implicit def umlLinkEndDestructionData( _e: Uml#LinkEndDestructionData ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLLinkEndDestructionData]
  implicit def umlLiteralBoolean( _e: Uml#LiteralBoolean ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLLiteralBoolean]
  implicit def umlLiteralInteger( _e: Uml#LiteralInteger ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLLiteralInteger]
  implicit def umlLiteralNull( _e: Uml#LiteralNull ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLLiteralNull]
  implicit def umlLiteralReal( _e: Uml#LiteralReal ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLLiteralReal]
  implicit def umlLiteralSpecification( _e: Uml#LiteralSpecification ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLLiteralSpecification]
  implicit def umlLiteralString( _e: Uml#LiteralString ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLLiteralString]
  implicit def umlLiteralUnlimitedNatural( _e: Uml#LiteralUnlimitedNatural ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLLiteralUnlimitedNatural]
  implicit def umlLoopNode( _e: Uml#LoopNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLLoopNode]
  implicit def umlManifestation( _e: Uml#Manifestation ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLManifestation]
  implicit def umlMergeNode( _e: Uml#MergeNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLMergeNode]
  implicit def umlMessage( _e: Uml#Message ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLMessage]
  implicit def umlMessageEnd( _e: Uml#MessageEnd ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLMessageEnd]
  implicit def umlMessageEvent( _e: Uml#MessageEvent ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLMessageEvent]
  implicit def umlMessageOccurrenceSpecification( _e: Uml#MessageOccurrenceSpecification ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLMessageOccurrenceSpecification]
  implicit def umlModel( _e: Uml#Model ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLModel]
  implicit def umlMultiplicityElement( _e: Uml#MultiplicityElement ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLMultiplicityElement]
  implicit def umlNamedElement( _e: Uml#NamedElement ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLNamedElement]
  implicit def umlNamespace( _e: Uml#Namespace ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLNamespace]
  implicit def umlNode( _e: Uml#Node ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLNode]
  implicit def umlObjectFlow( _e: Uml#ObjectFlow ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLObjectFlow]
  implicit def umlObjectNode( _e: Uml#ObjectNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLObjectNode]
  implicit def umlObservation( _e: Uml#Observation ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLObservation]
  implicit def umlOccurrenceSpecification( _e: Uml#OccurrenceSpecification ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLOccurrenceSpecification]
  implicit def umlOpaqueAction( _e: Uml#OpaqueAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLOpaqueAction]
  implicit def umlOpaqueBehavior( _e: Uml#OpaqueBehavior ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLOpaqueBehavior]
  implicit def umlOpaqueExpression( _e: Uml#OpaqueExpression ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLOpaqueExpression]
  implicit def umlOperation( _e: Uml#Operation ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLOperation]
  implicit def umlOperationTemplateParameter( _e: Uml#OperationTemplateParameter ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLOperationTemplateParameter]
  implicit def umlOutputPin( _e: Uml#OutputPin ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLOutputPin]
  implicit def umlPackage( _e: Uml#Package ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLPackage]
  implicit def umlPackageImport( _e: Uml#PackageImport ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLPackageImport]
  implicit def umlPackageMerge( _e: Uml#PackageMerge ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLPackageMerge]
  implicit def umlPackageableElement( _e: Uml#PackageableElement ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLPackageableElement]
  implicit def umlParameter( _e: Uml#Parameter ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLParameter]
  implicit def umlParameterSet( _e: Uml#ParameterSet ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLParameterSet]
  implicit def umlParameterableElement( _e: Uml#ParameterableElement ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLParameterableElement]
  implicit def umlPartDecomposition( _e: Uml#PartDecomposition ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLPartDecomposition]
  implicit def umlPin( _e: Uml#Pin ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLPin]
  implicit def umlPort( _e: Uml#Port ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLPort]
  implicit def umlPrimitiveType( _e: Uml#PrimitiveType ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLPrimitiveType]
  implicit def umlProfile( _e: Uml#Profile ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLProfile]
  implicit def umlProfileApplication( _e: Uml#ProfileApplication ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLProfileApplication]
  implicit def umlProperty( _e: Uml#Property ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLProperty]
  implicit def umlProtocolConformance( _e: Uml#ProtocolConformance ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLProtocolConformance]
  implicit def umlProtocolStateMachine( _e: Uml#ProtocolStateMachine ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLProtocolStateMachine]
  implicit def umlProtocolTransition( _e: Uml#ProtocolTransition ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLProtocolTransition]
  implicit def umlPseudostate( _e: Uml#Pseudostate ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLPseudostate]
  implicit def umlQualifierValue( _e: Uml#QualifierValue ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLQualifierValue]
  implicit def umlRaiseExceptionAction( _e: Uml#RaiseExceptionAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLRaiseExceptionAction]
  implicit def umlReadExtentAction( _e: Uml#ReadExtentAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLReadExtentAction]
  implicit def umlReadIsClassifiedObjectAction( _e: Uml#ReadIsClassifiedObjectAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLReadIsClassifiedObjectAction]
  implicit def umlReadLinkAction( _e: Uml#ReadLinkAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLReadLinkAction]
  implicit def umlReadLinkObjectEndAction( _e: Uml#ReadLinkObjectEndAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLReadLinkObjectEndAction]
  implicit def umlReadLinkObjectEndQualifierAction( _e: Uml#ReadLinkObjectEndQualifierAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLReadLinkObjectEndQualifierAction]
  implicit def umlReadSelfAction( _e: Uml#ReadSelfAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLReadSelfAction]
  implicit def umlReadStructuralFeatureAction( _e: Uml#ReadStructuralFeatureAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLReadStructuralFeatureAction]
  implicit def umlReadVariableAction( _e: Uml#ReadVariableAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLReadVariableAction]
  implicit def umlRealization( _e: Uml#Realization ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLRealization]
  implicit def umlReception( _e: Uml#Reception ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLReception]
  implicit def umlReclassifyObjectAction( _e: Uml#ReclassifyObjectAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLReclassifyObjectAction]
  implicit def umlRedefinableElement( _e: Uml#RedefinableElement ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLRedefinableElement]
  implicit def umlRedefinableTemplateSignature( _e: Uml#RedefinableTemplateSignature ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLRedefinableTemplateSignature]
  implicit def umlReduceAction( _e: Uml#ReduceAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLReduceAction]
  implicit def umlRegion( _e: Uml#Region ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLRegion]
  implicit def umlRelationship( _e: Uml#Relationship ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLRelationship]
  implicit def umlRemoveStructuralFeatureValueAction( _e: Uml#RemoveStructuralFeatureValueAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLRemoveStructuralFeatureValueAction]
  implicit def umlRemoveVariableValueAction( _e: Uml#RemoveVariableValueAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLRemoveVariableValueAction]
  implicit def umlReplyAction( _e: Uml#ReplyAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLReplyAction]
  implicit def umlSendObjectAction( _e: Uml#SendObjectAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLSendObjectAction]
  implicit def umlSendSignalAction( _e: Uml#SendSignalAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLSendSignalAction]
  implicit def umlSequenceNode( _e: Uml#SequenceNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLSequenceNode]
  implicit def umlSignal( _e: Uml#Signal ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLSignal]
  implicit def umlSignalEvent( _e: Uml#SignalEvent ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLSignalEvent]
  implicit def umlSlot( _e: Uml#Slot ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLSlot]
  implicit def umlStartClassifierBehaviorAction( _e: Uml#StartClassifierBehaviorAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLStartClassifierBehaviorAction]
  implicit def umlStartObjectBehaviorAction( _e: Uml#StartObjectBehaviorAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLStartObjectBehaviorAction]
  implicit def umlState( _e: Uml#State ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLState]
  implicit def umlStateInvariant( _e: Uml#StateInvariant ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLStateInvariant]
  implicit def umlStateMachine( _e: Uml#StateMachine ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLStateMachine]
  implicit def umlStereotype( _e: Uml#Stereotype ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLStereotype]
  implicit def umlStringExpression( _e: Uml#StringExpression ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLStringExpression]
  implicit def umlStructuralFeature( _e: Uml#StructuralFeature ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLStructuralFeature]
  implicit def umlStructuralFeatureAction( _e: Uml#StructuralFeatureAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLStructuralFeatureAction]
  implicit def umlStructuredActivityNode( _e: Uml#StructuredActivityNode ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLStructuredActivityNode]
  implicit def umlStructuredClassifier( _e: Uml#StructuredClassifier ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLStructuredClassifier]
  implicit def umlSubstitution( _e: Uml#Substitution ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLSubstitution]
  implicit def umlTemplateBinding( _e: Uml#TemplateBinding ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLTemplateBinding]
  implicit def umlTemplateParameter( _e: Uml#TemplateParameter ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLTemplateParameter]
  implicit def umlTemplateParameterSubstitution( _e: Uml#TemplateParameterSubstitution ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLTemplateParameterSubstitution]
  implicit def umlTemplateSignature( _e: Uml#TemplateSignature ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLTemplateSignature]
  implicit def umlTemplateableElement( _e: Uml#TemplateableElement ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLTemplateableElement]
  implicit def umlTestIdentityAction( _e: Uml#TestIdentityAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLTestIdentityAction]
  implicit def umlTimeConstraint( _e: Uml#TimeConstraint ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLTimeConstraint]
  implicit def umlTimeEvent( _e: Uml#TimeEvent ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLTimeEvent]
  implicit def umlTimeExpression( _e: Uml#TimeExpression ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLTimeExpression]
  implicit def umlTimeInterval( _e: Uml#TimeInterval ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLTimeInterval]
  implicit def umlTimeObservation( _e: Uml#TimeObservation ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLTimeObservation]
  implicit def umlTransition( _e: Uml#Transition ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLTransition]
  implicit def umlTrigger( _e: Uml#Trigger ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLTrigger]
  implicit def umlType( _e: Uml#Type ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLType]
  implicit def umlTypedElement( _e: Uml#TypedElement ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLTypedElement]
  implicit def umlUnmarshallAction( _e: Uml#UnmarshallAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLUnmarshallAction]
  implicit def umlUsage( _e: Uml#Usage ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLUsage]
  implicit def umlUseCase( _e: Uml#UseCase ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLUseCase]
  implicit def umlValuePin( _e: Uml#ValuePin ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLValuePin]
  implicit def umlValueSpecification( _e: Uml#ValueSpecification ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLValueSpecification]
  implicit def umlValueSpecificationAction( _e: Uml#ValueSpecificationAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLValueSpecificationAction]
  implicit def umlVariable( _e: Uml#Variable ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLVariable]
  implicit def umlVariableAction( _e: Uml#VariableAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLVariableAction]
  implicit def umlVertex( _e: Uml#Vertex ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLVertex]
  implicit def umlWriteLinkAction( _e: Uml#WriteLinkAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLWriteLinkAction]
  implicit def umlWriteStructuralFeatureAction( _e: Uml#WriteStructuralFeatureAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLWriteStructuralFeatureAction]
  implicit def umlWriteVariableAction( _e: Uml#WriteVariableAction ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLWriteVariableAction]

  // MagicDraw-specific

  implicit def umlDiagram( _e: Uml#Diagram ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLDiagram]
  implicit def umlElementValue( _e: Uml#ElementValue ) = cacheLookupOrUpdate( _e ).asInstanceOf[MagicDrawUMLElementValue]

  implicit def umlMagicDrawUMLAbstraction( _e: UMLAbstraction[Uml] ) = _e match { case mdE: MagicDrawUMLAbstraction => mdE }
  implicit def umlMagicDrawUMLAcceptCallAction( _e: UMLAcceptCallAction[Uml] ) = _e match { case mdE: MagicDrawUMLAcceptCallAction => mdE }
  implicit def umlMagicDrawUMLAcceptEventAction( _e: UMLAcceptEventAction[Uml] ) = _e match { case mdE: MagicDrawUMLAcceptEventAction => mdE }
  implicit def umlMagicDrawUMLAction( _e: UMLAction[Uml] ) = _e match { case mdE: MagicDrawUMLAction => mdE }
  implicit def umlMagicDrawUMLActionExecutionSpecification( _e: UMLActionExecutionSpecification[Uml] ) = _e match { case mdE: MagicDrawUMLActionExecutionSpecification => mdE }
  implicit def umlMagicDrawUMLActionInputPin( _e: UMLActionInputPin[Uml] ) = _e match { case mdE: MagicDrawUMLActionInputPin => mdE }
  implicit def umlMagicDrawUMLActivity( _e: UMLActivity[Uml] ) = _e match { case mdE: MagicDrawUMLActivity => mdE }
  implicit def umlMagicDrawUMLActivityEdge( _e: UMLActivityEdge[Uml] ) = _e match { case mdE: MagicDrawUMLActivityEdge => mdE }
  implicit def umlMagicDrawUMLActivityFinalNode( _e: UMLActivityFinalNode[Uml] ) = _e match { case mdE: MagicDrawUMLActivityFinalNode => mdE }
  implicit def umlMagicDrawUMLActivityGroup( _e: UMLActivityGroup[Uml] ) = _e match { case mdE: MagicDrawUMLActivityGroup => mdE }
  implicit def umlMagicDrawUMLActivityNode( _e: UMLActivityNode[Uml] ) = _e match { case mdE: MagicDrawUMLActivityNode => mdE }
  implicit def umlMagicDrawUMLActivityParameterNode( _e: UMLActivityParameterNode[Uml] ) = _e match { case mdE: MagicDrawUMLActivityParameterNode => mdE }
  implicit def umlMagicDrawUMLActivityPartition( _e: UMLActivityPartition[Uml] ) = _e match { case mdE: MagicDrawUMLActivityPartition => mdE }
  implicit def umlMagicDrawUMLActor( _e: UMLActor[Uml] ) = _e match { case mdE: MagicDrawUMLActor => mdE }
  implicit def umlMagicDrawUMLAddStructuralFeatureValueAction( _e: UMLAddStructuralFeatureValueAction[Uml] ) = _e match { case mdE: MagicDrawUMLAddStructuralFeatureValueAction => mdE }
  implicit def umlMagicDrawUMLAddVariableValueAction( _e: UMLAddVariableValueAction[Uml] ) = _e match { case mdE: MagicDrawUMLAddVariableValueAction => mdE }
  implicit def umlMagicDrawUMLAnyReceiveEvent( _e: UMLAnyReceiveEvent[Uml] ) = _e match { case mdE: MagicDrawUMLAnyReceiveEvent => mdE }
  implicit def umlMagicDrawUMLArtifact( _e: UMLArtifact[Uml] ) = _e match { case mdE: MagicDrawUMLArtifact => mdE }
  implicit def umlMagicDrawUMLAssociation( _e: UMLAssociation[Uml] ) = _e match { case mdE: MagicDrawUMLAssociation => mdE }
  implicit def umlMagicDrawUMLAssociationClass( _e: UMLAssociationClass[Uml] ) = _e match { case mdE: MagicDrawUMLAssociationClass => mdE }
  implicit def umlMagicDrawUMLBehavior( _e: UMLBehavior[Uml] ) = _e match { case mdE: MagicDrawUMLBehavior => mdE }
  implicit def umlMagicDrawUMLBehaviorExecutionSpecification( _e: UMLBehaviorExecutionSpecification[Uml] ) = _e match { case mdE: MagicDrawUMLBehaviorExecutionSpecification => mdE }
  implicit def umlMagicDrawUMLBehavioralFeature( _e: UMLBehavioralFeature[Uml] ) = _e match { case mdE: MagicDrawUMLBehavioralFeature => mdE }
  implicit def umlMagicDrawUMLBehavioredClassifier( _e: UMLBehavioredClassifier[Uml] ) = _e match { case mdE: MagicDrawUMLBehavioredClassifier => mdE }
  implicit def umlMagicDrawUMLBroadcastSignalAction( _e: UMLBroadcastSignalAction[Uml] ) = _e match { case mdE: MagicDrawUMLBroadcastSignalAction => mdE }
  implicit def umlMagicDrawUMLCallAction( _e: UMLCallAction[Uml] ) = _e match { case mdE: MagicDrawUMLCallAction => mdE }
  implicit def umlMagicDrawUMLCallBehaviorAction( _e: UMLCallBehaviorAction[Uml] ) = _e match { case mdE: MagicDrawUMLCallBehaviorAction => mdE }
  implicit def umlMagicDrawUMLCallEvent( _e: UMLCallEvent[Uml] ) = _e match { case mdE: MagicDrawUMLCallEvent => mdE }
  implicit def umlMagicDrawUMLCallOperationAction( _e: UMLCallOperationAction[Uml] ) = _e match { case mdE: MagicDrawUMLCallOperationAction => mdE }
  implicit def umlMagicDrawUMLCentralBufferNode( _e: UMLCentralBufferNode[Uml] ) = _e match { case mdE: MagicDrawUMLCentralBufferNode => mdE }
  implicit def umlMagicDrawUMLChangeEvent( _e: UMLChangeEvent[Uml] ) = _e match { case mdE: MagicDrawUMLChangeEvent => mdE }
  implicit def umlMagicDrawUMLClass( _e: UMLClass[Uml] ) = _e match { case mdE: MagicDrawUMLClass => mdE }
  implicit def umlMagicDrawUMLClassifier( _e: UMLClassifier[Uml] ) = _e match { case mdE: MagicDrawUMLClassifier => mdE }
  implicit def umlMagicDrawUMLClassifierTemplateParameter( _e: UMLClassifierTemplateParameter[Uml] ) = _e match { case mdE: MagicDrawUMLClassifierTemplateParameter => mdE }
  implicit def umlMagicDrawUMLClause( _e: UMLClause[Uml] ) = _e match { case mdE: MagicDrawUMLClause => mdE }
  implicit def umlMagicDrawUMLClearAssociationAction( _e: UMLClearAssociationAction[Uml] ) = _e match { case mdE: MagicDrawUMLClearAssociationAction => mdE }
  implicit def umlMagicDrawUMLClearStructuralFeatureAction( _e: UMLClearStructuralFeatureAction[Uml] ) = _e match { case mdE: MagicDrawUMLClearStructuralFeatureAction => mdE }
  implicit def umlMagicDrawUMLClearVariableAction( _e: UMLClearVariableAction[Uml] ) = _e match { case mdE: MagicDrawUMLClearVariableAction => mdE }
  implicit def umlMagicDrawUMLCollaboration( _e: UMLCollaboration[Uml] ) = _e match { case mdE: MagicDrawUMLCollaboration => mdE }
  implicit def umlMagicDrawUMLCollaborationUse( _e: UMLCollaborationUse[Uml] ) = _e match { case mdE: MagicDrawUMLCollaborationUse => mdE }
  implicit def umlMagicDrawUMLCombinedFragment( _e: UMLCombinedFragment[Uml] ) = _e match { case mdE: MagicDrawUMLCombinedFragment => mdE }
  implicit def umlMagicDrawUMLComment( _e: UMLComment[Uml] ) = _e match { case mdE: MagicDrawUMLComment => mdE }
  implicit def umlMagicDrawUMLCommunicationPath( _e: UMLCommunicationPath[Uml] ) = _e match { case mdE: MagicDrawUMLCommunicationPath => mdE }
  implicit def umlMagicDrawUMLComponent( _e: UMLComponent[Uml] ) = _e match { case mdE: MagicDrawUMLComponent => mdE }
  implicit def umlMagicDrawUMLComponentRealization( _e: UMLComponentRealization[Uml] ) = _e match { case mdE: MagicDrawUMLComponentRealization => mdE }
  implicit def umlMagicDrawUMLConditionalNode( _e: UMLConditionalNode[Uml] ) = _e match { case mdE: MagicDrawUMLConditionalNode => mdE }
  implicit def umlMagicDrawUMLConnectableElement( _e: UMLConnectableElement[Uml] ) = _e match { case mdE: MagicDrawUMLConnectableElement => mdE }
  implicit def umlMagicDrawUMLConnectableElementTemplateParameter( _e: UMLConnectableElementTemplateParameter[Uml] ) = _e match { case mdE: MagicDrawUMLConnectableElementTemplateParameter => mdE }
  implicit def umlMagicDrawUMLConnectionPointReference( _e: UMLConnectionPointReference[Uml] ) = _e match { case mdE: MagicDrawUMLConnectionPointReference => mdE }
  implicit def umlMagicDrawUMLConnector( _e: UMLConnector[Uml] ) = _e match { case mdE: MagicDrawUMLConnector => mdE }
  implicit def umlMagicDrawUMLConnectorEnd( _e: UMLConnectorEnd[Uml] ) = _e match { case mdE: MagicDrawUMLConnectorEnd => mdE }
  implicit def umlMagicDrawUMLConsiderIgnoreFragment( _e: UMLConsiderIgnoreFragment[Uml] ) = _e match { case mdE: MagicDrawUMLConsiderIgnoreFragment => mdE }
  implicit def umlMagicDrawUMLConstraint( _e: UMLConstraint[Uml] ) = _e match { case mdE: MagicDrawUMLConstraint => mdE }
  implicit def umlMagicDrawUMLContinuation( _e: UMLContinuation[Uml] ) = _e match { case mdE: MagicDrawUMLContinuation => mdE }
  implicit def umlMagicDrawUMLControlFlow( _e: UMLControlFlow[Uml] ) = _e match { case mdE: MagicDrawUMLControlFlow => mdE }
  implicit def umlMagicDrawUMLControlNode( _e: UMLControlNode[Uml] ) = _e match { case mdE: MagicDrawUMLControlNode => mdE }
  implicit def umlMagicDrawUMLCreateLinkAction( _e: UMLCreateLinkAction[Uml] ) = _e match { case mdE: MagicDrawUMLCreateLinkAction => mdE }
  implicit def umlMagicDrawUMLCreateLinkObjectAction( _e: UMLCreateLinkObjectAction[Uml] ) = _e match { case mdE: MagicDrawUMLCreateLinkObjectAction => mdE }
  implicit def umlMagicDrawUMLCreateObjectAction( _e: UMLCreateObjectAction[Uml] ) = _e match { case mdE: MagicDrawUMLCreateObjectAction => mdE }
  implicit def umlMagicDrawUMLDataStoreNode( _e: UMLDataStoreNode[Uml] ) = _e match { case mdE: MagicDrawUMLDataStoreNode => mdE }
  implicit def umlMagicDrawUMLDataType( _e: UMLDataType[Uml] ) = _e match { case mdE: MagicDrawUMLDataType => mdE }
  implicit def umlMagicDrawUMLDecisionNode( _e: UMLDecisionNode[Uml] ) = _e match { case mdE: MagicDrawUMLDecisionNode => mdE }
  implicit def umlMagicDrawUMLDependency( _e: UMLDependency[Uml] ) = _e match { case mdE: MagicDrawUMLDependency => mdE }
  implicit def umlMagicDrawUMLDeployedArtifact( _e: UMLDeployedArtifact[Uml] ) = _e match { case mdE: MagicDrawUMLDeployedArtifact => mdE }
  implicit def umlMagicDrawUMLDeployment( _e: UMLDeployment[Uml] ) = _e match { case mdE: MagicDrawUMLDeployment => mdE }
  implicit def umlMagicDrawUMLDeploymentSpecification( _e: UMLDeploymentSpecification[Uml] ) = _e match { case mdE: MagicDrawUMLDeploymentSpecification => mdE }
  implicit def umlMagicDrawUMLDeploymentTarget( _e: UMLDeploymentTarget[Uml] ) = _e match { case mdE: MagicDrawUMLDeploymentTarget => mdE }
  implicit def umlMagicDrawUMLDestroyLinkAction( _e: UMLDestroyLinkAction[Uml] ) = _e match { case mdE: MagicDrawUMLDestroyLinkAction => mdE }
  implicit def umlMagicDrawUMLDestroyObjectAction( _e: UMLDestroyObjectAction[Uml] ) = _e match { case mdE: MagicDrawUMLDestroyObjectAction => mdE }
  implicit def umlMagicDrawUMLDestructionOccurrenceSpecification( _e: UMLDestructionOccurrenceSpecification[Uml] ) = _e match { case mdE: MagicDrawUMLDestructionOccurrenceSpecification => mdE }
  implicit def umlMagicDrawUMLDevice( _e: UMLDevice[Uml] ) = _e match { case mdE: MagicDrawUMLDevice => mdE }
  implicit def umlMagicDrawUMLDirectedRelationship( _e: UMLDirectedRelationship[Uml] ) = _e match { case mdE: MagicDrawUMLDirectedRelationship => mdE }
  implicit def umlMagicDrawUMLDuration( _e: UMLDuration[Uml] ) = _e match { case mdE: MagicDrawUMLDuration => mdE }
  implicit def umlMagicDrawUMLDurationConstraint( _e: UMLDurationConstraint[Uml] ) = _e match { case mdE: MagicDrawUMLDurationConstraint => mdE }
  implicit def umlMagicDrawUMLDurationInterval( _e: UMLDurationInterval[Uml] ) = _e match { case mdE: MagicDrawUMLDurationInterval => mdE }
  implicit def umlMagicDrawUMLDurationObservation( _e: UMLDurationObservation[Uml] ) = _e match { case mdE: MagicDrawUMLDurationObservation => mdE }
  implicit def umlMagicDrawUMLElement( _e: UMLElement[Uml] ) = _e match { case mdE: MagicDrawUMLElement => mdE }
  implicit def umlMagicDrawUMLElementImport( _e: UMLElementImport[Uml] ) = _e match { case mdE: MagicDrawUMLElementImport => mdE }
  implicit def umlMagicDrawUMLEncapsulatedClassifier( _e: UMLEncapsulatedClassifier[Uml] ) = _e match { case mdE: MagicDrawUMLEncapsulatedClassifier => mdE }
  implicit def umlMagicDrawUMLEnumeration( _e: UMLEnumeration[Uml] ) = _e match { case mdE: MagicDrawUMLEnumeration => mdE }
  implicit def umlMagicDrawUMLEnumerationLiteral( _e: UMLEnumerationLiteral[Uml] ) = _e match { case mdE: MagicDrawUMLEnumerationLiteral => mdE }
  implicit def umlMagicDrawUMLEvent( _e: UMLEvent[Uml] ) = _e match { case mdE: MagicDrawUMLEvent => mdE }
  implicit def umlMagicDrawUMLExceptionHandler( _e: UMLExceptionHandler[Uml] ) = _e match { case mdE: MagicDrawUMLExceptionHandler => mdE }
  implicit def umlMagicDrawUMLExecutableNode( _e: UMLExecutableNode[Uml] ) = _e match { case mdE: MagicDrawUMLExecutableNode => mdE }
  implicit def umlMagicDrawUMLExecutionEnvironment( _e: UMLExecutionEnvironment[Uml] ) = _e match { case mdE: MagicDrawUMLExecutionEnvironment => mdE }
  implicit def umlMagicDrawUMLExecutionOccurrenceSpecification( _e: UMLExecutionOccurrenceSpecification[Uml] ) = _e match { case mdE: MagicDrawUMLExecutionOccurrenceSpecification => mdE }
  implicit def umlMagicDrawUMLExecutionSpecification( _e: UMLExecutionSpecification[Uml] ) = _e match { case mdE: MagicDrawUMLExecutionSpecification => mdE }
  implicit def umlMagicDrawUMLExpansionNode( _e: UMLExpansionNode[Uml] ) = _e match { case mdE: MagicDrawUMLExpansionNode => mdE }
  implicit def umlMagicDrawUMLExpansionRegion( _e: UMLExpansionRegion[Uml] ) = _e match { case mdE: MagicDrawUMLExpansionRegion => mdE }
  implicit def umlMagicDrawUMLExpression( _e: UMLExpression[Uml] ) = _e match { case mdE: MagicDrawUMLExpression => mdE }
  implicit def umlMagicDrawUMLExtend( _e: UMLExtend[Uml] ) = _e match { case mdE: MagicDrawUMLExtend => mdE }
  implicit def umlMagicDrawUMLExtension( _e: UMLExtension[Uml] ) = _e match { case mdE: MagicDrawUMLExtension => mdE }
  implicit def umlMagicDrawUMLExtensionEnd( _e: UMLExtensionEnd[Uml] ) = _e match { case mdE: MagicDrawUMLExtensionEnd => mdE }
  implicit def umlMagicDrawUMLExtensionPoint( _e: UMLExtensionPoint[Uml] ) = _e match { case mdE: MagicDrawUMLExtensionPoint => mdE }
  implicit def umlMagicDrawUMLFeature( _e: UMLFeature[Uml] ) = _e match { case mdE: MagicDrawUMLFeature => mdE }
  implicit def umlMagicDrawUMLFinalNode( _e: UMLFinalNode[Uml] ) = _e match { case mdE: MagicDrawUMLFinalNode => mdE }
  implicit def umlMagicDrawUMLFinalState( _e: UMLFinalState[Uml] ) = _e match { case mdE: MagicDrawUMLFinalState => mdE }
  implicit def umlMagicDrawUMLFlowFinalNode( _e: UMLFlowFinalNode[Uml] ) = _e match { case mdE: MagicDrawUMLFlowFinalNode => mdE }
  implicit def umlMagicDrawUMLForkNode( _e: UMLForkNode[Uml] ) = _e match { case mdE: MagicDrawUMLForkNode => mdE }
  implicit def umlMagicDrawUMLFunctionBehavior( _e: UMLFunctionBehavior[Uml] ) = _e match { case mdE: MagicDrawUMLFunctionBehavior => mdE }
  implicit def umlMagicDrawUMLGate( _e: UMLGate[Uml] ) = _e match { case mdE: MagicDrawUMLGate => mdE }
  implicit def umlMagicDrawUMLGeneralOrdering( _e: UMLGeneralOrdering[Uml] ) = _e match { case mdE: MagicDrawUMLGeneralOrdering => mdE }
  implicit def umlMagicDrawUMLGeneralization( _e: UMLGeneralization[Uml] ) = _e match { case mdE: MagicDrawUMLGeneralization => mdE }
  implicit def umlMagicDrawUMLGeneralizationSet( _e: UMLGeneralizationSet[Uml] ) = _e match { case mdE: MagicDrawUMLGeneralizationSet => mdE }
  implicit def umlMagicDrawUMLImage( _e: UMLImage[Uml] ) = _e match { case mdE: MagicDrawUMLImage => mdE }
  implicit def umlMagicDrawUMLInclude( _e: UMLInclude[Uml] ) = _e match { case mdE: MagicDrawUMLInclude => mdE }
  implicit def umlMagicDrawUMLInformationFlow( _e: UMLInformationFlow[Uml] ) = _e match { case mdE: MagicDrawUMLInformationFlow => mdE }
  implicit def umlMagicDrawUMLInformationItem( _e: UMLInformationItem[Uml] ) = _e match { case mdE: MagicDrawUMLInformationItem => mdE }
  implicit def umlMagicDrawUMLInitialNode( _e: UMLInitialNode[Uml] ) = _e match { case mdE: MagicDrawUMLInitialNode => mdE }
  implicit def umlMagicDrawUMLInputPin( _e: UMLInputPin[Uml] ) = _e match { case mdE: MagicDrawUMLInputPin => mdE }
  implicit def umlMagicDrawUMLInstanceSpecification( _e: UMLInstanceSpecification[Uml] ) = _e match { case mdE: MagicDrawUMLInstanceSpecification => mdE }
  implicit def umlMagicDrawUMLInstanceValue( _e: UMLInstanceValue[Uml] ) = _e match { case mdE: MagicDrawUMLInstanceValue => mdE }
  implicit def umlMagicDrawUMLInteraction( _e: UMLInteraction[Uml] ) = _e match { case mdE: MagicDrawUMLInteraction => mdE }
  implicit def umlMagicDrawUMLInteractionConstraint( _e: UMLInteractionConstraint[Uml] ) = _e match { case mdE: MagicDrawUMLInteractionConstraint => mdE }
  implicit def umlMagicDrawUMLInteractionFragment( _e: UMLInteractionFragment[Uml] ) = _e match { case mdE: MagicDrawUMLInteractionFragment => mdE }
  implicit def umlMagicDrawUMLInteractionOperand( _e: UMLInteractionOperand[Uml] ) = _e match { case mdE: MagicDrawUMLInteractionOperand => mdE }
  implicit def umlMagicDrawUMLInteractionUse( _e: UMLInteractionUse[Uml] ) = _e match { case mdE: MagicDrawUMLInteractionUse => mdE }
  implicit def umlMagicDrawUMLInterface( _e: UMLInterface[Uml] ) = _e match { case mdE: MagicDrawUMLInterface => mdE }
  implicit def umlMagicDrawUMLInterfaceRealization( _e: UMLInterfaceRealization[Uml] ) = _e match { case mdE: MagicDrawUMLInterfaceRealization => mdE }
  implicit def umlMagicDrawUMLInterruptibleActivityRegion( _e: UMLInterruptibleActivityRegion[Uml] ) = _e match { case mdE: MagicDrawUMLInterruptibleActivityRegion => mdE }
  implicit def umlMagicDrawUMLInterval( _e: UMLInterval[Uml] ) = _e match { case mdE: MagicDrawUMLInterval => mdE }
  implicit def umlMagicDrawUMLIntervalConstraint( _e: UMLIntervalConstraint[Uml] ) = _e match { case mdE: MagicDrawUMLIntervalConstraint => mdE }
  implicit def umlMagicDrawUMLInvocationAction( _e: UMLInvocationAction[Uml] ) = _e match { case mdE: MagicDrawUMLInvocationAction => mdE }
  implicit def umlMagicDrawUMLJoinNode( _e: UMLJoinNode[Uml] ) = _e match { case mdE: MagicDrawUMLJoinNode => mdE }
  implicit def umlMagicDrawUMLLifeline( _e: UMLLifeline[Uml] ) = _e match { case mdE: MagicDrawUMLLifeline => mdE }
  implicit def umlMagicDrawUMLLinkAction( _e: UMLLinkAction[Uml] ) = _e match { case mdE: MagicDrawUMLLinkAction => mdE }
  implicit def umlMagicDrawUMLLinkEndCreationData( _e: UMLLinkEndCreationData[Uml] ) = _e match { case mdE: MagicDrawUMLLinkEndCreationData => mdE }
  implicit def umlMagicDrawUMLLinkEndData( _e: UMLLinkEndData[Uml] ) = _e match { case mdE: MagicDrawUMLLinkEndData => mdE }
  implicit def umlMagicDrawUMLLinkEndDestructionData( _e: UMLLinkEndDestructionData[Uml] ) = _e match { case mdE: MagicDrawUMLLinkEndDestructionData => mdE }
  implicit def umlMagicDrawUMLLiteralBoolean( _e: UMLLiteralBoolean[Uml] ) = _e match { case mdE: MagicDrawUMLLiteralBoolean => mdE }
  implicit def umlMagicDrawUMLLiteralInteger( _e: UMLLiteralInteger[Uml] ) = _e match { case mdE: MagicDrawUMLLiteralInteger => mdE }
  implicit def umlMagicDrawUMLLiteralNull( _e: UMLLiteralNull[Uml] ) = _e match { case mdE: MagicDrawUMLLiteralNull => mdE }
  implicit def umlMagicDrawUMLLiteralReal( _e: UMLLiteralReal[Uml] ) = _e match { case mdE: MagicDrawUMLLiteralReal => mdE }
  implicit def umlMagicDrawUMLLiteralSpecification( _e: UMLLiteralSpecification[Uml] ) = _e match { case mdE: MagicDrawUMLLiteralSpecification => mdE }
  implicit def umlMagicDrawUMLLiteralString( _e: UMLLiteralString[Uml] ) = _e match { case mdE: MagicDrawUMLLiteralString => mdE }
  implicit def umlMagicDrawUMLLiteralUnlimitedNatural( _e: UMLLiteralUnlimitedNatural[Uml] ) = _e match { case mdE: MagicDrawUMLLiteralUnlimitedNatural => mdE }
  implicit def umlMagicDrawUMLLoopNode( _e: UMLLoopNode[Uml] ) = _e match { case mdE: MagicDrawUMLLoopNode => mdE }
  implicit def umlMagicDrawUMLManifestation( _e: UMLManifestation[Uml] ) = _e match { case mdE: MagicDrawUMLManifestation => mdE }
  implicit def umlMagicDrawUMLMergeNode( _e: UMLMergeNode[Uml] ) = _e match { case mdE: MagicDrawUMLMergeNode => mdE }
  implicit def umlMagicDrawUMLMessage( _e: UMLMessage[Uml] ) = _e match { case mdE: MagicDrawUMLMessage => mdE }
  implicit def umlMagicDrawUMLMessageEnd( _e: UMLMessageEnd[Uml] ) = _e match { case mdE: MagicDrawUMLMessageEnd => mdE }
  implicit def umlMagicDrawUMLMessageEvent( _e: UMLMessageEvent[Uml] ) = _e match { case mdE: MagicDrawUMLMessageEvent => mdE }
  implicit def umlMagicDrawUMLMessageOccurrenceSpecification( _e: UMLMessageOccurrenceSpecification[Uml] ) = _e match { case mdE: MagicDrawUMLMessageOccurrenceSpecification => mdE }
  implicit def umlMagicDrawUMLModel( _e: UMLModel[Uml] ) = _e match { case mdE: MagicDrawUMLModel => mdE }
  implicit def umlMagicDrawUMLMultiplicityElement( _e: UMLMultiplicityElement[Uml] ) = _e match { case mdE: MagicDrawUMLMultiplicityElement => mdE }
  implicit def umlMagicDrawUMLNamedElement( _e: UMLNamedElement[Uml] ) = _e match { case mdE: MagicDrawUMLNamedElement => mdE }
  implicit def umlMagicDrawUMLNamespace( _e: UMLNamespace[Uml] ) = _e match { case mdE: MagicDrawUMLNamespace => mdE }
  implicit def umlMagicDrawUMLNode( _e: UMLNode[Uml] ) = _e match { case mdE: MagicDrawUMLNode => mdE }
  implicit def umlMagicDrawUMLObjectFlow( _e: UMLObjectFlow[Uml] ) = _e match { case mdE: MagicDrawUMLObjectFlow => mdE }
  implicit def umlMagicDrawUMLObjectNode( _e: UMLObjectNode[Uml] ) = _e match { case mdE: MagicDrawUMLObjectNode => mdE }
  implicit def umlMagicDrawUMLObservation( _e: UMLObservation[Uml] ) = _e match { case mdE: MagicDrawUMLObservation => mdE }
  implicit def umlMagicDrawUMLOccurrenceSpecification( _e: UMLOccurrenceSpecification[Uml] ) = _e match { case mdE: MagicDrawUMLOccurrenceSpecification => mdE }
  implicit def umlMagicDrawUMLOpaqueAction( _e: UMLOpaqueAction[Uml] ) = _e match { case mdE: MagicDrawUMLOpaqueAction => mdE }
  implicit def umlMagicDrawUMLOpaqueBehavior( _e: UMLOpaqueBehavior[Uml] ) = _e match { case mdE: MagicDrawUMLOpaqueBehavior => mdE }
  implicit def umlMagicDrawUMLOpaqueExpression( _e: UMLOpaqueExpression[Uml] ) = _e match { case mdE: MagicDrawUMLOpaqueExpression => mdE }
  implicit def umlMagicDrawUMLOperation( _e: UMLOperation[Uml] ) = _e match { case mdE: MagicDrawUMLOperation => mdE }
  implicit def umlMagicDrawUMLOperationTemplateParameter( _e: UMLOperationTemplateParameter[Uml] ) = _e match { case mdE: MagicDrawUMLOperationTemplateParameter => mdE }
  implicit def umlMagicDrawUMLOutputPin( _e: UMLOutputPin[Uml] ) = _e match { case mdE: MagicDrawUMLOutputPin => mdE }
  implicit def umlMagicDrawUMLPackage( _e: UMLPackage[Uml] ) = _e match { case mdE: MagicDrawUMLPackage => mdE }
  implicit def umlMagicDrawUMLPackageImport( _e: UMLPackageImport[Uml] ) = _e match { case mdE: MagicDrawUMLPackageImport => mdE }
  implicit def umlMagicDrawUMLPackageMerge( _e: UMLPackageMerge[Uml] ) = _e match { case mdE: MagicDrawUMLPackageMerge => mdE }
  implicit def umlMagicDrawUMLPackageableElement( _e: UMLPackageableElement[Uml] ) = _e match { case mdE: MagicDrawUMLPackageableElement => mdE }
  implicit def umlMagicDrawUMLParameter( _e: UMLParameter[Uml] ) = _e match { case mdE: MagicDrawUMLParameter => mdE }
  implicit def umlMagicDrawUMLParameterSet( _e: UMLParameterSet[Uml] ) = _e match { case mdE: MagicDrawUMLParameterSet => mdE }
  implicit def umlMagicDrawUMLParameterableElement( _e: UMLParameterableElement[Uml] ) = _e match { case mdE: MagicDrawUMLParameterableElement => mdE }
  implicit def umlMagicDrawUMLPartDecomposition( _e: UMLPartDecomposition[Uml] ) = _e match { case mdE: MagicDrawUMLPartDecomposition => mdE }
  implicit def umlMagicDrawUMLPin( _e: UMLPin[Uml] ) = _e match { case mdE: MagicDrawUMLPin => mdE }
  implicit def umlMagicDrawUMLPort( _e: UMLPort[Uml] ) = _e match { case mdE: MagicDrawUMLPort => mdE }
  implicit def umlMagicDrawUMLPrimitiveType( _e: UMLPrimitiveType[Uml] ) = _e match { case mdE: MagicDrawUMLPrimitiveType => mdE }
  implicit def umlMagicDrawUMLProfile( _e: UMLProfile[Uml] ) = _e match { case mdE: MagicDrawUMLProfile => mdE }
  implicit def umlMagicDrawUMLProfileApplication( _e: UMLProfileApplication[Uml] ) = _e match { case mdE: MagicDrawUMLProfileApplication => mdE }
  implicit def umlMagicDrawUMLProperty( _e: UMLProperty[Uml] ) = _e match { case mdE: MagicDrawUMLProperty => mdE }
  implicit def umlMagicDrawUMLProtocolConformance( _e: UMLProtocolConformance[Uml] ) = _e match { case mdE: MagicDrawUMLProtocolConformance => mdE }
  implicit def umlMagicDrawUMLProtocolStateMachine( _e: UMLProtocolStateMachine[Uml] ) = _e match { case mdE: MagicDrawUMLProtocolStateMachine => mdE }
  implicit def umlMagicDrawUMLProtocolTransition( _e: UMLProtocolTransition[Uml] ) = _e match { case mdE: MagicDrawUMLProtocolTransition => mdE }
  implicit def umlMagicDrawUMLPseudostate( _e: UMLPseudostate[Uml] ) = _e match { case mdE: MagicDrawUMLPseudostate => mdE }
  implicit def umlMagicDrawUMLQualifierValue( _e: UMLQualifierValue[Uml] ) = _e match { case mdE: MagicDrawUMLQualifierValue => mdE }
  implicit def umlMagicDrawUMLRaiseExceptionAction( _e: UMLRaiseExceptionAction[Uml] ) = _e match { case mdE: MagicDrawUMLRaiseExceptionAction => mdE }
  implicit def umlMagicDrawUMLReadExtentAction( _e: UMLReadExtentAction[Uml] ) = _e match { case mdE: MagicDrawUMLReadExtentAction => mdE }
  implicit def umlMagicDrawUMLReadIsClassifiedObjectAction( _e: UMLReadIsClassifiedObjectAction[Uml] ) = _e match { case mdE: MagicDrawUMLReadIsClassifiedObjectAction => mdE }
  implicit def umlMagicDrawUMLReadLinkAction( _e: UMLReadLinkAction[Uml] ) = _e match { case mdE: MagicDrawUMLReadLinkAction => mdE }
  implicit def umlMagicDrawUMLReadLinkObjectEndAction( _e: UMLReadLinkObjectEndAction[Uml] ) = _e match { case mdE: MagicDrawUMLReadLinkObjectEndAction => mdE }
  implicit def umlMagicDrawUMLReadLinkObjectEndQualifierAction( _e: UMLReadLinkObjectEndQualifierAction[Uml] ) = _e match { case mdE: MagicDrawUMLReadLinkObjectEndQualifierAction => mdE }
  implicit def umlMagicDrawUMLReadSelfAction( _e: UMLReadSelfAction[Uml] ) = _e match { case mdE: MagicDrawUMLReadSelfAction => mdE }
  implicit def umlMagicDrawUMLReadStructuralFeatureAction( _e: UMLReadStructuralFeatureAction[Uml] ) = _e match { case mdE: MagicDrawUMLReadStructuralFeatureAction => mdE }
  implicit def umlMagicDrawUMLReadVariableAction( _e: UMLReadVariableAction[Uml] ) = _e match { case mdE: MagicDrawUMLReadVariableAction => mdE }
  implicit def umlMagicDrawUMLRealization( _e: UMLRealization[Uml] ) = _e match { case mdE: MagicDrawUMLRealization => mdE }
  implicit def umlMagicDrawUMLReception( _e: UMLReception[Uml] ) = _e match { case mdE: MagicDrawUMLReception => mdE }
  implicit def umlMagicDrawUMLReclassifyObjectAction( _e: UMLReclassifyObjectAction[Uml] ) = _e match { case mdE: MagicDrawUMLReclassifyObjectAction => mdE }
  implicit def umlMagicDrawUMLRedefinableElement( _e: UMLRedefinableElement[Uml] ) = _e match { case mdE: MagicDrawUMLRedefinableElement => mdE }
  implicit def umlMagicDrawUMLRedefinableTemplateSignature( _e: UMLRedefinableTemplateSignature[Uml] ) = _e match { case mdE: MagicDrawUMLRedefinableTemplateSignature => mdE }
  implicit def umlMagicDrawUMLReduceAction( _e: UMLReduceAction[Uml] ) = _e match { case mdE: MagicDrawUMLReduceAction => mdE }
  implicit def umlMagicDrawUMLRegion( _e: UMLRegion[Uml] ) = _e match { case mdE: MagicDrawUMLRegion => mdE }
  implicit def umlMagicDrawUMLRelationship( _e: UMLRelationship[Uml] ) = _e match { case mdE: MagicDrawUMLRelationship => mdE }
  implicit def umlMagicDrawUMLRemoveStructuralFeatureValueAction( _e: UMLRemoveStructuralFeatureValueAction[Uml] ) = _e match { case mdE: MagicDrawUMLRemoveStructuralFeatureValueAction => mdE }
  implicit def umlMagicDrawUMLRemoveVariableValueAction( _e: UMLRemoveVariableValueAction[Uml] ) = _e match { case mdE: MagicDrawUMLRemoveVariableValueAction => mdE }
  implicit def umlMagicDrawUMLReplyAction( _e: UMLReplyAction[Uml] ) = _e match { case mdE: MagicDrawUMLReplyAction => mdE }
  implicit def umlMagicDrawUMLSendObjectAction( _e: UMLSendObjectAction[Uml] ) = _e match { case mdE: MagicDrawUMLSendObjectAction => mdE }
  implicit def umlMagicDrawUMLSendSignalAction( _e: UMLSendSignalAction[Uml] ) = _e match { case mdE: MagicDrawUMLSendSignalAction => mdE }
  implicit def umlMagicDrawUMLSequenceNode( _e: UMLSequenceNode[Uml] ) = _e match { case mdE: MagicDrawUMLSequenceNode => mdE }
  implicit def umlMagicDrawUMLSignal( _e: UMLSignal[Uml] ) = _e match { case mdE: MagicDrawUMLSignal => mdE }
  implicit def umlMagicDrawUMLSignalEvent( _e: UMLSignalEvent[Uml] ) = _e match { case mdE: MagicDrawUMLSignalEvent => mdE }
  implicit def umlMagicDrawUMLSlot( _e: UMLSlot[Uml] ) = _e match { case mdE: MagicDrawUMLSlot => mdE }
  implicit def umlMagicDrawUMLStartClassifierBehaviorAction( _e: UMLStartClassifierBehaviorAction[Uml] ) = _e match { case mdE: MagicDrawUMLStartClassifierBehaviorAction => mdE }
  implicit def umlMagicDrawUMLStartObjectBehaviorAction( _e: UMLStartObjectBehaviorAction[Uml] ) = _e match { case mdE: MagicDrawUMLStartObjectBehaviorAction => mdE }
  implicit def umlMagicDrawUMLState( _e: UMLState[Uml] ) = _e match { case mdE: MagicDrawUMLState => mdE }
  implicit def umlMagicDrawUMLStateInvariant( _e: UMLStateInvariant[Uml] ) = _e match { case mdE: MagicDrawUMLStateInvariant => mdE }
  implicit def umlMagicDrawUMLStateMachine( _e: UMLStateMachine[Uml] ) = _e match { case mdE: MagicDrawUMLStateMachine => mdE }
  implicit def umlMagicDrawUMLStereotype( _e: UMLStereotype[Uml] ) = _e match { case mdE: MagicDrawUMLStereotype => mdE }
  implicit def umlMagicDrawUMLStringExpression( _e: UMLStringExpression[Uml] ) = _e match { case mdE: MagicDrawUMLStringExpression => mdE }
  implicit def umlMagicDrawUMLStructuralFeature( _e: UMLStructuralFeature[Uml] ) = _e match { case mdE: MagicDrawUMLStructuralFeature => mdE }
  implicit def umlMagicDrawUMLStructuralFeatureAction( _e: UMLStructuralFeatureAction[Uml] ) = _e match { case mdE: MagicDrawUMLStructuralFeatureAction => mdE }
  implicit def umlMagicDrawUMLStructuredActivityNode( _e: UMLStructuredActivityNode[Uml] ) = _e match { case mdE: MagicDrawUMLStructuredActivityNode => mdE }
  implicit def umlMagicDrawUMLStructuredClassifier( _e: UMLStructuredClassifier[Uml] ) = _e match { case mdE: MagicDrawUMLStructuredClassifier => mdE }
  implicit def umlMagicDrawUMLSubstitution( _e: UMLSubstitution[Uml] ) = _e match { case mdE: MagicDrawUMLSubstitution => mdE }
  implicit def umlMagicDrawUMLTemplateBinding( _e: UMLTemplateBinding[Uml] ) = _e match { case mdE: MagicDrawUMLTemplateBinding => mdE }
  implicit def umlMagicDrawUMLTemplateParameter( _e: UMLTemplateParameter[Uml] ) = _e match { case mdE: MagicDrawUMLTemplateParameter => mdE }
  implicit def umlMagicDrawUMLTemplateParameterSubstitution( _e: UMLTemplateParameterSubstitution[Uml] ) = _e match { case mdE: MagicDrawUMLTemplateParameterSubstitution => mdE }
  implicit def umlMagicDrawUMLTemplateSignature( _e: UMLTemplateSignature[Uml] ) = _e match { case mdE: MagicDrawUMLTemplateSignature => mdE }
  implicit def umlMagicDrawUMLTemplateableElement( _e: UMLTemplateableElement[Uml] ) = _e match { case mdE: MagicDrawUMLTemplateableElement => mdE }
  implicit def umlMagicDrawUMLTestIdentityAction( _e: UMLTestIdentityAction[Uml] ) = _e match { case mdE: MagicDrawUMLTestIdentityAction => mdE }
  implicit def umlMagicDrawUMLTimeConstraint( _e: UMLTimeConstraint[Uml] ) = _e match { case mdE: MagicDrawUMLTimeConstraint => mdE }
  implicit def umlMagicDrawUMLTimeEvent( _e: UMLTimeEvent[Uml] ) = _e match { case mdE: MagicDrawUMLTimeEvent => mdE }
  implicit def umlMagicDrawUMLTimeExpression( _e: UMLTimeExpression[Uml] ) = _e match { case mdE: MagicDrawUMLTimeExpression => mdE }
  implicit def umlMagicDrawUMLTimeInterval( _e: UMLTimeInterval[Uml] ) = _e match { case mdE: MagicDrawUMLTimeInterval => mdE }
  implicit def umlMagicDrawUMLTimeObservation( _e: UMLTimeObservation[Uml] ) = _e match { case mdE: MagicDrawUMLTimeObservation => mdE }
  implicit def umlMagicDrawUMLTransition( _e: UMLTransition[Uml] ) = _e match { case mdE: MagicDrawUMLTransition => mdE }
  implicit def umlMagicDrawUMLTrigger( _e: UMLTrigger[Uml] ) = _e match { case mdE: MagicDrawUMLTrigger => mdE }
  implicit def umlMagicDrawUMLType( _e: UMLType[Uml] ) = _e match { case mdE: MagicDrawUMLType => mdE }
  implicit def umlMagicDrawUMLTypedElement( _e: UMLTypedElement[Uml] ) = _e match { case mdE: MagicDrawUMLTypedElement => mdE }
  implicit def umlMagicDrawUMLUnmarshallAction( _e: UMLUnmarshallAction[Uml] ) = _e match { case mdE: MagicDrawUMLUnmarshallAction => mdE }
  implicit def umlMagicDrawUMLUsage( _e: UMLUsage[Uml] ) = _e match { case mdE: MagicDrawUMLUsage => mdE }
  implicit def umlMagicDrawUMLUseCase( _e: UMLUseCase[Uml] ) = _e match { case mdE: MagicDrawUMLUseCase => mdE }
  implicit def umlMagicDrawUMLValuePin( _e: UMLValuePin[Uml] ) = _e match { case mdE: MagicDrawUMLValuePin => mdE }
  implicit def umlMagicDrawUMLValueSpecification( _e: UMLValueSpecification[Uml] ) = _e match { case mdE: MagicDrawUMLValueSpecification => mdE }
  implicit def umlMagicDrawUMLValueSpecificationAction( _e: UMLValueSpecificationAction[Uml] ) = _e match { case mdE: MagicDrawUMLValueSpecificationAction => mdE }
  implicit def umlMagicDrawUMLVariable( _e: UMLVariable[Uml] ) = _e match { case mdE: MagicDrawUMLVariable => mdE }
  implicit def umlMagicDrawUMLVariableAction( _e: UMLVariableAction[Uml] ) = _e match { case mdE: MagicDrawUMLVariableAction => mdE }
  implicit def umlMagicDrawUMLVertex( _e: UMLVertex[Uml] ) = _e match { case mdE: MagicDrawUMLVertex => mdE }
  implicit def umlMagicDrawUMLWriteLinkAction( _e: UMLWriteLinkAction[Uml] ) = _e match { case mdE: MagicDrawUMLWriteLinkAction => mdE }
  implicit def umlMagicDrawUMLWriteStructuralFeatureAction( _e: UMLWriteStructuralFeatureAction[Uml] ) = _e match { case mdE: MagicDrawUMLWriteStructuralFeatureAction => mdE }
  implicit def umlMagicDrawUMLWriteVariableAction( _e: UMLWriteVariableAction[Uml] ) = _e match { case mdE: MagicDrawUMLWriteVariableAction => mdE }


  // -------------

  def makeMDIllegalArgumentExceptionValidation(
    p: Project,
    validationMessage: String,
    elementMessages: Map[Element, ( String, List[NMAction] )],
    validationSuiteQName: String, validationConstraintQName: String ): Try[Option[MagicDrawValidationDataResults]] =
    MagicDrawValidationDataResults.getMDValidationProfileAndConstraint( p, validationSuiteQName, validationConstraintQName ) match {
      case None => Failure( new IllegalArgumentException( s"Failed to find MD's Validation Profile '${validationSuiteQName}' & Constraint '${validationConstraintQName}'" ) )
      case Some( ( vSuite, c ) ) =>
        val runData = new ValidationRunData( vSuite.suite, false, elementMessages.keys, vSuite.vsh.getRuleSeverityLevel( c ) )
        val results = elementMessages map {
          case ( element, ( message, actions ) ) =>
            val annotation = new Annotation( element, c, message, actions )
            actions foreach { action =>
              action match {
                case vaa: ValidationAnnotationAction => vaa.annotation = Some( annotation )
                case _                               => ()
              }
            }
            new RuleViolationResult( annotation, c )
        }

        Success( Some( MagicDrawValidationDataResults( validationMessage, runData, results, List[RunnableWithProgress]() ) ) )
    }

}