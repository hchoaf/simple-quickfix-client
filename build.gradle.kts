plugins {
    id("java")
}

group = "org.hchoaf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.quickfixj:quickfixj-core:2.3.0")
    implementation("org.quickfixj:quickfixj-messages-all:2.3.0")
    implementation("org.apache.logging.log4j:log4j-core:2.23.0")
}

tasks.test {
    useJUnitPlatform()
}