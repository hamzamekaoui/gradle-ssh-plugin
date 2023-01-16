package com.azmah.ssh;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SSHPluginFunctionalTest {

  @TempDir
  File projectDir;

  private File getBuildFile() {
    return new File(projectDir, "build.gradle.kts");
  }

  private File getSettingsFile() {
    return new File(projectDir, "settings.gradle.kts");
  }

  @Test
  void canRunTask() throws IOException {
    String newLine = System.getProperty("line.separator");
    writeString(getSettingsFile(), "");
    writeString(
      getBuildFile(),
      "import com.azmah.ssh.tasks.SSHExec\n" +
      "\n" +
      "plugins {\n" +
      "    id(\"com.azmah.ssh\")\n" +
      "}\n" +
      "\n" +
      "ssh {\n" +
      "    remotes {\n" +
      "        create(\"deployment\") {\n" +
      "            host.set(\"localhost\")\n" +
      "            port.set(22)\n" +
      "            user.set(\"user\")\n" +
      "            password.set(\"justapassword\")\n" +
      "        }\n" +
      "    }\n" +
      "}\n" +
      "\n" +
      "tasks.register<SSHExec>(\"printWorkingDirectory\") {\n" +
      "    command.set(\"pwd\")\n" +
      "}\n"
    );
    GradleRunner runner = GradleRunner.create();
    runner.forwardOutput();
    runner.withPluginClasspath();
    runner.withArguments("runPrintWorkingDirectory");
    runner.withProjectDir(projectDir);
    BuildResult result = runner.build();
    assertThat(result.getOutput()).contains("/home/user");
  }

  private void writeString(File file, String string) throws IOException {
    try (Writer writer = new FileWriter(file)) {
      writer.write(string);
    }
  }
}
