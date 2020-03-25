package com.fastaccess.ui.modules.repos.issues.create;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.fastaccess.BuildConfig;
import com.fastaccess.R;
import com.fastaccess.data.dao.CreateIssueModel;
import com.fastaccess.data.dao.IssueRequestModel;
import com.fastaccess.data.dao.LabelListModel;
import com.fastaccess.data.dao.LabelModel;
import com.fastaccess.data.dao.UsersListModel;
import com.fastaccess.data.dao.model.Login;
import com.fastaccess.data.dao.model.User;
import com.fastaccess.helper.BundleConstant;
import com.fastaccess.helper.InputHelper;
import com.fastaccess.helper.RxHelper;
import com.fastaccess.provider.rest.RestProvider;
import com.fastaccess.ui.base.mvp.BaseMvp;
import com.fastaccess.ui.base.mvp.presenter.BasePresenter;

import java.util.ArrayList;

/**
 * Created by Kosh on 19 Feb 2017, 12:18 PM
 */

public class CreateIssuePresenter extends BasePresenter<CreateIssueMvp.View> implements CreateIssueMvp.Presenter {

    @com.evernote.android.state.State
    boolean isCollaborator;


    @Override
    public void checkAuthority(@NonNull String login, @NonNull String repoId) {
        manageViewDisposable(RxHelper.getObservable(RestProvider.getRepoService(isEnterprise()).
                isCollaborator(login, repoId, Login.getUser().getLogin()))
                .subscribe(booleanResponse -> {
                    isCollaborator = booleanResponse.code() == 204;
                    sendToView(CreateIssueMvp.View::onShowIssueMisc);
                }, Throwable::printStackTrace));
    }

