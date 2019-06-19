package com.beijing.chelingling.view;

/**
 * Created by shidu on 2018/3/30.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import com.beijing.chelingling.R;

public class GifView extends View {

    /**
     * 默认为1秒
     */
    private static final int DEFAULT_MOVIE_DURATION = 1000;

    private int mMovieResourceId;

    private Movie mMovie;

    private long mMovieStart;

    private int mCurrentAnimationTime = 0;

    private float mLeft;

    private float mTop;

    private float mScale;

    private int mMeasuredMovieWidth;

    private int mMeasuredMovieHeight;

    private boolean mVisible = true;

    private volatile boolean mPaused = false;

    public GifView(Context context) {
        this(context, null);
    }

    public GifView(Context context, AttributeSet attrs) {
        this(context, attrs, R.styleable.CustomTheme_gifViewStyle);
    }

    public GifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setViewAttributes(context, attrs, defStyle);
    }

    @SuppressLint("NewApi")
    private void setViewAttributes(Context context, AttributeSet attrs,
                                   int defStyle) {
        //如果版本>=11
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        // 从描述文件中读出gif的值，创建出Movie实例
        TypedArray array=context.obtainStyledAttributes
                (attrs, R.styleable.GifView, defStyle, R.style.AppTheme);
        mMovieResourceId = array.getResourceId(R.styleable.GifView_gif, -1);
        mPaused = array.getBoolean(R.styleable.GifView_paused, false);
        array.recycle();
        if(mMovieResourceId!=-1){
            mMovie=Movie.decodeStream(getResources().openRawResource(mMovieResourceId));
        }
    }

    /**
     * 设置gif图资源
     * @param movieResId
     */
    public void setMovieResource(int movieResId) {
        this.mMovieResourceId = movieResId;
        mMovie = Movie.decodeStream(getResources().openRawResource(
                mMovieResourceId));
        requestLayout();
    }

    public void setMovie(Movie movie) {
        this.mMovie = movie;
        requestLayout();
    }

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovieTime(int time) {
        mCurrentAnimationTime = time;
        invalidate();
    }

    /**
     * 设置暂停
     *
     * @param paused
     */

    /*
     * SystemClock.currentThreadTimeMillis(); // 在当前线程中已运行的时间
     * SystemClock.elapsedRealtime(); // 从开机到现在的毫秒书（手机睡眠(sleep)的时间也包括在内）
     * SystemClock.uptimeMillis(); // 从开机到现在的毫秒书（手机睡眠的时间不包括在内）
     * SystemClock.sleep(100); //
     * 类似Thread.sleep(100);但是该方法会忽略InterruptedException
     * SystemClock.setCurrentTimeMillis(1000); //
     * 设置时钟的时间，和System.setCurrentTimeMillis类似
     */
    public void setPaused(boolean paused) {
        mPaused = paused;
        if (!paused) {
            mMovieStart = SystemClock.uptimeMillis() - mCurrentAnimationTime;
        }
        invalidate();
    }

    /**
     * 判断gif图是否停止了
     *
     * @return
     */
    public boolean isPaused(){
        return mPaused;
    }

    //重新设置一下宽高
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(mMovie!=null){
            //获取movie的宽高
            int movieWidth=mMovie.width();
            int movieHeight=mMovie.height();
            int maximumWidth=MeasureSpec.getSize(widthMeasureSpec);
            float scaleW=(float)movieWidth/(float)maximumWidth;
            mScale=1f/scaleW;
            //进行伸缩
            mMeasuredMovieWidth=maximumWidth;
            mMeasuredMovieHeight=(int) (movieHeight*mScale);

            setMeasuredDimension(mMeasuredMovieWidth, mMeasuredMovieHeight);
        }else{
            setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
        }
    }

    //视图的绘制仅在Framework层分为三个阶段measure，layout，draw。
    //覆写onLayout的目的是为了指定视图的显示位置，
    //方法执行的前后顺序是在onMeasure之后，因为视图肯定是只有知道大小的情况下，才能确定怎么摆放。
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mLeft=(getWidth()-mMeasuredMovieWidth)/2f;
        mTop=(getHeight()-mMeasuredMovieHeight)/2f;
        mVisible=(getVisibility()==View.VISIBLE);
    }

    protected void onDraw(Canvas canvas) {
        if(mMovie!=null){
            if(!mPaused){
                updateAnimationTime();
                drawMovieFrame(canvas);
                invalidateView();
            }else{
                drawMovieFrame(canvas);
            }
        }
    }


    private void invalidateView() {
        if(mVisible){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
                postInvalidateOnAnimation();
            }else{
                invalidate();
            }
        }
    }

    //更新动画时间
    @SuppressLint("NewApi")
    private void updateAnimationTime(){
        long now=SystemClock.uptimeMillis();
        //如果是第一帧，记录起始时间
        if(mMovieStart==0){
            mMovieStart=now;
        }
        //取出动画的时长
        int dur=mMovie.duration();
        if(dur==0){
            dur=DEFAULT_MOVIE_DURATION;
        }
        //算出需要显示第几帧
        mCurrentAnimationTime=(int) ((now-mMovieStart)%dur);
    }

    @SuppressLint("WrongConstant")
    private void drawMovieFrame(Canvas canvas){
        //设置显示的帧，绘制即可
        mMovie.setTime(mCurrentAnimationTime);
        //用来保存Canvas的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。
        canvas.save(Canvas.MATRIX_SAVE_FLAG);
        canvas.scale(mScale, mScale);
        mMovie.draw(canvas, mLeft/mScale, mTop/mScale);
        //用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。
        canvas.restore();
        //注意：save和restore要配对使用（restore可以比save少，但不能多），
        //如果restore调用次数比save多，会引发Error。
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        mVisible=(screenState==SCREEN_STATE_ON);
        invalidateView();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        mVisible=(visibility==VISIBLE);
        invalidateView();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        mVisible=(visibility==VISIBLE);
        invalidateView();
    }
}
