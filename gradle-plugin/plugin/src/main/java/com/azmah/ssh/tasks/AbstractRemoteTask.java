package com.azmah.ssh.tasks;

import com.azmah.ssh.Remote;
import com.azmah.ssh.internal.services.SSHClientService;
import org.apache.sshd.client.session.ClientSession;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;

public abstract class AbstractRemoteTask extends DefaultTask {

    @Internal
    public final Property<SSHClientService> getSSHClientService() {
        return sshClientService;
    }

    private final Property<SSHClientService> sshClientService = getProject().getObjects().property(SSHClientService.class);

    @Input
    public abstract ListProperty<Remote> getRemotes();

    @Internal
    public ClientSession getSession(String host, Integer port, String username, String password) {
        return sshClientService.get().getSession(host, port, username, password);
    }
}
