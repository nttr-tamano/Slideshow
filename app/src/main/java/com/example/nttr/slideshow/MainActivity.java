package com.example.nttr.slideshow;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class MainActivity extends AppCompatActivity {

    ImageSwitcher mImageSwitcher;

    int[] mImageResouces = {
            R.drawable.slide00,
            R.drawable.slide01,
            R.drawable.slide02,
            R.drawable.slide03,
            R.drawable.slide04,
            R.drawable.slide05,
            R.drawable.slide06,
            R.drawable.slide07,
            R.drawable.slide08,
            R.drawable.slide09,
    };

    int mPosition = 0;

    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);

//        // 旧が右へ、新が左から入ってくる
//        mImageSwitcher.setInAnimation(MainActivity.this,android.R.anim.slide_in_left);
//        mImageSwitcher.setOutAnimation(MainActivity.this,android.R.anim.slide_out_right);

        // 旧が消えて、新が現れる
        mImageSwitcher.setInAnimation(MainActivity.this,android.R.anim.fade_in);
        mImageSwitcher.setOutAnimation(MainActivity.this,android.R.anim.fade_out);

        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(MainActivity.this);
                return imageView;

            }
        });
        // イメージスイッチャーへ画像を表示
        mImageSwitcher.setImageResource(R.drawable.slide00);

        Button prevButton = (Button) findViewById(R.id.prevButton);
        Button nextButton = (Button) findViewById(R.id.nextButton);

        // 前へ戻るボタン
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movePosition(-1);
            }
        });

        // 次へ進むボタン
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movePosition(1);
            }
        });

        mMediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.getdown);
        mMediaPlayer.setLooping(true);
    }

    // 画面が表示されたとき
    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer.start();
    }

    // http://androidgamepark.blogspot.jp/2013/03/bgnmediaplayer.html

    // 画面が閉じたとき
    @Override
    protected void onPause() {
        super.onPause();
//        mMediaPlayer.stop();
        mMediaPlayer.pause();
    }

    // 終了時
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
        mMediaPlayer.release(); // メモリ解放
    }

    // 表示する画像を指定数だけ前/先と入れ替える関数
    // 感想：先頭へ、末尾へ、一定数進める、一定数戻るにも使える
    private void movePosition(int move) {
        mPosition = mPosition + move;
        if (mPosition >= mImageResouces.length) {
            mPosition = 0;
        } else if (mPosition < 0) {
            mPosition = mImageResouces.length - 1;
        }
        mImageSwitcher.setImageResource(mImageResouces[mPosition]);

    }
}
