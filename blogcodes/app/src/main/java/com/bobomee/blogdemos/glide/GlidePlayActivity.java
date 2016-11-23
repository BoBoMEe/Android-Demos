package com.bobomee.blogdemos.glide;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bobomee.android.common.util.ToastUtil;
import com.bobomee.blogdemos.R;
import com.bobomee.blogdemos.base.BaseActivity;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.NotificationTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by bobomee on 16/4/24.
 */
public class GlidePlayActivity extends BaseActivity {


    private static final int NOTIFICATION_ID = 0x11;
    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.imageView6)
    ImageView imageView6;
    @BindView(R.id.imageView7)
    ImageView imageView7;
    @BindView(R.id.container)
    LinearLayout container;
    private Unbinder mBind;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glide_play_layout);
        mBind = ButterKnife.bind(this);

        simpleUse();
        CenterCrop();
        FitCenter();
        thumbnail();
        notification();
        errorListener();
    }

    private void simpleUse() {

        Glide.with(this).
                load(pic_url).
                placeholder(R.mipmap.ic_launcher).
                skipMemoryCache(true).
                diskCacheStrategy(DiskCacheStrategy.NONE).
                error(R.mipmap.error).
                crossFade().
                override(100, 100).
                bitmapTransform(new RoundedCornersTransformation(this, 30, 10, RoundedCornersTransformation.CornerType.ALL)).
                into(imageView1);

    }

    private void FitCenter() {

        Glide.with(this).
                load(pic_url).
                placeholder(R.mipmap.ic_launcher).
                skipMemoryCache(true).
                diskCacheStrategy(DiskCacheStrategy.NONE).
                error(R.mipmap.error).
                crossFade().
                fitCenter().
                animate(R.anim.slide_in_left).
                override(200, 600).
                priority(Priority.HIGH).
                into(imageView2);

    }

    private void CenterCrop() {

        Glide.with(this).
                load(pic_url).
                placeholder(R.mipmap.ic_launcher).
                skipMemoryCache(true).
                diskCacheStrategy(DiskCacheStrategy.NONE).
                error(R.mipmap.error).
                crossFade().
                centerCrop().
                bitmapTransform(new BlurTransformation(this)).
                priority(Priority.LOW).
                animate(R.anim.scale_in).
                override(600, 200).
                into(imageView3);
    }

    private void thumbnail() {

        ItemView itemView = new ItemView(this);
        ViewTarget<ItemView, GlideDrawable> target = new ViewTarget<ItemView, GlideDrawable>(itemView) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                this.view.setImageView(resource.getCurrent());
            }
        };

        //用其它图片作为缩略图
        DrawableRequestBuilder<Integer> thumbnailRequest = Glide
                .with(this)
                .load(R.drawable.vector_heart);
        Glide.with(this)
                .load(pic_url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .thumbnail(thumbnailRequest)
                .into(target);

        container.addView(itemView);
    }

    private void errorListener() {
        Glide
                .with(this)
                .load("")
                .listener(requestListener)
                .error(R.drawable.ic_menu_camera)
                .into(imageView4);
    }


    ///////////////////////////////////////notifaction///////////////////////////////////////

    private NotificationTarget notificationTarget;

    private void notification() {

        final RemoteViews rv = new RemoteViews(getPackageName(), R.layout.notification_vew);

        rv.setImageViewResource(R.id.remoteview_notification_icon, R.mipmap.a);

        rv.setTextViewText(R.id.remoteview_notification_headline, "Headline");
        rv.setTextViewText(R.id.remoteview_notification_short_message, "Short Message");

// build notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Content Title")
                        .setContentText("Content Text")
                        .setContent(rv)
                        .setPriority(NotificationCompat.PRIORITY_MIN);

        final Notification notification = mBuilder.build();

// set big content view for newer androids
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification.bigContentView = rv;
        }

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID, notification);


        notificationTarget = new NotificationTarget(
                this,
                rv,
                R.id.remoteview_notification_icon,
                notification,
                NOTIFICATION_ID);


        Glide
                .with(getApplicationContext()) // safer!
                .load(pic_url)
                .asBitmap()
                .into(notificationTarget);
    }

    /////////////////////////////////////listener/////////////////////////////////////////

    private RequestListener<String, GlideDrawable> requestListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
            // todo log exception

            // important to return false so the error placeholder can be placed
            ToastUtil.show(GlidePlayActivity.this, e.toString());
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            return false;
        }
    };

}
