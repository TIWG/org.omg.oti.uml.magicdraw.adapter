import sbt._
import sbt.RichURI.fromURI
import Keys._
import xerial.sbt.Pack._
import com.typesafe.sbt.SbtGit._
import com.banno.license.Plugin.LicenseKeys._
import java.io.File
import java.net.URI
import java.util.Locale

/**
 * sbt -Dsbt.global.staging=sbt.staging -Doti.core.local=<dir where org.omg.oti is checked out from SVN>
 */
object OTIMagicDraw extends Build {
  
  override def buildLoaders = BuildLoader.resolve( mygitResolver ) :: BuildLoader.resolve( mysvnResolver ) :: Nil

  /**
   * With the current 'git' resolver, a URI like this: ".../gov.nasa.jpl.A" results in a "shortName" as just "gov".
   * In principle, this isn't bad since this "shortName" is in a directory created based on the hash of the entire URI.
   * However, it results in a confusing situation when we have multiple sub-projects like this:
   * .../<hash1>/gov
   * .../<hash2>/gov
   * .../<hash3>/gov
   * It makes it difficult to tell what each project is.
   *
   *
   * @see http://www.scala-sbt.org/0.13.5/docs/Extending/Build-Loaders.html
   */
  abstract class MyResolver( override val scheme: String ) extends Resolvers.DistributedVCS {

    override def toResolver: BuildLoader.Resolver = ( info: BuildLoader.ResolveInfo ) => {
      val uri = info.uri.withoutMarkerScheme
      val localCopy = uniqueSubdirectoryFor( normalized( uri ), in = info.staging )
      val from = uri.withoutFragment.toASCIIString

      if ( uri.hasFragment ) {
        val branch = uri.getFragment
        Some {
          () =>
            creates( localCopy ) {
              clone( from, to = localCopy )
              checkout( from, branch, in = localCopy )
            }
        }
      }
      else Some { () => creates( localCopy ) { clone( from, to = localCopy ) } }
    }

    def checkout( from: String, branch: String, in: File )
    
    private def normalized( uri: URI ) = uri.copy( scheme = scheme )

    def uniqueSubdirectoryFor( uri: URI, in: File ) = {
      in.mkdirs()
      val base = new File( in, Hash.halfHashString( uri.normalize.toASCIIString ) )
      val last = shortName( uri ) match { case Some( n ) => normalizeDirectoryName( n ); case None => "root" }
      new File( base, last )
    }

    def shortName( uri: URI ): Option[String] =
      Option( uri.withoutMarkerScheme.getPath ).flatMap { _.split( "/" ).map( _.trim ).filterNot( _.isEmpty ).lastOption }

    def normalizeDirectoryName( name: String ): String

    private lazy val onWindows = {
      val os = System.getenv( "OSTYPE" )
      val isCygwin = ( os != null ) && os.toLowerCase( Locale.ENGLISH ).contains( "cygwin" )
      val isWindows = System.getProperty( "os.name", "" ).toLowerCase( Locale.ENGLISH ).contains( "windows" )
      isWindows && !isCygwin
    }

    def run( command: String* ) {
      run( None, command: _* )
    }

    def run( cwd: Option[File], command: String* ) {
      val result = Process(
        if ( onWindows ) "cmd" +: "/c" +: command
        else command,
        cwd ) !;
      if ( result != 0 )
        sys.error( "Nonzero exit code (" + result + "): " + command.mkString( " " ) )
    }

    def creates( file: File )( f: => Unit ) =
      {
        if ( !file.exists )
          try {
            f
          }
          catch {
            case e: Throwable =>
              IO.delete( file )
              throw e
          }
        file
      }

  }

  class MyGITResolver extends MyResolver( "mygit" ) {

    override def clone( from: String, to: File ) = {
      run( "git", "clone", from, to.getAbsolutePath )
    }

    override def checkout( from: String, branch: String, in: File ) =
      checkout( branch, in )
      
    override def checkout( branch: String, in: File ) = {
      run( Some( in ), "git", "checkout", "-q", branch )
    }

    override def normalizeDirectoryName( name: String ): String =
      StringUtilities.normalize( dropGITExtension( name ) )

    def dropGITExtension( name: String ): String =
      if ( name.endsWith( ".git" ) ) name.substring( 0, name.length - 4 ) else name

  }

  class MySVNResolver extends MyResolver( "mysvn" ) {

    override def clone( from: String, to: File ) = ()

    override def checkout( from: String, revision: String, in: File ) = {
      run("svn", "checkout", "-q", "-r", revision, from, in.getAbsolutePath)
    }
   
    override def checkout( branch: String, in: File ) = ()

    override def normalizeDirectoryName( name: String ): String =
      StringUtilities.normalize( name )

  }

  def mygitResolver( i: BuildLoader.ResolveInfo ): Option[() => File] =
    if ( i.uri.getScheme != "mygit" )
      None
    else
      new MyGITResolver().toResolver( i )
      
  def mysvnResolver( i: BuildLoader.ResolveInfo ): Option[() => File] =
    if ( i.uri.getScheme != "mysvn" )
      None
    else
      new MySVNResolver().toResolver( i )

