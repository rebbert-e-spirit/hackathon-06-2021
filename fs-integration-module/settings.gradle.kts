/*
 * This file was generated by the Gradle 'init' task.
 *
 * The settings file is used to specify which projects to include in your build.
 *
 * Detailed information about configuring a multi-project build in Gradle can be found
 * in the user manual at https://docs.gradle.org/6.7/userguide/multi_project_builds.html
 */
pluginManagement {
    repositories {
        val artifactory_username: String by settings
        val artifactory_password: String by settings
        maven {
            url = java.net.URI.create("https://artifactory.e-spirit.de/artifactory/repo/")
            credentials {
                username = artifactory_username
                password = artifactory_password
            }
        }
        gradlePluginPortal()
    }
}

rootProject.name = "fs-integration-module"
