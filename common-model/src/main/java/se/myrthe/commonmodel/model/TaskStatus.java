package se.myrthe.commonmodel.model;

/**
 * Represents the enum task status within the system.
 *
 * @author Myrthe Bil
 * @version 1.0
 * @since 1.0
 */
public enum TaskStatus {
  /**
   * Used for tasks that have not been picked up yet.
   */
  TODO,
  /**
   * For tasks that are in progress.
   */
  IN_PROGRESS,
  /**
   * For tasks that have been completed.
   */
  COMPLETED
}
