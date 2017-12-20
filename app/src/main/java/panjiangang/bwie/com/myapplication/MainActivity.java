package panjiangang.bwie.com.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import panjiangang.bwie.com.myapplication.activity.Main2Activity;

public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SimpleDraweeView draweeView = findViewById(R.id.main_sdv);
//        Uri uri2 = Uri.parse("http://img2.ph.126.net/uzguQM3gqa3NcK2XaOD3FA==/6631646108817614243.gif");
//        DraweeController mDraweeController = Fresco.newDraweeControllerBuilder()
//                .setAutoPlayAnimations(true)
//                .setUri(uri2)//设置uri
//                .build();
//        Toast.makeText(this, "1111111111", Toast.LENGTH_SHORT).show();
//        draweeView.setController(mDraweeController);

//        handler.sendEmptyMessageDelayed(0, 3000);
    }
}
