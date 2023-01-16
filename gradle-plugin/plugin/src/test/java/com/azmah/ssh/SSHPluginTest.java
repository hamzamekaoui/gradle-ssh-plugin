package com.azmah.ssh;

import static org.assertj.core.api.Assertions.*;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

class SSHPluginTest {

  @Test
  void pluginRegistersSSHExtension() {
    Project project = ProjectBuilder.builder().build();
    project.getPlugins().apply("com.azmah.ssh");
    assertThat(project.getExtensions().findByName("ssh")).isNotNull();
  }
}
