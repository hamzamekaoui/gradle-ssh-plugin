package com.azmah.ssh;

import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.api.Project;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SshPluginTest {
    @Test void pluginRegistersATask() {
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply("com.azmah.ssh.gradle");
        assertNotNull(project.getTasks().findByName("printVersion"));
    }
}
