package com.example.rj.openeyesvideo.ui.activity;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rj.openeyesvideo.R;
import com.example.rj.openeyesvideo.base.Contract.DetailContract;
import com.example.rj.openeyesvideo.base.RootActivity;
import com.example.rj.openeyesvideo.component.ImageLoader;
import com.example.rj.openeyesvideo.component.SimpleListener;
import com.example.rj.openeyesvideo.model.bean.ItemListBean;
import com.example.rj.openeyesvideo.presenter.DetailPresenter;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import butterknife.BindView;
import butterknife.Unbinder;

public class DetailActivity extends RootActivity<DetailPresenter> implements DetailContract.View {


    @BindView(R.id.vedio_player)
    StandardGSYVideoPlayer vedioPlayer;

    @BindView(R.id.view_main)
    RecyclerView recyclerView;

    private ItemListBean itemListBean;
    String Url;
    private OrientationUtils orientationUtils;
    ImageView imageView;
    boolean isPlay;
    boolean isPause;



    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        getIntentData();
        initRecyclerView();
        stateLoading();
        //vedioPlayer.setThumbImageView(imageView);
        vedioPlayer.getTitleTextView().setVisibility(View.GONE);
        vedioPlayer.getBackButton().setVisibility(View.GONE);
        orientationUtils=new OrientationUtils(this,vedioPlayer);
        orientationUtils.setEnable(false);
        GSYVideoOptionBuilder builder=new GSYVideoOptionBuilder();
        builder.setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setSeekRatio(1)
                .setUrl(Url)
                .setCacheWithPlay(false)
                .setStandardVideoAllCallBack(new SimpleListener(){
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        orientationUtils.setEnable(true);
                        isPlay=true;
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        Log.d("hzj", "onEnterFullscreen: ");
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                        Log.d("hzj", "onAutoComplete: ");
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        Log.d("hzj", "onQuitFullscreen: ");
                        if(orientationUtils!=null){
                            orientationUtils.backToProtVideo();
                        }
                    }
                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if(orientationUtils!=null){
                            orientationUtils.setEnable(!lock);
                        }
                    }
                })
                .setGSYVideoProgressListener(new GSYVideoProgressListener() {
                    @Override
                    public void onProgress(int progress, int secProgress, int currentPosition, int duration) {
                        Log.d("hzj  ", "onProgress: ");
                    }
                })
                .build(vedioPlayer);

        vedioPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
                vedioPlayer.startWindowFullscreen(DetailActivity.this,true,true);
            }
        });
    }

    private void initRecyclerView() {


    }

    private void getIntentData() {
        itemListBean=(ItemListBean) getIntent().getSerializableExtra("itemListBean");
        Url=itemListBean.getData().getPlayUrl();
        //Url=getIntent().getExtras().getString("url");
        //String image=getIntent().getExtras().getString("image");
        //int id=getIntent().getExtras().getInt("itemId");
        String image=itemListBean.getData().getCover().getFeed();
        int id=itemListBean.getData().getId();
        Log.d("hzj", "getIntentData: "+Url);
        imageView=new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoader.load(this,image,imageView);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void showContent() {

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    private GSYVideoPlayer getPlayer(){
        if(vedioPlayer.getFullWindowPlayer()!=null){
            return vedioPlayer.getFullWindowPlayer();
        }
        return vedioPlayer;
    }


//    @Override
//    public void onBackPresse() {
//        Log.d("hzj", "onBackPressedSupport: ");
//        if (orientationUtils!=null){
//            Log.d("hzj", "onBackPressedSupport: orientationUtils");
//            orientationUtils.backToProtVideo();
//        }
//        if (StandardGSYVideoPlayer.backFromWindowFull(this)){
//            return;
//        }
//        super.onBackPressed();
//    }

    @Override
    public void onBackPressedSupport() {
        Log.d("hzj", "onBackPressedSupport: ");
        if (orientationUtils!=null){
            Log.d("hzj", "onBackPressedSupport: orientationUtils");
            orientationUtils.backToProtVideo();
        }
        if (StandardGSYVideoPlayer.backFromWindowFull(this)){
            return;
        }
        super.onBackPressedSupport();
    }

    @Override
    protected void onPause() {
        getPlayer().onVideoPause();
        super.onPause();
        isPause=true;
    }

    @Override
    protected void onResume() {
        getPlayer().onVideoResume();
        super.onResume();
        isPause=false;
    }

    @Override
    protected void onDestroy() {

        if(isPlay){
            getPlayer().release();
        }
        if(orientationUtils!=null){
            orientationUtils.releaseListener();
        }
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(isPlay&& !isPause){
            vedioPlayer.onConfigurationChanged(this,newConfig,orientationUtils);
        }
    }

    @Override
    public void showErrorMsg(String s) {

    }
}
