package com.azmah.ssh;

import com.azmah.ssh.internal.services.SSHClientService;
import com.azmah.ssh.tasks.AbstractRemoteTask;
import org.gradle.api.Project;
import org.gradle.api.Plugin;
import org.gradle.api.provider.Provider;

@SuppressWarnings("unused")
public class SSHPlugin implements Plugin<Project> {

    public static final String SSH_EXTENSION_NAME = "ssh";
    public static final String SSH_BUILD_SERVICE_NAME = "sshBuildService";

    @Override
    public void apply(Project project) {
        final SSHExtension sshExtension = project.getExtensions().create(SSH_EXTENSION_NAME, SSHExtension.class);
        final Provider<SSHClientService> sshClientService = project.getGradle().getSharedServices().registerIfAbsent(SSH_BUILD_SERVICE_NAME, SSHClientService.class, spec -> spec.parameters(parameters -> {
        }));
        project.getTasks().withType(AbstractRemoteTask.class).configureEach(task -> {
            task.getSSHClientService().set(sshClientService.get());
            task.getRemotes().set(sshExtension.getRemotes());
        });
    }
}
