package com.fastaccess.ui.modules.feeds;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FeedsPresenterTest {

    @Test
    public void should_use_enterprise_value_in_pref_if_current_user_is_login_user() {
        FeedsPresenter feedsPresenter = new FeedsPresenter();
        feedsPresenter.setUser("qinyu");

        assertTrue(feedsPresenter.isEnterpriseUser(true, "qinyu"));
        assertFalse(feedsPresenter.isEnterpriseUser(false, "qinyu"));
    }

    @Test
    public void should_user_enterprise_in_model_if_current_user_is_not_login_user() {
        FeedsPresenter feedsPresenter = new FeedsPresenter();
        feedsPresenter.setUser("not qinyu");

        feedsPresenter.setEnterprise(true);
        assertTrue(feedsPresenter.isEnterpriseUser(true, "qinyu"));
        assertTrue(feedsPresenter.isEnterpriseUser(false, "qinyu"));

        feedsPresenter.setEnterprise(false);
        assertFalse(feedsPresenter.isEnterpriseUser(true, "qinyu"));
        assertFalse(feedsPresenter.isEnterpriseUser(false, "qinyu"));
    }
}