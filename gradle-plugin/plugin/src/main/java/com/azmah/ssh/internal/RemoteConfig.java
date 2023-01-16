package com.azmah.ssh.internal;

import java.util.Objects;

public class RemoteConfig {

  private final String host;
  private final Integer port;
  private final String user;

  public RemoteConfig(String host, Integer port, String user) {
    this.host = host;
    this.port = port;
    this.user = user;
  }

  public String getHost() {
    return host;
  }

  public Integer getPort() {
    return port;
  }

  public String getUser() {
    return user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RemoteConfig that = (RemoteConfig) o;
    return (
      host.equals(that.host) && port.equals(that.port) && user.equals(that.user)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(host, port, user);
  }
}
