package com.fastaccess.ui.modules.repos.issues.create;


import com.fastaccess.data.dao.model.Issue;
import com.fastaccess.data.dao.model.PullRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class CreateIssuePresenterTest {

    private CreateIssuePresenter createIssuePresenter;
    private CreateIssueMvp.View mockCreateIssueView;

    @Before
    public void setUp() {
        MockSettings mockSettings = Mockito.withSettings().defaultAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                if ("onSubmit".equals(invocation.getMethod().getName())) {
                    return invocation.callRealMethod();
                }
                return null;
            }
        });
        createIssuePresenter = Mockito.mock(CreateIssuePresenter.class, mockSettings);
        mockCreateIssueView = Mockito.mock(CreateIssueMvp.View.class);
        when(createIssuePresenter.getView()).thenReturn(mockCreateIssueView);
    }

    @Test
    public void should_return_when_onSubmit_issue_title_is_empty_and_view_no_empty() {
        CreateIssueParam createIssueParam = new CreateIssueParam();
        createIssueParam.setIssueInfo(new IssueInfo());
        createIssuePresenter.onSubmit(createIssueParam);
        verify(mockCreateIssueView, times(1)).onTitleError(true);
        verify(createIssuePresenter, never()).createRequest(any());
        verify(createIssuePresenter, never()).createRequestByIssue(any());
        verify(createIssuePresenter, never()).createRequestByPullRequest(any());
    }

    @Test
    public void should_return_when_onSubmit_issue_title_is_empty_and_view_is_empty() {
        CreateIssueParam createIssueParam = new CreateIssueParam();
        createIssueParam.setIssueInfo(new IssueInfo());
        when(createIssuePresenter.getView()).thenReturn(null);
        createIssuePresenter.onSubmit(createIssueParam);
        verify(createIssuePresenter, never()).createRequest(any());
        verify(createIssuePresenter, never()).createRequestByIssue(any());
        verify(createIssuePresenter, never()).createRequestByPullRequest(any());
    }

    @Test
    public void should_return_when_onSubmit_issue_and_pull_request_is_null() {
        CreateIssueParam createIssueParam = new CreateIssueParam();
        IssueInfo issueInfo = new IssueInfo();
        issueInfo.setTitle("hello world!");
        createIssueParam.setIssueInfo(issueInfo);
        createIssuePresenter.onSubmit(createIssueParam);
        verify(mockCreateIssueView, times(1)).onTitleError(false);
        verify(createIssuePresenter, times(1)).createRequest(any());
        verify(createIssuePresenter, never()).createRequestByIssue(any());
        verify(createIssuePresenter, never()).createRequestByPullRequest(any());
    }

    @Test
    public void should_return_when_onSubmit_issue_and_pull_request_is_not_null() {
        CreateIssueParam createIssueParam = new CreateIssueParam();
        IssueInfo issueInfo = new IssueInfo();
        issueInfo.setTitle("hello world!");
        createIssueParam.setIssueInfo(issueInfo);
        createIssueParam.setIssueModel(new Issue());
        createIssueParam.setPullRequestModel(new PullRequest());
        createIssuePresenter.onSubmit(createIssueParam);
        verify(mockCreateIssueView, times(1)).onTitleError(false);
        verify(createIssuePresenter, never()).createRequest(any());
        verify(createIssuePresenter, times(1)).createRequestByIssue(any());
        verify(createIssuePresenter, times(1)).createRequestByPullRequest(any());
    }
}