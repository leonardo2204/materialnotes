package leonardo2204.com.materialnotes.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by leonardo on 7/1/16.
 */

public class EndDrawableEditText extends AppCompatEditText {

    private OnDrawableClickListener onDrawableClickListener;

    public EndDrawableEditText(Context context) {
        super(context);
        setupUI();
    }

    public EndDrawableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupUI();
    }

    public EndDrawableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupUI();
    }

    public void setOnDrawableClickListener(OnDrawableClickListener onDrawableClickListener) {
        this.onDrawableClickListener = onDrawableClickListener;
    }

    private void setupUI() {
        this.setOnTouchListener(new OnTouchListener() {
            final int DRAWABLE_LEFT = 0;
            final int DRAWABLE_TOP = 1;
            final int DRAWABLE_RIGHT = 2;
            final int DRAWABLE_BOTTOM = 3;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int leftEdgeRightDrawable = EndDrawableEditText.this.getRight() - EndDrawableEditText.this.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                    if (event.getRawX() >= leftEdgeRightDrawable) {
                        if (EndDrawableEditText.this.onDrawableClickListener != null) {
                            EndDrawableEditText.this.onDrawableClickListener.onEndDrawableClick();
                        }
                        return true;
                    }
                }

                return false;
            }
        });
    }

    public interface OnDrawableClickListener {
        void onEndDrawableClick();
    }

}
