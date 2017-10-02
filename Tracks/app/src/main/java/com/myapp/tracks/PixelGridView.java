package com.myapp.tracks;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hugo on 24/04/17.
 */

public class PixelGridView extends View {
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    private Paint blackPaint = new Paint();
    private Paint whitePaint = new Paint();
    private Paint orangePaint = new Paint();
    int oorangecolor = ContextCompat.getColor(getContext(), R.color.colorOrange); // new

    private boolean[][] cellChecked;
    public static List<String> storespeed1 = new ArrayList();
//    public static List<String> storeav = new ArrayList();


    public PixelGridView(Context context) {
        this(context, null);
    }

    public PixelGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        whitePaint.setColor(0xffffffff);
        whitePaint.setStrokeWidth(8);
        orangePaint.setColor(oorangecolor);

    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        calculateDimensions();
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
        calculateDimensions();
    }

    public int getNumRows() {
        return numRows;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDimensions();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec/2);
    }


    private void calculateDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return;
        }

        cellWidth = getWidth() / numColumns;
        cellHeight = getHeight() / numRows;

        cellChecked = new boolean[numColumns][numRows];

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        if (numColumns == 0 || numRows == 0) {
            return;
        }
        int width = getWidth();
        int height = getHeight();

        for (int i = 0; i < numColumns; i++) {
            if(i< storespeed1.size()){
                int coco = Integer.parseInt(storespeed1.get(i));
            }
            for (int j = 0; j < numRows; j++) {
                if(i< storespeed1.size()) {
                    int coco = Integer.parseInt(storespeed1.get(i));
                    if (j >= (diagram.time0+(diagram.time0/4)) - coco) {
                        canvas.drawRect(i * cellWidth, j * cellHeight,
                                (i + 1) * cellWidth, (j + 1) * cellHeight,
                                orangePaint);
                    }
                }

            }
        }

        for (int i = 1; i < numColumns; i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, whitePaint);
        }

    }
}