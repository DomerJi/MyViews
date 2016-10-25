package bluetooth.jsp.com.myviews.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2016/10/20.
 */
public class Clock extends View implements Runnable{
    private Paint mPaint1 = new Paint();
    private Paint mPaint2 = new Paint();

    private int centerX = 0;
    private int centerY = 0;

    private int screenWidth = 0;
    private int screenHeight = 0;
    private Paint mTextPaint;

    private RectF mRange = new RectF();
    private String timeStr;

    public Clock(Context context) {
        this(context,null);
    }

    public Clock(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint1.setColor(Color.BLACK);
        mPaint1.setStrokeWidth(1);
        mPaint1.setAntiAlias(true);
        mPaint1.setStyle(Paint.Style.STROKE);

        mPaint2.setColor(Color.RED);
        mPaint2.setStrokeWidth(2);
        mPaint2.setAntiAlias(true);
        mPaint2.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setStrokeWidth(2);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        centerX = screenWidth/2;
        centerY = screenHeight/2;
        mRange.set(100,centerY-centerX-100,screenWidth-100,centerX+centerX-100);

        time.start();

    }

    /**
     * 文字的大小
     */
    private float mTextSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 将坐标系原点移动到画布正中心
        canvas.translate(centerX, centerY);
        canvas.drawCircle(0, 0, centerX - 100, mPaint1);

        canvas.drawCircle(0, 0, centerX - 150, mPaint1);

        for(int i = 0; i < 60; i++){
            if(i%5==0){
                canvas.drawLine(0, centerX - 160, 0, centerX - 100, mPaint2);
                canvas.rotate(6);
            }else {
                canvas.drawLine(0,centerX-150,0,centerX-100,mPaint1);
                canvas.rotate(6);
            }
        }
//        int startAngle = 0;
//        int tempAngle = 360 / 12;
//
//        for (int i = 0;i < 12;i++){
//            drawText(startAngle,tempAngle*i+tempAngle,(i+1)+"",canvas);
//        }

        canvas.save();
        mTextPaint.setTextSize(sp2px(15));
        int scalewith = (int) mTextPaint.measureText("12");
        canvas.drawText("12", -scalewith / 2, -centerX + 200, mTextPaint);
        canvas.restore();

        canvas.save();
        int scalewith1 = (int) mTextPaint.measureText("3");
        canvas.drawText("3", -scalewith1 / 2 + centerX - 200, 0 + scalewith1 / 2, mTextPaint);
        canvas.restore();

        if(!TextUtils.isEmpty(timeStr)){
            String[] time = timeStr.split("\\:");
            int h = Integer.parseInt(time[0]);
            int m = Integer.parseInt(time[1]);
            int s = Integer.parseInt(time[2]);
            canvas.save();
            canvas.rotate((float) (30 * h + (m / 60f) * 30f));
            canvas.drawLine(0, 0, 0, -centerX + 290, mPaint1);
            canvas.restore();

            canvas.save();
            canvas.rotate(6 * m);
            canvas.drawLine(0, 0, 0, -centerX + 260, mPaint1);
            canvas.restore();

            canvas.save();
            canvas.rotate(6 * s);
            canvas.drawLine(0, 0, 0, -centerX + 230, mPaint2);
            canvas.restore();

            int scalewith2 = (int) mTextPaint.measureText(timeStr);
            canvas.drawText(timeStr,-scalewith2/2,100,mTextPaint);

            Log.e("TIME", timeStr + "_____________________*********" + time[0] + time[1] + time[2]);

        }


//        postInvalidateDelayed(1000);
//        Log.e("TIME",timeStr+"_____________________--");


    }


    public int getFontHeight(Paint paint)
    {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent -  fm.ascent) + 2;
    }



    /**
     * 绘制文本
     *
     * @param rect
     * @param startAngle
     * @param sweepAngle
     * @param string
     */
    private void drawText(float startAngle, float sweepAngle, String string,Canvas mCanvas) {
        int mRadius = centerX-100;
        Path path = new Path();
        path.addArc(mRange, startAngle, sweepAngle);
        float textWidth = mTextPaint.measureText(string);
        // 利用水平偏移让文字居中
        float hOffset = (float) (mRadius * Math.PI / 12 / 2 - textWidth / 2);// 水平偏移
        float vOffset = mRadius / 2 / 6;// 垂直偏移
        mCanvas.drawTextOnPath(string, path, hOffset, vOffset, mTextPaint);
    }
    private boolean flag = true;
    Thread time = new Thread(this);
    @Override
    public void run() {
        while (flag){
            Message msg = new Message();
            msg.what = 0;
            handler.sendMessage(msg);
            try {
                time.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                timeStr = format.format(new Date());
                Log.e("TIME",timeStr);
                postInvalidate();
            }else{
            }
        }
    };

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}
