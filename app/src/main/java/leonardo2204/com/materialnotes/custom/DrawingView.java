package leonardo2204.com.materialnotes.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.ThumbnailUtils;
import android.os.Parcel;
import android.os.Parcelable;
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
    private Stack<Path> pathStack = new Stack<>();
    private Stack<Path> redoPathStack = new Stack<>();
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

        //mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Path path : pathStack) {
            canvas.drawPath(path, paint);
        }

        canvas.drawPath(mPath, paint);
    }

    public Bitmap getDrawingBitmap() {
        Bitmap bmp = getDrawingCache();
        bmp = ThumbnailUtils.extractThumbnail(bmp, 512, 512);

        return bmp;
    }

    public void clearCanvas() {
        setDrawingCacheEnabled(false);
        pathStack.clear();
        onSizeChanged(width, height, width, height);
        invalidate();
        setDrawingCacheEnabled(true);
    }

    public void undoDrawing() {
        setDrawingCacheEnabled(false);
        redoPathStack.add(pathStack.pop());
        invalidate();
        setDrawingCacheEnabled(true);
    }

    public void redoDrawing() {
        pathStack.add(redoPathStack.pop());
        invalidate();
    }

    public boolean canUndo() {
        return !pathStack.isEmpty();
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
        pathStack.add(mPath);
        // commit the path to our offscreen
        //mCanvas.drawPath(mPath,  paint);
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

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);

        ss.pathStack = this.pathStack;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(((SavedState) state).getSuperState());

        this.pathStack = ss.pathStack;
    }

    public interface OnDrawLine {
        void hasLineOnScreen(boolean hasLine);
    }

    private static class SavedState extends BaseSavedState {

        public static final Parcelable.Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        Stack<Path> pathStack;

        public SavedState(Parcel source) {
            super(source);
            this.pathStack = (Stack<Path>) source.readSerializable();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeSerializable(this.pathStack);
        }
    }

}
