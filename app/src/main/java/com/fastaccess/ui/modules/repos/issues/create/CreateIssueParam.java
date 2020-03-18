package com.fastaccess.ui.modules.repos.issues.create;

import androidx.annotation.Nullable;

import com.fastaccess.data.dao.LabelModel;
import com.fastaccess.data.dao.MilestoneModel;
import com.fastaccess.data.dao.model.Issue;
import com.fastaccess.data.dao.model.PullRequest;
import com.fastaccess.data.dao.model.User;

import java.util.ArrayList;

public class CreateIssueParam {
    private IssueInfo issueInfo;
    @Nullable
    private Issue issueModel;
    @Nullable
    private PullRequest pullRequestModel;
    @Nullable
    private ArrayList<LabelModel> labels;
    @Nullable
    private MilestoneModel milestoneModel;
    @Nullable
    private ArrayList<User> users;

    public CreateIssueParam() {

    }

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

    public void setIssueInfo(IssueInfo issueInfo) {
        this.issueInfo = issueInfo;
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

    public void setIssueModel(@Nullable Issue issueModel) {
        this.issueModel = issueModel;
    }

    public void setPullRequestModel(@Nullable PullRequest pullRequestModel) {
        this.pullRequestModel = pullRequestModel;
    }
}
