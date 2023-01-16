package com.azmah.ssh;

import org.gradle.api.Named;
import org.gradle.api.provider.Property;

public interface Remote extends Named {
    Property<String> getHost();

    Property<Integer> getPort();

    Property<String> getUser();

    Property<String> getPassword();
}
