package org.omg.oti.magicdraw

import org.omg.oti._
import org.omg.oti.operations._

trait MagicDrawUMLElementImport 
  extends UMLElementImport[MagicDrawUML]
  with MagicDrawUMLDirectedRelationship {

  override protected def e: Uml#ElementImport
  import ops._

  override def alias = 
    e.getAlias match {
    case null => None
    case "" => None
    case s => Some( s )
  }
    
  override def importedElement =
    Option.apply( e.getImportedElement )
    
  override def importingNamespace =
    Option.apply( e.getImportingNamespace )
    
  override def metaclassReference_profile =
    Option.apply( e.get_profileOfMetaclassReference )
}
