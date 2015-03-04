package org.omg.oti.magicdraw

import scala.collection.JavaConversions._

import org.omg.oti.api._
import org.omg.oti.operations._

trait MagicDrawUMLPackageableElement 
  extends UMLPackageableElement[MagicDrawUML]
  with MagicDrawUMLNamedElement
  with MagicDrawUMLParameterableElement {

  override protected def e: Uml#PackageableElement
  import ops._
  
  override def visibility = e.getVisibility match {
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PUBLIC => Some( UMLVisibilityKind.public )
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PRIVATE => Some( UMLVisibilityKind._private )
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PROTECTED => Some( UMLVisibilityKind._protected )
    case com.nomagic.uml2.ext.magicdraw.classes.mdkernel.VisibilityKindEnum.PACKAGE => Some( UMLVisibilityKind._package )
  }
  
  override def importedElement_import = e.get_elementImportOfImportedElement.toSet[Uml#ElementImport]
  
  override def deployedElement_deploymentTarget = ???
  
  override def importedMember_namespace = ???
  
  override def utilizedElement_manifestation = e.get_manifestationOfUtilizedElement.toSet[Uml#Manifestation]
  
}