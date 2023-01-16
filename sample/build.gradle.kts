import com.azmah.ssh.tasks.SSHExec

plugins {
    id("com.azmah.ssh")
}

ssh {
    remotes {
        create("deployment") {
            host.set("localhost")
            port.set(22)
            user.set("user")
            password.set("justapassword")
        }
    }
}

tasks.register<SSHExec>("printWorkingDirectory") {
    command.set("pwd")
}
