plugins {
    id("io.papermc.paperweight.userdev") version "1.5.13" // Check for new versions at https://plugins.gradle.org/plugin/io.papermc.paperweight.userdev
}

dependencies {
    paperweight.paperDevBundle("1.18.2-R0.1-SNAPSHOT")
    compileOnly(project(":api"))
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    compileJava {
        options.encoding = "UTF-8"
    }
}