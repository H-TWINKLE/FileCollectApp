package com.twinkle.hfilm.java;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.loopj.android.image.SmartImageView;
import com.twinkle.hfilm.R;


public   class NetworkImageHolderView implements Holder<String> {
    private SmartImageView smartImageView;
    @Override
    public View createView(Context context) {

        smartImageView = new SmartImageView(context);
        smartImageView.setScaleType(SmartImageView.ScaleType.FIT_XY);
        return smartImageView;
    }
    @Override
    public void UpdateUI(Context context,  int position, String data) {
        smartImageView.setImageUrl(data, R.drawable.logo_fir);

    }

}
