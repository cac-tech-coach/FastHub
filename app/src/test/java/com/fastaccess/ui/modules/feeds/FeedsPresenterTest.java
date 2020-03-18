package com.fastaccess.ui.modules.feeds;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class FeedsPresenterTest {

    @Test
    public void name() {
        FeedsPresenter feedsPresenter = new FeedsPresenter();
        feedsPresenter.attachView(mock(FeedsMvp.View.class));
    }
}