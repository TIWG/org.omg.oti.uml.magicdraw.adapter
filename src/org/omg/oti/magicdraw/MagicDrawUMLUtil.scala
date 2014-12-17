package org.omg.oti.magicdraw

import scala.util.Failure
import scala.util.Success
import scala.util.Try

import com.nomagic.magicdraw.core.Project
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.ElementValue

import org.omg.oti.IllegalElementException

case class MagicDrawUMLUtil( val project: Project ) 
    extends org.omg.oti.UMLUtil[MagicDrawUML] with MagicDrawUMLOps { self =>
  
  import self._
  
  val element2id = scala.collection.mutable.HashMap[MagicDrawUML#Element, Try[String]]()
  
  val MD_crule0: ContainedElement2IDRule = {
    case ( owner, ownerID, cf, is: MagicDrawUML#InstanceSpecification ) if ( is.getOwner.getAppliedStereotypeInstance == is ) =>
      Success( ownerID + "_" + xmlSafeID( cf.getName ) + ".appliedStereotypeInstance" )
  }
  
  val MD_crule1a0: ContainedElement2IDRule = {
    case ( owner, ownerID, cf, ev: ElementValue ) =>
      ev.getElement match {
        case null => Failure( IllegalElementException[MagicDrawUML]( "ElementValue without Element is not supported", ev.asInstanceOf[MagicDrawUML#Element] ))
        case nev: MagicDrawUML#NamedElement =>
          getNamedElement_name( nev ) match {
            case None => Failure( IllegalElementException[MagicDrawUML]( "ElementValue must refer to a named NamedElement", ev.asInstanceOf[MagicDrawUML#Element] ))
            case Some( n ) => Success( ownerID + "_" + xmlSafeID( cf.getName ) + "." + n )
          }
      }
  }
  
  protected val elementRules = List( rule0 )
  protected val containmentRules = List( MD_crule0, crule1, MD_crule1a0, crule1a, crule1b, crule2, crule3, crule4, crule5, crule6 )
       
}