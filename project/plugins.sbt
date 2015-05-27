resolvers += Resolver.url("sbt-license-plugin-releases", url("http://dl.bintray.com/banno/oss"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.banno" % "sbt-license-plugin" % "0.1.4")

resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.4")

resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"

addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.6.12")

resolvers += Classpaths.sbtPluginReleases

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.0.1")

// https://github.com/jrudolph/sbt-dependency-graph
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.5")
