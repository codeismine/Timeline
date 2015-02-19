package com.synerzip.timeline;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.synerzip.timeline.asynctasks.FetchTimelineAsyncTask;
import com.synerzip.timeline.constants.TimelineConstants;

public class TimelineActivity extends Activity {

	private ListView mListView;
	private ProgressBar mProgressBar;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private Button mRefreshButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline_activity);

		mListView = (ListView) findViewById(android.R.id.list);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

		mRefreshButton = (Button) findViewById(R.id.refreshButton);
		mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				checkConnectionAndExecute();
			}
		});
		checkConnectionAndExecute();

		mRefreshButton.setOnClickListener(new RefreshButtonOnClickListener());
	}

	private void checkConnectionAndExecute() {
		if (TimelineConstants.isConnectingToInternet(this)) {
			new FetchTimelineAsyncTask(this, mProgressBar, mListView,
					mSwipeRefreshLayout, mRefreshButton).execute();
		} else {
			mListView.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.GONE);
			mRefreshButton.setVisibility(View.VISIBLE);
			mSwipeRefreshLayout.setRefreshing(false);
			Toast.makeText(this,
					getResources().getString(R.string.internet_error),
					Toast.LENGTH_LONG).show();
		}
	}

	private class RefreshButtonOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			view.setVisibility(View.GONE);
			checkConnectionAndExecute();

		}

	}

}
