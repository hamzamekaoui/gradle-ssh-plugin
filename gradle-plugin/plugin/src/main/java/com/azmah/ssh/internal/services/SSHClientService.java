package com.azmah.ssh.internal.services;

import com.azmah.ssh.internal.RemoteConfig;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.gradle.api.GradleException;
import org.gradle.api.services.BuildService;
import org.gradle.api.services.BuildServiceParameters;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract  class SSHClientService implements BuildService<BuildServiceParameters.None>, AutoCloseable {
    private final Map<RemoteConfig, SshClient> sshClients;
    @Inject
    @SuppressWarnings("squid:S5993")
    public SSHClientService() {
        this.sshClients = new ConcurrentHashMap<>();
    }

    public SshClient getClient(RemoteConfig config, String password) {
        return sshClients.computeIfAbsent(config, c -> {
            SshClient sshClient = SshClient.setUpDefaultClient();
            sshClient.start();
            sshClient.addPasswordIdentity(password);
            return sshClient;
        });
    }

    public ClientSession getSession(String host, Integer port, String username, String password) {
        RemoteConfig config = new RemoteConfig(host, port, username);
        try {
            SshClient client = getClient(config, password);
            ClientSession session = client.connect(config.getUser(), config.getHost(), config.getPort()).verify().getSession();
            session.auth().verify();
            return session;
        } catch (IOException e) {
            throw new GradleException(e.getMessage());
        }
    }

    @Override
    public void close() {
        for (SshClient sshClient : sshClients.values()) {
            try {
                sshClient.close();
            } catch (IOException e) {
                throw new GradleException(e.getMessage());
            }
        }
    }
}
