package panjiangang.bwie.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import panjiangang.bwie.com.myapplication.activity.Main2Activity;

public class Circle_jdt extends View {
    private Paint paint = new Paint();
    private boolean runing = true;
    private int p = 0;
    Context context;

    public Circle_jdt(Context context) {
        super(context);
    }

    public Circle_jdt(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(Color.RED);//设置画笔的颜色
        //paint.setStyle(Paint.Style.STROKE);//设置画笔 填充是空心的

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (runing) {
                    if (p >= 360) {
                        runing = false;
                        return;
                    }

                    p += 10;
                    postInvalidate(); //子线程刷新 系统调用onDraw（） 方法

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public Circle_jdt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);//设置画笔的颜色
        int x = getWidth() / 2;
        int y = getHeight() / 2;

        int radius = 200;//设置园的大小
        paint.setStrokeWidth(30);//设置画笔的粗细
//定义一个区域
        RectF rectF = new RectF(x - radius, y - radius, x + radius, y + radius);
        //画弧
//       useCentor  true 从中心点开始画 false 中心点不现实
        canvas.drawArc(rectF, -90, p, true, paint);

        int text = (int) ((float) p / 360 * 100);

//        measureText  测量字符串的宽度
        float textWidth = paint.measureText(text + "%");
        Rect rextText = new Rect();
//        rextText.height() 获取字符串的高度
        paint.getTextBounds(text + "%", 0, (text + "%").length(), rextText);

        paint.setTextSize(30);
        paint.setStrokeWidth(1);
        //画文字
        paint.setColor(Color.BLACK);//设置画笔的颜色
        canvas.drawText(text + "%", x - textWidth / 2, y + rextText.height() / 2, paint);
        if (p == 360) {
            Toast.makeText(context, "欢迎使用", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, Main2Activity.class);
            context.startActivity(intent);
        }
    }

}

