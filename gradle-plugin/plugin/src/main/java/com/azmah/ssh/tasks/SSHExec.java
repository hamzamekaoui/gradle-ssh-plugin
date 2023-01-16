package com.azmah.ssh.tasks;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.EnumSet;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.gradle.api.GradleException;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

public abstract class SSHExec extends AbstractRemoteTask {

  /**
   * The command to run.
   */
  @Input
  public abstract Property<String> getCommand();

  @TaskAction
  public void runCommand() throws GradleException {
    getRemotes()
      .get()
      .forEach(remote -> {
        try (
          ClientSession session = getSession(
            remote.getHost().get(),
            remote.getPort().get(),
            remote.getUser().get(),
            remote.getPassword().get()
          )
        ) {
          try (
            OutputStream stdout = new ByteArrayOutputStream();
            OutputStream stderr = new ByteArrayOutputStream();
            ClientChannel channel = session.createExecChannel(
              getCommand().get()
            )
          ) {
            channel.setOut(stdout);
            channel.setErr(stderr);
            channel.open().verify();
            channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 0L);
            getLogger().lifecycle("Output: {}", stdout);
          }
        } catch (Exception e) {
          throw new GradleException(e.getMessage());
        }
      });
  }
}
