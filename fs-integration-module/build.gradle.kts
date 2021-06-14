import de.espirit.mavenplugins.fsmchecker.ComplianceLevel

plugins {
    id("java")
//    id("de.espirit.firstspirit-module-annotations") version "0.15.10" apply true
    id("maven-publish")
    id("de.espirit.firstspirit-module") version "0.15.10" apply true
    id("de.espirit.firstspirit") version "1.0.10" apply true
}



repositories {
    maven {
        setUrl("https://artifactory.e-spirit.de/artifactory/repo/")
        credentials {
            username = "${project.properties["artifactory_username"]}"
            password = "${project.properties["artifactory_password"]}"
        }
    }
}

//afterEvaluate {
//    rootProject.tasks["afterReleaseBuild"].dependsOn(tasks.matching { it.name == "publish" } as TaskCollection<*>)
//}

group = "de.espirit.firstspirit.modules.hackathon"

val fsLicense by configurations.creating

dependencies {
    compileOnly(group = "de.espirit.firstspirit", name = "fs-isolated-runtime", version = "${project.properties["firstSpiritVersion"]}")
    fsLicense(group = "de.espirit.firstspirit", name = "fs-license", version = "5-SNAPSHOT", ext = "conf")

}


firstSpirit {
    setVersion("${project.properties["firstSpiritVersion"]}")
}

tasks {

    assembleFSM {
//        val msmIntegrationWebProject = project(":fs-hackathon-cc-webapp");
//        dependsOn(project.tasks["build"])
        setProperty("archiveBaseName", rootProject.name)
//        from(project.buildDir.resolve("fsm-resources/fs-multisite-cc-webapp/fsui").path) {
//            into("fs-multisite-cc-webapp/fsui")
//            include("**/*.*")
//        }
    }

//    create("installModuleOnTargetFs", com.espirit.moddev.gradle.fs.tasks.FsInstallModuleTask::class) {
//        dependsOn(assembleFSM)
//        fsm = assembleFSM.get().outputs.files.first().absolutePath
//
//        configure<com.espirit.moddev.gradle.fs.extensions.FsConnectionExtension> {
//            setHost(getStringProperty("moduleInstallServerHost", "localhost"))
//            setPort(getStringProperty("moduleInstallServerPort", "-1").toInt())
//            connectionType = com.espirit.moddev.connection.FsConnectionType.valueOf(getStringProperty("moduleInstallServerConnectionMode", "HTTP"))
//            user = getStringProperty("moduleInstallServerUserName", "Admin")
//            password = getStringProperty("moduleInstallServerPassword", "Admin")
//        }
//    }

//    release {
//        ignoredSnapshotDependencies = listOf("de.espirit.firstspirit:fs-license", "de.espirit.firstspirit:fs-isolated-webrt")
//    }
// publish artifacts after release

}

/*
 * FirstSpirit Module Gradle Plugin configuration
 */
firstSpiritModule {
    moduleName = "fs-integration-module"
    displayName = "Hackathon Microapp Integration"
    vendor = "e-Spirit"

    isolationDetectorUrl = "https://fsdev.e-spirit.de/FsmDependencyDetector/"
    firstSpiritVersion = "${project.properties["firstSpiritVersion"]}"
    complianceLevel = ComplianceLevel.MINIMAL //todo: FIXME and change back to highest or at least DEFAULT

   // webAppComponent(project)
}

publishing {
    repositories {
        maven {
            credentials {
                username = "${project.properties["artifactory_username"]}"
                password = "${project.properties["artifactory_password"]}"
            }
            val releaseRepository = "core-platform-mvn-release"
            val snapshotRepository = "core-platform-mvn-snapshot"
            val resultingRepository = if (project.version.toString().endsWith("SNAPSHOT")) snapshotRepository else releaseRepository
            url = `java.net`.URI("https://artifactory.e-spirit.de/artifactory/$resultingRepository")
        }
    }
    publications {
        register(rootProject.name, MavenPublication::class) {
            artifactId = rootProject.name
            artifact(tasks.assembleFSM.get())
        }
    }
}

fun Project.getStringProperty(name: String, defaultValue: String): String = findProperty(name)?.toString()
        ?: defaultValue

