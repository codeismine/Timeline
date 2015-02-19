package com.synerzip.timeline.constants;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.synerzip.timeline.structures.PostDetails;

public class TimelineConstants {

	public static String timelineURL = "https://alpha-api.app.net/stream/0/posts/stream/global";
	private static final String DATA = "data";
	private static final String USER = "user";
	private static final String USERNAME = "username";
	private static final String AVATARIMAGE = "avatar_image";
	private static final String URL = "url";
	private static final String Description = "description";
	private static final String TEXT = "text";

	public static ArrayList<PostDetails> returnPostDetails(String json)

	{
		ArrayList<PostDetails> postDetails = new ArrayList<PostDetails>();
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray = jsonObject.getJSONArray(DATA);
			for (int i = 0; i < jsonArray.length(); i++) {
				PostDetails postDetail = new PostDetails();
				JSONObject dataJsonObject = jsonArray.getJSONObject(i);

				JSONObject userJsonObject = dataJsonObject.getJSONObject(USER);

				postDetail.setPosterName(userJsonObject.isNull(USERNAME) ? ""
						: userJsonObject.getString(USERNAME));

				// if (userJsonObject.getJSONObject(AVATARIMAGE) != null) {
				postDetail.setAvatarURL(userJsonObject.getJSONObject(
						AVATARIMAGE).isNull(URL) ? "" : userJsonObject
						.getJSONObject(AVATARIMAGE).getString(URL));
				// } else {
				// postDetail.setAvatarURL("");
				// }

				postDetail.setPostText(userJsonObject
						.getJSONObject(Description).isNull("TEXT") ? ""
						: userJsonObject.getJSONObject(Description).getString(
								TEXT));

				postDetails.add(postDetail);

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return postDetails;

	}

	public static void CopyStream(InputStream inputStream,
			OutputStream outputStream) {
		final int buffer_size = 1024;
		try {

			byte[] bytes = new byte[buffer_size];
			for (;;) {
				// Read byte from input stream

				int count = inputStream.read(bytes, 0, buffer_size);
				if (count == -1)
					break;

				// Write byte from output stream
				outputStream.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static boolean isConnectingToInternet(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}
}
