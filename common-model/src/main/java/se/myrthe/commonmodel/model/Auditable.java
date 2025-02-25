package se.myrthe.commonmodel.model;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * A base class for auditable entities that provides common auditing fields and
 * functionality. This class is meant to be extended by entity classes requiring
 * audit information.
 *
 * <p>The {@code Auditable<T>} class uses JPA annotations to map common fields,
 * such as creation and modification timestamps, to database columns. The
 * {@link jakarta.persistence.MappedSuperclass} annotation allows entities
 * inheriting this class to embed these fields directly in their database
 * tables.</p>
 *
 * <p>Typical use cases include tracking when an entity was created and last
 * updated, with support for tracking the user responsible for changes.</p>
 *
 * <pre>
 * {@code
 * @Entity
 * public class MyEntity extends Auditable<String> {
 *     // additional fields and methods
 * }
 * }
 * </pre>
 *
 * @param <T> the type of the user responsible for changes (e.g. String, User
 *            entity)
 * @author Myrthe Bil
 * @version 1.0
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<T> {

  /**
   * The user who created the entity. This field is automatically populated by
   * the auditing system when the entity is created.
   *
   * <p>&lt;T&gt; the type of the user responsible for creating the entity (e.g.,
   * String, User)
   */
  //  @CreatedBy
  //  TODO: once Spring Security AuditorAware has been implemented, uncomment
  private T createdBy;

  /**
   * The timestamp when the entity was created. This field is automatically
   * populated by the auditing system when the entity is created. It is
   * annotated with {@code @Temporal(TemporalType.TIMESTAMP)} to store the
   * timestamp in a database column as a full date-time value.
   */
  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  /**
   * The user who last modified the entity. This field is automatically
   * populated by the auditing system when the entity is modified.
   *
   * <p>&lt;T&gt; the type of the user responsible for modifying the entity (e.g.,
   * String, User)
   */
  //  @LastModifiedBy
  //  TODO: once Spring Security AuditorAware has been implemented, uncomment
  private T lastModifiedBy;

  /**
   * The timestamp when the entity was last modified. This field is
   * automatically populated by the auditing system whenever the entity is
   * updated. It is annotated with {@code @Temporal(TemporalType.TIMESTAMP)} to
   * store the timestamp in a database column as a full date-time value.
   */
  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModifiedAt;

  /**
   * Gets the user responsible for the creation of the entity.
   *
   * @return the user who created the entity
   */
  public T getCreatedBy() {
    return createdBy;
  }

  /**
   * Sets the user responsible for the creation of the entity.
   *
   * @param createdBy the user who created the entity
   */
  public void setCreatedBy(final T createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * Gets the creation timestamp of the entity.
   *
   * @return the creation timestamp
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Gets the user responsible for the last modification of the entity.
   *
   * @return the user who last modified the entity
   */
  public T getLastModifiedBy() {
    return lastModifiedBy;
  }

  /**
   * Sets the user responsible for the last modification of the entity.
   *
   * @param lastModifiedBy the user who last modified the entity
   */
  public void setLastModifiedBy(final T lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  /**
   * Gets the timestamp of the last modification of the entity.
   *
   * @return the last modification timestamp
   */
  public Date getLastModifiedAt() {
    return lastModifiedAt;
  }

  /**
   * Updates the timestamps and sets the user responsible before the entity is
   * persisted. Typically invoked by the persistence lifecycle events.
   */
  @PrePersist
  protected void onCreate() {
    Date date = new Date();
    createdAt = date;
    lastModifiedAt = date;
  }

  /**
   * Updates the {@code lastModifiedAt} timestamp and sets the user responsible
   * before the entity is updated.
   */
  @PreUpdate
  protected void onUpdate() {
    lastModifiedAt = new Date();
  }
}
