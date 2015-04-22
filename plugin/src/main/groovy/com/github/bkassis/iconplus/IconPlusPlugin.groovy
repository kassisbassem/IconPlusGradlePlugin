package com.github.bkassis.iconplus

import com.android.builder.core.BuilderConstants
import org.gradle.api.Plugin
import org.gradle.api.Project

class IconPlusPlugin implements Plugin<Project> {
    void apply(Project project) {

        project.extensions.create("iconplus", IconPlusExtension)
        project.android.applicationVariants.all { variant ->
            /* skip release builds */
            if (variant.buildType.name.equals(BuilderConstants.RELEASE)) {
                // log.debug("Skipping build type: ${variant.buildType.name}")
                return;
            }





            variant.outputs.each { output ->
                /* set up overlay task */




                def overlayTask = project.task(type: IconPlusTask, "iconplus${variant.name}") {
                    manifestFile = output.processManifest.manifestOutputFile
                    resourcesPath = variant.mergeResources.outputDir
                    flavorName = variant.name
                }

                output.processManifest.dependsOn overlayTask

            }
        }


    }


}

class IconPlusExtension {
    /**
     * Text color in #rrggbbaa format.
     */
    String textColor = "#FFF"

    /**
     * Background color in #rrggbbaa format.
     */
    String backgroundColor = "#1A1A1A"

    /**
     * text content
     */
    String format = '$flavorName'

}