    @Override
    public void onActivityForResult(int resultCode, int requestCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK && requestCode == BundleConstant.REQUEST_CODE) {
            if (intent != null && intent.getExtras() != null) {
                CharSequence charSequence = intent.getExtras().getCharSequence(BundleConstant.EXTRA);
                if (!InputHelper.isEmpty(charSequence)) {
                    sendToView(view -> view.onSetCode(charSequence));
                }
            }
        }
    }

    @Override
    public void onSubmit(CreateIssueParam createIssueParam) {

        boolean isEmptyTitle = InputHelper.isEmpty(createIssueParam.getIssueInfo().getTitle());
        if (getView() != null) {
            getView().onTitleError(isEmptyTitle);
        }
        if (isEmptyTitle) {
            return;
        }
        if (createIssueParam.getIssueModel() == null && createIssueParam.getPullRequestModel() == null) {
            createRequest(createIssueParam);
            return;
        }
        if (createIssueParam.getIssueModel() != null) {
            createRequestByIssue(createIssueParam);
        }
        if (createIssueParam.getPullRequestModel() != null) {
            createRequestByPullRequest(createIssueParam);
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    protected void createRequestByPullRequest(CreateIssueParam createIssueParam) {
        int number = createIssueParam.getPullRequestModel().getNumber();
        createIssueParam.getPullRequestModel().setBody(InputHelper.toString(createIssueParam.getIssueInfo().getDescription()));
        createIssueParam.getPullRequestModel().setTitle(createIssueParam.getIssueInfo().getTitle());
        if (isCollaborator) {
            if (createIssueParam.getLabels() != null) {
                LabelListModel labelModels = new LabelListModel();
                labelModels.addAll(createIssueParam.getLabels());
                createIssueParam.getPullRequestModel().setLabels(labelModels);
            }
            if (createIssueParam.getMilestoneModel() != null) {
                createIssueParam.getPullRequestModel().setMilestone(createIssueParam.getMilestoneModel());
            }
            if (createIssueParam.getUsers() != null) {
                UsersListModel usersListModel = new UsersListModel();
                usersListModel.addAll(createIssueParam.getUsers());
                createIssueParam.getPullRequestModel().setAssignees(usersListModel);
            }
        }
        IssueRequestModel requestModel = IssueRequestModel.clone(createIssueParam.getPullRequestModel(), false);
        makeRestCall(RestProvider.getPullRequestService(isEnterprise()).editPullRequest(createIssueParam.getIssueInfo().getLogin(), createIssueParam.getIssueInfo().getRepo(), number, requestModel)
                .flatMap(pullRequest1 -> RestProvider.getIssueService(isEnterprise()).getIssue(createIssueParam.getIssueInfo().getLogin(), createIssueParam.getIssueInfo().getRepo(), number),
                        //hack to get reactions from issue api
                        (pullRequest1, issueReaction) -> {
                            if (issueReaction != null) {
                                pullRequest1.setReactions(issueReaction.getReactions());
                            }
                            return pullRequest1;
                        }), pr -> {
            if (pr != null) {
                sendToView(view -> view.onSuccessSubmission(pr));
            } else {
                sendToView(view -> view.showMessage(R.string.error, R.string.error_creating_issue));
            }
        }, false);
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    protected void createRequestByIssue(CreateIssueParam createIssueParam) {
        createIssueParam.getIssueModel().setBody(InputHelper.toString(createIssueParam.getIssueInfo().getDescription()));
        createIssueParam.getIssueModel().setTitle(createIssueParam.getIssueInfo().getTitle());
        int number = createIssueParam.getIssueModel().getNumber();
        if (isCollaborator) {
            if (createIssueParam.getLabels() != null) {
                LabelListModel labelModels = new LabelListModel();
                labelModels.addAll(createIssueParam.getLabels());
                createIssueParam.getIssueModel().setLabels(labelModels);
            }
            if (createIssueParam.getMilestoneModel() != null) {
                createIssueParam.getIssueModel().setMilestone(createIssueParam.getMilestoneModel());
            }
            if (createIssueParam.getUsers() != null) {
                UsersListModel usersListModel = new UsersListModel();
                usersListModel.addAll(createIssueParam.getUsers());
                createIssueParam.getIssueModel().setAssignees(usersListModel);
            }
        }
        IssueRequestModel requestModel = IssueRequestModel.clone(createIssueParam.getIssueModel(), false);
        makeRestCall(RestProvider.getIssueService(isEnterprise()).editIssue(createIssueParam.getIssueInfo().getLogin(), createIssueParam.getIssueInfo().getRepo(), number, requestModel),
                issueModel -> {
                    if (issueModel != null) {
                        sendToView(view -> view.onSuccessSubmission(issueModel));
                    } else {
                        sendToView(view -> view.showMessage(R.string.error, R.string.error_creating_issue));
                    }
                }, false);
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    protected void createRequest(CreateIssueParam createIssueParam) {
        CreateIssueModel createIssue = new CreateIssueModel();
        createIssue.setBody(InputHelper.toString(createIssueParam.getIssueInfo().getDescription()));
        createIssue.setTitle(createIssueParam.getIssueInfo().getTitle());
        if (isCollaborator) {
            if (createIssueParam.getLabels() != null && !createIssueParam.getLabels().isEmpty()) {
                createIssue.setLabels(Stream.of(createIssueParam.getLabels()).map(LabelModel::getName).collect(Collectors.toCollection(ArrayList::new)));
            }
            if (createIssueParam.getUsers() != null && !createIssueParam.getUsers().isEmpty()) {
                createIssue.setAssignees(Stream.of(createIssueParam.getUsers()).map(User::getLogin).collect(Collectors.toCollection(ArrayList::new)));
            }
            if (createIssueParam.getMilestoneModel() != null) {
                createIssue.setMilestone((long) createIssueParam.getMilestoneModel().getNumber());
            }
        }
        makeRestCall(RestProvider.getIssueService(isEnterprise()).createIssue(createIssueParam.getIssueInfo().getLogin(), createIssueParam.getIssueInfo().getRepo(), createIssue),
                issueModel -> {
                    if (issueModel != null) {
                        sendToView(view -> view.onSuccessSubmission(issueModel));
                    } else {
                        sendToView(view -> view.showMessage(R.string.error, R.string.error_creating_issue));
                    }
                }, false);
    }

    @Override
    public void onCheckAppVersion() {
        makeRestCall(RestProvider.getRepoService(false).getLatestRelease("k0shk0sh", "FastHub"),
                release -> {
                    if (release != null) {
                        if (!BuildConfig.VERSION_NAME.contains(release.getTagName())) {
                            sendToView(CreateIssueMvp.View::onShowUpdate);
                        } else {
                            sendToView(BaseMvp.FAView::hideProgress);
                        }
                    }
                }, false);
    }

    @Override
    public boolean isCollaborator() {
        return isCollaborator;
    }
}
