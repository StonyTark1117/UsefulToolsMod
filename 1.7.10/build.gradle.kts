plugins {
    id("java-library")
    id("com.gtnewhorizons.retrofuturagradle") version "1.4.9"
}

group = "com.stonytark.usefultoolsmod"
version = providers.gradleProperty("mod_version").get() + "+mc.1.7.10.forge"

base {
    archivesName.set("usefultoolsmod")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
    withSourcesJar()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
}

minecraft {
    mcVersion.set("1.7.10")
    username.set("Developer")
    injectedTags.put("VERSION", project.version)
    extraRunJvmArguments.add("-ea:com.stonytark.usefultoolsmod")
}

tasks.injectTags.configure {
    outputClassName.set("com.stonytark.usefultoolsmod.BuildTags")
}

tasks.processResources.configure {
    val releaseVersion = project.version.toString()
    inputs.property("version", releaseVersion)
    filesMatching("mcmod.info") {
        expand(mapOf("version" to releaseVersion))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}
