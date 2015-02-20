package com.synerzip.timeline;

import java.util.ArrayList;

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
import com.synerzip.timeline.structures.PostDetails;
import com.synerzip.timeline.utility.TimelineUtility;

/**
 * TimelineActivity represents a list showing Poster's Avatar, Name and
 * Description of the post
 * 
 * @author Jitesh Lalwani
 */

public class TimelineActivity extends Activity {

	private ListView mListView;
	private ProgressBar mProgressBar;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private Button mRefreshButton;
	private ArrayList<PostDetails> mPostDetails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline_activity);

		mPostDetails = new ArrayList<PostDetails>();

		mListView = (ListView) findViewById(android.R.id.list);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

		mSwipeRefreshLayout.setColorSchemeResources(R.color.orange,
				R.color.green, R.color.blue, R.color.orange);

		mRefreshButton = (Button) findViewById(R.id.refreshButton);
		mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {

				checkConnectionAndExecute("swipeRefresh");
			}
		});
		checkConnectionAndExecute("");

		mRefreshButton.setOnClickListener(new RefreshButtonOnClickListener());
	}

	/**
	 * This function checks if Internet connection is available and if
	 * connection is available than calls FetchTimelineAsyncTask which loads the
	 * data in ListView
	 * 
	 * @param type
	 *            type determines whether this function was called from
	 *            SwipeRefreshLayout or not
	 */
	private void checkConnectionAndExecute(String type) {
		if (TimelineUtility.isConnectingToInternet(this)) {
			new FetchTimelineAsyncTask(this, mProgressBar, mListView,
					mSwipeRefreshLayout, mRefreshButton, mPostDetails)
					.execute();
		} else {
			if (type.equals("swipeRefresh")) {
				mSwipeRefreshLayout.setRefreshing(false);
			} else {
				mListView.setVisibility(View.GONE);
				mProgressBar.setVisibility(View.GONE);
				mRefreshButton.setVisibility(View.VISIBLE);
			}

			Toast.makeText(this,
					getResources().getString(R.string.internet_error),
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * OnClickListener Inner class for Refresh Button
	 * 
	 * @author Jitesh Lalwani
	 */
	private class RefreshButtonOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {

			view.setVisibility(View.GONE);
			checkConnectionAndExecute("");

		}

	}

}
