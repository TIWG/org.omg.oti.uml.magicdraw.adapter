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
import org.omg.oti._
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