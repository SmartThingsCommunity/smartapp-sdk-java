package com.smartthings.sdk.smartapp.core.extensions.contextstore;


/**
 * Implementations of this interface handle storing and updating
 * InstalledAppContext instances, including keeping tokens up-to-date.
 *
 * IMPORTANT: Implementations of this class are responsible for keeping
 * the tokens up-to-date. Refresh tokens expire after 30 days.
 */
public interface InstalledAppContextStore<T extends InstalledAppContext> {
    /**
     * Record the given context and keep its tokens up-to-date. Call this when
     * the application is installed.
     */
    void add(T context);

    /**
     * Update the previously-recorded context and keep its tokens up-to-date.
     * Call this when the application is updated.
     */
    default void update(T context) {
        add(context);
    }

    /**
     * Remove this application from the context store. Call this when the
     * application is uninstalled.
     */
    void remove(String installedAppId);

    /**
     * Get the context for the given installedAppId. Tokens included are good
     * for at least 3 minutes. (If there is less than 3 minutes left, they
     * will be refreshed before being returned.)
     */
    T get(String installedAppId);
}
