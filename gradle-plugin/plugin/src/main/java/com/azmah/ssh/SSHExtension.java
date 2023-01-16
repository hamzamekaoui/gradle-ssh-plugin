package com.azmah.ssh;

import org.gradle.api.NamedDomainObjectContainer;

/**
 * The extension for configuring the remote hosts for {@link SSHPlugin} tasks.
 * <p>
 * The extension can be used (in Groovy DSL) as the following
 * <pre>
 * ssh {
 *     remotes {
 *         deployment {
 *             host = 'some_host'
 *             port = 'some_port'
 *             user = 'some_user'
 *             password = 'some_password'
 *         }
 *     }
 * }
 * </pre>
 */
public interface SSHExtension {
  /**
   * The remotes container
   */
  NamedDomainObjectContainer<Remote> getRemotes();
}
