plugins {
    id("java")
    id("maven-publish")
}

buildscript {
    repositories {
        mavenCentral()
    }
}

if (JavaVersion.current() != JavaVersion.VERSION_1_8 &&
        sourceSets["main"].allJava.files.any { it.name == "module-info.java" }) {
    tasks.withType<JavaCompile> {
        // if you DO define a module-info.java file:
        options.compilerArgs.addAll(listOf("-Xplugin:Manifold", "--module-path", classpath.asPath))
    }
} else {
    println("TEst")
    tasks.withType<JavaCompile> {
        // If you DO NOT define a module-info.java file:
        options.compilerArgs.addAll(listOf("-Xplugin:Manifold"))
        print(options.compilerArgs)
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
        filesMatching("plugin.yml") {
            expand("version" to project.properties["version"])
        }
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
    compileOnly("org.projectlombok:lombok:1.18.16")
    annotationProcessor("org.projectlombok:lombok:1.18.16")
    implementation("systems.manifold:manifold-ext-rt:2020.1.51")
    implementation("systems.manifold:manifold-rt:2020.1.51")
    implementation("systems.manifold:manifold-exceptions:2020.1.51")
    annotationProcessor("systems.manifold:manifold-ext:2020.1.51")
    annotationProcessor("systems.manifold:manifold-exceptions:2020.1.51")
    annotationProcessor("systems.manifold:manifold:2020.1.51")
    annotationProcessor("systems.manifold:manifold-preprocessor:2020.1.51")
    annotationProcessor("systems.manifold:manifold-preprocessor:2020.1.51")
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

