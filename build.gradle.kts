val buildSSHImage = tasks.register<Exec>("buildSSHImage") {
    workingDir("$rootDir/utils")
    commandLine("docker", "build", "-t", "alpine-sshd" ,".")
}

tasks.register<Exec>("startSSHContainer") {
    dependsOn(buildSSHImage)
    commandLine("docker", "run", "-d", "-p", "22:22", "alpine-sshd")
}
