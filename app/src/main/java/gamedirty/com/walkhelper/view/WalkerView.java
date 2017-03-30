package gamedirty.com.walkhelper.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sovnem on 2017/3/30,15:43.
 */

public class WalkerView extends View {
    public WalkerView(Context context) {
        super(context);
    }

    public WalkerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WalkerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
