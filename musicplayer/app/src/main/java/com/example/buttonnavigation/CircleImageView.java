package com.example.buttonnavigation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class CircleImageView extends androidx.appcompat.widget.AppCompatImageView {
    //画笔
    private Paint paint;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }
    //画圆形图片
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if(drawable!=null){
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            //获得圆形的位图
            Bitmap b = getCilrcleBitmap(bitmap);
            //绘制的区域
            Rect rect = new Rect(0,0,b.getWidth(),b.getHeight());
            //绘制的大小(可以稍大一点,为边框预留)
            Rect rectDest = new Rect(0,0,getWidth(),getHeight());
            //绘制
            canvas.drawBitmap(b,rect,rectDest,paint);

        }else{
            super.onDraw(canvas);
        }
    }
    //获取圆形图片
    private Bitmap getCilrcleBitmap(Bitmap bitmap){//源图像
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        //打开抗锯齿
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        //设置目标图像区域外的颜色,(透明度,红色,绿色,蓝色)
        //canvas.drawARGB(90,90,0,0);
        //绘制目标图像
        canvas.drawCircle(bitmap.getWidth()/2,bitmap.getHeight()/2,bitmap.getWidth()/2,paint);
        //设置图像混合模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制区域
        Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        //绘制位置
        // Rect rectDest = new Rect(0,0,50,50);

        canvas.drawBitmap(bitmap,rect,rect,paint);
        paint.reset();
        return output;
    }
}
