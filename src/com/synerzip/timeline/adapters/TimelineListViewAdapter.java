package com.synerzip.timeline.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.synerzip.timeline.R;
import com.synerzip.timeline.structures.ImageLoader;
import com.synerzip.timeline.structures.PostDetails;

/**
 * Used to create each list row. Inflate timeline_card_item.xml file for each row and call ImageLoader.java to download image from url and resize downloaded image cache on sdcard.
 * @author Jitesh Lalwani
 */
public class TimelineListViewAdapter extends ArrayAdapter<PostDetails> {

	private Context mContext;
	private ArrayList<PostDetails> mPostDetails;
	public ImageLoader imageLoader;

	public TimelineListViewAdapter(Context context, int resource,
			ArrayList<PostDetails> postDetails) {
		super(context, resource, postDetails);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mPostDetails = postDetails;
		imageLoader = new ImageLoader(context);
	}

	static class ViewHolder {
		public ImageView avatarImageView;
		public TextView posterNameTextView;
		public TextView postTextView;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;

		if (convertView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(R.layout.timeline_card_item, parent,
					false);

			viewHolder = new ViewHolder();
			viewHolder.avatarImageView = (ImageView) convertView
					.findViewById(R.id.avatarImageView);
			viewHolder.posterNameTextView = (TextView) convertView
					.findViewById(R.id.posterNameTextView);

			viewHolder.postTextView = (TextView) convertView
					.findViewById(R.id.postTextView);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}


		Animation animation = AnimationUtils.loadAnimation(mContext,
				android.R.anim.fade_in);
		viewHolder.avatarImageView.startAnimation(animation);
		viewHolder.posterNameTextView.setText(mPostDetails.get(position)
				.getPosterName());
		viewHolder.postTextView.setText(mPostDetails.get(position)
				.getPostText());
		imageLoader.DisplayImage(mPostDetails.get(position).getAvatarURL(),
				viewHolder.avatarImageView);

		return convertView;
	}

}