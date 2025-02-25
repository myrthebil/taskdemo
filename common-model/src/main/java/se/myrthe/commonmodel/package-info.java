/**
 * This package contains the core domain models, repositories, and utilities for
 * handling {@link se.myrthe.commonmodel.model.Task}s and
 * {@link se.myrthe.commonmodel.model.User} within the services.
 *
 * <p>The package includes:
 * <ul>
 *     <li><b>Models</b>: Defines the entity representations for {@code Task}
 *     and {@code User}.</li>
 *     <li><b>Repositories</b>: Interfaces for data access
 *     and persistence operations.</li>
 *     <li><b>Utilities</b>: Shared helper functions for common operations.</li>
 * </ul>
 *
 * <p>Usage:
 * <pre>
 *     User user = User.builder().username("Bob").build();
 *     userRepository.save(user);
 * </pre>
 *
 * @author Myrthe Bil
 * @version 1.0
 * @since 1.0
 */
package se.myrthe.commonmodel;
