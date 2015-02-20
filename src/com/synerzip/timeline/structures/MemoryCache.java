package com.synerzip.timeline.structures;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * Used to set cache folder size limit ( How much mb/kb downloaded image cache folder will store ) and also used to clear cache files from sdcard.
 * @author Jitesh Lalwani
 */
public class MemoryCache {

	private static final String TAG = "MemoryCache";

	// Last argument true for LRU ordering
	private Map<String, Bitmap> cache = Collections
			.synchronizedMap(new LinkedHashMap<String, Bitmap>(10, 1.5f, true));

	// current allocated size
	private long size = 0;

	// max memory cache folder used to download images in bytes
	private long limit = 1000000;

	public MemoryCache() {

		// use 25% of available heap size
		setLimit(Runtime.getRuntime().maxMemory() / 4);
	}

	/**
	 * Set Limits for Memory Cache
	 * @param new_limit
	 */
	public void setLimit(long new_limit) {

		limit = new_limit;
		Log.i(TAG, "MemoryCache will use up to " + limit / 1024. / 1024. + "MB");
	}

	/**
	 * Returns Bitmap
	 * @param id
	 * @return
	 */
	public Bitmap get(String id) {
		try {
			if (!cache.containsKey(id))
				return null;

			return cache.get(id);

		} catch (NullPointerException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * @param id
	 * @param bitmap
	 */
	public void put(String id, Bitmap bitmap) {
		try {
			if (cache.containsKey(id))
				size -= getSizeInBytes(cache.get(id));
			cache.put(id, bitmap);
			size += getSizeInBytes(bitmap);
			checkSize();
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	/**
	 * Checks Size
	 */
	private void checkSize() {
		Log.i(TAG, "cache size=" + size + " length=" + cache.size());
		if (size > limit) {

			// least recently accessed item will be the first one iterated
			Iterator<Entry<String, Bitmap>> iter = cache.entrySet().iterator();

			while (iter.hasNext()) {
				Entry<String, Bitmap> entry = iter.next();
				size -= getSizeInBytes(entry.getValue());
				iter.remove();
				if (size <= limit)
					break;
			}
			Log.i(TAG, "Clean cache. New size " + cache.size());
		}
	}

	/**
	 * Clear cache
	 */
	public void clear() {
		try {
			cache.clear();
			size = 0;
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Gets Size in Bytes for Bitmap
	 * @param bitmap
	 * @return
	 */
	long getSizeInBytes(Bitmap bitmap) {
		if (bitmap == null)
			return 0;
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
}