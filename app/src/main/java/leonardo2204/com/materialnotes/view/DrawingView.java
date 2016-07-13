package leonardo2204.com.materialnotes.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.ThumbnailUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Stack;

/**
 * Created by leonardo on 6/30/16.
 */

public class DrawingView extends View {

    private static final float TOUCH_TOLERANCE = 4;
    public int width;
    public  int height;
    private Context context;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mBitmapPaint;
    private Paint paint;
    private float mX, mY;
    private Stack<Path> pathDeque = new Stack<>();
    private OnDrawLine onDrawLine;

    public DrawingView(Context context) {
        this(context, null, 0);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeWidth(4f);

        setDrawingCacheEnabled(true);
    }

    public void setOnDrawLine(OnDrawLine onDrawLine) {
        this.onDrawLine = onDrawLine;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        for (Path path : pathDeque) {
            canvas.drawPath(path, paint);
        }
    }

    public Bitmap getDrawingBitmap() {
        Bitmap bmp = getDrawingCache();
        bmp = ThumbnailUtils.extractThumbnail(bmp, 512, 512);

        return bmp;
    }

    public void clearCanvas() {
        setDrawingCacheEnabled(false);
        pathDeque.clear();
        onSizeChanged(width, height, width, height);
        invalidate();
        setDrawingCacheEnabled(true);
    }

    public void undoDrawing() {
        setDrawingCacheEnabled(false);
        pathDeque.pop();
        invalidate();
        setDrawingCacheEnabled(true);
    }

    public boolean canUndo() {
        return !pathDeque.isEmpty();
    }

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        mPath.lineTo(mX, mY);
        pathDeque.add(mPath);
        // commit the path to our offscreen
        mCanvas.drawPath(mPath,  paint);
        mPath = new Path();
        if (onDrawLine != null)
            onDrawLine.hasLineOnScreen(canUndo());
        // kill this so we don't double draw
        //mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }

    public interface OnDrawLine {
        void hasLineOnScreen(boolean hasLine);
    }

}
