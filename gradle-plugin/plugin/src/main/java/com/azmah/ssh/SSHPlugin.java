package com.azmah.ssh;

import com.azmah.ssh.internal.services.SSHClientService;
import com.azmah.ssh.tasks.AbstractRemoteTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.provider.Provider;

/**
 * Gradle plugin that provides basic tasks to run commands against remotes using SSH.
 * <p>
 * Exposes the extension {@link SSHExtension} to configure the remotes.
 */
public class SSHPlugin implements Plugin<Project> {

  /**
   * The name of the extension.
   */
  public static final String SSH_EXTENSION_NAME = "ssh";

  @Override
  public void apply(Project project) {
    final SSHExtension sshExtension = project
      .getExtensions()
      .create(SSH_EXTENSION_NAME, SSHExtension.class);
    final Provider<SSHClientService> sshClientService = project
      .getGradle()
      .getSharedServices()
      .registerIfAbsent(
        "ssh",
        SSHClientService.class,
        spec -> spec.parameters(parameters -> {})
      );
    project
      .getTasks()
      .withType(AbstractRemoteTask.class)
      .configureEach(task -> {
        task.getSSHClientService().set(sshClientService.get());
        task.getRemotes().set(sshExtension.getRemotes());
      });
  }
}
