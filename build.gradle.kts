plugins {
    id("java")
    id("maven-publish")
}

buildscript {
    repositories {
        mavenCentral()
    }
}


group = "skywolf46"
version = properties["version"] as String

tasks {
    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.properties["version"])
        }
    }
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.github.com/milkyway0308/CommandAnnotation") {
        credentials {
            username = properties["gpr.user"] as String
            password = properties["gpr.key"] as String
        }
    }

    maven("https://maven.pkg.github.com/milkyway0308/PlaceHolders") {
        credentials {
            username = properties["gpr.user"] as String
            password = properties["gpr.key"] as String
        }
    }

}

dependencies {
    implementation("skywolf46:commandannotation:latest.release") {
        isChanging = true
    }
    implementation("skywolf46:placeholders:latest.release") {
        isChanging = true
    }
    compileOnly(files("V:/API/Java/Minecraft/Bukkit/Spigot/Spigot 1.12.2.jar"))
}



publishing {
    repositories {
        maven {
            name = "Github"
            url = uri("https://maven.pkg.github.com/FUNetwork/SkywolfExtraUtility")
            credentials {
                username = properties["gpr.user"] as String
                password = properties["gpr.key"] as String
            }
        }
    }
    publications {
        create<MavenPublication>("jar") {
            from(components["java"])
            groupId = "skywolf46"
            artifactId = "exutil"
            version = properties["version"] as String
            pom {
                url.set("https://github.com/FUNetwork/SkywolfExtraUtility.git")
            }
        }
    }
}
