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
        }
    	//------------------------------------------------------
    	Options options = new BitmapFactory.Options();
        options.inScaled = false;
    	Bitmap OverlayBitmap = BitmapFactory.decodeResource(getResources(), mOverlayId, options);
    	canvas.drawBitmap(OverlayBitmap, 0.0f, 0.0f, null);
    	//------------------------------------------------------
    	//Draw small rectangle and write the meaning of this rectangle.
    	//------------------------------------------------------
    	paint.setColor(mDataArray.get(0).Color);
        canvas.drawRect(0, 200, 30, 180, paint);
        paint.setColor(Color.DKGRAY);
        canvas.drawText("Tiền ăn trong tháng", 50, 194, paint);
        
        paint.setColor(mDataArray.get(1).Color);
        canvas.drawRect(0, 230, 30, 210, paint );
        paint.setColor(Color.DKGRAY);
        canvas.drawText("Tiền đổ xăng xe", 50, 224, paint);
        
        paint.setColor(mDataArray.get(2).Color);
        canvas.drawRect(0, 260, 30, 240, paint );
        paint.setColor(Color.DKGRAY);
        canvas.drawText("Tiền phòng trọ", 50, 254, paint);
        
        paint.setColor(mDataArray.get(3).Color);
        canvas.drawRect(0, 290, 30, 270, paint );
        paint.setColor(Color.DKGRAY);
        canvas.drawText("Tiền điện nước", 50, 284, paint);
        
        paint.setColor(mDataArray.get(4).Color);
        canvas.drawRect(0, 320, 30, 300, paint );
        paint.setColor(Color.DKGRAY);
        canvas.drawText("Tiền mạng Internet", 50, 314, paint);
        
        
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
