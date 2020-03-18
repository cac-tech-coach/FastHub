package com.fastaccess.ui.modules.repos.issues.create;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.fastaccess.data.dao.model.Issue;
import com.fastaccess.data.dao.model.PullRequest;
import com.fastaccess.ui.base.mvp.BaseMvp;
import com.fastaccess.ui.modules.repos.extras.assignees.AssigneesMvp;
import com.fastaccess.ui.modules.repos.extras.labels.LabelsMvp;
import com.fastaccess.ui.modules.repos.extras.milestone.MilestoneMvp;

/**
 * Created by Kosh on 19 Feb 2017, 12:12 PM
 */

public interface CreateIssueMvp {

    interface View extends BaseMvp.FAView, LabelsMvp.SelectedLabelsListener, AssigneesMvp.SelectedAssigneesListener,
            MilestoneMvp.OnMilestoneSelected {
        void onSetCode(@NonNull CharSequence charSequence);

        void onTitleError(boolean isEmptyTitle);

        void onDescriptionError(boolean isEmptyDesc);

        void onSuccessSubmission(Issue issueModel);

        void onSuccessSubmission(PullRequest issueModel);

        void onShowUpdate();

        void onShowIssueMisc();
    }

    interface Presenter extends BaseMvp.FAPresenter {

        void checkAuthority(@NonNull String login, @NonNull String repoId);

        void onActivityForResult(int resultCode, int requestCode, Intent intent);

        void onSubmit(CreateIssueParam createIssueParam);

        void onCheckAppVersion();

        boolean isCollaborator();
    }
}
