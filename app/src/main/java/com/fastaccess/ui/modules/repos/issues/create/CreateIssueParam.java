package com.fastaccess.ui.modules.repos.issues.create;

import androidx.annotation.Nullable;

import com.fastaccess.data.dao.LabelModel;
import com.fastaccess.data.dao.MilestoneModel;
import com.fastaccess.data.dao.model.Issue;
import com.fastaccess.data.dao.model.PullRequest;
import com.fastaccess.data.dao.model.User;

import java.util.ArrayList;

public class CreateIssueParam {
    private final IssueInfo issueInfo;
    @Nullable
    private final Issue issueModel;
    @Nullable
    private final PullRequest pullRequestModel;
    @Nullable
    private final ArrayList<LabelModel> labels;
    @Nullable
    private final MilestoneModel milestoneModel;
    @Nullable
    private final ArrayList<User> users;

    public CreateIssueParam(IssueInfo issueInfo, @Nullable Issue issueModel, @Nullable PullRequest pullRequestModel, @Nullable ArrayList<LabelModel> labels, @Nullable MilestoneModel milestoneModel, @Nullable ArrayList<User> users) {
        this.issueInfo = issueInfo;
        this.issueModel = issueModel;
        this.pullRequestModel = pullRequestModel;
        this.labels = labels;
        this.milestoneModel = milestoneModel;
        this.users = users;
    }

    public IssueInfo getIssueInfo() {
        return issueInfo;
    }

    public Issue getIssueModel() {
        return issueModel;
    }

    public PullRequest getPullRequestModel() {
        return pullRequestModel;
    }

    public ArrayList<LabelModel> getLabels() {
        return labels;
    }

    public MilestoneModel getMilestoneModel() {
        return milestoneModel;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
