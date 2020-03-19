package com.fastaccess.ui.modules.feeds;

import com.google.common.truth.Truth;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FeedsPresenterTest {

    private FeedsPresenter feedsPresenter;

    @Before
    public void setUp() throws Exception {
        feedsPresenter = new FeedsPresenter();
    }

    @Test
    public void should_use_enterprise_value_in_pref_if_current_user_is_login_user() {
        feedsPresenter.setUser("qinyu");

        assertThat(feedsPresenter.isEnterpriseUser(true, "qinyu")).isTrue();
        assertThat(feedsPresenter.isEnterpriseUser(false, "qinyu")).isFalse();
    }

    @Test
    public void should_user_enterprise_in_model_if_current_user_is_not_login_user() {
        feedsPresenter.setUser("not qinyu");

        feedsPresenter.setEnterprise(true);
        assertTrue(feedsPresenter.isEnterpriseUser(true, "qinyu"));
        assertTrue(feedsPresenter.isEnterpriseUser(false, "qinyu"));

        feedsPresenter.setEnterprise(false);
        assertFalse(feedsPresenter.isEnterpriseUser(true, "qinyu"));
        assertFalse(feedsPresenter.isEnterpriseUser(false, "qinyu"));
    }
}