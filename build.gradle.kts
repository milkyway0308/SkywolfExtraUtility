plugins {
    kotlin("jvm") version "1.4.30"
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "2.0.4"

}

buildscript {
    repositories {
        mavenCentral()
    }
}


group = "skywolf46"
version = properties["version"] as String
java {
    targetCompatibility = JavaVersion.VERSION_1_8
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    processResources {
        outputs.upToDateWhen { false }
        filesMatching("plugin.yml") {
            expand("version" to project.properties["version"])
        }
    }
    shadowJar {
        archiveClassifier.set("shaded")
    }
    jar {
        dependsOn(shadowJar)
    }
}

repositories {
    mavenCentral()
    jcenter()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
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
    implementation(kotlin("stdlib"))
    compileOnly("org.projectlombok:lombok:1.18.16")
    compileOnly("skywolf46:placeholders:latest.release")
    annotationProcessor("org.projectlombok:lombok:1.18.16")
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

