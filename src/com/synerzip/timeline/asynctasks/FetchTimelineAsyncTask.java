package com.synerzip.timeline.asynctasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.synerzip.timeline.R;
import com.synerzip.timeline.adapters.TimelineListViewAdapter;
import com.synerzip.timeline.constants.TimelineConstants;
import com.synerzip.timeline.structures.PostDetails;
import com.synerzip.timeline.utility.TimelineUtility;

/**
 * FetchTimelineAsyncTask fetches data to be shown in Listview (Avatar, Name and
 * Description)
 * @author Jitesh Lalwani
 */
public class FetchTimelineAsyncTask extends AsyncTask<Void, Void, String> {

	private Context mContext;
	private ProgressBar mProgressBar;
	private ListView mListView;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private Button mRefreshButton;
	private ArrayList<PostDetails> mPostDetails;

	public FetchTimelineAsyncTask(Context context, ProgressBar progressBar,
			ListView listView, SwipeRefreshLayout swipeRefreshLayout,
			Button refreshButton, ArrayList<PostDetails> postDetails) {
		super();
		this.mContext = context;
		this.mProgressBar = progressBar;
		this.mListView = listView;
		this.mSwipeRefreshLayout = swipeRefreshLayout;
		this.mRefreshButton = refreshButton;
		this.mPostDetails = postDetails;
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
		mProgressBar.setVisibility(View.VISIBLE);
		// mListView.setVisibility(View.GONE);
		mRefreshButton.setVisibility(View.GONE);
	}

	@Override
	protected String doInBackground(Void... params) {

		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(TimelineConstants.timelineURL);

		try {
			// Execute HTTP Post Request
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return result;
			}

			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream inputStream = null;

			BufferedReader reader;
			if (httpEntity != null) {
				inputStream = httpEntity.getContent();
			}
			reader = new BufferedReader(new InputStreamReader(inputStream),
					8000);
			StringBuffer builder = new StringBuffer("");
			String line = reader.readLine();
			while (line != null) {
				builder.append(line);
				line = reader.readLine();
			}
			inputStream.close();
			result = builder.toString();

			return result;

		} catch (Exception e) {
			return result;
		}

	}

	@Override
	protected void onPostExecute(String result) {

		super.onPostExecute(result);
		mProgressBar.setVisibility(View.GONE);
		mSwipeRefreshLayout.setRefreshing(false);
		if (result != null) {
			ArrayList<PostDetails> postDetails = TimelineUtility
					.returnPostDetails(result);

			if (postDetails != null) {
				mPostDetails.addAll(0, postDetails);
				TimelineListViewAdapter timelineListViewAdapter = new TimelineListViewAdapter(
						mContext, R.layout.timeline_card_item, mPostDetails);

				mListView.setVisibility(View.VISIBLE);
				mListView.setAdapter(timelineListViewAdapter);
			} else {
				Toast.makeText(mContext,
						mContext.getResources().getString(R.string.data_error),
						Toast.LENGTH_LONG).show();
				mRefreshButton.setVisibility(View.VISIBLE);
			}

		} else {

			Toast.makeText(mContext,
					mContext.getResources().getString(R.string.internet_error),
					Toast.LENGTH_LONG).show();
			mRefreshButton.setVisibility(View.VISIBLE);

		}
	}

}
