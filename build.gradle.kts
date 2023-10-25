plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("com.google.api-client:google-api-client:2.0.0")
    implementation ("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
    implementation ("com.google.apis:google-api-services-gmail:v1-rev20220404-2.0.0")
    implementation ("javax.mail:mail:1.4.7")
    implementation("org.apache.commons:commons-csv:1.10.0")
    implementation("com.sun.mail:javax.mail:1.6.2")
}

tasks.test {
    useJUnitPlatform()
}
