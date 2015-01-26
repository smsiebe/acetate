package org.geoint.acetate.data.version;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates a data field as one that distinguishes the owning system of the
 * data.
 *
 * Data that is imported/managed by an external system is unique in that this
 * data is often used as reference-only, and updated only by changes in the
 * external system. Components can use this annotation, along with
 * {@link OwningSystemId} to enforce any application-level constraints as a
 * cross-cutting concern.
 * <p>
 * Since data of the same type (class) can be locally created/sourced as well as
 * remotely created/sourced, we mark the data fields, not the class itself, to
 * avoid class explosion caused by numerous external systems.
 * 
 * @see OwningSystemId
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OwningSystemBind {

}
