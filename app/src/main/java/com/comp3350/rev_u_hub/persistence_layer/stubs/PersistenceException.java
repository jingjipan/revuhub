package com.comp3350.rev_u_hub.persistence_layer.stubs;
/**
 * java.sql.SQLException is a checked exception, but our interface doesn't have any
 * checked exceptions, so wrap java.sql.SQLException in an unchecked java.lang.RuntimeException
 * so we can throw them around, but not *have* to catch them if we don't want to.
 */
public class PersistenceException extends RuntimeException {
    public PersistenceException(final Exception cause) {
        super(cause);
    }
}
