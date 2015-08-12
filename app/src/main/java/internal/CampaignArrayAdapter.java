package internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wepay.wecrowd.wecrowd.R;

import java.util.ArrayList;

import models.Campaign;

/**
 * Created by zachv on 7/21/15.
 */
public class CampaignArrayAdapter extends ArrayAdapter<Campaign> {
    private final Context context;
    private final ArrayList<Campaign> values;


    public CampaignArrayAdapter(Context context, ArrayList<Campaign> values) {
        super(context, -1, values);

        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        View rowView;
        TextView titleTextView, goalTextView;
        final ImageView imageView;
        final Campaign campaign;
        final Bitmap campaignImage;
        final String cacheKey;

        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.item_campaign_feed, parent, false);
        campaign = this.values.get(position);

        titleTextView = (TextView) rowView.findViewById(R.id.campaign_feed_cell_title);
        titleTextView.setText(campaign.getTitle(), TextView.BufferType.NORMAL);

        goalTextView = (TextView) rowView.findViewById(R.id.campaign_feed_cell_end_date);
        goalTextView.setText(campaign.getEndDate());

        imageView = (ImageView) rowView.findViewById(R.id.campaign_feed_image);

        // Check if the image already exists in the cache
        cacheKey = ImageCache.getKeyForID(campaign.getCampaignID());
        campaignImage = ImageCache.getBitmapFromCache(cacheKey);

        if (campaignImage == null) {
            Campaign.fetchImage(campaign, new APIResponseHandler() {
                @Override
                public void onCompletion(Bitmap bitmap, Throwable throwable) {


                    imageView.setImageBitmap(bitmap);
                    imageView.invalidate();

                    ImageCache.addBitmapToCache(cacheKey, bitmap);
                }
            });
        } else {
            imageView.setImageBitmap(campaignImage);
            imageView.invalidate();
        }

        return rowView;
    }


}
