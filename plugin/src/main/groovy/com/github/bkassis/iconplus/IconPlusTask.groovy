package com.github.bkassis.iconplus

import com.github.bkassis.iconplus.image.ImageOverlayTask
import com.github.bkassis.iconplus.image.LauncherIconAttribute
import com.github.bkassis.iconplus.image.util.FileDrawableUtil
import groovy.io.FileType
import groovy.text.SimpleTemplateEngine
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class IconPlusTask extends DefaultTask {
    @Input
    File manifestFile
    File resourcesPath
    String flavorName

    @TaskAction
    def exec() {


        def manifestXml = new XmlSlurper().parse(manifestFile).declareNamespace('android': 'http://schemas.android.com/apk/res/android')
        def iconFileName = manifestXml.application.@'android:icon'.text().split("/")[1]

        def formatBinding = ['flavorName': flavorName]
        def caption = new SimpleTemplateEngine().createTemplate(project.iconplus.format).make(formatBinding)




        resourcesPath.eachDirMatch(~/^drawable.*|^mipmap.*/) { dir ->
            dir.eachFileMatch(FileType.FILES, ~"^${iconFileName}.*") { file ->
                ImageOverlayTask imageOverlayTask = new ImageOverlayTask(file);

                LauncherIconAttribute launcherIconAttribute = new LauncherIconAttribute();
                launcherIconAttribute.backgroundColor = "${project.iconplus.backgroundColor}";
                launcherIconAttribute.text = "${caption}";
                launcherIconAttribute.textColor = "${project.iconplus.textColor}";

                ImageOverlayTask.IconResolution iconResolution = FileDrawableUtil.fetchResolution(file);
                imageOverlayTask.executeIconLauncherOverlay(iconResolution, launcherIconAttribute);


            }


        }



    }


}