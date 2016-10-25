package bluetooth.jsp.com.myviews.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by admin on 2016/10/20.
 */
public class SmilingFaceLoading extends View implements Runnable{

    private Paint mPaint1 = new Paint();
    private Paint mPaint2 = new Paint();
    private Paint mPaint3 = new Paint();

    private int radius = 10;
    private int lRadius = 50;

    private int mWidth;
    private int mHeight;

    private int starAnge = 0;
    private int sweepAnge = 180;

    private int rotate = 30;

    private boolean flag = true;
    private int speed = 6;
    private int speed2 = speed/2;


    public SmilingFaceLoading(Context context) {
        this(context, null);
    }

    public SmilingFaceLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint1.setColor(Color.BLUE);
        mPaint1.setAntiAlias(true);
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint1.setStrokeWidth(1);

        mPaint3.setColor(Color.BLUE);
        mPaint3.setAntiAlias(true);
        mPaint3.setStyle(Paint.Style.STROKE);
        mPaint3.setStrokeWidth(radius * 2);
        mPaint3.setStrokeCap(Paint.Cap.ROUND);

        mPaint2.setColor(Color.RED);
        mPaint2.setAntiAlias(true);
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setStrokeWidth(1);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.GREEN);//背景
//        canvas.drawCircle(0, 0, radius, mPaint2);//中心点
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.rotate(30);
        canvas.drawCircle(0, -mHeight / 2 + radius, radius, mPaint1);

        canvas.rotate(-60);
        canvas.drawCircle(0, -mHeight / 2 + radius, radius, mPaint1);




        canvas.rotate(rotate);
        RectF rectF = new RectF(-mWidth / 2+radius,-mHeight / 2+radius,mWidth / 2-radius,mHeight / 2-radius);
        Path path = new Path();
        path.addArc(rectF,starAnge,sweepAnge);

        canvas.drawPath(path, mPaint3);

        handler.postDelayed(this,500);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            rotate+=speed;
            if(rotate>180){
                rotate = -180;
            }

            if(sweepAnge>=180){
                flag = true;
            }
            if(sweepAnge<=0){
                flag = false;
            }
            if(flag){
                sweepAnge-=speed2;
            }else {
                sweepAnge+=speed2;
            }
            invalidate();

        }
    };

    @Override
    public void run() {
        Message msg = new Message();
        handler.sendMessage(msg);
        handler.removeCallbacks(this);
        handler.postDelayed(this,5);
    }
}
