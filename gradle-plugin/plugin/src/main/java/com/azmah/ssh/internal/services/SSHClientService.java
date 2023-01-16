package com.azmah.ssh.internal.services;

import com.azmah.ssh.internal.RemoteConfig;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.gradle.api.GradleException;
import org.gradle.api.services.BuildService;
import org.gradle.api.services.BuildServiceParameters;

public abstract class SSHClientService
  implements BuildService<BuildServiceParameters.None>, AutoCloseable {

  private final Map<RemoteConfig, SshClient> sshClients;

  @Inject
  @SuppressWarnings("squid:S5993")
  public SSHClientService() {
    this.sshClients = new ConcurrentHashMap<>();
  }

  /**
   * Returns the SSH client.
   * Initializes an instance upon first request.
   *
   * @param config Remote config
   * @param password password
   * @return SSH client
   */
  public SshClient getClient(RemoteConfig config, String password) {
    return sshClients.computeIfAbsent(
      config,
      c -> {
        SshClient sshClient = SshClient.setUpDefaultClient();
        sshClient.start();
        sshClient.addPasswordIdentity(password);
        return sshClient;
      }
    );
  }

  /**
   * Checks if Docker host URL starts with http(s) and if so, converts it to tcp
   * which is accepted by docker-java library.
   *
   * @param host Remote host
   * @param port Remote port
   * @param username Remote user
   * @param password Remote user's password
   * @return The SSH session
   */
  public ClientSession getSession(
    String host,
    Integer port,
    String username,
    String password
  ) {
    RemoteConfig config = new RemoteConfig(host, port, username);
    try {
      SshClient client = getClient(config, password);
      ClientSession session = client
        .connect(config.getUser(), config.getHost(), config.getPort())
        .verify()
        .getSession();
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
