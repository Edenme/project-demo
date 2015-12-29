package com.edittextwithdelbtn;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;

public class EditTextWithDelBtn extends EditText {
    private boolean mIsShowDelBtn;
    private boolean mIsFocus;
    public static final int mClearBtnPaddingRight = 10;
    private Bitmap mClearBitmap;

    public EditTextWithDelBtn(Context context) {
        super(context);
        init(getContext(), null);
    }

    public EditTextWithDelBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(getContext(), attrs);
    }

    public EditTextWithDelBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextWithDelBtn);
        mIsShowDelBtn = typedArray.getBoolean(R.styleable.EditTextWithDelBtn_show_clear_btn, false);
        typedArray.recycle();

        if (mClearBitmap == null) {
            mClearBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_clear_btn);
        }

        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mIsFocus = hasFocus;
            }
        });
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                setEditTextPadding();
                return false;
            }
        });
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEditTextPadding();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setEditTextPadding() {
        Rect rect = new Rect();
        String text = getText().toString();
        String hintText = getHint().toString();
        if (text.length() == 0) {
            getPaint().getTextBounds(hintText, 0, hintText.length(), rect);
        } else {
            getPaint().getTextBounds(text, 0, text.length(), rect);

        }
        int textWidth = rect.width();
        int screenX = getScreenWidth(getContext());
        int[] po = new int[2];
        getLocationInWindow(po);
        int left = po[0];
        int padding = screenX / 2 - textWidth / 2 - left;
        if (padding > 0) {
            setPadding(padding, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsShowDelBtn && mIsFocus)
            canvas.drawBitmap(mClearBitmap, getWidth() - mClearBitmap.getWidth() - mClearBtnPaddingRight, getHeight() / 2 - mClearBitmap.getHeight() / 2, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (pointOnBitmap(event.getX(), event.getY()) && mIsShowDelBtn) {
            setText("");
        }
        return super.onTouchEvent(event);
    }

    private boolean pointOnBitmap(float x, float y) {
        if (x < getWidth() - mClearBtnPaddingRight && x > getWidth() - mClearBtnPaddingRight - mClearBitmap.getWidth() &&
                y > getHeight() / 2 - mClearBitmap.getHeight() / 2 && y < getHeight() / 2 + mClearBitmap.getHeight() / 2) {
            return true;
        }
        return false;
    }

    /**
     * 获得屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
