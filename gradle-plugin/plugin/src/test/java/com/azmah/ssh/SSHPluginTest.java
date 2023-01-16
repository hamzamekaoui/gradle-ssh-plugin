package com.azmah.ssh;

import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.api.Project;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class SSHPluginTest {
    @Test void pluginRegistersSSHExtension() {
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply("com.azmah.ssh");
        assertThat(project.getExtensions().findByName("ssh")).isNotNull();
    }
}
