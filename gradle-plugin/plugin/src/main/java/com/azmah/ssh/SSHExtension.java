package com.azmah.ssh;

import org.gradle.api.NamedDomainObjectContainer;

public interface SSHExtension {
    NamedDomainObjectContainer<Remote> getRemotes();
}
