package com.smartthings.sdk.smartapp.core.extensions.contextstore;

import java.time.Duration;
import java.time.Instant;

import com.smartthings.sdk.smartapp.core.models.InstallData;
import com.smartthings.sdk.smartapp.core.models.InstalledApp;
import com.smartthings.sdk.smartapp.core.models.UpdateData;


/**
 * This is the default implementation of InstalledAppContext which stores
 * basic information related to installed apps. In particular, this includes
 * token information to allow for keeping them up-to-date for out of band
 * API requests.
 *
 * Instances of this class are created and managed by the context store.
 */
public class DefaultInstalledAppContext implements InstalledAppContext {
    private InstalledApp installedApp;
    private String authToken;
    private Instant authTokenExpiration;
    private String refreshToken;
    private Instant refreshTokenExpiration;

    public static final Duration authTokenDuration = Duration.ofMinutes(5);
    public static final Duration refreshTokenDuration = Duration.ofDays(30);

    /**
     * Helper method for creating the context from the install data.
     */
    public static DefaultInstalledAppContext from(InstallData installData) {
        return new DefaultInstalledAppContext(installData.getInstalledApp(), installData.getAuthToken(),
                installData.getRefreshToken());
    }

    /**
     * Helper method for creating the context from the update data.
     */
    public static DefaultInstalledAppContext from(UpdateData updateData) {
        return new DefaultInstalledAppContext(updateData.getInstalledApp(), updateData.getAuthToken(),
                updateData.getRefreshToken());
    }

    public DefaultInstalledAppContext() {
        // Include empty constructor to make this class as amenable to
        // persistence engines as possible.
    }

    public DefaultInstalledAppContext(InstalledApp installedApp, String authToken, String refreshToken) {
        this.installedApp = installedApp;
        this.authToken = authToken;
        this.refreshToken = refreshToken;

        Instant now = Instant.now();
        authTokenExpiration = now.plus(authTokenDuration);
        refreshTokenExpiration = now.plus(refreshTokenDuration);
    }

    @Override
    public String getInstalledAppId() {
        return installedApp != null ? installedApp.getInstalledAppId() : null;
    }

    public InstalledApp getInstalledApp() {
        return installedApp;
    }
    public void setInstalledApp(InstalledApp installedApp) {
        this.installedApp = installedApp;
    }

    public String getAuthToken() {
        return authToken;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Get the approximate expiration time of the auth token.
     */
    public Instant getAuthTokenExpiration() {
        return authTokenExpiration;
    }
    public void setAuthTokenExpiration(Instant authTokenExpiration) {
        this.authTokenExpiration = authTokenExpiration;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Get the approximate expiration time of the refresh token.
     */
    public Instant getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }
    public void setRefreshTokenExpiration(Instant refreshTokenExpiration) {
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authToken == null) ? 0 : authToken.hashCode());
        result = prime * result + ((authTokenExpiration == null) ? 0 : authTokenExpiration.hashCode());
        result = prime * result + ((installedApp == null) ? 0 : installedApp.hashCode());
        result = prime * result + ((refreshToken == null) ? 0 : refreshToken.hashCode());
        result = prime * result + ((refreshTokenExpiration == null) ? 0 : refreshTokenExpiration.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DefaultInstalledAppContext other = (DefaultInstalledAppContext) obj;
        if (authToken == null) {
            if (other.authToken != null) {
                return false;
            }
        } else if (!authToken.equals(other.authToken)) {
            return false;
        }
        if (authTokenExpiration == null) {
            if (other.authTokenExpiration != null) {
                return false;
            }
        } else if (!authTokenExpiration.equals(other.authTokenExpiration)) {
            return false;
        }
        if (installedApp == null) {
            if (other.installedApp != null) {
                return false;
            }
        } else if (!installedApp.equals(other.installedApp)) {
            return false;
        }
        if (refreshToken == null) {
            if (other.refreshToken != null) {
                return false;
            }
        } else if (!refreshToken.equals(other.refreshToken)) {
            return false;
        }
        if (refreshTokenExpiration == null) {
            if (other.refreshTokenExpiration != null) {
                return false;
            }
        } else if (!refreshTokenExpiration.equals(other.refreshTokenExpiration)) {
            return false;
        }
        return true;
    }
}
