package com.synerzip.timeline.structures;

import java.io.File;

import android.content.Context;

/**
 * Used to create folder at sdcard and create map to store downloaded image
 * information.
 * @author Jitesh Lalwani
 */
public class FileCache {

	private File cacheDir;

	public FileCache(Context context) {

		// Find the dir at SDCARD to save cached images

		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			// if SDCARD is mounted (SDCARD is present on device and mounted)
			cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"LazyList");
		} else {
			// if checking on simulator the create cache dir in your application
			// context
			cacheDir = context.getCacheDir();
		}

		if (!cacheDir.exists()) {
			// create cache dir in your application context
			cacheDir.mkdirs();
		}
	}

	/**
	 * Identify images by hashcode or encode by URLEncoder.encode.
	 * @param url
	 * @return
	 */
	public File getFile(String url) {

		String filename = String.valueOf(url.hashCode());

		File file = new File(cacheDir, filename);
		return file;

	}

	/**
	 * Delete all cache directory files
	 */
	public void clear() {
		// list all files inside cache directory
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;

		for (File f : files)
			f.delete();
	}

}