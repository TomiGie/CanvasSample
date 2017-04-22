package jp.itnav.canvastouchsample;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 画面情報を取得
        Display display = getWindowManager().getDefaultDisplay();

        GameView touchView = new GameView(this, display);

        // GameViewをセット
        setContentView(touchView);
    }
}
