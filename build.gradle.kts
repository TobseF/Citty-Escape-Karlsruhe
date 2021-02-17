import com.soywiz.korge.gradle.GameCategory
import com.soywiz.korge.gradle.KorgeGradlePlugin
import com.soywiz.korge.gradle.Orientation.LANDSCAPE
import com.soywiz.korge.gradle.korge

buildscript {
    val korgePluginVersion: String by project

    repositories {
        mavenLocal()
        maven { url = uri("https://dl.bintray.com/korlibs/korlibs") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        mavenCentral()
        google()
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-dev") }
        maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
    }
    dependencies {
        classpath("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:$korgePluginVersion")
    }
}

apply<KorgeGradlePlugin>()

korge {
    id = "tfr.game.kittyescape"
    name = "KittyEscape"
    exeBaseName = "Kitty Escape"
    description = "Find your escaped kitty in this cooperative multiplayer browser game ."
    gameCategory = GameCategory.STRATEGY

    //  icon = project.projectDir["appicon.png"]
    orientation = LANDSCAPE

    authorName = "Tobse"
    authorHref = "https://github.com/TobseF/"
}
