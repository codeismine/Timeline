package com.synerzip.timeline;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.synerzip.timeline.asynctasks.FetchTimelineAsyncTask;

public class TimelineActivity extends Activity {

	private ListView mListView;
	private ProgressBar mProgressBar;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline_activity);

		mListView = (ListView) findViewById(android.R.id.list);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				refreshTimeLine();
			}
		});

		new FetchTimelineAsyncTask(this, mProgressBar, mListView, mSwipeRefreshLayout)
				.execute();
	}

	private void refreshTimeLine() {
		new FetchTimelineAsyncTask(this, mProgressBar, mListView, mSwipeRefreshLayout)
				.execute();
	}

}
