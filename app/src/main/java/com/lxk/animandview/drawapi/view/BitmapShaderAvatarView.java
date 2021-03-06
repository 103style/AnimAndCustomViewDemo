package com.lxk.animandview.drawapi.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.view.View;

import com.lxk.animandview.R;
import com.lxk.animandview.utils.DensityUtils;

/**
 * @author https://github.com/103style
 * @date 2020/4/9 19:50
 * <p>
 * 类似例子：https://github.com/MostafaGazar/CustomShapeImageView
 */
public class BitmapShaderAvatarView extends View {

    private Bitmap bitmap;
    private Paint fillPaint;
    private BitmapShader clamp;
    private Path path;

    //因为这边只是测试  所以只写了一个参数的构造方法， 各位按需重写
    public BitmapShaderAvatarView(Context context) {
        super(context);
        fillPaint = DensityUtils.initPaint(context, Paint.Style.FILL);
        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Bitmap temp = BitmapFactory.decodeResource(getResources(), R.drawable.android11);
        Matrix matrix = new Matrix();
        //扩大到铺满屏幕
        float scaleX = w / 5 * 1.0f / temp.getWidth();
        matrix.setScale(scaleX, scaleX);
        bitmap = Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), matrix, true);
        clamp = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        fillPaint.setShader(clamp);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //设置画布背景
        canvas.drawColor(Color.argb(128, 0, 0, 0));

        int width = getMeasuredWidth() / 5;
        int translate = width * 2;
        int gap = translate / 4;
        canvas.save();
        canvas.translate(gap, gap);
        canvas.drawCircle(width / 2, width / 2, width / 2, fillPaint);

        canvas.translate(translate - gap, 0);
        canvas.drawRoundRect(0, 0, width, width, width / 4, width / 4, fillPaint);

        canvas.translate(translate - gap, 0);
        canvas.drawRoundRect(0, 0, width, width, width / 8, width / 8, fillPaint);

        canvas.restore();


        canvas.save();
        canvas.translate(gap, translate + gap);

        canvas.drawRect(0, 0, width, width, fillPaint);

        canvas.translate(translate - gap, 0);
        path.reset();
        path.moveTo(width / 2, 0);
        path.lineTo(0, width / 2);
        path.lineTo(width / 2, width);
        path.lineTo(width, width / 2);
        path.close();
        canvas.drawPath(path, fillPaint);

        canvas.translate(translate - gap, 0);
        //五角星画法
        //https://jingyan.baidu.com/article/e4d08ffda964730fd2f60dbd.html
        path.reset();
        //五角星把圆分成五份  每份圆弧角度为 360 / 5 = 72
        int cos72 = (int) (width / 2.0f * Math.cos(Math.PI / 180 * 72));
        int sin72 = (int) (width / 2.0f * Math.sin(Math.PI / 180 * 72));
        //54 = 72 - (90-72)
        int cos54 = (int) (width / 2.0f * Math.cos(Math.PI / 180 * 54));
        int sin54 = (int) (width / 2.0f * Math.sin(Math.PI / 180 * 54));
        path.moveTo(width / 2, 0);
        path.lineTo(width / 2 - cos54, width / 2 + sin54);
        path.lineTo(width / 2 + sin72, width / 2 - cos72);
        path.lineTo(width / 2 - sin72, width / 2 - cos72);
        path.lineTo(width / 2 + cos54, width / 2 + sin54);
        path.close();
        canvas.drawPath(path, fillPaint);
        canvas.restore();


        canvas.save();
        canvas.translate(gap, translate * 2 + gap);

        //爱心
        //http://www.360doc.com/content/17/0819/14/44182525_680393802.shtml
        //第 05点  例子中的 2 表示爱心上部宽度额一半    -2.14 表示 爱心下部分的高度
        path.reset();
        //爱心上部的公式
        path.moveTo(0, width / 4);
        float dx;
        for (float i = 0; i <= width; i += 0.01f) {
            //width/2 → 0 → width/2
            dx = i > width / 2f ? (i - width / 2f) : (width / 2f - i);
            path.lineTo(i, width / 4 - (float) Math.sqrt(width / 2f * dx - dx * dx));

        }
        path.lineTo(width, width / 4);
        path.lineTo(0, width / 4);
        float ty;
        //爱心下部的公式
        float dy = (float) (width * 3 / 4 / Math.sqrt(Math.sqrt(width / 2)));
        for (float i = 0; i <= width; i += 0.01f) {
            //width/2 → 0 → width/2
            dx = i > width / 2f ? (i - width / 2f) : (width / 2f - i);
            ty = width / 4f + (float) (dy * Math.sqrt(Math.sqrt(width / 2f) - Math.sqrt(dx)));
            path.lineTo(i, ty);

        }
        path.lineTo(width, width / 4);
        canvas.drawPath(path, fillPaint);
        canvas.restore();
    }

}
