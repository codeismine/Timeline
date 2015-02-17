package com.synerzip.timeline.asynctasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.synerzip.timeline.constants.TimelineConstants;
import com.synerzip.timeline.structures.PostDetails;

public class FetchTimelineAsyncTask extends AsyncTask<Void, Void, String> {

	private Context mContext;
	private ProgressBar mProgressBar;
	private ListView mListView;
	private SwipeRefreshLayout mSwipeRefreshLayout;

	public FetchTimelineAsyncTask(Context context, ProgressBar progressBar,
			ListView listView, SwipeRefreshLayout swipeRefreshLayout) {
		super();
		this.mContext = context;
		this.mProgressBar = progressBar;
		this.mListView = listView;
		this.mSwipeRefreshLayout = swipeRefreshLayout;
	}

	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(TimelineConstants.timelineURL);

		try {
			// Execute HTTP Post Request
			HttpResponse httpResponse = httpClient.execute(httpPost);
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
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (result != null) {
			ArrayList<PostDetails> postDetails = TimelineConstants
					.returnPostDetails(result);
			
		} else {

		}
	}

}