  case class DependentProjectInfo( val resolverName: String, val name: String, val remoteURLFormat: String, val defaultVersion: String ) {

    private lazy val onWindows = {
      val os = System.getenv( "OSTYPE" )
      val isCygwin = ( os != null ) && os.toLowerCase( Locale.ENGLISH ).contains( "cygwin" )
      val isWindows = System.getProperty( "os.name", "" ).toLowerCase( Locale.ENGLISH ).contains( "windows" )
      isWindows && !isCygwin
    }

    def run( cwd: Option[File], command: String* ): String = {
      Process(
        if ( onWindows ) "cmd" +: "/c" +: command
        else command,
        cwd ).!!.trim
    }

    lazy val user = System.getProperty( name + ".user", System.getProperty( "user.name" ) )
    lazy val version = Option.apply( System.getProperty( name + ".local" ) ) match {
      case None => defaultVersion
      case Some( local ) =>
        val dir = new File( local )
        require( dir.exists() )
        require( dir.isDirectory() )
        resolverName match {
          case "mysvn" => ""
          case "mygit" => run( Some( dir ), "git", "log", "-n1", "--format=%h" )
          case _ => ""
        }        
    }
    lazy val remoteURL = System.getProperty( name + ".remote", remoteURLFormat ).replace("%s", user )
    lazy val localURL = "file://" + Option.apply( System.getProperty( name + ".local" ) ).getOrElse( throw new IllegalArgumentException( s"Need to specify the system property: ${name}.local" ) )
    lazy val repoMode = ( System.getProperty( name + ".repo", "" ), System.getProperty( name + ".local", "" ), System.getProperty( name + ".remote", "" ) ) match {
      case ( "local", _, _ )                   => "local"
      case ( "remote", _, _ )                  => "remote"
      case ( "", url, _ ) if ( url.nonEmpty )  => "local"
      case ( "", "", url ) if ( url.nonEmpty ) => "remote"
      case ( x, _, _ )                         => throw new IllegalArgumentException( s"The value of the property ${name}.repo must be either local or remote, not: ${x}" )
    }
    lazy val defaultURL = repoMode match {
      case "remote" => remoteURL
      case "local"  => localURL
    }
    lazy val defaultURI = s"${resolverName}:${defaultURL}#${version}"
    lazy val getURI = System.getProperty( name + ".uri", defaultURI )
  }

  val otiCoreInfo = DependentProjectInfo( "mysvn",
    "oti.core",
    "http://svn.omg.org/repos/TIWG/Eclipse%20Scala%20Projects/org.omg.oti",
    "620" )
    
  object Versions {
    val scala = "2.11.5"
    val oti_core_uri = otiCoreInfo.getURI
  }
    
  def makeProjectRef( uri: URI, id: String ) =
    if ( file( id ).exists ) ProjectRef( file( id ), id )
    else ProjectRef( uri, id )

  val addFromEclipseClasspath = TaskKey[Unit](
    "addFromEclipseClasspath",
    "Adds to the SBT classpath the contents of the Eclipse classpath")
  
  lazy val oti = makeProjectRef( uri( Versions.oti_core_uri ), "oti-core" )
    
  lazy val md = Project(
      "oti-magicDraw",
      file( "." ),
      settings = Defaults.coreDefaultSettings ++ Defaults.runnerSettings ++ Defaults.baseTasks ++ com.banno.license.Plugin.licenseSettings ++ Seq(            
        scalaVersion := Versions.scala,
        removeExistingHeaderBlock := true,
        scalaSource in Compile := baseDirectory.value / "src",
        shellPrompt := { state => Project.extract(state).currentRef.project + "> " }
      )
    ) dependsOn (
        oti % "test->test;compile->compile" )
          
  val extraPackFun: Def.Initialize[Task[Seq[( File, String )]]] = Def.task[Seq[( File, String )]] {
    def getFileIfExists( f: File, where: String ): Option[( File, String )] = if ( f.exists() ) Some( ( f, s"${where}/${f.getName()}" ) ) else None

    val ivyHome: File = Classpaths.bootIvyHome( appConfiguration.value ) getOrElse sys.error( "Launcher did not provide the Ivy home directory." )
    
    // this is a workaround; how should it be done properly in sbt?

    // goal: process the list of library dependencies of the project.
    // that is, we should be able to tell the classification of each library dependency module as shown in sbt:
    //
    // > show libraryDependencies
    // [info] List(
    //    org.scala-lang:scala-library:2.11.2, 
    //    org.scala-lang:scala-library:2.11.2:provided, 
    //    org.scala-lang:scala-compiler:2.11.2:provided, 
    //    org.scala-lang:scala-reflect:2.11.2:provided, 
    //    com.typesafe:config:1.2.1:compile, 
    //    org.scalacheck:scalacheck:1.11.5:compile, 
    //    org.scalatest:scalatest:2.2.1:compile, 
    //    org.specs2:specs2:2.4:compile, 
    //    org.parboiled:parboiled:2.0.0:compile)

    // but... libraryDependencies is a SettingKey (see ld below)
    // I haven't figured out how to get the sequence of modules from it.
    val ld: SettingKey[Seq[ModuleID]] = libraryDependencies
    
    // workaround... I found this API that I managed to call...
    // this overrides the classification of all jars -- i.e., it is as if all library dependencies had been classified as "compile".

    // for now... it's a reasonable approaximation of the goal...
    val managed: Classpath = Classpaths.managedJars( Compile, classpathTypes.value, update.value )
    val result: Seq[( File, String )] = managed flatMap { af: Attributed[File] =>
      af.metadata.entries.toList flatMap { e: AttributeEntry[_] =>
        e.value match {
          case null => Seq()
          case m: ModuleID => Seq() ++
            getFileIfExists( new File( ivyHome, s"cache/${m.organization}/${m.name}/srcs/${m.name}-${m.revision}-sources.jar" ), "lib.srcs" ) ++
            getFileIfExists( new File( ivyHome, s"cache/${m.organization}/${m.name}/docs/${m.name}-${m.revision}-javadoc.jar" ), "lib.javadoc" )
          case _ => Seq()
        }
      }
    }
    result
  }
}
