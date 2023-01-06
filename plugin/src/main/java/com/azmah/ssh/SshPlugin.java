package com.azmah.ssh;

import org.gradle.api.Project;
import org.gradle.api.Plugin;

public class SshPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getTasks().register("printVersion", task -> {
            task.doLast(s -> System.out.println("SSH"));
        });
    }
}
