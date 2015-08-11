package internal;

import android.graphics.Bitmap;
import android.util.LruCache;

import models.Campaign;

/**
 * Created by zachv on 8/11/15.
 */
public class ImageCache {
    private static final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private static final int cacheSize = maxMemory / 8;
    private static final String IMAGE_CACHE_PREFIX = "IMG_";

    private static LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            return bitmap.getByteCount() / 1024;
        }
    };

    public static void addBitmapToCache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) {
            memCache.put(key, bitmap);
        }
    }

    public static Bitmap getBitmapFromCache(String key) {
        return memCache.get(key);
    }

    public static String getKeyForID(String ID) {
        return IMAGE_CACHE_PREFIX + ID;
    }

    public static String getKeyForID(Integer ID) {
        return IMAGE_CACHE_PREFIX + ID.toString();
    }
}
