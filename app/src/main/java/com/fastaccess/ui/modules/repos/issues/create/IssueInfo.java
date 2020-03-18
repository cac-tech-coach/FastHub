package com.fastaccess.ui.modules.repos.issues.create;

import androidx.annotation.NonNull;

public class IssueInfo {
    @NonNull
    private String title = "";
    @NonNull
    private CharSequence description = "";
    @NonNull
    private String login = "";
    @NonNull
    private String repo = "";

    public IssueInfo() {

    }

    public IssueInfo(@NonNull String title, @NonNull CharSequence description, @NonNull String login, @NonNull String repo) {
        this.title = title;
        this.description = description;
        this.login = login;
        this.repo = repo;
    }

    public String getTitle() {
        return title;
    }

    public CharSequence getDescription() {
        return description;
    }

    public String getLogin() {
        return login;
    }

    public String getRepo() {
        return repo;
    }
}
