package report.activity;

import java.text.DecimalFormat;
import java.util.List;
import report.activity.GraphicActivity.PieDetailsItem;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public class ViewPieChart extends View{

	private static final int WAIT = 0;
	private static final int IS_READY_TO_DRAW = 1;
	private static final int IS_DRAW = 2;
	private static final float START_INC = 270;//Góc bắt đầu là 0h hướng theo kim đồng hồ
	private Paint mBgPaints   = new Paint();
	private Paint mLinePaints = new Paint();
	private int   mOverlayId;
	private int   mWidth;
	private int   mHeight;
	private int   mGapLeft;
	private int   mGapRight;
	private int   mGapTop;
	private int   mGapBottom;
	private int   mBgColor;
	private int   mState = WAIT;
	private float mStart;
	private float mSweep;
	private int   mMaxConnection;
	private List<PieDetailsItem> mDataArray;
	
	//--------------------------------------------------------------------------------------
	public ViewPieChart (Context context){
		super(context);
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    	
    	Paint paint = new Paint();
    	//------------------------------------------------------
    	if (mState != IS_READY_TO_DRAW) return;
    	canvas.drawColor(mBgColor);
    	//------------------------------------------------------
    	mBgPaints.setAntiAlias(true);
    	mBgPaints.setStyle(Paint.Style.FILL);
    	mBgPaints.setColor(0x88FF0000);
    	mBgPaints.setStrokeWidth(0.5f);
    	//------------------------------------------------------
    	mLinePaints.setAntiAlias(true);
    	mLinePaints.setStyle(Paint.Style.STROKE);
    	mLinePaints.setColor(0xff000000);
    	mLinePaints.setStrokeWidth(0.5f);
    	//------------------------------------------------------
    	RectF mOvals = new RectF( mGapLeft, mGapTop, mWidth - mGapRight, mHeight - mGapBottom);
    	//------------------------------------------------------
    	mStart = START_INC;
    	//------------------------------------------------------------
    	// Draw Legend on Pie
    	//------------------------------------------------------------
    	float lblX;
    	float lblY;
    	//Define the location of notes.
    	int SmallRectX = 200;
    	int SmallRectY = 180;
    	int TextLocation = 194;
    	
    	String LblPercent;
    	float Percent;
    	DecimalFormat FloatFormatter = new DecimalFormat("0.## %");
    	float CenterOffset = (mWidth / 2); // Pie Center from Top-Left origin
    	float Conv = (float)(2*Math.PI/360);	 // Constant for convert Degree to rad.
    	float Radius = 2 *(mWidth/2)/ 3;	 // Radius of the circle will be drawn the legend.
    	Rect bounds = new Rect();
    	//------------------------------------------------------------
    	PieDetailsItem Item;
    	for (int i = 0; i < mDataArray.size(); i++) {
    		Item = (PieDetailsItem) mDataArray.get(i);
    		
    		Percent = (float)Item.Count / (float)mMaxConnection;
    	    mSweep = (float) 360 * Percent;
    	    // Format Label
    	    LblPercent = FloatFormatter.format(Percent);
    	    // Get Label width and height in pixels
    	    mLinePaints.getTextBounds(LblPercent,0,LblPercent.length(),bounds);
    	    // Claculate final coords for Label
    	    lblX = (float) ((float) CenterOffset + Radius*Math.cos( Conv*(mStart+mSweep/2)))- bounds.width()/2;
    	    lblY = (float) ((float) CenterOffset + Radius*Math.sin( Conv*(mStart+mSweep/2)))+ bounds.height()/2;
    	    // Dwraw Label on Canvas
    		mBgPaints.setColor(Item.Color);
    		canvas.drawArc(mOvals, mStart, mSweep, true, mBgPaints);
    		canvas.drawArc(mOvals, mStart, mSweep, true, mLinePaints);
    		canvas.drawText(LblPercent, lblX , lblY , mLinePaints);
    		mStart += mSweep;
    		//------------------------------------------------------
        	//Draw small rectangle and write the meaning of this rectangle.
        	//------------------------------------------------------
    		paint.setColor(mDataArray.get(i).Color);
            canvas.drawRect(0, SmallRectX, 30, SmallRectY, paint);
            paint.setColor(Color.DKGRAY);
            canvas.drawText(LblPercent + " " + Item.Label, 50, TextLocation, paint);
            if(i == 0)
            {
            	canvas.drawText(Item.title, 0, 164, paint);
            }
            SmallRectX += 30;
            SmallRectY += 30;
            TextLocation += 30;
        }
    	//------------------------------------------------------
    	Options options = new BitmapFactory.Options();
        options.inScaled = false;
    	Bitmap OverlayBitmap = BitmapFactory.decodeResource(getResources(), mOverlayId, options);
    	
    	//-----------------------------------------------
    	// Added code Begin
    	//-----------------------------------------------
    	int width = OverlayBitmap.getWidth();
    	int height = OverlayBitmap.getHeight();
    	int newWidth = mWidth;
    	int newHeight = mWidth;
    	float scaleWidth = ((float) newWidth) / width;
    	float scaleHeight = ((float) newHeight) / height;
    	Matrix matrix = new Matrix();
    	matrix.postScale(scaleWidth, scaleHeight);
    	Bitmap resizedBitmap = Bitmap.createBitmap(OverlayBitmap, 0, 0,width, height, matrix, true);
    	//-----------------------------------------------
    	// End Added code
    	//-----------------------------------------------
    	//canvas.drawBitmap(OverlayBitmap, 0.0f, 0.0f, null);
    	canvas.drawBitmap(resizedBitmap, 0.0f, 0.0f, null);
        //------------------------------------------------------
    	mState = IS_DRAW;
    }
	//--------------------------------------------------------------------------------------
	//public void
	//--------------------------------------------------------------------------------------
    public void setGeometry(int width, int height, int GapLeft, int GapRight, int GapTop, int GapBottom, int OverlayId) {
    	mWidth     = width;
   	 	mHeight    = height;
   	 	mGapLeft   = GapLeft;
   	 	mGapRight  = GapRight;
   	 	mGapTop    = GapTop;
   	 	mGapBottom = GapBottom;
   	 	mOverlayId = OverlayId;
    }
    //--------------------------------------------------------------------------------------
    public void setSkinParams(int bgColor) {
   	 	mBgColor   = bgColor;
    }
    //--------------------------------------------------------------------------------------
    public void setData(List<PieDetailsItem> data, int MaxConnection) {
    	mDataArray = data;
    	mMaxConnection = MaxConnection;
    	mState = IS_READY_TO_DRAW;
    }
    //--------------------------------------------------------------------------------------
    public void setState(int State) {
    	mState = State;
    }
    //--------------------------------------------------------------------------------------
    public int getColorValue( int Index ) {
   	 	if (mDataArray == null) return 0;
   	 	if (Index < 0){
   	 		return ((PieDetailsItem)mDataArray.get(0)).Color;
   	 	} else if (Index >= mDataArray.size()){
   	 		return ((PieDetailsItem)mDataArray.get(mDataArray.size()-1)).Color;
   	 	} else {
   	 		return ((PieDetailsItem)mDataArray.get(mDataArray.size()-1)).Color;
   	 	}
    }
    
}
