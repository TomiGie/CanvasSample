package jp.itnav.canvastouchsample;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by Tomigy@ITNAV.
 */


public class GameView extends View {
    private static final int ENEMY_WIDTH = 200;
    private static final int ENEMY_HEIGHT = 200;
    private static final int ENEMY_TIME = 5000;

    private int mScreenWidth;
    private int mScreenHeight;
    private float mTouchX = 0;
    private float mTouchY = 0;
    private Resources mResources;
    private Rect mCharacterRect;
    private Rect mEnemyRect;
    private Rect mScreenRect;

    private Handler mHandler;

    // image Bitmaps
    private Bitmap mBackground;
    private Bitmap mCharacter;
    private Bitmap mEnemy;

    public GameView(Context context, Display display) {
        super(context);
        setUp(display);
        loadBitmaps();
    }

    private void setUp(Display display) {
        mHandler = new Handler();
        Point size = new Point();
        display.getSize(size);
        mScreenWidth = size.x;
        mScreenHeight = size.y;
        mResources = getResources();
        mScreenRect = new Rect(0, 0, mScreenWidth, mScreenHeight);
        mEnemyRect = getRandomRect();
    }

    private void loadBitmaps() {
        mBackground = getBitmap(mResources, R.mipmap.bg);
        mCharacter = getBitmap(mResources, R.mipmap.ic_launcher);
        mEnemy = getBitmap(mResources, R.mipmap.enemy);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTouchX = event.getX();
        mTouchY = event.getY();

        // 画面に触れた場合のみ、描画を更新
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            invalidate();
        }

        return true;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        Paint p = new Paint();
        // background
        canvas.drawBitmap(mBackground, null, mScreenRect, p);

        // draw character
        drawCharacter(canvas, mTouchX, mTouchY, p);

        if (isHitEnemy((int) mTouchX, (int) mTouchY)) {
            // change enemy position
            mEnemyRect = getRandomRect();
        }
        else {
            // draw enemy
            drawEnemy(canvas);
        }
    }

    private Bitmap getBitmap(Resources res, int resId) {
        return BitmapFactory.decodeResource(res, resId);
    }

    /**
     * 当たり判定メソッド
     * @return
     */
    private boolean isHitEnemy(int x, int y) {
        return mEnemyRect.contains(x, y);
    }

    private void drawCharacter(Canvas canvas, float x, float y, Paint p) {
        canvas.drawBitmap(mCharacter, x, y, p);
    }

    private void drawEnemy(Canvas canvas) {

        canvas.drawBitmap(mEnemy, null, mEnemyRect, null);
    }

    private Rect getRandomRect() {
        Random r = new Random();
        int left = r.nextInt(mScreenWidth - ENEMY_WIDTH);
        int top = r.nextInt(mScreenHeight - ENEMY_HEIGHT);

        return new Rect(left, top, left + ENEMY_WIDTH, top + ENEMY_HEIGHT);
    }

//    public class EnemyTask extends AsyncTask<Integer, Integer, Integer> {
//        private Canvas canvas;
//
//        public EnemyTask(Canvas canvas) {
//            super();
//            this.canvas = canvas;
//        }
//
//        @Override
//        protected Integer doInBackground(Integer... integers) {
//            try {
//                Thread.sleep(ENEMY_TIME);
//
//                drawEnemy(canvas);
//            }
//            catch (InterruptedException e) {
//
//            }
//            return null;
//        }
//    }

}
