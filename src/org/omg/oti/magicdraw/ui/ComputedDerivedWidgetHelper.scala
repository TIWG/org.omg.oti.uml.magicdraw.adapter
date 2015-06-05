/*
 *
 * License Terms
 *
 * Copyright (c) 2014-2015, California Institute of Technology ("Caltech").
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
package org.omg.oti.magicdraw.ui

import java.awt.event.ActionEvent
import java.awt.event.InputEvent
import javax.swing.JOptionPane
import scala.collection.JavaConversions._
import scala.language.postfixOps
import scala.util.Failure
import scala.util.Success
import scala.util.Try
import com.nomagic.magicdraw.core.Application
import com.nomagic.magicdraw.core.Project
import com.nomagic.magicdraw.ui.dialogs.specifications.SpecificationDialogManager
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element
import gov.nasa.jpl.dynamicScripts.DynamicScriptsTypes
import gov.nasa.jpl.dynamicScripts.magicdraw.DynamicScriptsPlugin
import gov.nasa.jpl.dynamicScripts.magicdraw.designations.MagicDrawElementKindDesignation
import gov.nasa.jpl.dynamicScripts.magicdraw.specificationDialog.SpecificationComputedComponent
import gov.nasa.jpl.dynamicScripts.magicdraw.ui.nodes._
import gov.nasa.jpl.dynamicScripts.magicdraw.ui.tables._
import gov.nasa.jpl.dynamicScripts.magicdraw.utils._
import org.omg.oti.api._
import org.omg.oti.magicdraw._
import scala.reflect.{ classTag, ClassTag }


object ComputedDerivedWidgetHelper {

  def makeComputedDerivedTreeForPackageNameMetaclass( derived: DynamicScriptsTypes.ComputedDerivedWidget ): DynamicScriptsTypes.ComputedDerivedTree =
    DynamicScriptsTypes.ComputedDerivedTree(
      derived.name, derived.icon, derived.context, derived.access,
      derived.className, derived.methodName, derived.refresh,
      columnValueTypes = Some( Seq(
        DynamicScriptsTypes.DerivedFeatureValueType(
          key = DynamicScriptsTypes.SName( "context" ),
          typeName = DynamicScriptsTypes.HName( "context" ),
          typeInfo = DynamicScriptsTypes.StringTypeDesignation() ),
        DynamicScriptsTypes.DerivedFeatureValueType(
          key = DynamicScriptsTypes.SName( "name" ),
          typeName = DynamicScriptsTypes.HName( "name" ),
          typeInfo = DynamicScriptsTypes.StringTypeDesignation() ),
        DynamicScriptsTypes.DerivedFeatureValueType(
          key = DynamicScriptsTypes.SName( "metaclass" ),
          typeName = DynamicScriptsTypes.HName( "metaclass" ),
          typeInfo = DynamicScriptsTypes.StringTypeDesignation() ) ) ) )

  def createRowForElement( e: UMLElement[MagicDrawUML] )( implicit umlUtil: MagicDrawUMLUtil ): Map[String, AbstractTreeNodeInfo] = {
    import umlUtil._
    Map(
      "context" -> ( e.owner match {
        case None => LabelNodeInfo( "<none>" )
        case Some( o ) => o match {
          case parent: UMLNamedElement[Uml] => ReferenceNodeInfo( parent.qualifiedName.get, parent.getMagicDrawElement )
          case parent                       => ReferenceNodeInfo( parent.id, parent.getMagicDrawElement )
        }
      } ),
      "name" ->
        ( e match {
          case ne: UMLNamedElement[Uml] =>
            ReferenceNodeInfo(
              ( ne, ne.name ) match {
                case ( l: UMLLiteralBoolean[Uml], _ )          => l.value.toString
                case ( l: UMLLiteralInteger[Uml], _ )          => l.value.toString
                case ( l: UMLLiteralReal[Uml], _ )             => l.value.toString
                case ( l: UMLLiteralString[Uml], _ )           => l.value.toString
                case ( l: UMLLiteralUnlimitedNatural[Uml], _ ) => l.value.toString
                case ( v: UMLInstanceValue[Uml], _ ) => v.instance match {
                  case None      => "<unbound element>"
                  case Some( e ) => s"=> ${e.mofMetaclassName}: ${e.id}"
                }
                case ( v: MagicDrawUMLElementValue, _ ) => v.element match {
                  case None      => "<unbound element>"
                  case Some( e ) => s"=> ${e.mofMetaclassName}: ${e.id}"
                }
                case ( _, Some( name ) ) => name
                case ( _, _ )            => ne.id
              },
              e.getMagicDrawElement )
          case e: UMLElement[Uml] =>
            ReferenceNodeInfo( e.id, e.getMagicDrawElement )
        } ),
      "metaclass" -> LabelNodeInfo( e.xmiType.head ) )
  }

  def createGroupTableUIPanelForElements[U <: UMLElement[MagicDrawUML]](
    derived: DynamicScriptsTypes.ComputedDerivedWidget,
    pes: Iterable[U] )( implicit util: MagicDrawUMLUtil ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = {

    val rows: Seq[Map[String, AbstractTreeNodeInfo]] = pes map ( createRowForElement( _ ) ) toSeq

    val ui = GroupTableNodeUI(
      makeComputedDerivedTreeForPackageNameMetaclass( derived ),
      rows,
      Seq( "context", "name", "metaclass" ) )
    //ui._table.addMouseListener( DoubleClickMouseListener.createAbstractTreeNodeInfoDoubleClickMouseListener( ui._table ) )
    HyperlinkTableCellValueEditorRenderer.addRenderer4AbstractTreeNodeInfo( ui._table )

    val validationAnnotations = rows flatMap
      ( _.values ) flatMap
      ( AbstractTreeNodeInfo.collectAnnotationsRecursively( _ ) ) toSeq

    Success( ( ui.panel, validationAnnotations ) )
  }

  def elementOperationWidget[U <: UMLElement[MagicDrawUML], V <: UMLElement[MagicDrawUML]](
    derived: DynamicScriptsTypes.ComputedDerivedWidget,
    mdE: MagicDrawUML#Element,
    f: Function1[U, Iterable[V]],
    util: MagicDrawUMLUtil )( implicit uTag: ClassTag[U] ): Try[( java.awt.Component, Seq[ValidationAnnotation] )] = {
      val e = util.umlElement( mdE )
      val uClass = uTag.runtimeClass
      require (uClass != null)
      if ( uClass.isInstance( e ) ) 
        createGroupTableUIPanelForElements[V]( derived, f( e.asInstanceOf[U] ) )( util )
      else 
        Failure( new IllegalArgumentException(s"${mdE.getHumanType}: ${mdE.getID} is not a kind of ${uClass.getName}"))
  }
    

}