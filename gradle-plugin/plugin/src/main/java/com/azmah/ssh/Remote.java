package com.azmah.ssh;

import org.gradle.api.Named;
import org.gradle.api.provider.Property;

/**
 * Defines a configuration for a remote host.
 */
public interface Remote extends Named {
  /**
   * The remote's host
   */
  Property<String> getHost();

  /**
   * The remote's port
   */
  Property<Integer> getPort();

  /**
   * The remote's user
   */
  Property<String> getUser();

  /**
   * The remote's password
   */
  Property<String> getPassword();
}
