includeTargets << grailsScript("Init")

target(main: "The description of the script goes here!") {
    ant.copy(
            todir: "${basedir}/src/templates/scaffolding", overwrite: true) {
            fileset(dir: "${dbReverseEngineerPluginDir}/src/templates/scaffolding")
        }
}

setDefaultTarget(main)
