package com.flashcats.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
public class LoggedInUserView {
    private String displayName;
    private String sessionId;

    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    LoggedInUserView(String displayName,String sessionId) {
        this.sessionId = sessionId;
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }

    String getSessionId() {
        return sessionId;
    }
}