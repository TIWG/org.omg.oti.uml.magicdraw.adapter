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
package org.omg.oti.magicdraw.uml.write

import com.nomagic.magicdraw.openapi.uml.SessionManager
import com.nomagic.magicdraw.openapi.uml.ReadOnlyElementException

import org.omg.oti.magicdraw.uml.read._
import org.omg.oti.uml.read.api._
import org.omg.oti.uml.write.api.UMLUpdate

import scala.collection.JavaConversions._
import scala.util.{Failure, Success, Try}

case class MagicDrawUMLUpdate(override val ops: MagicDrawUMLUtil)
  extends UMLUpdate[MagicDrawUML] {

  val f = ops.project.getElementsFactory

  val sm = SessionManager.getInstance()

  def checkSession[M <: com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element](e: M): Try[M] =
    if (sm.isSessionCreated(ops.project))
      try {
        sm.checkReadOnly(e)
        Success(e)
      } catch {
        case t: ReadOnlyElementException =>
          Failure(t)
        case t: Throwable =>
          Failure(t)
      }
    else
      Failure(new IllegalArgumentException(
        s"Modifying a MagicDraw UML project requires a MagicDraw session for that project"))

  def checkElement[M <: com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element](e: M): Try[M] =
    if (e.isInvalid)
      Failure(new IllegalArgumentException(
        s"Invalid ${e.getHumanName} (ID=${e.getID})"
      ))
    else
      Success(e)

  /**
   * creates/deletes link instances of an association 'from' a metaclass <OTIM> 'to' <OTIN>
   * where the 'to' association end property is a composite unordered collection
   *
   * @param m The association 'from' object of type OTIM
   * @param mAdapter Maps an OTIM object to the MagicDraw-specific wrapper object of type MAdapt
   * @param ns The association 'to' collection of objects of type OTIN
   * @param nAdapter Maps an OTIN object to the MagicDraw-specific wrapper object of type NAdapt
   * @param mAdaptee Maps a MagicDraw-specific wrapper object of type MAdapt to the MagicDraw-specific adaptee of type M
   * @param m_n MagicDraw-specific method to compute the association-end 'to' unordered collection for a 'from' object
   * @param nAdaptee Maps a MagicDraw-specific wrapper object of type NAdapt to the MagicDraw-specific adaptee of type N
   * @tparam M The MagicDraw-specific type of the 'from' association end
   * @tparam OTIM The MagicDraw-specific OTI adapter for M
   * @tparam MAdapt The MagicDraw-specific OTI adaptee wrapper for M
   * @tparam N The MagicDraw-specific type of the 'to' association end
   * @tparam OTIN The MagicDraw-specific OTI adapter for N
   * @tparam NAdapt The MagicDraw-specific OTI adaptee wrapper for M
   * @return
   */
  def composesUnorderedLinks[
  M <: com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element,
  OTIM <: UMLElement[MagicDrawUML],
  MAdapt <: MagicDrawUMLElement,
  N <: com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element,
  OTIN <: UMLElement[MagicDrawUML],
  NAdapt <: MagicDrawUMLElement]
  (m: OTIM, mAdapter: (OTIM => MAdapt),
   ns: Set[OTIN], nAdapter: (OTIN => NAdapt),
   mAdaptee: (MAdapt => M),
   m_n: (M => java.util.Collection[N]),
   nAdaptee: (NAdapt => N)): Try[Unit] =
    checkSession(mAdaptee(mAdapter(m))) flatMap { _from =>
      ( Try[(M, Set[N])](Tuple2(_from, Set())) /: ns ) {
        case ( Failure(f), _ ) => Failure(f)
        case ( Success(Tuple2(f,vs)), e) =>
          checkSession(nAdaptee(nAdapter(e))) match {
            case Failure(f) => Failure(f)
            case Success(ve) => Success(Tuple2(f, vs + ve))
          }
      }
    } map { case (_from, _tos ) =>

      val _current = m_n(_from)

      val _toAdd = for {
        _add <- _tos
        if !_current.contains(_add)
      } yield _add

      val _toRemove = for {
        _rem <- _current
        if !_tos.contains(_rem)
      } yield _rem

      for {_rem <- _toRemove} m_n(_from).remove(_rem)

      for {_add <- _toAdd} m_n(_from).add(_add)

      Unit
    }

  /**
   * creates/deletes link instances of an association 'from' a metaclass <OTIM> 'to' <OTIN>
   * where the 'to' association end property is a composite ordered collection
   *
   * @param m The association 'from' object of type OTIM
   * @param mAdapter Maps an OTIM object to the MagicDraw-specific wrapper object of type MAdapt
   * @param ns The association 'to' collection of objects of type OTIN
   * @param nAdapter Maps an OTIN object to the MagicDraw-specific wrapper object of type NAdapt
   * @param mAdaptee Maps a MagicDraw-specific wrapper object of type MAdapt to the MagicDraw-specific adaptee of type M
   * @param m_n MagicDraw-specific method to compute the association-end 'to' ordered collection for a 'from' object
   * @param nAdaptee Maps a MagicDraw-specific wrapper object of type NAdapt to the MagicDraw-specific adaptee of type N
   * @tparam M The MagicDraw-specific type of the 'from' association end
   * @tparam OTIM The MagicDraw-specific OTI adapter for M
   * @tparam MAdapt The MagicDraw-specific OTI adaptee wrapper for M
   * @tparam N The MagicDraw-specific type of the 'to' association end
   * @tparam OTIN The MagicDraw-specific OTI adapter for N
   * @tparam NAdapt The MagicDraw-specific OTI adaptee wrapper for M
   * @return
   */
  def composesOrderedLinks[
  M <: com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element,
  OTIM <: UMLElement[MagicDrawUML],
  MAdapt <: MagicDrawUMLElement,
  N <: com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element,
  OTIN <: UMLElement[MagicDrawUML],
  NAdapt <: MagicDrawUMLElement]
  (m: OTIM, mAdapter: (OTIM => MAdapt),
   ns: Seq[OTIN], nAdapter: (OTIN => NAdapt),
   mAdaptee: (MAdapt => M),
   m_n: (M => java.util.Collection[N]),
   nAdaptee: (NAdapt => N)): Try[Unit] =
    checkSession(mAdaptee(mAdapter(m))) flatMap { _from =>
      ( Try[(M, Seq[N])](Tuple2(_from, Seq())) /: ns ) {
        case ( Failure(f), _ ) => Failure(f)
        case ( Success(Tuple2(f,vs)), e) =>
          checkSession(nAdaptee(nAdapter(e))) match {
            case Failure(f) => Failure(f)
            case Success(ve) => Success(Tuple2(f, vs :+ ve))
          }
      }
    } map { case (_from, _tos ) =>

      m_n(_from).clear()
      for {_add <- _tos} m_n(_from).add(_add)

      Unit
    }

  /**
   * creates/deletes link instances of an association 'from' a metaclass <OTIM> 'to' <OTIN>
   * where the 'to' association end property is a composite non-colllection type
   *
   * @param m The association 'from' object of type OTIM
   * @param mAdapter Maps an OTIM object to the MagicDraw-specific wrapper object of type MAdapt
   * @param n The association 'to' object of type OTIN
   * @param nAdapter Maps an OTIN object to the MagicDraw-specific wrapper object of type NAdapt
   * @param mAdaptee Maps a MagicDraw-specific wrapper object of type MAdapt to the MagicDraw-specific adaptee of type M
   * @param m_n MagicDraw-specific method to compute the association-end 'to' object for a 'from' object
   * @param nAdaptee Maps a MagicDraw-specific wrapper object of type NAdapt to the MagicDraw-specific adaptee of type N
   * @tparam M The MagicDraw-specific type of the 'from' association end
   * @tparam OTIM The MagicDraw-specific OTI adapter for M
   * @tparam MAdapt The MagicDraw-specific OTI adaptee wrapper for M
   * @tparam N The MagicDraw-specific type of the 'to' association end
   * @tparam OTIN The MagicDraw-specific OTI adapter for N
   * @tparam NAdapt The MagicDraw-specific OTI adaptee wrapper for M
   * @return
   */
  def composesOptionalLink[
  M <: com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element,
  OTIM <: UMLElement[MagicDrawUML],
  MAdapt <: MagicDrawUMLElement,
  N <: com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element,
  OTIN <: UMLElement[MagicDrawUML],
  NAdapt <: MagicDrawUMLElement]
  (m: OTIM, mAdapter: (OTIM => MAdapt),
   n: Option[OTIN], nAdapter: (OTIN => NAdapt),
   mAdaptee: (MAdapt => M),
   m_n: ((M,N) => Unit),
   nAdaptee: (NAdapt => N)): Try[Unit] =
    checkSession(mAdaptee(mAdapter(m))) flatMap { _from =>
      n match {
        case None =>
          m_n(_from, null.asInstanceOf[N])
          Success(Unit)
        case Some(e) =>
          checkSession(nAdaptee(nAdapter(e))) match {
            case Failure(f) =>
              Failure(f)
            case Success(ve) =>
              m_n(_from, ve)
              Success(Unit)
          }
      }
    }

  /**
   * creates/deletes link instances of an association 'from' a metaclass <OTIM> 'to' <OTIN>
   * where the 'to' association end property is a non-composite unordered collection
   *
   * @param m The association 'from' object of type OTIM
   * @param mAdapter Maps an OTIM object to the MagicDraw-specific wrapper object of type MAdapt
   * @param ns The association 'to' collection of objects of type OTIN
   * @param nAdapter Maps an OTIN object to the MagicDraw-specific wrapper object of type NAdapt
   * @param mAdaptee Maps a MagicDraw-specific wrapper object of type MAdapt to the MagicDraw-specific adaptee of type M
   * @param m_n MagicDraw-specific method to compute the association-end 'to' unordered collection for a 'from' object
   * @param nAdaptee Maps a MagicDraw-specific wrapper object of type NAdapt to the MagicDraw-specific adaptee of type N
   * @tparam M The MagicDraw-specific type of the 'from' association end
   * @tparam OTIM The MagicDraw-specific OTI adapter for M
   * @tparam MAdapt The MagicDraw-specific OTI adaptee wrapper for M
   * @tparam N The MagicDraw-specific type of the 'to' association end
   * @tparam OTIN The MagicDraw-specific OTI adapter for N
   * @tparam NAdapt The MagicDraw-specific OTI adaptee wrapper for M
   * @return
   */
  def referencesUnorderedLinks[
  M <: com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element,
  OTIM <: UMLElement[MagicDrawUML],
  MAdapt <: MagicDrawUMLElement,
  N <: com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element,
  OTIN <: UMLElement[MagicDrawUML],
  NAdapt <: MagicDrawUMLElement]
  (m: OTIM, mAdapter: (OTIM => MAdapt),
   ns: Set[OTIN], nAdapter: (OTIN => NAdapt),
   mAdaptee: (MAdapt => M),
   m_n: (M => java.util.Collection[N]),
   nAdaptee: (NAdapt => N)): Try[Unit] =
    checkSession(mAdaptee(mAdapter(m))) flatMap { _from =>
      ( Try[(M, Set[N])](Tuple2(_from, Set())) /: ns ) {
        case ( Failure(f), _ ) => Failure(f)
        case ( Success(Tuple2(f,vs)), e) =>
          checkElement(nAdaptee(nAdapter(e))) match {
            case Failure(f) => Failure(f)
            case Success(ve) => Success(Tuple2(f, vs + ve))
          }
      }
    } map { case (_from, _tos ) =>

      val _current = m_n(_from)

      val _toAdd = for {
        _add <- _tos
        if !_current.contains(_add)
      } yield _add

      val _toRemove = for {
        _rem <- _current
        if !_tos.contains(_rem)
      } yield _rem

      for {_rem <- _toRemove} m_n(_from).remove(_rem)

      for {_add <- _toAdd} m_n(_from).add(_add)

      Unit
    }

  /**
   * creates/deletes link instances of an association 'from' a metaclass <OTIM> 'to' <OTIN>
   * where the 'to' association end property is a non-composite ordered collection
   *
   * @param m The association 'from' object of type OTIM
   * @param mAdapter Maps an OTIM object to the MagicDraw-specific wrapper object of type MAdapt
   * @param ns The association 'to' collection of objects of type OTIN
   * @param nAdapter Maps an OTIN object to the MagicDraw-specific wrapper object of type NAdapt
   * @param mAdaptee Maps a MagicDraw-specific wrapper object of type MAdapt to the MagicDraw-specific adaptee of type M
   * @param m_n MagicDraw-specific method to compute the association-end 'to' ordered collection for a 'from' object
   * @param nAdaptee Maps a MagicDraw-specific wrapper object of type NAdapt to the MagicDraw-specific adaptee of type N
   * @tparam M The MagicDraw-specific type of the 'from' association end
   * @tparam OTIM The MagicDraw-specific OTI adapter for M
   * @tparam MAdapt The MagicDraw-specific OTI adaptee wrapper for M
   * @tparam N The MagicDraw-specific type of the 'to' association end
   * @tparam OTIN The MagicDraw-specific OTI adapter for N
   * @tparam NAdapt The MagicDraw-specific OTI adaptee wrapper for M
   * @return
   */
  def referencesOrderedLinks[
  M <: com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element,
  OTIM <: UMLElement[MagicDrawUML],
  MAdapt <: MagicDrawUMLElement,
  N <: com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element,
  OTIN <: UMLElement[MagicDrawUML],
  NAdapt <: MagicDrawUMLElement]
  (m: OTIM, mAdapter: (OTIM => MAdapt),
   ns: Seq[OTIN], nAdapter: (OTIN => NAdapt),
   mAdaptee: (MAdapt => M),
   m_n: (M => java.util.Collection[N]),
   nAdaptee: (NAdapt => N)): Try[Unit] =
    checkSession(mAdaptee(mAdapter(m))) flatMap { _from =>
      ( Try[(M, Seq[N])](Tuple2(_from, Seq())) /: ns ) {
        case ( Failure(f), _ ) => Failure(f)
        case ( Success(Tuple2(f,vs)), e) =>
          checkElement(nAdaptee(nAdapter(e))) match {
            case Failure(f) => Failure(f)
            case Success(ve) => Success(Tuple2(f, vs :+ ve))
          }
      }
    } map { case (_from, _tos ) =>

      m_n(_from).clear()
      for {_add <- _tos} m_n(_from).add(_add)

      Unit
    }

  /**
   * creates/deletes link instances of an association 'from' a metaclass <OTIM> 'to' <OTIN>
   * where the 'to' association end property is a non-composite non-colllection type
   *
   * @param m The association 'from' object of type OTIM
   * @param mAdapter Maps an OTIM object to the MagicDraw-specific wrapper object of type MAdapt
   * @param n The association 'to' object of type OTIN
   * @param nAdapter Maps an OTIN object to the MagicDraw-specific wrapper object of type NAdapt
   * @param mAdaptee Maps a MagicDraw-specific wrapper object of type MAdapt to the MagicDraw-specific adaptee of type M
   * @param m_n MagicDraw-specific method to compute the association-end 'to' object for a 'from' object
   * @param nAdaptee Maps a MagicDraw-specific wrapper object of type NAdapt to the MagicDraw-specific adaptee of type N
   * @tparam M The MagicDraw-specific type of the 'from' association end
   * @tparam OTIM The MagicDraw-specific OTI adapter for M
   * @tparam MAdapt The MagicDraw-specific OTI adaptee wrapper for M
   * @tparam N The MagicDraw-specific type of the 'to' association end
   * @tparam OTIN The MagicDraw-specific OTI adapter for N
   * @tparam NAdapt The MagicDraw-specific OTI adaptee wrapper for M
   * @return
   */
  def referencesOptionalLink[
  M <: com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element,
  OTIM <: UMLElement[MagicDrawUML],
  MAdapt <: MagicDrawUMLElement,
  N <: com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element,
  OTIN <: UMLElement[MagicDrawUML],
  NAdapt <: MagicDrawUMLElement]
  (m: OTIM, mAdapter: (OTIM => MAdapt),
   n: Option[OTIN], nAdapter: (OTIN => NAdapt),
   mAdaptee: (MAdapt => M),
   m_n: ((M,N) => Unit),
   nAdaptee: (NAdapt => N)): Try[Unit] =
    checkSession(mAdaptee(mAdapter(m))) flatMap { _from =>
      n match {
        case None =>
          m_n(_from, null.asInstanceOf[N])
          Success(Unit)
        case Some(e) =>
          checkElement(nAdaptee(nAdapter(e))) match {
            case Failure(f) =>
              Failure(f)
            case Success(ve) =>
              m_n(_from, ve)
              Success(Unit)
          }
      }
    }

  // Abstraction

  override def links_Abstraction_abstraction_compose_mapping_OpaqueExpression
  (from: UMLAbstraction[MagicDrawUML],
   to: Option[UMLOpaqueExpression[MagicDrawUML]]): Try[Unit] =
  composesOptionalLink(
    from, ops.umlMagicDrawUMLAbstraction,
    to, ops.umlMagicDrawUMLOpaqueExpression,
    (x:MagicDrawUMLAbstraction) => x.getMagicDrawAbstraction,
    (x:com.nomagic.uml2.ext.magicdraw.classes.mddependencies.Abstraction,
      y:com.nomagic.uml2.ext.magicdraw.classes.mdkernel.OpaqueExpression) => x.setMapping(y),
    (x:MagicDrawUMLOpaqueExpression) => x.getMagicDrawOpaqueExpression)

  // AcceptCallAction

  override def links_AcceptCallAction_acceptCallAction_compose_returnInformation_OutputPin
  (from: UMLAcceptCallAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
    composesOptionalLink(
      from, ops.umlMagicDrawUMLAcceptCallAction,
      to, ops.umlMagicDrawUMLOutputPin,
      (x:MagicDrawUMLAcceptCallAction) => x.getMagicDrawAcceptCallAction,
      (x:com.nomagic.uml2.ext.magicdraw.actions.mdcompleteactions.AcceptCallAction,
       y:com.nomagic.uml2.ext.magicdraw.actions.mdbasicactions.OutputPin) => x.setReturnInformation(y),
      (x:MagicDrawUMLOutputPin) => x.getMagicDrawOutputPin)

  // AcceptEventAction

  override def links_AcceptEventAction_acceptEventAction_compose_result_OutputPin
  (from: UMLAcceptEventAction[MagicDrawUML],
   to: Seq[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_AcceptEventAction_acceptEventAction_compose_trigger_Trigger
  (from: UMLAcceptEventAction[MagicDrawUML],
   to: Set[UMLTrigger[MagicDrawUML]]): Try[Unit] =
	???

  override def set_AcceptEventAction_isUnmarshall
  (e: UMLAcceptEventAction[MagicDrawUML], isUnmarshall: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLAcceptEventAction(e).getMagicDrawAcceptEventAction) map { _e =>
      _e.setUnmarshall(isUnmarshall)
      Unit
    }

  // Action

  override def links_Action_action_compose_localPostcondition_Constraint
  (from: UMLAction[MagicDrawUML],
   to: Set[UMLConstraint[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Action_action_compose_localPrecondition_Constraint
  (from: UMLAction[MagicDrawUML],
   to: Set[UMLConstraint[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Action_isLocallyReentrant
  (e: UMLAction[MagicDrawUML], isLocallyReentrant: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLAction(e).getMagicDrawAction) map { _e =>
      _e.setLocallyReentrant(isLocallyReentrant)
      Unit
    }

  // ActionExecutionSpecification

  override def links_ActionExecutionSpecification_actionExecutionSpecification_reference_action_Action
  (from: UMLActionExecutionSpecification[MagicDrawUML],
   to: Option[UMLAction[MagicDrawUML]]): Try[Unit] =
	???

  // ActionInputPin

  override def links_ActionInputPin_actionInputPin_compose_fromAction_Action
  (from: UMLActionInputPin[MagicDrawUML],
   to: Option[UMLAction[MagicDrawUML]]): Try[Unit] =
	???

  // Activity

  override def links_Activity_activity_compose_edge_ActivityEdge
  (from: UMLActivity[MagicDrawUML],
   to: Set[UMLActivityEdge[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Activity_inActivity_compose_group_ActivityGroup
  (from: UMLActivity[MagicDrawUML],
   to: Set[UMLActivityGroup[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Activity_activity_compose_node_ActivityNode
  (from: UMLActivity[MagicDrawUML],
   to: Set[UMLActivityNode[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Activity_activity_reference_partition_ActivityPartition
  (from: UMLActivity[MagicDrawUML],
   to: Set[UMLActivityPartition[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Activity_activity_compose_structuredNode_StructuredActivityNode
  (from: UMLActivity[MagicDrawUML],
   to: Set[UMLStructuredActivityNode[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Activity_activityScope_compose_variable_Variable
  (from: UMLActivity[MagicDrawUML],
   to: Set[UMLVariable[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Activity_isReadOnly
  (e: UMLActivity[MagicDrawUML], isReadOnly: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLActivity(e).getMagicDrawActivity) map { _e =>
      _e.setReadOnly(isReadOnly)
      Unit
    }

  override def set_Activity_isSingleExecution
  (e: UMLActivity[MagicDrawUML], isSingleExecution: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLActivity(e).getMagicDrawActivity) map { _e =>
      _e.setSingleExecution(isSingleExecution)
      Unit
    }

  // ActivityEdge

  override def links_ActivityEdge_activityEdge_compose_guard_ValueSpecification
  (from: UMLActivityEdge[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ActivityEdge_edge_reference_inPartition_ActivityPartition
  (from: UMLActivityEdge[MagicDrawUML],
   to: Set[UMLActivityPartition[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ActivityEdge_interruptingEdge_reference_interrupts_InterruptibleActivityRegion
  (from: UMLActivityEdge[MagicDrawUML],
   to: Option[UMLInterruptibleActivityRegion[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ActivityEdge_activityEdge_reference_redefinedEdge_ActivityEdge
  (from: UMLActivityEdge[MagicDrawUML],
   to: Set[UMLActivityEdge[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ActivityEdge_outgoing_reference_source_ActivityNode
  (from: UMLActivityEdge[MagicDrawUML],
   to: Option[UMLActivityNode[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ActivityEdge_incoming_reference_target_ActivityNode
  (from: UMLActivityEdge[MagicDrawUML],
   to: Option[UMLActivityNode[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ActivityEdge_activityEdge_compose_weight_ValueSpecification
  (from: UMLActivityEdge[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  // ActivityFinalNode


  // ActivityGroup


  // ActivityNode

  override def links_ActivityNode_node_reference_inInterruptibleRegion_InterruptibleActivityRegion
  (from: UMLActivityNode[MagicDrawUML],
   to: Set[UMLInterruptibleActivityRegion[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ActivityNode_node_reference_inPartition_ActivityPartition
  (from: UMLActivityNode[MagicDrawUML],
   to: Set[UMLActivityPartition[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ActivityNode_target_reference_incoming_ActivityEdge
  (from: UMLActivityNode[MagicDrawUML],
   to: Set[UMLActivityEdge[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ActivityNode_source_reference_outgoing_ActivityEdge
  (from: UMLActivityNode[MagicDrawUML],
   to: Set[UMLActivityEdge[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ActivityNode_activityNode_reference_redefinedNode_ActivityNode
  (from: UMLActivityNode[MagicDrawUML],
   to: Set[UMLActivityNode[MagicDrawUML]]): Try[Unit] =
	???

  // ActivityParameterNode

  override def links_ActivityParameterNode_activityParameterNode_reference_parameter_Parameter
  (from: UMLActivityParameterNode[MagicDrawUML],
   to: Option[UMLParameter[MagicDrawUML]]): Try[Unit] =
	???

  // ActivityPartition

  override def links_ActivityPartition_inPartition_reference_edge_ActivityEdge
  (from: UMLActivityPartition[MagicDrawUML],
   to: Set[UMLActivityEdge[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ActivityPartition_inPartition_reference_node_ActivityNode
  (from: UMLActivityPartition[MagicDrawUML],
   to: Set[UMLActivityNode[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ActivityPartition_activityPartition_reference_represents_Element
  (from: UMLActivityPartition[MagicDrawUML],
   to: Option[UMLElement[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ActivityPartition_superPartition_compose_subpartition_ActivityPartition
  (from: UMLActivityPartition[MagicDrawUML],
   to: Set[UMLActivityPartition[MagicDrawUML]]): Try[Unit] =
	???

  override def set_ActivityPartition_isDimension
  (e: UMLActivityPartition[MagicDrawUML], isDimension: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLActivityPartition(e).getMagicDrawActivityPartition) map { _e =>
      _e.setDimension(isDimension)
      Unit
    }

  override def set_ActivityPartition_isExternal
  (e: UMLActivityPartition[MagicDrawUML], isExternal: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLActivityPartition(e).getMagicDrawActivityPartition) map { _e =>
      _e.setExternal(isExternal)
      Unit
    }


  // Actor


  // AddStructuralFeatureValueAction

  override def links_AddStructuralFeatureValueAction_addStructuralFeatureValueAction_compose_insertAt_InputPin
  (from: UMLAddStructuralFeatureValueAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def set_AddStructuralFeatureValueAction_isReplaceAll
  (e: UMLAddStructuralFeatureValueAction[MagicDrawUML], isReplaceAll: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLAddStructuralFeatureValueAction(e).getMagicDrawAddStructuralFeatureValueAction) map { _e =>
      _e.setReplaceAll(isReplaceAll)
      Unit
    }

  // AddVariableValueAction

  override def links_AddVariableValueAction_addVariableValueAction_compose_insertAt_InputPin
  (from: UMLAddVariableValueAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def set_AddVariableValueAction_isReplaceAll
  (e: UMLAddVariableValueAction[MagicDrawUML], isReplaceAll: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLAddVariableValueAction(e).getMagicDrawAddVariableValueAction) map { _e =>
      _e.setReplaceAll(isReplaceAll)
      Unit
    }

  // AnyReceiveEvent


  // Artifact

  override def links_Artifact_artifact_compose_manifestation_Manifestation
  (from: UMLArtifact[MagicDrawUML],
   to: Set[UMLManifestation[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Artifact_artifact_compose_nestedArtifact_Artifact
  (from: UMLArtifact[MagicDrawUML],
   to: Set[UMLArtifact[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Artifact_artifact_compose_ownedAttribute_Property
  (from: UMLArtifact[MagicDrawUML],
   to: Seq[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Artifact_artifact_compose_ownedOperation_Operation
  (from: UMLArtifact[MagicDrawUML],
   to: Seq[UMLOperation[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Artifact_fileName
  (e: UMLArtifact[MagicDrawUML], fileName: Option[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLArtifact(e).getMagicDrawArtifact) map { _e =>
      fileName match {
        case None => _e.setFileName(null)
        case Some(f) => _e.setFileName(f)
      }
    Unit
  }


  // Association

  override def links_Association_association_reference_memberEnd_Property
  (from: UMLAssociation[MagicDrawUML],
   to: Seq[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Association_association_reference_navigableOwnedEnd_Property
  (from: UMLAssociation[MagicDrawUML],
   to: Set[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Association_owningAssociation_compose_ownedEnd_Property
  (from: UMLAssociation[MagicDrawUML],
   to: Iterable[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Association_isDerived
  (e: UMLAssociation[MagicDrawUML], isDerived: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLAssociation(e).getMagicDrawAssociation) map { _e =>
    _e.setDerived(isDerived)
    Unit
  }


  // AssociationClass


  // Behavior

  override def links_Behavior_behavior_compose_ownedParameter_Parameter
  (from: UMLBehavior[MagicDrawUML],
   to: Seq[UMLParameter[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Behavior_behavior_compose_ownedParameterSet_ParameterSet
  (from: UMLBehavior[MagicDrawUML],
   to: Set[UMLParameterSet[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Behavior_behavior_compose_postcondition_Constraint
  (from: UMLBehavior[MagicDrawUML],
   to: Set[UMLConstraint[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Behavior_behavior_compose_precondition_Constraint
  (from: UMLBehavior[MagicDrawUML],
   to: Set[UMLConstraint[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Behavior_behavior_reference_redefinedBehavior_Behavior
  (from: UMLBehavior[MagicDrawUML],
   to: Set[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Behavior_method_reference_specification_BehavioralFeature
  (from: UMLBehavior[MagicDrawUML],
   to: Option[UMLBehavioralFeature[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Behavior_isReentrant
  (e: UMLBehavior[MagicDrawUML], isReentrant: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLBehavior(e).getMagicDrawBehavior) map { _e =>
      _e.setReentrant(isReentrant)
      Unit
    }


  // BehaviorExecutionSpecification

  override def links_BehaviorExecutionSpecification_behaviorExecutionSpecification_reference_behavior_Behavior
  (from: UMLBehaviorExecutionSpecification[MagicDrawUML],
   to: Option[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  // BehavioralFeature

  override def links_BehavioralFeature_specification_reference_method_Behavior
  (from: UMLBehavioralFeature[MagicDrawUML],
   to: Set[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  override def links_BehavioralFeature_ownerFormalParam_compose_ownedParameter_Parameter
  (from: UMLBehavioralFeature[MagicDrawUML],
   to: Seq[UMLParameter[MagicDrawUML]]): Try[Unit] =
	???

  override def links_BehavioralFeature_behavioralFeature_compose_ownedParameterSet_ParameterSet
  (from: UMLBehavioralFeature[MagicDrawUML],
   to: Set[UMLParameterSet[MagicDrawUML]]): Try[Unit] =
	???

  override def links_BehavioralFeature_behavioralFeature_reference_raisedException_Type
  (from: UMLBehavioralFeature[MagicDrawUML],
   to: Set[UMLType[MagicDrawUML]]): Try[Unit] =
	???

  override def set_BehavioralFeature_concurrency
  (e: UMLBehavioralFeature[MagicDrawUML], concurrency: UMLCallConcurrencyKind.Value): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLBehavioralFeature(e).getMagicDrawBehavioralFeature) map { _e =>
      _e.setConcurrency(concurrency match {
        case UMLCallConcurrencyKind.concurrent => com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.CallConcurrencyKindEnum.CONCURRENT
        case UMLCallConcurrencyKind.guarded => com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.CallConcurrencyKindEnum.GUARDED
        case UMLCallConcurrencyKind.sequential => com.nomagic.uml2.ext.magicdraw.commonbehaviors.mdcommunications.CallConcurrencyKindEnum.SEQUENTIAL
      })
      Unit
    }

  override def set_BehavioralFeature_isAbstract
  (e: UMLBehavioralFeature[MagicDrawUML], isAbstract: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLBehavioralFeature(e).getMagicDrawBehavioralFeature) map { _e =>
      _e.setAbstract(isAbstract)
      Unit
    }

  // BehavioredClassifier

  override def links_BehavioredClassifier_behavioredClassifier_reference_classifierBehavior_Behavior
  (from: UMLBehavioredClassifier[MagicDrawUML],
   to: Option[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  override def links_BehavioredClassifier_implementingClassifier_compose_interfaceRealization_InterfaceRealization
  (from: UMLBehavioredClassifier[MagicDrawUML],
   to: Set[UMLInterfaceRealization[MagicDrawUML]]): Try[Unit] =
	???

  override def links_BehavioredClassifier_behavioredClassifier_compose_ownedBehavior_Behavior
  (from: UMLBehavioredClassifier[MagicDrawUML],
   to: Set[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  // BroadcastSignalAction

  override def links_BroadcastSignalAction_broadcastSignalAction_reference_signal_Signal
  (from: UMLBroadcastSignalAction[MagicDrawUML],
   to: Option[UMLSignal[MagicDrawUML]]): Try[Unit] =
	???

  // CallAction

  override def links_CallAction_callAction_compose_result_OutputPin
  (from: UMLCallAction[MagicDrawUML],
   to: Seq[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def set_CallAction_isSynchronous
  (e: UMLCallAction[MagicDrawUML], isSynchronous: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLCallAction(e).getMagicDrawCallAction) map { _e =>
      _e.setSynchronous(isSynchronous)
      Unit
    }

  // CallBehaviorAction

  override def links_CallBehaviorAction_callBehaviorAction_reference_behavior_Behavior
  (from: UMLCallBehaviorAction[MagicDrawUML],
   to: Option[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  // CallEvent

  override def links_CallEvent_callEvent_reference_operation_Operation
  (from: UMLCallEvent[MagicDrawUML],
   to: Option[UMLOperation[MagicDrawUML]]): Try[Unit] =
	???

  // CallOperationAction

  override def links_CallOperationAction_callOperationAction_reference_operation_Operation
  (from: UMLCallOperationAction[MagicDrawUML],
   to: Option[UMLOperation[MagicDrawUML]]): Try[Unit] =
	???

  override def links_CallOperationAction_callOperationAction_compose_target_InputPin
  (from: UMLCallOperationAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  // CentralBufferNode


  // ChangeEvent

  override def links_ChangeEvent_changeEvent_compose_changeExpression_ValueSpecification
  (from: UMLChangeEvent[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  // Class

  override def links_Class_nestingClass_compose_nestedClassifier_Classifier
  (from: UMLClass[MagicDrawUML],
   to: Seq[UMLClassifier[MagicDrawUML]]): Try[Unit] =
    composesOrderedLinks(
      from, ops.umlMagicDrawUMLClass,
      to, ops.umlMagicDrawUMLClassifier,
      (x:MagicDrawUMLClass) => x.getMagicDrawClass,
      (x:com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class) => x.getNestedClassifier,
      (x:MagicDrawUMLClassifier) => x.getMagicDrawClassifier)

  override def links_Class_class_compose_ownedAttribute_Property
  (from: UMLClass[MagicDrawUML],
   to: Seq[UMLProperty[MagicDrawUML]]): Try[Unit] =
    composesOrderedLinks(
      from, ops.umlMagicDrawUMLClass,
      to, ops.umlMagicDrawUMLProperty,
      (x:MagicDrawUMLClass) => x.getMagicDrawClass,
      (x:com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class) => x.getOwnedAttribute,
      (x:MagicDrawUMLProperty) => x.getMagicDrawProperty)

  override def links_Class_class_compose_ownedOperation_Operation
  (from: UMLClass[MagicDrawUML],
   to: Seq[UMLOperation[MagicDrawUML]]): Try[Unit] =
    composesOrderedLinks(
      from, ops.umlMagicDrawUMLClass,
      to, ops.umlMagicDrawUMLOperation,
      (x:MagicDrawUMLClass) => x.getMagicDrawClass,
      (x:com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class) => x.getOwnedOperation,
      (x:MagicDrawUMLOperation) => x.getMagicDrawOperation)

  override def links_Class_class_compose_ownedReception_Reception
  (from: UMLClass[MagicDrawUML],
   to: Set[UMLReception[MagicDrawUML]]): Try[Unit] =
    composesUnorderedLinks(
      from, ops.umlMagicDrawUMLClass,
      to, ops.umlMagicDrawUMLReception,
      (x:MagicDrawUMLClass) => x.getMagicDrawClass,
      (x:com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class) => x.getOwnedReception,
      (x:MagicDrawUMLReception) => x.getMagicDrawReception)

  override def set_Class_isAbstract
  (e: UMLClass[MagicDrawUML], isAbstract: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLClass(e).getMagicDrawClass) map { _e =>
      _e.setAbstract(isAbstract)
      Unit
    }

  override def set_Class_isActive
  (e: UMLClass[MagicDrawUML], isActive: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLClass(e).getMagicDrawClass) map { _e =>
      _e.setActive(isActive)
      Unit
    }

  // Classifier

  override def links_Classifier_classifier_compose_collaborationUse_CollaborationUse
  (from: UMLClassifier[MagicDrawUML],
   to: Set[UMLCollaborationUse[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Classifier_specific_compose_generalization_Generalization
  (from: UMLClassifier[MagicDrawUML],
   to: Set[UMLGeneralization[MagicDrawUML]]): Try[Unit] =
  composesUnorderedLinks(
    from, ops.umlMagicDrawUMLClassifier,
    to, ops.umlMagicDrawUMLGeneralization,
    (x:MagicDrawUMLClassifier) => x.getMagicDrawClassifier,
    (x:com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Classifier) => x.getGeneralization,
    (x:MagicDrawUMLGeneralization) => x.getMagicDrawGeneralization)

  override def links_Classifier_classifier_compose_ownedTemplateSignature_RedefinableTemplateSignature
  (from: UMLClassifier[MagicDrawUML],
   to: Option[UMLRedefinableTemplateSignature[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Classifier_classifier_compose_ownedUseCase_UseCase
  (from: UMLClassifier[MagicDrawUML],
   to: Set[UMLUseCase[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Classifier_powertype_reference_powertypeExtent_GeneralizationSet
  (from: UMLClassifier[MagicDrawUML],
   to: Set[UMLGeneralizationSet[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Classifier_classifier_reference_redefinedClassifier_Classifier
  (from: UMLClassifier[MagicDrawUML],
   to: Set[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Classifier_classifier_reference_representation_CollaborationUse
  (from: UMLClassifier[MagicDrawUML],
   to: Option[UMLCollaborationUse[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Classifier_substitutingClassifier_compose_substitution_Substitution
  (from: UMLClassifier[MagicDrawUML],
   to: Set[UMLSubstitution[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Classifier_parameteredElement_reference_templateParameter_ClassifierTemplateParameter
  (from: UMLClassifier[MagicDrawUML],
   to: Option[UMLClassifierTemplateParameter[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Classifier_subject_reference_useCase_UseCase
  (from: UMLClassifier[MagicDrawUML],
   to: Set[UMLUseCase[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Classifier_isAbstract
  (e: UMLClassifier[MagicDrawUML], isAbstract: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLClassifier(e).getMagicDrawClassifier) map { _e =>
      _e.setAbstract(isAbstract)
      Unit
    }

  override def set_Classifier_isFinalSpecialization
  (e: UMLClassifier[MagicDrawUML], isFinalSpecialization: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLClassifier(e).getMagicDrawClassifier) map { _e =>
      _e.setFinalSpecialization(isFinalSpecialization)
      Unit
    }

  // ClassifierTemplateParameter

  override def links_ClassifierTemplateParameter_classifierTemplateParameter_reference_constrainingClassifier_Classifier
  (from: UMLClassifierTemplateParameter[MagicDrawUML],
   to: Set[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ClassifierTemplateParameter_templateParameter_reference_parameteredElement_Classifier
  (from: UMLClassifierTemplateParameter[MagicDrawUML],
   to: Option[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  override def set_ClassifierTemplateParameter_allowSubstitutable
  (e: UMLClassifierTemplateParameter[MagicDrawUML], allowSubstitutable: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLClassifierTemplateParameter(e).getMagicDrawClassifierTemplateParameter) map { _e =>
      _e.setAllowSubstitutable(allowSubstitutable)
      Unit
    }

  // Clause

  override def links_Clause_clause_reference_body_ExecutableNode
  (from: UMLClause[MagicDrawUML],
   to: Set[UMLExecutableNode[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Clause_clause_reference_bodyOutput_OutputPin
  (from: UMLClause[MagicDrawUML],
   to: Seq[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Clause_clause_reference_decider_OutputPin
  (from: UMLClause[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Clause_successorClause_reference_predecessorClause_Clause
  (from: UMLClause[MagicDrawUML],
   to: Set[UMLClause[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Clause_predecessorClause_reference_successorClause_Clause
  (from: UMLClause[MagicDrawUML],
   to: Set[UMLClause[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Clause_clause_reference_test_ExecutableNode
  (from: UMLClause[MagicDrawUML],
   to: Set[UMLExecutableNode[MagicDrawUML]]): Try[Unit] =
	???

  // ClearAssociationAction

  override def links_ClearAssociationAction_clearAssociationAction_reference_association_Association
  (from: UMLClearAssociationAction[MagicDrawUML],
   to: Option[UMLAssociation[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ClearAssociationAction_clearAssociationAction_compose_object_InputPin
  (from: UMLClearAssociationAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  // ClearStructuralFeatureAction

  override def links_ClearStructuralFeatureAction_clearStructuralFeatureAction_compose_result_OutputPin
  (from: UMLClearStructuralFeatureAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  // ClearVariableAction


  // Collaboration

  override def links_Collaboration_collaboration_reference_collaborationRole_ConnectableElement
  (from: UMLCollaboration[MagicDrawUML],
   to: Set[UMLConnectableElement[MagicDrawUML]]): Try[Unit] =
	???

  // CollaborationUse

  override def links_CollaborationUse_collaborationUse_compose_roleBinding_Dependency
  (from: UMLCollaborationUse[MagicDrawUML],
   to: Set[UMLDependency[MagicDrawUML]]): Try[Unit] =
	???

  override def links_CollaborationUse_collaborationUse_reference_type_Collaboration
  (from: UMLCollaborationUse[MagicDrawUML],
   to: Option[UMLCollaboration[MagicDrawUML]]): Try[Unit] =
	???

  // CombinedFragment

  override def links_CombinedFragment_combinedFragment_compose_cfragmentGate_Gate
  (from: UMLCombinedFragment[MagicDrawUML],
   to: Set[UMLGate[MagicDrawUML]]): Try[Unit] =
	???

  override def links_CombinedFragment_combinedFragment_compose_operand_InteractionOperand
  (from: UMLCombinedFragment[MagicDrawUML],
   to: Seq[UMLInteractionOperand[MagicDrawUML]]): Try[Unit] =
	???

  override def set_CombinedFragment_interactionOperator
  (e: UMLCombinedFragment[MagicDrawUML], interactionOperator: UMLInteractionOperatorKind.Value): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLCombinedFragment(e).getMagicDrawCombinedFragment) map { _e =>
      _e.setInteractionOperator(interactionOperator match {
        case UMLInteractionOperatorKind.alt => com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.ALT
        case UMLInteractionOperatorKind.assert => com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.ASSERT
        case UMLInteractionOperatorKind.break => com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.BREAK
        case UMLInteractionOperatorKind.consider => com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.CONSIDER
        case UMLInteractionOperatorKind.critical => com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.CRITICAL
        case UMLInteractionOperatorKind.ignore => com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.IGNORE
        case UMLInteractionOperatorKind.loop => com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.LOOP
        case UMLInteractionOperatorKind.neg => com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.NEG
        case UMLInteractionOperatorKind.opt => com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.OPT
        case UMLInteractionOperatorKind.par => com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.PAR
        case UMLInteractionOperatorKind.seq => com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.SEQ
        case UMLInteractionOperatorKind.strict => com.nomagic.uml2.ext.magicdraw.interactions.mdfragments.InteractionOperatorKindEnum.STRICT
      })
      Unit
    }

  // Comment

  override def links_Comment_comment_reference_annotatedElement_Element
  (from: UMLComment[MagicDrawUML],
   to: Set[UMLElement[MagicDrawUML]]): Try[Unit] =
    referencesUnorderedLinks(
    from, ops.umlMagicDrawUMLComment,
    to, ops.umlMagicDrawUMLElement,
      (x:MagicDrawUMLComment) => x.getMagicDrawComment,
      (x:com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Comment) => x.getAnnotatedElement,
      (x:MagicDrawUMLElement) => x.getMagicDrawElement
    )


  override def set_Comment_body
  (e: UMLComment[MagicDrawUML], body: Option[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLComment(e).getMagicDrawComment) map { _e =>
      body match {
        case Some(s) => _e.setBody(s)
        case None => _e.setBody(null)
      }
      Unit
    }

  // CommunicationPath


  // Component

  override def links_Component_component_compose_packagedElement_PackageableElement
  (from: UMLComponent[MagicDrawUML],
   to: Set[UMLPackageableElement[MagicDrawUML]]): Try[Unit] =
    composesUnorderedLinks(
      from, ops.umlMagicDrawUMLComponent,
      to, ops.umlMagicDrawUMLPackageableElement,
      (x:MagicDrawUMLComponent) => x.getMagicDrawComponent,
      (x:com.nomagic.uml2.ext.magicdraw.components.mdbasiccomponents.Component) => x.getPackagedElement,
      (x:MagicDrawUMLPackageableElement) => x.getMagicDrawPackageableElement)

  override def links_Component_abstraction_compose_realization_ComponentRealization
  (from: UMLComponent[MagicDrawUML],
   to: Set[UMLComponentRealization[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Component_isIndirectlyInstantiated
  (e: UMLComponent[MagicDrawUML], isIndirectlyInstantiated: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLComponent(e).getMagicDrawComponent) map { _e =>
      _e.setIndirectlyInstantiated(isIndirectlyInstantiated)
      Unit
    }

  // ComponentRealization

  override def links_ComponentRealization_componentRealization_reference_realizingClassifier_Classifier
  (from: UMLComponentRealization[MagicDrawUML],
   to: Set[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  // ConditionalNode

  override def links_ConditionalNode_conditionalNode_compose_clause_Clause
  (from: UMLConditionalNode[MagicDrawUML],
   to: Set[UMLClause[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ConditionalNode_conditionalNode_compose_result_OutputPin
  (from: UMLConditionalNode[MagicDrawUML],
   to: Seq[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def set_ConditionalNode_isAssured
  (e: UMLConditionalNode[MagicDrawUML], isAssured: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLConditionalNode(e).getMagicDrawConditionalNode) map { _e =>
      _e.setAssured(isAssured)
      Unit
    }

  override def set_ConditionalNode_isDeterminate
  (e: UMLConditionalNode[MagicDrawUML], isDeterminate: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLConditionalNode(e).getMagicDrawConditionalNode) map { _e =>
      _e.setDeterminate(isDeterminate)
      Unit
    }

  // ConnectableElement

  override def links_ConnectableElement_parameteredElement_reference_templateParameter_ConnectableElementTemplateParameter
  (from: UMLConnectableElement[MagicDrawUML],
   to: Option[UMLConnectableElementTemplateParameter[MagicDrawUML]]): Try[Unit] =
	???

  // ConnectableElementTemplateParameter

  override def links_ConnectableElementTemplateParameter_templateParameter_reference_parameteredElement_ConnectableElement
  (from: UMLConnectableElementTemplateParameter[MagicDrawUML],
   to: Option[UMLConnectableElement[MagicDrawUML]]): Try[Unit] =
	???

  // ConnectionPointReference

  override def links_ConnectionPointReference_connectionPointReference_reference_entry_Pseudostate
  (from: UMLConnectionPointReference[MagicDrawUML],
   to: Set[UMLPseudostate[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ConnectionPointReference_connectionPointReference_reference_exit_Pseudostate
  (from: UMLConnectionPointReference[MagicDrawUML],
   to: Set[UMLPseudostate[MagicDrawUML]]): Try[Unit] =
	???

  // Connector

  override def links_Connector_connector_reference_contract_Behavior
  (from: UMLConnector[MagicDrawUML],
   to: Set[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Connector_connector_compose_end_ConnectorEnd
  (from: UMLConnector[MagicDrawUML],
   to: Seq[UMLConnectorEnd[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Connector_connector_reference_redefinedConnector_Connector
  (from: UMLConnector[MagicDrawUML],
   to: Set[UMLConnector[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Connector_connector_reference_type_Association
  (from: UMLConnector[MagicDrawUML],
   to: Option[UMLAssociation[MagicDrawUML]]): Try[Unit] =
	???

  // ConnectorEnd

  override def links_ConnectorEnd_connectorEnd_reference_partWithPort_Property
  (from: UMLConnectorEnd[MagicDrawUML],
   to: Option[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  // ConsiderIgnoreFragment

  override def links_ConsiderIgnoreFragment_considerIgnoreFragment_reference_message_NamedElement
  (from: UMLConsiderIgnoreFragment[MagicDrawUML],
   to: Set[UMLNamedElement[MagicDrawUML]]): Try[Unit] =
	???

  // Constraint

  override def links_Constraint_constraint_reference_constrainedElement_Element
  (from: UMLConstraint[MagicDrawUML],
   to: Seq[UMLElement[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Constraint_owningConstraint_compose_specification_ValueSpecification
  (from: UMLConstraint[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  // Continuation


  override def set_Continuation_setting
  (e: UMLContinuation[MagicDrawUML], setting: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLContinuation(e).getMagicDrawContinuation) map { _e =>
      _e.setSetting(setting)
      Unit
    }

  // ControlFlow


  // ControlNode


  // CreateLinkAction

  override def links_CreateLinkAction_createLinkAction_compose_endData_LinkEndCreationData
  (from: UMLCreateLinkAction[MagicDrawUML],
   to: Iterable[UMLLinkEndCreationData[MagicDrawUML]]): Try[Unit] =
	???

  // CreateLinkObjectAction

  override def links_CreateLinkObjectAction_createLinkObjectAction_compose_result_OutputPin
  (from: UMLCreateLinkObjectAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  // CreateObjectAction

  override def links_CreateObjectAction_createObjectAction_reference_classifier_Classifier
  (from: UMLCreateObjectAction[MagicDrawUML],
   to: Option[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  override def links_CreateObjectAction_createObjectAction_compose_result_OutputPin
  (from: UMLCreateObjectAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  // DataStoreNode


  // DataType

  override def links_DataType_datatype_compose_ownedAttribute_Property
  (from: UMLDataType[MagicDrawUML],
   to: Seq[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  override def links_DataType_datatype_compose_ownedOperation_Operation
  (from: UMLDataType[MagicDrawUML],
   to: Seq[UMLOperation[MagicDrawUML]]): Try[Unit] =
	???

  // DecisionNode

  override def links_DecisionNode_decisionNode_reference_decisionInput_Behavior
  (from: UMLDecisionNode[MagicDrawUML],
   to: Option[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  override def links_DecisionNode_decisionNode_reference_decisionInputFlow_ObjectFlow
  (from: UMLDecisionNode[MagicDrawUML],
   to: Option[UMLObjectFlow[MagicDrawUML]]): Try[Unit] =
	???

  // Dependency

  override def links_Dependency_supplierDependency_reference_supplier_NamedElement
  (from: UMLDependency[MagicDrawUML],
   to: Set[UMLNamedElement[MagicDrawUML]]): Try[Unit] =
	???

  // DeployedArtifact


  // Deployment

  override def links_Deployment_deployment_compose_configuration_DeploymentSpecification
  (from: UMLDeployment[MagicDrawUML],
   to: Set[UMLDeploymentSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Deployment_deploymentForArtifact_reference_deployedArtifact_DeployedArtifact
  (from: UMLDeployment[MagicDrawUML],
   to: Set[UMLDeployedArtifact[MagicDrawUML]]): Try[Unit] =
	???

  // DeploymentSpecification

  override def set_DeploymentSpecification_deploymentLocation
  (e: UMLDeploymentSpecification[MagicDrawUML], deploymentLocation: Option[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLDeploymentSpecification(e).getMagicDrawDeploymentSpecification) map { _e =>
      deploymentLocation match {
        case Some(s) => _e.setDeploymentLocation(s)
        case None => _e.setDeploymentLocation(null)
      }
      Unit
    }

  override def set_DeploymentSpecification_executionLocation
  (e: UMLDeploymentSpecification[MagicDrawUML], executionLocation: Option[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLDeploymentSpecification(e).getMagicDrawDeploymentSpecification) map { _e =>
      executionLocation match {
        case Some(s) => _e.setExecutionLocation(s)
        case None => _e.setExecutionLocation(null)
      }
      Unit
    }

  // DeploymentTarget

  override def links_DeploymentTarget_location_compose_deployment_Deployment
  (from: UMLDeploymentTarget[MagicDrawUML],
   to: Set[UMLDeployment[MagicDrawUML]]): Try[Unit] =
	???

  // DestroyLinkAction

  override def links_DestroyLinkAction_destroyLinkAction_compose_endData_LinkEndDestructionData
  (from: UMLDestroyLinkAction[MagicDrawUML],
   to: Iterable[UMLLinkEndDestructionData[MagicDrawUML]]): Try[Unit] =
	???

  // DestroyObjectAction

  override def links_DestroyObjectAction_destroyObjectAction_compose_target_InputPin
  (from: UMLDestroyObjectAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def set_DestroyObjectAction_isDestroyLinks
  (e: UMLDestroyObjectAction[MagicDrawUML], isDestroyLinks: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLDestroyObjectAction(e).getMagicDrawDestroyObjectAction) map { _e =>
      _e.setDestroyLinks(isDestroyLinks)
      Unit
    }

  override def set_DestroyObjectAction_isDestroyOwnedObjects
  (e: UMLDestroyObjectAction[MagicDrawUML], isDestroyOwnedObjects: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLDestroyObjectAction(e).getMagicDrawDestroyObjectAction) map { _e =>
      _e.setDestroyOwnedObjects(isDestroyOwnedObjects)
      Unit
    }

  // DestructionOccurrenceSpecification


  // Device


  // DirectedRelationship


  // Duration

  override def links_Duration_duration_compose_expr_ValueSpecification
  (from: UMLDuration[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Duration_duration_reference_observation_Observation
  (from: UMLDuration[MagicDrawUML],
   to: Set[UMLObservation[MagicDrawUML]]): Try[Unit] =
	???

  // DurationConstraint

  override def links_DurationConstraint_durationConstraint_compose_specification_DurationInterval
  (from: UMLDurationConstraint[MagicDrawUML],
   to: Option[UMLDurationInterval[MagicDrawUML]]): Try[Unit] =
	???

  override def set_DurationConstraint_firstEvent
  (e: UMLDurationConstraint[MagicDrawUML], firstEvent: Set[Boolean]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLDurationConstraint(e).getMagicDrawDurationConstraint) map { _e =>
      _e.isFirstEvent().clear()
      for { b <- firstEvent} _e.isFirstEvent().add(b)
      Unit
    }

  // DurationInterval

  override def links_DurationInterval_durationInterval_reference_max_Duration
  (from: UMLDurationInterval[MagicDrawUML],
   to: Option[UMLDuration[MagicDrawUML]]): Try[Unit] =
	???

  override def links_DurationInterval_durationInterval_reference_min_Duration
  (from: UMLDurationInterval[MagicDrawUML],
   to: Option[UMLDuration[MagicDrawUML]]): Try[Unit] =
	???

  // DurationObservation

  override def links_DurationObservation_durationObservation_reference_event_NamedElement
  (from: UMLDurationObservation[MagicDrawUML],
   to: Seq[UMLNamedElement[MagicDrawUML]]): Try[Unit] =
	???

  override def set_DurationObservation_firstEvent
  (e: UMLDurationObservation[MagicDrawUML], firstEvent: Seq[Boolean]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLDurationObservation(e).getMagicDrawDurationObservation) map { _e =>
      _e.isFirstEvent().clear()
      for { b <- firstEvent } _e.isFirstEvent().add(b)
      Unit
    }

  // Element

  override def links_Element_owningElement_compose_ownedComment_Comment
  (from: UMLElement[MagicDrawUML],
   to: Set[UMLComment[MagicDrawUML]]): Try[Unit] =
	???

  // ElementImport

  override def links_ElementImport_import_reference_importedElement_PackageableElement
  (from: UMLElementImport[MagicDrawUML],
   to: Option[UMLPackageableElement[MagicDrawUML]]): Try[Unit] =
	???

  override def set_ElementImport_alias
  (e: UMLElementImport[MagicDrawUML], alias: Option[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLElementImport(e).getMagicDrawElementImport) map { _e =>
      alias match {
        case None => _e.setAlias(null)
        case Some(s) => _e.setAlias(s)
      }
      Unit
    }

  override def set_ElementImport_visibility
  (e: UMLElementImport[MagicDrawUML], visibility: UMLVisibilityKind.Value): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLElementImport(e).getMagicDrawElementImport) map { _e =>
      _e.setVisibility(visibility match {
        case UMLVisibilityKind._package => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PACKAGE
        case UMLVisibilityKind._private => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PRIVATE
        case UMLVisibilityKind._protected => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PROTECTED
        case UMLVisibilityKind.public => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PUBLIC
      })
      Unit
    }

  // EncapsulatedClassifier


  // Enumeration

  override def links_Enumeration_enumeration_compose_ownedLiteral_EnumerationLiteral
  (from: UMLEnumeration[MagicDrawUML],
   to: Seq[UMLEnumerationLiteral[MagicDrawUML]]): Try[Unit] =
	???

  // EnumerationLiteral


  // Event


  // ExceptionHandler

  override def links_ExceptionHandler_exceptionHandler_reference_exceptionInput_ObjectNode
  (from: UMLExceptionHandler[MagicDrawUML],
   to: Option[UMLObjectNode[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ExceptionHandler_exceptionHandler_reference_exceptionType_Classifier
  (from: UMLExceptionHandler[MagicDrawUML],
   to: Set[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ExceptionHandler_exceptionHandler_reference_handlerBody_ExecutableNode
  (from: UMLExceptionHandler[MagicDrawUML],
   to: Option[UMLExecutableNode[MagicDrawUML]]): Try[Unit] =
	???

  // ExecutableNode

  override def links_ExecutableNode_protectedNode_compose_handler_ExceptionHandler
  (from: UMLExecutableNode[MagicDrawUML],
   to: Set[UMLExceptionHandler[MagicDrawUML]]): Try[Unit] =
	???

  // ExecutionEnvironment


  // ExecutionOccurrenceSpecification

  override def links_ExecutionOccurrenceSpecification_executionOccurrenceSpecification_reference_execution_ExecutionSpecification
  (from: UMLExecutionOccurrenceSpecification[MagicDrawUML],
   to: Option[UMLExecutionSpecification[MagicDrawUML]]): Try[Unit] =
	???

  // ExecutionSpecification

  override def links_ExecutionSpecification_executionSpecification_reference_finish_OccurrenceSpecification
  (from: UMLExecutionSpecification[MagicDrawUML],
   to: Option[UMLOccurrenceSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ExecutionSpecification_executionSpecification_reference_start_OccurrenceSpecification
  (from: UMLExecutionSpecification[MagicDrawUML],
   to: Option[UMLOccurrenceSpecification[MagicDrawUML]]): Try[Unit] =
	???

  // ExpansionNode

  override def links_ExpansionNode_inputElement_reference_regionAsInput_ExpansionRegion
  (from: UMLExpansionNode[MagicDrawUML],
   to: Option[UMLExpansionRegion[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ExpansionNode_outputElement_reference_regionAsOutput_ExpansionRegion
  (from: UMLExpansionNode[MagicDrawUML],
   to: Option[UMLExpansionRegion[MagicDrawUML]]): Try[Unit] =
	???

  // ExpansionRegion

  override def links_ExpansionRegion_regionAsInput_reference_inputElement_ExpansionNode
  (from: UMLExpansionRegion[MagicDrawUML],
   to: Set[UMLExpansionNode[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ExpansionRegion_regionAsOutput_reference_outputElement_ExpansionNode
  (from: UMLExpansionRegion[MagicDrawUML],
   to: Set[UMLExpansionNode[MagicDrawUML]]): Try[Unit] =
	???

  override def set_ExpansionRegion_mode
  (e: UMLExpansionRegion[MagicDrawUML], mode: UMLExpansionKind.Value): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLExpansionRegion(e).getMagicDrawExpansionRegion) map { _e =>
      _e.setMode(mode match {
        case UMLExpansionKind.iterative => com.nomagic.uml2.ext.magicdraw.activities.mdextrastructuredactivities.ExpansionKindEnum.ITERATIVE
        case UMLExpansionKind.parallel => com.nomagic.uml2.ext.magicdraw.activities.mdextrastructuredactivities.ExpansionKindEnum.PARALLEL
        case UMLExpansionKind.stream => com.nomagic.uml2.ext.magicdraw.activities.mdextrastructuredactivities.ExpansionKindEnum.STREAM
      })
      Unit
    }

  // Expression

  override def links_Expression_expression_compose_operand_ValueSpecification
  (from: UMLExpression[MagicDrawUML],
   to: Seq[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Expression_symbol
  (e: UMLExpression[MagicDrawUML], symbol: Option[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLExpression(e).getMagicDrawExpression) map { _e =>
      symbol match {
        case Some(s) => _e.setSymbol(s)
        case None => _e.setSymbol(null)
      }
      Unit
    }

  // Extend

  override def links_Extend_extend_compose_condition_Constraint
  (from: UMLExtend[MagicDrawUML],
   to: Option[UMLConstraint[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Extend_extend_reference_extendedCase_UseCase
  (from: UMLExtend[MagicDrawUML],
   to: Option[UMLUseCase[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Extend_extension_reference_extensionLocation_ExtensionPoint
  (from: UMLExtend[MagicDrawUML],
   to: Seq[UMLExtensionPoint[MagicDrawUML]]): Try[Unit] =
	???

  // Extension

  override def links_Extension_extension_compose_ownedEnd_ExtensionEnd
  (from: UMLExtension[MagicDrawUML],
   to: Iterable[UMLExtensionEnd[MagicDrawUML]]): Try[Unit] =
	???

  // ExtensionEnd

  override def links_ExtensionEnd_extensionEnd_reference_type_Stereotype
  (from: UMLExtensionEnd[MagicDrawUML],
   to: Option[UMLStereotype[MagicDrawUML]]): Try[Unit] =
	???


  // ExtensionPoint


  // Feature


  override def set_Feature_isStatic
  (e: UMLFeature[MagicDrawUML], isStatic: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLFeature(e).getMagicDrawFeature) map { _e =>
      _e.setStatic(isStatic)
      Unit
    }

  // FinalNode


  // FinalState


  // FlowFinalNode


  // ForkNode


  // FunctionBehavior


  // Gate


  // GeneralOrdering

  override def links_GeneralOrdering_toBefore_reference_after_OccurrenceSpecification
  (from: UMLGeneralOrdering[MagicDrawUML],
   to: Option[UMLOccurrenceSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def links_GeneralOrdering_toAfter_reference_before_OccurrenceSpecification
  (from: UMLGeneralOrdering[MagicDrawUML],
   to: Option[UMLOccurrenceSpecification[MagicDrawUML]]): Try[Unit] =
	???

  // Generalization

  override def links_Generalization_generalization_reference_general_Classifier
  (from: UMLGeneralization[MagicDrawUML],
   to: Option[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Generalization_generalization_reference_generalizationSet_GeneralizationSet
  (from: UMLGeneralization[MagicDrawUML],
   to: Set[UMLGeneralizationSet[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Generalization_isSubstitutable
  (e: UMLGeneralization[MagicDrawUML], isSubstitutable: Option[Boolean]): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLGeneralization(e).getMagicDrawGeneralization) map { _e =>
      _e.setSubstitutable(isSubstitutable match {
        case Some(b) => b
        case None => true
      })
      Unit
    }

  // GeneralizationSet

  override def links_GeneralizationSet_generalizationSet_reference_generalization_Generalization
  (from: UMLGeneralizationSet[MagicDrawUML],
   to: Set[UMLGeneralization[MagicDrawUML]]): Try[Unit] =
	???

  override def links_GeneralizationSet_powertypeExtent_reference_powertype_Classifier
  (from: UMLGeneralizationSet[MagicDrawUML],
   to: Option[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  override def set_GeneralizationSet_isCovering
  (e: UMLGeneralizationSet[MagicDrawUML], isCovering: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLGeneralizationSet(e).getMagicDrawGeneralizationSet) map { _e =>
      _e.setCovering(isCovering)
      Unit
    }

  override def set_GeneralizationSet_isDisjoint
  (e: UMLGeneralizationSet[MagicDrawUML], isDisjoint: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLGeneralizationSet(e).getMagicDrawGeneralizationSet) map { _e =>
      _e.setDisjoint(isDisjoint)
      Unit
    }

  // Image


  override def set_Image_content
  (e: UMLImage[MagicDrawUML], content: Option[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLImage(e).getMagicDrawImage) map { _e =>
      content match {
        case Some(s) => _e.setContent(s)
        case None => _e.setContent(null)
      }
      Unit
    }

  override def set_Image_format
  (e: UMLImage[MagicDrawUML], format: Option[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLImage(e).getMagicDrawImage) map { _e =>
      format match {
        case Some(s) => _e.setFormat(s)
        case None => _e.setFormat(null)
      }
      Unit
    }

  override def set_Image_location
  (e: UMLImage[MagicDrawUML], location: Option[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLImage(e).getMagicDrawImage) map { _e =>
      location match {
        case Some(s) => _e.setLocation(s)
        case None => _e.setLocation(null)
      }
      Unit
    }

  // Include

  override def links_Include_include_reference_addition_UseCase
  (from: UMLInclude[MagicDrawUML],
   to: Option[UMLUseCase[MagicDrawUML]]): Try[Unit] =
	???

  // InformationFlow

  override def links_InformationFlow_conveyingFlow_reference_conveyed_Classifier
  (from: UMLInformationFlow[MagicDrawUML],
   to: Set[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InformationFlow_informationFlow_reference_informationSource_NamedElement
  (from: UMLInformationFlow[MagicDrawUML],
   to: Set[UMLNamedElement[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InformationFlow_informationFlow_reference_informationTarget_NamedElement
  (from: UMLInformationFlow[MagicDrawUML],
   to: Set[UMLNamedElement[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InformationFlow_abstraction_reference_realization_Relationship
  (from: UMLInformationFlow[MagicDrawUML],
   to: Set[UMLRelationship[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InformationFlow_informationFlow_reference_realizingActivityEdge_ActivityEdge
  (from: UMLInformationFlow[MagicDrawUML],
   to: Set[UMLActivityEdge[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InformationFlow_informationFlow_reference_realizingConnector_Connector
  (from: UMLInformationFlow[MagicDrawUML],
   to: Set[UMLConnector[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InformationFlow_informationFlow_reference_realizingMessage_Message
  (from: UMLInformationFlow[MagicDrawUML],
   to: Set[UMLMessage[MagicDrawUML]]): Try[Unit] =
	???

  // InformationItem

  override def links_InformationItem_representation_reference_represented_Classifier
  (from: UMLInformationItem[MagicDrawUML],
   to: Set[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  // InitialNode


  // InputPin


  // InstanceSpecification

  override def links_InstanceSpecification_instanceSpecification_reference_classifier_Classifier
  (from: UMLInstanceSpecification[MagicDrawUML],
   to: Iterable[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InstanceSpecification_owningInstance_compose_slot_Slot
  (from: UMLInstanceSpecification[MagicDrawUML],
   to: Set[UMLSlot[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InstanceSpecification_owningInstanceSpec_compose_specification_ValueSpecification
  (from: UMLInstanceSpecification[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  // InstanceValue

  override def links_InstanceValue_instanceValue_reference_instance_InstanceSpecification
  (from: UMLInstanceValue[MagicDrawUML],
   to: Option[UMLInstanceSpecification[MagicDrawUML]]): Try[Unit] =
	???

  // Interaction

  override def links_Interaction_interaction_compose_action_Action
  (from: UMLInteraction[MagicDrawUML],
   to: Set[UMLAction[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Interaction_interaction_compose_formalGate_Gate
  (from: UMLInteraction[MagicDrawUML],
   to: Set[UMLGate[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Interaction_enclosingInteraction_compose_fragment_InteractionFragment
  (from: UMLInteraction[MagicDrawUML],
   to: Seq[UMLInteractionFragment[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Interaction_interaction_compose_lifeline_Lifeline
  (from: UMLInteraction[MagicDrawUML],
   to: Set[UMLLifeline[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Interaction_interaction_compose_message_Message
  (from: UMLInteraction[MagicDrawUML],
   to: Set[UMLMessage[MagicDrawUML]]): Try[Unit] =
	???

  // InteractionConstraint

  override def links_InteractionConstraint_interactionConstraint_compose_maxint_ValueSpecification
  (from: UMLInteractionConstraint[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InteractionConstraint_interactionConstraint_compose_minint_ValueSpecification
  (from: UMLInteractionConstraint[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  // InteractionFragment

  override def links_InteractionFragment_coveredBy_reference_covered_Lifeline
  (from: UMLInteractionFragment[MagicDrawUML],
   to: Iterable[UMLLifeline[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InteractionFragment_interactionFragment_compose_generalOrdering_GeneralOrdering
  (from: UMLInteractionFragment[MagicDrawUML],
   to: Set[UMLGeneralOrdering[MagicDrawUML]]): Try[Unit] =
	???

  // InteractionOperand

  override def links_InteractionOperand_enclosingOperand_compose_fragment_InteractionFragment
  (from: UMLInteractionOperand[MagicDrawUML],
   to: Seq[UMLInteractionFragment[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InteractionOperand_interactionOperand_compose_guard_InteractionConstraint
  (from: UMLInteractionOperand[MagicDrawUML],
   to: Option[UMLInteractionConstraint[MagicDrawUML]]): Try[Unit] =
	???

  // InteractionUse

  override def links_InteractionUse_interactionUse_compose_actualGate_Gate
  (from: UMLInteractionUse[MagicDrawUML],
   to: Set[UMLGate[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InteractionUse_interactionUse_compose_argument_ValueSpecification
  (from: UMLInteractionUse[MagicDrawUML],
   to: Seq[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InteractionUse_interactionUse_reference_refersTo_Interaction
  (from: UMLInteractionUse[MagicDrawUML],
   to: Option[UMLInteraction[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InteractionUse_interactionUse_compose_returnValue_ValueSpecification
  (from: UMLInteractionUse[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InteractionUse_interactionUse_reference_returnValueRecipient_Property
  (from: UMLInteractionUse[MagicDrawUML],
   to: Option[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  // Interface

  override def links_Interface_interface_compose_nestedClassifier_Classifier
  (from: UMLInterface[MagicDrawUML],
   to: Seq[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Interface_interface_compose_ownedAttribute_Property
  (from: UMLInterface[MagicDrawUML],
   to: Seq[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Interface_interface_compose_ownedOperation_Operation
  (from: UMLInterface[MagicDrawUML],
   to: Seq[UMLOperation[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Interface_interface_compose_ownedReception_Reception
  (from: UMLInterface[MagicDrawUML],
   to: Set[UMLReception[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Interface_interface_compose_protocol_ProtocolStateMachine
  (from: UMLInterface[MagicDrawUML],
   to: Option[UMLProtocolStateMachine[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Interface_interface_reference_redefinedInterface_Interface
  (from: UMLInterface[MagicDrawUML],
   to: Set[UMLInterface[MagicDrawUML]]): Try[Unit] =
	???

  // InterfaceRealization

  override def links_InterfaceRealization_interfaceRealization_reference_contract_Interface
  (from: UMLInterfaceRealization[MagicDrawUML],
   to: Option[UMLInterface[MagicDrawUML]]): Try[Unit] =
	???

  // InterruptibleActivityRegion

  override def links_InterruptibleActivityRegion_interrupts_reference_interruptingEdge_ActivityEdge
  (from: UMLInterruptibleActivityRegion[MagicDrawUML],
   to: Set[UMLActivityEdge[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InterruptibleActivityRegion_inInterruptibleRegion_reference_node_ActivityNode
  (from: UMLInterruptibleActivityRegion[MagicDrawUML],
   to: Set[UMLActivityNode[MagicDrawUML]]): Try[Unit] =
	???

  // Interval

  override def links_Interval_interval_reference_max_ValueSpecification
  (from: UMLInterval[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Interval_interval_reference_min_ValueSpecification
  (from: UMLInterval[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  // IntervalConstraint

  override def links_IntervalConstraint_intervalConstraint_compose_specification_Interval
  (from: UMLIntervalConstraint[MagicDrawUML],
   to: Option[UMLInterval[MagicDrawUML]]): Try[Unit] =
	???

  // InvocationAction

  override def links_InvocationAction_invocationAction_compose_argument_InputPin
  (from: UMLInvocationAction[MagicDrawUML],
   to: Seq[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_InvocationAction_invocationAction_reference_onPort_Port
  (from: UMLInvocationAction[MagicDrawUML],
   to: Option[UMLPort[MagicDrawUML]]): Try[Unit] =
	???

  // JoinNode

  override def links_JoinNode_joinNode_compose_joinSpec_ValueSpecification
  (from: UMLJoinNode[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def set_JoinNode_isCombineDuplicate
  (e: UMLJoinNode[MagicDrawUML], isCombineDuplicate: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLJoinNode(e).getMagicDrawJoinNode) map { _e =>
      _e.setCombineDuplicate(isCombineDuplicate)
      Unit
    }

  // Lifeline

  override def links_Lifeline_covered_reference_coveredBy_InteractionFragment
  (from: UMLLifeline[MagicDrawUML],
   to: Set[UMLInteractionFragment[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Lifeline_lifeline_reference_decomposedAs_PartDecomposition
  (from: UMLLifeline[MagicDrawUML],
   to: Option[UMLPartDecomposition[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Lifeline_lifeline_reference_represents_ConnectableElement
  (from: UMLLifeline[MagicDrawUML],
   to: Option[UMLConnectableElement[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Lifeline_lifeline_compose_selector_ValueSpecification
  (from: UMLLifeline[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  // LinkAction

  override def links_LinkAction_linkAction_compose_endData_LinkEndData
  (from: UMLLinkAction[MagicDrawUML],
   to: Iterable[UMLLinkEndData[MagicDrawUML]]): Try[Unit] =
	???

  override def links_LinkAction_linkAction_compose_inputValue_InputPin
  (from: UMLLinkAction[MagicDrawUML],
   to: Set[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  // LinkEndCreationData

  override def links_LinkEndCreationData_linkEndCreationData_reference_insertAt_InputPin
  (from: UMLLinkEndCreationData[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def set_LinkEndCreationData_isReplaceAll
  (e: UMLLinkEndCreationData[MagicDrawUML], isReplaceAll: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLLinkEndCreationData(e).getMagicDrawLinkEndCreationData) map { _e =>
      _e.setReplaceAll(isReplaceAll)
      Unit
    }

  // LinkEndData

  override def links_LinkEndData_linkEndData_reference_end_Property
  (from: UMLLinkEndData[MagicDrawUML],
   to: Option[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  override def links_LinkEndData_linkEndData_compose_qualifier_QualifierValue
  (from: UMLLinkEndData[MagicDrawUML],
   to: Set[UMLQualifierValue[MagicDrawUML]]): Try[Unit] =
	???

  override def links_LinkEndData_linkEndData_reference_value_InputPin
  (from: UMLLinkEndData[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  // LinkEndDestructionData

  override def links_LinkEndDestructionData_linkEndDestructionData_reference_destroyAt_InputPin
  (from: UMLLinkEndDestructionData[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def set_LinkEndDestructionData_isDestroyDuplicates
  (e: UMLLinkEndDestructionData[MagicDrawUML], isDestroyDuplicates: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLLinkEndDestructionData(e).getMagicDrawLinkEndDestructionData) map { _e =>
      _e.setDestroyDuplicates(isDestroyDuplicates)
      Unit
    }

  // LiteralBoolean


  override def set_LiteralBoolean_value
  (e: UMLLiteralBoolean[MagicDrawUML], value: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLLiteralBoolean(e).getMagicDrawLiteralBoolean) map { _e =>
      _e.setValue(value)
      Unit
    }

  // LiteralInteger


  override def set_LiteralInteger_value
  (e: UMLLiteralInteger[MagicDrawUML], value: Integer): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLLiteralInteger(e).getMagicDrawLiteralInteger) map { _e =>
      _e.setValue(value)
      Unit
    }

  // LiteralNull


  // LiteralReal


  override def set_LiteralReal_value
  (e: UMLLiteralReal[MagicDrawUML], value: Double): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLLiteralReal(e).getMagicDrawLiteralReal) map { _e =>
      _e.setValue(value)
      Unit
    }

  // LiteralSpecification


  // LiteralString


  override def set_LiteralString_value
  (e: UMLLiteralString[MagicDrawUML], value: Option[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLLiteralString(e).getMagicDrawLiteralString) map { _e =>
     value match {
       case None => _e.setValue(null)
       case Some(s) => _e.setValue(s)
     }
      Unit
    }

  // LiteralUnlimitedNatural


  override def set_LiteralUnlimitedNatural_value
  (e: UMLLiteralUnlimitedNatural[MagicDrawUML], value: Integer): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLLiteralUnlimitedNatural(e).getMagicDrawLiteralUnlimitedNatural) map { _e =>
      _e.setValue(value)
      Unit
    }

  // LoopNode

  override def links_LoopNode_loopNode_reference_bodyOutput_OutputPin
  (from: UMLLoopNode[MagicDrawUML],
   to: Seq[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_LoopNode_loopNode_reference_bodyPart_ExecutableNode
  (from: UMLLoopNode[MagicDrawUML],
   to: Set[UMLExecutableNode[MagicDrawUML]]): Try[Unit] =
	???

  override def links_LoopNode_loopNode_reference_decider_OutputPin
  (from: UMLLoopNode[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_LoopNode_loopNode_compose_loopVariable_OutputPin
  (from: UMLLoopNode[MagicDrawUML],
   to: Seq[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_LoopNode_loopNode_compose_loopVariableInput_InputPin
  (from: UMLLoopNode[MagicDrawUML],
   to: Seq[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_LoopNode_loopNode_compose_result_OutputPin
  (from: UMLLoopNode[MagicDrawUML],
   to: Seq[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_LoopNode_loopNode_reference_setupPart_ExecutableNode
  (from: UMLLoopNode[MagicDrawUML],
   to: Set[UMLExecutableNode[MagicDrawUML]]): Try[Unit] =
	???

  override def links_LoopNode_loopNode_reference_test_ExecutableNode
  (from: UMLLoopNode[MagicDrawUML],
   to: Set[UMLExecutableNode[MagicDrawUML]]): Try[Unit] =
	???

  override def set_LoopNode_isTestedFirst
  (e: UMLLoopNode[MagicDrawUML], isTestedFirst: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLLoopNode(e).getMagicDrawLoopNode) map { _e =>
      _e.setTestedFirst(isTestedFirst)
      Unit
    }

  // Manifestation

  override def links_Manifestation_manifestation_reference_utilizedElement_PackageableElement
  (from: UMLManifestation[MagicDrawUML],
   to: Option[UMLPackageableElement[MagicDrawUML]]): Try[Unit] =
	???

  // MergeNode


  // Message

  override def links_Message_message_compose_argument_ValueSpecification
  (from: UMLMessage[MagicDrawUML],
   to: Seq[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Message_message_reference_connector_Connector
  (from: UMLMessage[MagicDrawUML],
   to: Option[UMLConnector[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Message_endMessage_reference_receiveEvent_MessageEnd
  (from: UMLMessage[MagicDrawUML],
   to: Option[UMLMessageEnd[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Message_endMessage_reference_sendEvent_MessageEnd
  (from: UMLMessage[MagicDrawUML],
   to: Option[UMLMessageEnd[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Message_message_reference_signature_NamedElement
  (from: UMLMessage[MagicDrawUML],
   to: Option[UMLNamedElement[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Message_messageSort
  (e: UMLMessage[MagicDrawUML], messageSort: UMLMessageSort.Value): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLMessage(e).getMagicDrawMessage) map { _e =>
      _e.setMessageSort(messageSort match {
        case UMLMessageSort.asynchCall => com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageSortEnum.ASYNCHCALL
        case UMLMessageSort.asynchSignal => com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageSortEnum.ASYNCHSIGNAL
        case UMLMessageSort.createMessage => com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageSortEnum.CREATEMESSAGE
        case UMLMessageSort.deleteMessage => com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageSortEnum.DELETEMESSAGE
        case UMLMessageSort.reply => com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageSortEnum.REPLY
        case UMLMessageSort.synchCall => com.nomagic.uml2.ext.magicdraw.interactions.mdbasicinteractions.MessageSortEnum.SYNCHCALL
      })
      Unit
    }

  // MessageEnd

  override def links_MessageEnd_messageEnd_reference_message_Message
  (from: UMLMessageEnd[MagicDrawUML],
   to: Option[UMLMessage[MagicDrawUML]]): Try[Unit] =
	???

  // MessageEvent


  // MessageOccurrenceSpecification


  // Model


  override def set_Model_viewpoint
  (e: UMLModel[MagicDrawUML], viewpoint: Option[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLModel(e).getMagicDrawModel) map { _e =>
      viewpoint match {
        case Some(s) => _e.setViewpoint(s)
        case None => _e.setViewpoint(null)
      }
      Unit
    }

  // MultiplicityElement

  override def links_MultiplicityElement_owningLower_compose_lowerValue_ValueSpecification
  (from: UMLMultiplicityElement[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def links_MultiplicityElement_owningUpper_compose_upperValue_ValueSpecification
  (from: UMLMultiplicityElement[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def set_MultiplicityElement_isOrdered
  (e: UMLMultiplicityElement[MagicDrawUML], isOrdered: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLMultiplicityElement(e).getMagicDrawMultiplicityElement) map { _e =>
      _e.setOrdered(isOrdered)
      Unit
    }

  override def set_MultiplicityElement_isUnique
  (e: UMLMultiplicityElement[MagicDrawUML], isUnique: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLMultiplicityElement(e).getMagicDrawMultiplicityElement) map { _e =>
      _e.setUnique(isUnique)
      Unit
    }

  // NamedElement

  override def links_NamedElement_namedElement_compose_nameExpression_StringExpression
  (from: UMLNamedElement[MagicDrawUML],
   to: Option[UMLStringExpression[MagicDrawUML]]): Try[Unit] =
	???

  override def set_NamedElement_name
  (e: UMLNamedElement[MagicDrawUML], name: Option[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLNamedElement(e).getMagicDrawNamedElement) map { _e =>
      name match {
        case Some(s) => _e.setName(s)
        case None => _e.setName(null)
      }
      Unit
    }

  override def set_NamedElement_visibility
  (e: UMLNamedElement[MagicDrawUML], visibility: Option[UMLVisibilityKind.Value]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLNamedElement(e).getMagicDrawNamedElement) map { _e =>
      _e.setVisibility(visibility match {
        case None => null
        case Some(v) => v match {
          case UMLVisibilityKind._package => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PACKAGE
          case UMLVisibilityKind._private => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PRIVATE
          case UMLVisibilityKind._protected => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PROTECTED
          case UMLVisibilityKind.public => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PUBLIC
        }
      })
      Unit
    }

  // Namespace

  override def links_Namespace_importingNamespace_compose_elementImport_ElementImport
  (from: UMLNamespace[MagicDrawUML],
   to: Set[UMLElementImport[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Namespace_context_compose_ownedRule_Constraint
  (from: UMLNamespace[MagicDrawUML],
   to: Set[UMLConstraint[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Namespace_importingNamespace_compose_packageImport_PackageImport
  (from: UMLNamespace[MagicDrawUML],
   to: Set[UMLPackageImport[MagicDrawUML]]): Try[Unit] =
	???

  // Node

  override def links_Node_node_compose_nestedNode_Node
  (from: UMLNode[MagicDrawUML],
   to: Set[UMLNode[MagicDrawUML]]): Try[Unit] =
	???

  // ObjectFlow

  override def links_ObjectFlow_objectFlow_reference_selection_Behavior
  (from: UMLObjectFlow[MagicDrawUML],
   to: Option[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ObjectFlow_objectFlow_reference_transformation_Behavior
  (from: UMLObjectFlow[MagicDrawUML],
   to: Option[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  override def set_ObjectFlow_isMulticast
  (e: UMLObjectFlow[MagicDrawUML], isMulticast: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLObjectFlow(e).getMagicDrawObjectFlow) map { _e =>
      _e.setMulticast(isMulticast)
      Unit
    }

  override def set_ObjectFlow_isMultireceive
  (e: UMLObjectFlow[MagicDrawUML], isMultireceive: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLObjectFlow(e).getMagicDrawObjectFlow) map { _e =>
      _e.setMultireceive(isMultireceive)
      Unit
    }

  // ObjectNode

  override def links_ObjectNode_objectNode_reference_inState_State
  (from: UMLObjectNode[MagicDrawUML],
   to: Set[UMLState[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ObjectNode_objectNode_reference_selection_Behavior
  (from: UMLObjectNode[MagicDrawUML],
   to: Option[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ObjectNode_objectNode_compose_upperBound_ValueSpecification
  (from: UMLObjectNode[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def set_ObjectNode_isControlType
  (e: UMLObjectNode[MagicDrawUML], isControlType: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLObjectNode(e).getMagicDrawObjectNode) map { _e =>
      _e.setControlType(isControlType)
      Unit
    }

  override def set_ObjectNode_ordering
  (e: UMLObjectNode[MagicDrawUML], ordering: UMLObjectNodeOrderingKind.Value): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLObjectNode(e).getMagicDrawObjectNode) map { _e =>
      _e.setOrdering(ordering match {
        case UMLObjectNodeOrderingKind.FIFO => com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ObjectNodeOrderingKindEnum.FIFO
        case UMLObjectNodeOrderingKind.LIFO => com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ObjectNodeOrderingKindEnum.LIFO
        case UMLObjectNodeOrderingKind.ordered => com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ObjectNodeOrderingKindEnum.ORDERED
        case UMLObjectNodeOrderingKind.unordered => com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ObjectNodeOrderingKindEnum.UNORDERED
      })
      Unit
    }

  // Observation


  // OccurrenceSpecification

  override def links_OccurrenceSpecification_events_reference_covered_Lifeline
  (from: UMLOccurrenceSpecification[MagicDrawUML],
   to: Iterable[UMLLifeline[MagicDrawUML]]): Try[Unit] =
	???

  override def links_OccurrenceSpecification_before_reference_toAfter_GeneralOrdering
  (from: UMLOccurrenceSpecification[MagicDrawUML],
   to: Set[UMLGeneralOrdering[MagicDrawUML]]): Try[Unit] =
	???

  override def links_OccurrenceSpecification_after_reference_toBefore_GeneralOrdering
  (from: UMLOccurrenceSpecification[MagicDrawUML],
   to: Set[UMLGeneralOrdering[MagicDrawUML]]): Try[Unit] =
	???

  // OpaqueAction

  override def links_OpaqueAction_opaqueAction_compose_inputValue_InputPin
  (from: UMLOpaqueAction[MagicDrawUML],
   to: Set[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_OpaqueAction_opaqueAction_compose_outputValue_OutputPin
  (from: UMLOpaqueAction[MagicDrawUML],
   to: Set[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def set_OpaqueAction_body
  (e: UMLOpaqueAction[MagicDrawUML], body: Seq[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLOpaqueAction(e).getMagicDrawOpaqueAction) map { _e =>
      _e.getBody().clear()
      for { b <- body } _e.getBody().add(b)
      Unit
    }

  override def set_OpaqueAction_language
  (e: UMLOpaqueAction[MagicDrawUML], language: Seq[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLOpaqueAction(e).getMagicDrawOpaqueAction) map { _e =>
      _e.getLanguage().clear()
      for { b <- language } _e.getLanguage().add(b)
      Unit
    }

  // OpaqueBehavior


  override def set_OpaqueBehavior_body
  (e: UMLOpaqueBehavior[MagicDrawUML], body: Seq[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLOpaqueBehavior(e).getMagicDrawOpaqueBehavior) map { _e =>
      _e.getBody().clear()
      for { b <- body } _e.getBody().add(b)
      Unit
    }

  override def set_OpaqueBehavior_language
  (e: UMLOpaqueBehavior[MagicDrawUML], language: Seq[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLOpaqueBehavior(e).getMagicDrawOpaqueBehavior) map { _e =>
      _e.getLanguage().clear()
      for { b <- language } _e.getLanguage().add(b)
      Unit
    }

  // OpaqueExpression

  override def links_OpaqueExpression_opaqueExpression_reference_behavior_Behavior
  (from: UMLOpaqueExpression[MagicDrawUML],
   to: Option[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  override def set_OpaqueExpression_body
  (e: UMLOpaqueExpression[MagicDrawUML], body: Seq[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLOpaqueExpression(e).getMagicDrawOpaqueExpression) map { _e =>
      _e.getBody().clear()
      for { b <- body } _e.getBody().add(b)
      Unit
    }

  override def set_OpaqueExpression_language
  (e: UMLOpaqueExpression[MagicDrawUML], language: Seq[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLOpaqueExpression(e).getMagicDrawOpaqueExpression) map { _e =>
      _e.getLanguage().clear()
      for { b <- language } _e.getLanguage().add(b)
      Unit
    }

  // Operation

  override def links_Operation_bodyContext_compose_bodyCondition_Constraint
  (from: UMLOperation[MagicDrawUML],
   to: Option[UMLConstraint[MagicDrawUML]]): Try[Unit] =
    composesOptionalLink(
      from, ops.umlMagicDrawUMLOperation,
      to, ops.umlMagicDrawUMLConstraint,
      (x:MagicDrawUMLOperation) => x.getMagicDrawOperation,
      (x:com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Operation,
       y:com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Constraint) => x.setBodyCondition(y),
      (x:MagicDrawUMLConstraint) => x.getMagicDrawConstraint)

  override def links_Operation_operation_compose_ownedParameter_Parameter
  (from: UMLOperation[MagicDrawUML],
   to: Seq[UMLParameter[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Operation_postContext_compose_postcondition_Constraint
  (from: UMLOperation[MagicDrawUML],
   to: Set[UMLConstraint[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Operation_preContext_compose_precondition_Constraint
  (from: UMLOperation[MagicDrawUML],
   to: Set[UMLConstraint[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Operation_operation_reference_raisedException_Type
  (from: UMLOperation[MagicDrawUML],
   to: Set[UMLType[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Operation_operation_reference_redefinedOperation_Operation
  (from: UMLOperation[MagicDrawUML],
   to: Set[UMLOperation[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Operation_parameteredElement_reference_templateParameter_OperationTemplateParameter
  (from: UMLOperation[MagicDrawUML],
   to: Option[UMLOperationTemplateParameter[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Operation_isQuery
  (e: UMLOperation[MagicDrawUML], isQuery: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLOperation(e).getMagicDrawOperation) map { _e =>
      _e.setQuery(isQuery)
      Unit
    }

  // OperationTemplateParameter

  override def links_OperationTemplateParameter_templateParameter_reference_parameteredElement_Operation
  (from: UMLOperationTemplateParameter[MagicDrawUML],
   to: Option[UMLOperation[MagicDrawUML]]): Try[Unit] =
	???

  // OutputPin


  // Package

  override def links_Package_receivingPackage_compose_packageMerge_PackageMerge
  (from: UMLPackage[MagicDrawUML],
   to: Set[UMLPackageMerge[MagicDrawUML]]): Try[Unit] =
    composesUnorderedLinks(
      from, ops.umlMagicDrawUMLPackage,
      to, ops.umlMagicDrawUMLPackageMerge,
      (x:MagicDrawUMLPackage) => x.getMagicDrawPackage,
      (x:com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package) => x.getPackageMerge,
      (x:MagicDrawUMLPackageMerge) => x.getMagicDrawPackageMerge)

  override def links_Package_owningPackage_compose_packagedElement_PackageableElement
  (from: UMLPackage[MagicDrawUML],
   to: Set[UMLPackageableElement[MagicDrawUML]]): Try[Unit] =
    composesUnorderedLinks(
      from, ops.umlMagicDrawUMLPackage,
      to, ops.umlMagicDrawUMLPackageableElement,
      (x:MagicDrawUMLPackage) => x.getMagicDrawPackage,
      (x:com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package) => x.getPackagedElement,
      (x:MagicDrawUMLPackageableElement) => x.getMagicDrawPackageableElement)

  override def links_Package_applyingPackage_compose_profileApplication_ProfileApplication
  (from: UMLPackage[MagicDrawUML],
   to: Set[UMLProfileApplication[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Package_URI
  (e: UMLPackage[MagicDrawUML], URI: Option[String]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLPackage(e).getMagicDrawPackage) map { _e =>
      URI match {
        case Some(s) => _e.setURI(s)
        case None => _e.setURI(null)
      }
      Unit
    }

  // PackageImport

  override def links_PackageImport_packageImport_reference_importedPackage_Package
  (from: UMLPackageImport[MagicDrawUML],
   to: Option[UMLPackage[MagicDrawUML]]): Try[Unit] =
	???

  override def set_PackageImport_visibility
  (e: UMLPackageImport[MagicDrawUML], visibility: UMLVisibilityKind.Value): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLPackageImport(e).getMagicDrawPackageImport) map { _e =>
      _e.setVisibility(visibility match {
        case UMLVisibilityKind._package => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PACKAGE
        case UMLVisibilityKind._private => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PRIVATE
        case UMLVisibilityKind._protected => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PROTECTED
        case UMLVisibilityKind.public => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PUBLIC
      })
      Unit
    }

  // PackageMerge

  override def links_PackageMerge_packageMerge_reference_mergedPackage_Package
  (from: UMLPackageMerge[MagicDrawUML],
   to: Option[UMLPackage[MagicDrawUML]]): Try[Unit] =
	???

  // PackageableElement


  override def set_PackageableElement_visibility
  (e: UMLPackageableElement[MagicDrawUML], visibility: Option[UMLVisibilityKind.Value]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLPackageableElement(e).getMagicDrawPackageableElement) map { _e =>
      _e.setVisibility(visibility match {
        case None => null
        case Some(v) => v match {
          case UMLVisibilityKind._package => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PACKAGE
          case UMLVisibilityKind._private => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PRIVATE
          case UMLVisibilityKind._protected => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PROTECTED
          case UMLVisibilityKind.public => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PUBLIC
        }
      })
      Unit
    }

  // Parameter

  override def links_Parameter_owningParameter_compose_defaultValue_ValueSpecification
  (from: UMLParameter[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Parameter_parameter_reference_parameterSet_ParameterSet
  (from: UMLParameter[MagicDrawUML],
   to: Set[UMLParameterSet[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Parameter_direction
  (e: UMLParameter[MagicDrawUML], direction: UMLParameterDirectionKind.Value): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLParameter(e).getMagicDrawParameter) map { _e =>
      _e.setDirection(direction match {
        case UMLParameterDirectionKind.in => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ParameterDirectionKindEnum.IN
        case UMLParameterDirectionKind.inout => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ParameterDirectionKindEnum.INOUT
        case UMLParameterDirectionKind.out => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ParameterDirectionKindEnum.OUT
        case UMLParameterDirectionKind._return => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ParameterDirectionKindEnum.RETURN
      })
      Unit
    }

  override def set_Parameter_effect
  (e: UMLParameter[MagicDrawUML], effect: Option[UMLParameterEffectKind.Value]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLParameter(e).getMagicDrawParameter) map { _e =>
      _e.setEffect(effect match {
        case None => null
        case Some(e) => e match {
          case UMLParameterEffectKind.create => com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ParameterEffectKindEnum.CREATE
          case UMLParameterEffectKind.delete => com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ParameterEffectKindEnum.DELETE
          case UMLParameterEffectKind.read => com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ParameterEffectKindEnum.READ
          case UMLParameterEffectKind.update => com.nomagic.uml2.ext.magicdraw.activities.mdcompleteactivities.ParameterEffectKindEnum.UPDATE
        }
      })
      Unit
    }

  override def set_Parameter_isException
  (e: UMLParameter[MagicDrawUML], isException: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLParameter(e).getMagicDrawParameter) map { _e =>
      _e.setException(isException)
      Unit
    }

  override def set_Parameter_isStream
  (e: UMLParameter[MagicDrawUML], isStream: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLParameter(e).getMagicDrawParameter) map { _e =>
      _e.setStream(isStream)
      Unit
    }

  // ParameterSet

  override def links_ParameterSet_parameterSet_compose_condition_Constraint
  (from: UMLParameterSet[MagicDrawUML],
   to: Set[UMLConstraint[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ParameterSet_parameterSet_reference_parameter_Parameter
  (from: UMLParameterSet[MagicDrawUML],
   to: Set[UMLParameter[MagicDrawUML]]): Try[Unit] =
	???

  // ParameterableElement

  override def links_ParameterableElement_parameteredElement_reference_templateParameter_TemplateParameter
  (from: UMLParameterableElement[MagicDrawUML],
   to: Option[UMLTemplateParameter[MagicDrawUML]]): Try[Unit] =
	???

  // PartDecomposition


  // Pin


  override def set_Pin_isControl
  (e: UMLPin[MagicDrawUML], isControl: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLPin(e).getMagicDrawPin) map { _e =>
      _e.setControl(isControl)
      Unit
    }

  // Port

  override def links_Port_port_reference_protocol_ProtocolStateMachine
  (from: UMLPort[MagicDrawUML],
   to: Option[UMLProtocolStateMachine[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Port_port_reference_redefinedPort_Port
  (from: UMLPort[MagicDrawUML],
   to: Set[UMLPort[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Port_isBehavior
  (e: UMLPort[MagicDrawUML], isBehavior: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLPort(e).getMagicDrawPort) map { _e =>
      _e.setBehavior(isBehavior)
      Unit
    }

  override def set_Port_isConjugated
  (e: UMLPort[MagicDrawUML], isConjugated: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLPort(e).getMagicDrawPort) map { _e =>
      _e.setConjugated(isConjugated)
      Unit
    }

  override def set_Port_isService
  (e: UMLPort[MagicDrawUML], isService: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLPort(e).getMagicDrawPort) map { _e =>
      _e.setService(isService)
      Unit
    }

  // PrimitiveType


  // Profile

  override def links_Profile_profile_compose_metaclassReference_ElementImport
  (from: UMLProfile[MagicDrawUML],
   to: Set[UMLElementImport[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Profile_profile_compose_metamodelReference_PackageImport
  (from: UMLProfile[MagicDrawUML],
   to: Set[UMLPackageImport[MagicDrawUML]]): Try[Unit] =
	???

  // ProfileApplication

  override def links_ProfileApplication_profileApplication_reference_appliedProfile_Profile
  (from: UMLProfileApplication[MagicDrawUML],
   to: Option[UMLProfile[MagicDrawUML]]): Try[Unit] =
	???

  override def set_ProfileApplication_isStrict
  (e: UMLProfileApplication[MagicDrawUML], isStrict: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLProfileApplication(e).getMagicDrawProfileApplication) map { _e =>
      _e.setStrict(isStrict)
      Unit
    }

  // Property

  override def links_Property_memberEnd_reference_association_Association
  (from: UMLProperty[MagicDrawUML],
   to: Option[UMLAssociation[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Property_owningProperty_compose_defaultValue_ValueSpecification
  (from: UMLProperty[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Property_associationEnd_compose_qualifier_Property
  (from: UMLProperty[MagicDrawUML],
   to: Seq[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Property_property_reference_redefinedProperty_Property
  (from: UMLProperty[MagicDrawUML],
   to: Set[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Property_property_reference_subsettedProperty_Property
  (from: UMLProperty[MagicDrawUML],
   to: Set[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Property_aggregation
  (e: UMLProperty[MagicDrawUML], aggregation: UMLAggregationKind.Value): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLProperty(e).getMagicDrawProperty) map { _e =>
      _e.setAggregation(aggregation match {
        case UMLAggregationKind.composite => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.AggregationKindEnum.COMPOSITE
        case UMLAggregationKind.none => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.AggregationKindEnum.NONE
        case UMLAggregationKind.shared => com.nomagic.uml2.ext.magicdraw.classes.mdkernel.AggregationKindEnum.SHARED
      })
      Unit
    }

  override def set_Property_isDerived
  (e: UMLProperty[MagicDrawUML], isDerived: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLProperty(e).getMagicDrawProperty) map { _e =>
      _e.setDerived(isDerived)
      Unit
    }

  override def set_Property_isDerivedUnion
  (e: UMLProperty[MagicDrawUML], isDerivedUnion: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLProperty(e).getMagicDrawProperty) map { _e =>
      _e.setDerivedUnion(isDerivedUnion)
      Unit
    }

  override def set_Property_isID
  (e: UMLProperty[MagicDrawUML], isID: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLProperty(e).getMagicDrawProperty) map { _e =>
      _e.setID(isID)
      Unit
    }

  // ProtocolConformance

  override def links_ProtocolConformance_protocolConformance_reference_generalMachine_ProtocolStateMachine
  (from: UMLProtocolConformance[MagicDrawUML],
   to: Option[UMLProtocolStateMachine[MagicDrawUML]]): Try[Unit] =
	???

  // ProtocolStateMachine

  override def links_ProtocolStateMachine_specificMachine_compose_conformance_ProtocolConformance
  (from: UMLProtocolStateMachine[MagicDrawUML],
   to: Set[UMLProtocolConformance[MagicDrawUML]]): Try[Unit] =
	???

  // ProtocolTransition

  override def links_ProtocolTransition_owningTransition_compose_postCondition_Constraint
  (from: UMLProtocolTransition[MagicDrawUML],
   to: Option[UMLConstraint[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ProtocolTransition_protocolTransition_compose_preCondition_Constraint
  (from: UMLProtocolTransition[MagicDrawUML],
   to: Option[UMLConstraint[MagicDrawUML]]): Try[Unit] =
	???

  // Pseudostate

  override def set_Pseudostate_kind
  (e: UMLPseudostate[MagicDrawUML], kind: UMLPseudostateKind.Value): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLPseudostate(e).getMagicDrawPseudostate) map { _e =>
      _e.setKind(kind match {
        case UMLPseudostateKind.choice => com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.CHOICE
        case UMLPseudostateKind.deepHistory => com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.DEEPHISTORY
        case UMLPseudostateKind.entryPoint => com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.ENTRYPOINT
        case UMLPseudostateKind.exitPoint => com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.EXITPOINT
        case UMLPseudostateKind.fork => com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.FORK
        case UMLPseudostateKind.initial => com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.INITIAL
        case UMLPseudostateKind.join => com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.JOIN
        case UMLPseudostateKind.junction => com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.JUNCTION
        case UMLPseudostateKind.shallowHistory => com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.SHALLOWHISTORY
        case UMLPseudostateKind.terminate => com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.PseudostateKindEnum.TERMINATE
      })
      Unit
    }

  // QualifierValue

  override def links_QualifierValue_qualifierValue_reference_qualifier_Property
  (from: UMLQualifierValue[MagicDrawUML],
   to: Option[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  override def links_QualifierValue_qualifierValue_reference_value_InputPin
  (from: UMLQualifierValue[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  // RaiseExceptionAction

  override def links_RaiseExceptionAction_raiseExceptionAction_compose_exception_InputPin
  (from: UMLRaiseExceptionAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  // ReadExtentAction

  override def links_ReadExtentAction_readExtentAction_reference_classifier_Classifier
  (from: UMLReadExtentAction[MagicDrawUML],
   to: Option[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ReadExtentAction_readExtentAction_compose_result_OutputPin
  (from: UMLReadExtentAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  // ReadIsClassifiedObjectAction

  override def links_ReadIsClassifiedObjectAction_readIsClassifiedObjectAction_reference_classifier_Classifier
  (from: UMLReadIsClassifiedObjectAction[MagicDrawUML],
   to: Option[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ReadIsClassifiedObjectAction_readIsClassifiedObjectAction_compose_object_InputPin
  (from: UMLReadIsClassifiedObjectAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ReadIsClassifiedObjectAction_readIsClassifiedObjectAction_compose_result_OutputPin
  (from: UMLReadIsClassifiedObjectAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def set_ReadIsClassifiedObjectAction_isDirect
  (e: UMLReadIsClassifiedObjectAction[MagicDrawUML], isDirect: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLReadIsClassifiedObjectAction(e).getMagicDrawReadIsClassifiedObjectAction) map { _e =>
      _e.setDirect(isDirect)
      Unit
    }

  // ReadLinkAction

  override def links_ReadLinkAction_readLinkAction_compose_result_OutputPin
  (from: UMLReadLinkAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  // ReadLinkObjectEndAction

  override def links_ReadLinkObjectEndAction_readLinkObjectEndAction_reference_end_Property
  (from: UMLReadLinkObjectEndAction[MagicDrawUML],
   to: Option[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ReadLinkObjectEndAction_readLinkObjectEndAction_compose_object_InputPin
  (from: UMLReadLinkObjectEndAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ReadLinkObjectEndAction_readLinkObjectEndAction_compose_result_OutputPin
  (from: UMLReadLinkObjectEndAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  // ReadLinkObjectEndQualifierAction

  override def links_ReadLinkObjectEndQualifierAction_readLinkObjectEndQualifierAction_compose_object_InputPin
  (from: UMLReadLinkObjectEndQualifierAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ReadLinkObjectEndQualifierAction_readLinkObjectEndQualifierAction_reference_qualifier_Property
  (from: UMLReadLinkObjectEndQualifierAction[MagicDrawUML],
   to: Option[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ReadLinkObjectEndQualifierAction_readLinkObjectEndQualifierAction_compose_result_OutputPin
  (from: UMLReadLinkObjectEndQualifierAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  // ReadSelfAction

  override def links_ReadSelfAction_readSelfAction_compose_result_OutputPin
  (from: UMLReadSelfAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  // ReadStructuralFeatureAction

  override def links_ReadStructuralFeatureAction_readStructuralFeatureAction_compose_result_OutputPin
  (from: UMLReadStructuralFeatureAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  // ReadVariableAction

  override def links_ReadVariableAction_readVariableAction_compose_result_OutputPin
  (from: UMLReadVariableAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  // Realization


  // Reception

  override def links_Reception_reception_reference_signal_Signal
  (from: UMLReception[MagicDrawUML],
   to: Option[UMLSignal[MagicDrawUML]]): Try[Unit] =
	???

  // ReclassifyObjectAction

  override def links_ReclassifyObjectAction_reclassifyObjectAction_reference_newClassifier_Classifier
  (from: UMLReclassifyObjectAction[MagicDrawUML],
   to: Set[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ReclassifyObjectAction_reclassifyObjectAction_compose_object_InputPin
  (from: UMLReclassifyObjectAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ReclassifyObjectAction_reclassifyObjectAction_reference_oldClassifier_Classifier
  (from: UMLReclassifyObjectAction[MagicDrawUML],
   to: Set[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  override def set_ReclassifyObjectAction_isReplaceAll
  (e: UMLReclassifyObjectAction[MagicDrawUML], isReplaceAll: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLReclassifyObjectAction(e).getMagicDrawReclassifyObjectAction) map { _e =>
      _e.setReplaceAll(isReplaceAll)
      Unit
    }

  // RedefinableElement


  override def set_RedefinableElement_isLeaf
  (e: UMLRedefinableElement[MagicDrawUML], isLeaf: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLRedefinableElement(e).getMagicDrawRedefinableElement) map { _e =>
      _e.setLeaf(isLeaf)
      Unit
    }

  // RedefinableTemplateSignature

  override def links_RedefinableTemplateSignature_redefinableTemplateSignature_reference_extendedSignature_RedefinableTemplateSignature
  (from: UMLRedefinableTemplateSignature[MagicDrawUML],
   to: Set[UMLRedefinableTemplateSignature[MagicDrawUML]]): Try[Unit] =
	???

  // ReduceAction

  override def links_ReduceAction_reduceAction_compose_collection_InputPin
  (from: UMLReduceAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ReduceAction_reduceAction_reference_reducer_Behavior
  (from: UMLReduceAction[MagicDrawUML],
   to: Option[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ReduceAction_reduceAction_compose_result_OutputPin
  (from: UMLReduceAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def set_ReduceAction_isOrdered
  (e: UMLReduceAction[MagicDrawUML], isOrdered: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLReduceAction(e).getMagicDrawReduceAction) map { _e =>
      _e.setOrdered(isOrdered)
      Unit
    }

  // Region

  override def links_Region_region_reference_extendedRegion_Region
  (from: UMLRegion[MagicDrawUML],
   to: Option[UMLRegion[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Region_container_compose_subvertex_Vertex
  (from: UMLRegion[MagicDrawUML],
   to: Set[UMLVertex[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Region_container_compose_transition_Transition
  (from: UMLRegion[MagicDrawUML],
   to: Set[UMLTransition[MagicDrawUML]]): Try[Unit] =
	???

  // Relationship


  // RemoveStructuralFeatureValueAction

  override def links_RemoveStructuralFeatureValueAction_removeStructuralFeatureValueAction_compose_removeAt_InputPin
  (from: UMLRemoveStructuralFeatureValueAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def set_RemoveStructuralFeatureValueAction_isRemoveDuplicates
  (e: UMLRemoveStructuralFeatureValueAction[MagicDrawUML], isRemoveDuplicates: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLRemoveStructuralFeatureValueAction(e).getMagicDrawRemoveStructuralFeatureValueAction) map { _e =>
      _e.setRemoveDuplicates(isRemoveDuplicates)
      Unit
    }

  // RemoveVariableValueAction

  override def links_RemoveVariableValueAction_removeVariableValueAction_compose_removeAt_InputPin
  (from: UMLRemoveVariableValueAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def set_RemoveVariableValueAction_isRemoveDuplicates
  (e: UMLRemoveVariableValueAction[MagicDrawUML], isRemoveDuplicates: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLRemoveVariableValueAction(e).getMagicDrawRemoveVariableValueAction) map { _e =>
      _e.setRemoveDuplicates(isRemoveDuplicates)
      Unit
    }

  // ReplyAction

  override def links_ReplyAction_replyAction_reference_replyToCall_Trigger
  (from: UMLReplyAction[MagicDrawUML],
   to: Option[UMLTrigger[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ReplyAction_replyAction_compose_replyValue_InputPin
  (from: UMLReplyAction[MagicDrawUML],
   to: Seq[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ReplyAction_replyAction_compose_returnInformation_InputPin
  (from: UMLReplyAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  // SendObjectAction

  override def links_SendObjectAction_sendObjectAction_compose_request_InputPin
  (from: UMLSendObjectAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_SendObjectAction_sendObjectAction_compose_target_InputPin
  (from: UMLSendObjectAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  // SendSignalAction

  override def links_SendSignalAction_sendSignalAction_reference_signal_Signal
  (from: UMLSendSignalAction[MagicDrawUML],
   to: Option[UMLSignal[MagicDrawUML]]): Try[Unit] =
	???

  override def links_SendSignalAction_sendSignalAction_compose_target_InputPin
  (from: UMLSendSignalAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  // SequenceNode

  override def links_SequenceNode_sequenceNode_compose_executableNode_ExecutableNode
  (from: UMLSequenceNode[MagicDrawUML],
   to: Seq[UMLExecutableNode[MagicDrawUML]]): Try[Unit] =
	???

  // Signal

  override def links_Signal_owningSignal_compose_ownedAttribute_Property
  (from: UMLSignal[MagicDrawUML],
   to: Seq[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  // SignalEvent

  override def links_SignalEvent_signalEvent_reference_signal_Signal
  (from: UMLSignalEvent[MagicDrawUML],
   to: Option[UMLSignal[MagicDrawUML]]): Try[Unit] =
	???

  // Slot

  override def links_Slot_slot_reference_definingFeature_StructuralFeature
  (from: UMLSlot[MagicDrawUML],
   to: Option[UMLStructuralFeature[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Slot_owningSlot_compose_value_ValueSpecification
  (from: UMLSlot[MagicDrawUML],
   to: Seq[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  // StartClassifierBehaviorAction

  override def links_StartClassifierBehaviorAction_startClassifierBehaviorAction_compose_object_InputPin
  (from: UMLStartClassifierBehaviorAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  // StartObjectBehaviorAction

  override def links_StartObjectBehaviorAction_startObjectBehaviorAction_compose_object_InputPin
  (from: UMLStartObjectBehaviorAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  // State

  override def links_State_state_compose_connection_ConnectionPointReference
  (from: UMLState[MagicDrawUML],
   to: Set[UMLConnectionPointReference[MagicDrawUML]]): Try[Unit] =
	???

  override def links_State_state_compose_connectionPoint_Pseudostate
  (from: UMLState[MagicDrawUML],
   to: Set[UMLPseudostate[MagicDrawUML]]): Try[Unit] =
	???

  override def links_State_state_compose_deferrableTrigger_Trigger
  (from: UMLState[MagicDrawUML],
   to: Set[UMLTrigger[MagicDrawUML]]): Try[Unit] =
	???

  override def links_State_state_compose_doActivity_Behavior
  (from: UMLState[MagicDrawUML],
   to: Option[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  override def links_State_state_compose_entry_Behavior
  (from: UMLState[MagicDrawUML],
   to: Option[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  override def links_State_state_compose_exit_Behavior
  (from: UMLState[MagicDrawUML],
   to: Option[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  override def links_State_state_reference_redefinedState_State
  (from: UMLState[MagicDrawUML],
   to: Option[UMLState[MagicDrawUML]]): Try[Unit] =
	???

  override def links_State_state_compose_region_Region
  (from: UMLState[MagicDrawUML],
   to: Set[UMLRegion[MagicDrawUML]]): Try[Unit] =
	???

  override def links_State_owningState_compose_stateInvariant_Constraint
  (from: UMLState[MagicDrawUML],
   to: Option[UMLConstraint[MagicDrawUML]]): Try[Unit] =
	???

  override def links_State_submachineState_reference_submachine_StateMachine
  (from: UMLState[MagicDrawUML],
   to: Option[UMLStateMachine[MagicDrawUML]]): Try[Unit] =
	???

  // StateInvariant

  override def links_StateInvariant_stateInvariant_reference_covered_Lifeline
  (from: UMLStateInvariant[MagicDrawUML],
   to: Iterable[UMLLifeline[MagicDrawUML]]): Try[Unit] =
	???

  override def links_StateInvariant_stateInvariant_compose_invariant_Constraint
  (from: UMLStateInvariant[MagicDrawUML],
   to: Option[UMLConstraint[MagicDrawUML]]): Try[Unit] =
	???

  // StateMachine

  override def links_StateMachine_stateMachine_compose_connectionPoint_Pseudostate
  (from: UMLStateMachine[MagicDrawUML],
   to: Set[UMLPseudostate[MagicDrawUML]]): Try[Unit] =
	???

  override def links_StateMachine_stateMachine_reference_extendedStateMachine_StateMachine
  (from: UMLStateMachine[MagicDrawUML],
   to: Set[UMLStateMachine[MagicDrawUML]]): Try[Unit] =
	???

  override def links_StateMachine_stateMachine_compose_region_Region
  (from: UMLStateMachine[MagicDrawUML],
   to: Set[UMLRegion[MagicDrawUML]]): Try[Unit] =
	???

  override def links_StateMachine_submachine_reference_submachineState_State
  (from: UMLStateMachine[MagicDrawUML],
   to: Set[UMLState[MagicDrawUML]]): Try[Unit] =
	???

  // Stereotype

  override def links_Stereotype_stereotype_compose_icon_Image
  (from: UMLStereotype[MagicDrawUML],
   to: Set[UMLImage[MagicDrawUML]]): Try[Unit] =
	???

  // StringExpression

  override def links_StringExpression_owningExpression_compose_subExpression_StringExpression
  (from: UMLStringExpression[MagicDrawUML],
   to: Seq[UMLStringExpression[MagicDrawUML]]): Try[Unit] =
	???

  // StructuralFeature


  override def set_StructuralFeature_isReadOnly
  (e: UMLStructuralFeature[MagicDrawUML], isReadOnly: Boolean): Try[Unit] =
  checkSession(ops.umlMagicDrawUMLStructuralFeature(e).getMagicDrawStructuralFeature) map { _e =>
      _e.setReadOnly(isReadOnly)
      Unit
    }

  // StructuralFeatureAction

  override def links_StructuralFeatureAction_structuralFeatureAction_compose_object_InputPin
  (from: UMLStructuralFeatureAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_StructuralFeatureAction_structuralFeatureAction_reference_structuralFeature_StructuralFeature
  (from: UMLStructuralFeatureAction[MagicDrawUML],
   to: Option[UMLStructuralFeature[MagicDrawUML]]): Try[Unit] =
	???

  // StructuredActivityNode

  override def links_StructuredActivityNode_inStructuredNode_compose_edge_ActivityEdge
  (from: UMLStructuredActivityNode[MagicDrawUML],
   to: Set[UMLActivityEdge[MagicDrawUML]]): Try[Unit] =
	???

  override def links_StructuredActivityNode_inStructuredNode_compose_node_ActivityNode
  (from: UMLStructuredActivityNode[MagicDrawUML],
   to: Set[UMLActivityNode[MagicDrawUML]]): Try[Unit] =
	???

  override def links_StructuredActivityNode_structuredActivityNode_compose_structuredNodeInput_InputPin
  (from: UMLStructuredActivityNode[MagicDrawUML],
   to: Set[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_StructuredActivityNode_structuredActivityNode_compose_structuredNodeOutput_OutputPin
  (from: UMLStructuredActivityNode[MagicDrawUML],
   to: Set[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_StructuredActivityNode_scope_compose_variable_Variable
  (from: UMLStructuredActivityNode[MagicDrawUML],
   to: Set[UMLVariable[MagicDrawUML]]): Try[Unit] =
	???

  override def set_StructuredActivityNode_mustIsolate
  (e: UMLStructuredActivityNode[MagicDrawUML], mustIsolate: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLStructuredActivityNode(e).getMagicDrawStructuredActivityNode) map { _e =>
      _e.setMustIsolate(mustIsolate)
      Unit
    }

  // StructuredClassifier

  override def links_StructuredClassifier_structuredClassifier_compose_ownedAttribute_Property
  (from: UMLStructuredClassifier[MagicDrawUML],
   to: Seq[UMLProperty[MagicDrawUML]]): Try[Unit] =
	???

  override def links_StructuredClassifier_structuredClassifier_compose_ownedConnector_Connector
  (from: UMLStructuredClassifier[MagicDrawUML],
   to: Set[UMLConnector[MagicDrawUML]]): Try[Unit] =
	???

  // Substitution

  override def links_Substitution_substitution_reference_contract_Classifier
  (from: UMLSubstitution[MagicDrawUML],
   to: Option[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  // TemplateBinding

  override def links_TemplateBinding_templateBinding_compose_parameterSubstitution_TemplateParameterSubstitution
  (from: UMLTemplateBinding[MagicDrawUML],
   to: Set[UMLTemplateParameterSubstitution[MagicDrawUML]]): Try[Unit] =
	???

  override def links_TemplateBinding_templateBinding_reference_signature_TemplateSignature
  (from: UMLTemplateBinding[MagicDrawUML],
   to: Option[UMLTemplateSignature[MagicDrawUML]]): Try[Unit] =
	???

  // TemplateParameter

  override def links_TemplateParameter_templateParameter_reference_default_ParameterableElement
  (from: UMLTemplateParameter[MagicDrawUML],
   to: Option[UMLParameterableElement[MagicDrawUML]]): Try[Unit] =
	???

  override def links_TemplateParameter_templateParameter_compose_ownedDefault_ParameterableElement
  (from: UMLTemplateParameter[MagicDrawUML],
   to: Option[UMLParameterableElement[MagicDrawUML]]): Try[Unit] =
	???

  override def links_TemplateParameter_owningTemplateParameter_compose_ownedParameteredElement_ParameterableElement
  (from: UMLTemplateParameter[MagicDrawUML],
   to: Option[UMLParameterableElement[MagicDrawUML]]): Try[Unit] =
	???

  override def links_TemplateParameter_templateParameter_reference_parameteredElement_ParameterableElement
  (from: UMLTemplateParameter[MagicDrawUML],
   to: Option[UMLParameterableElement[MagicDrawUML]]): Try[Unit] =
	???

  // TemplateParameterSubstitution

  override def links_TemplateParameterSubstitution_templateParameterSubstitution_reference_actual_ParameterableElement
  (from: UMLTemplateParameterSubstitution[MagicDrawUML],
   to: Option[UMLParameterableElement[MagicDrawUML]]): Try[Unit] =
	???

  override def links_TemplateParameterSubstitution_templateParameterSubstitution_reference_formal_TemplateParameter
  (from: UMLTemplateParameterSubstitution[MagicDrawUML],
   to: Option[UMLTemplateParameter[MagicDrawUML]]): Try[Unit] =
	???

  override def links_TemplateParameterSubstitution_owningTemplateParameterSubstitution_compose_ownedActual_ParameterableElement
  (from: UMLTemplateParameterSubstitution[MagicDrawUML],
   to: Option[UMLParameterableElement[MagicDrawUML]]): Try[Unit] =
	???

  // TemplateSignature

  override def links_TemplateSignature_signature_compose_ownedParameter_TemplateParameter
  (from: UMLTemplateSignature[MagicDrawUML],
   to: Seq[UMLTemplateParameter[MagicDrawUML]]): Try[Unit] =
	???

  override def links_TemplateSignature_templateSignature_reference_parameter_TemplateParameter
  (from: UMLTemplateSignature[MagicDrawUML],
   to: Seq[UMLTemplateParameter[MagicDrawUML]]): Try[Unit] =
	???

  // TemplateableElement

  override def links_TemplateableElement_template_compose_ownedTemplateSignature_TemplateSignature
  (from: UMLTemplateableElement[MagicDrawUML],
   to: Option[UMLTemplateSignature[MagicDrawUML]]): Try[Unit] =
	???

  override def links_TemplateableElement_boundElement_compose_templateBinding_TemplateBinding
  (from: UMLTemplateableElement[MagicDrawUML],
   to: Set[UMLTemplateBinding[MagicDrawUML]]): Try[Unit] =
	???

  // TestIdentityAction

  override def links_TestIdentityAction_testIdentityAction_compose_first_InputPin
  (from: UMLTestIdentityAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_TestIdentityAction_testIdentityAction_compose_result_OutputPin
  (from: UMLTestIdentityAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_TestIdentityAction_testIdentityAction_compose_second_InputPin
  (from: UMLTestIdentityAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  // TimeConstraint

  override def links_TimeConstraint_timeConstraint_compose_specification_TimeInterval
  (from: UMLTimeConstraint[MagicDrawUML],
   to: Option[UMLTimeInterval[MagicDrawUML]]): Try[Unit] =
	???

  override def set_TimeConstraint_firstEvent
  (e: UMLTimeConstraint[MagicDrawUML], firstEvent: Option[Boolean]): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLTimeConstraint(e).getMagicDrawTimeConstraint) map { _e =>
      _e.setFirstEvent(firstEvent match {
        case Some(b) => b
        case None => true
      })
      Unit
    }

  // TimeEvent

  override def links_TimeEvent_timeEvent_compose_when_TimeExpression
  (from: UMLTimeEvent[MagicDrawUML],
   to: Option[UMLTimeExpression[MagicDrawUML]]): Try[Unit] =
	???

  override def set_TimeEvent_isRelative
  (e: UMLTimeEvent[MagicDrawUML], isRelative: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLTimeEvent(e).getMagicDrawTimeEvent) map { _e =>
      _e.setRelative(isRelative)
      Unit
    }

  // TimeExpression

  override def links_TimeExpression_timeExpression_compose_expr_ValueSpecification
  (from: UMLTimeExpression[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  override def links_TimeExpression_timeExpression_reference_observation_Observation
  (from: UMLTimeExpression[MagicDrawUML],
   to: Set[UMLObservation[MagicDrawUML]]): Try[Unit] =
	???

  // TimeInterval

  override def links_TimeInterval_timeInterval_reference_max_TimeExpression
  (from: UMLTimeInterval[MagicDrawUML],
   to: Option[UMLTimeExpression[MagicDrawUML]]): Try[Unit] =
	???

  override def links_TimeInterval_timeInterval_reference_min_TimeExpression
  (from: UMLTimeInterval[MagicDrawUML],
   to: Option[UMLTimeExpression[MagicDrawUML]]): Try[Unit] =
	???

  // TimeObservation

  override def links_TimeObservation_timeObservation_reference_event_NamedElement
  (from: UMLTimeObservation[MagicDrawUML],
   to: Option[UMLNamedElement[MagicDrawUML]]): Try[Unit] =
	???

  override def set_TimeObservation_firstEvent
  (e: UMLTimeObservation[MagicDrawUML], firstEvent: Boolean): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLTimeObservation(e).getMagicDrawTimeObservation) map { _e =>
      _e.setFirstEvent(firstEvent)
      Unit
    }

  // Transition

  override def links_Transition_transition_compose_effect_Behavior
  (from: UMLTransition[MagicDrawUML],
   to: Option[UMLBehavior[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Transition_transition_compose_guard_Constraint
  (from: UMLTransition[MagicDrawUML],
   to: Option[UMLConstraint[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Transition_transition_reference_redefinedTransition_Transition
  (from: UMLTransition[MagicDrawUML],
   to: Option[UMLTransition[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Transition_transition_compose_trigger_Trigger
  (from: UMLTransition[MagicDrawUML],
   to: Set[UMLTrigger[MagicDrawUML]]): Try[Unit] =
	???

  override def set_Transition_kind
  (e: UMLTransition[MagicDrawUML], kind: UMLTransitionKind.Value): Try[Unit] =
    checkSession(ops.umlMagicDrawUMLTransition(e).getMagicDrawTransition) map { _e =>
      _e.setKind(kind match {
        case UMLTransitionKind.external => com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.TransitionKindEnum.EXTERNAL
        case UMLTransitionKind.internal => com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.TransitionKindEnum.INTERNAL
        case UMLTransitionKind.local => com.nomagic.uml2.ext.magicdraw.statemachines.mdbehaviorstatemachines.TransitionKindEnum.LOCAL
      })
      Unit
    }

  // Trigger

  override def links_Trigger_trigger_reference_event_Event
  (from: UMLTrigger[MagicDrawUML],
   to: Option[UMLEvent[MagicDrawUML]]): Try[Unit] =
	???

  override def links_Trigger_trigger_reference_port_Port
  (from: UMLTrigger[MagicDrawUML],
   to: Set[UMLPort[MagicDrawUML]]): Try[Unit] =
	???

  // Type


  // TypedElement

  override def links_TypedElement_typedElement_reference_type_Type
  (from: UMLTypedElement[MagicDrawUML],
   to: Option[UMLType[MagicDrawUML]]): Try[Unit] =
	???

  // UnmarshallAction

  override def links_UnmarshallAction_unmarshallAction_compose_object_InputPin
  (from: UMLUnmarshallAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_UnmarshallAction_unmarshallAction_compose_result_OutputPin
  (from: UMLUnmarshallAction[MagicDrawUML],
   to: Seq[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_UnmarshallAction_unmarshallAction_reference_unmarshallType_Classifier
  (from: UMLUnmarshallAction[MagicDrawUML],
   to: Option[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  // Usage


  // UseCase

  override def links_UseCase_extension_compose_extend_Extend
  (from: UMLUseCase[MagicDrawUML],
   to: Set[UMLExtend[MagicDrawUML]]): Try[Unit] =
	???

  override def links_UseCase_useCase_compose_extensionPoint_ExtensionPoint
  (from: UMLUseCase[MagicDrawUML],
   to: Set[UMLExtensionPoint[MagicDrawUML]]): Try[Unit] =
	???

  override def links_UseCase_includingCase_compose_include_Include
  (from: UMLUseCase[MagicDrawUML],
   to: Set[UMLInclude[MagicDrawUML]]): Try[Unit] =
	???

  override def links_UseCase_useCase_reference_subject_Classifier
  (from: UMLUseCase[MagicDrawUML],
   to: Set[UMLClassifier[MagicDrawUML]]): Try[Unit] =
	???

  // ValuePin

  override def links_ValuePin_valuePin_compose_value_ValueSpecification
  (from: UMLValuePin[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  // ValueSpecification


  // ValueSpecificationAction

  override def links_ValueSpecificationAction_valueSpecificationAction_compose_result_OutputPin
  (from: UMLValueSpecificationAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_ValueSpecificationAction_valueSpecificationAction_compose_value_ValueSpecification
  (from: UMLValueSpecificationAction[MagicDrawUML],
   to: Option[UMLValueSpecification[MagicDrawUML]]): Try[Unit] =
	???

  // Variable


  // VariableAction

  override def links_VariableAction_variableAction_reference_variable_Variable
  (from: UMLVariableAction[MagicDrawUML],
   to: Option[UMLVariable[MagicDrawUML]]): Try[Unit] =
	???

  // Vertex


  // WriteLinkAction


  // WriteStructuralFeatureAction

  override def links_WriteStructuralFeatureAction_writeStructuralFeatureAction_compose_result_OutputPin
  (from: UMLWriteStructuralFeatureAction[MagicDrawUML],
   to: Option[UMLOutputPin[MagicDrawUML]]): Try[Unit] =
	???

  override def links_WriteStructuralFeatureAction_writeStructuralFeatureAction_compose_value_InputPin
  (from: UMLWriteStructuralFeatureAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

  // WriteVariableAction

  override def links_WriteVariableAction_writeVariableAction_compose_value_InputPin
  (from: UMLWriteVariableAction[MagicDrawUML],
   to: Option[UMLInputPin[MagicDrawUML]]): Try[Unit] =
	???

}