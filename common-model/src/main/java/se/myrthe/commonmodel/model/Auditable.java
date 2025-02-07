package se.myrthe.commonmodel.model;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 * A base class for auditable entities that provides common auditing fields and functionality.
 * This class is meant to be extended by entity classes requiring audit information.
 *
 * <p>The {@code Auditable<T>} class uses JPA annotations to map common fields,
 * such as creation and modification timestamps, to database columns.
 * The {@link jakarta.persistence.MappedSuperclass} annotation allows entities
 * inheriting this class to embed these fields directly in their database tables.</p>
 *
 * <p>Typical use cases include tracking when an entity was created and last updated,
 * with support for tracking the user responsible for changes.</p>
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
 * @param <T> the type of the user responsible for changes (e.g., String, User entity)
 * @author Myrthe
 * @version 1.0
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<T> {

    @CreatedBy
    protected T createdBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;

    @LastModifiedBy
    protected T lastModifiedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastModifiedAt;

    public T getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final T createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public T getLastModifiedBy() {
        return lastModifiedBy;
    }
    
    public void setLastModifiedBy(final T lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(final Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    /**
     * Updates the timestamps and sets the user responsible before the entity is persisted.
     * Typically invoked by the persistence lifecycle events.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = lastModifiedAt = new Date();
    }

    /**
     * Updates the {@code lastModifiedAt} timestamp and sets the user responsible before the entity is updated.
     */
    @PreUpdate
    protected void onUpdate() {
        lastModifiedAt = new Date();
    }
}
