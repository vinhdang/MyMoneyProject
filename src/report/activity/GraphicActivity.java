package report.activity;

import java.util.ArrayList;
import java.util.List;

import main.activity.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GraphicActivity extends Activity {
	
	public class PieDetailsItem {
		public int    Count;
		public String Label;
		public float  Percent;
		public int    Color;
	}
	
	List<PieDetailsItem> PieData = new ArrayList<PieDetailsItem>(0);

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_report);
        //------------------------------------------------------------------------------------------
        // Used vars declaration
        //------------------------------------------------------------------------------------------
        
        PieDetailsItem Item;
        //Random mNumGen  = new Random();
        int MaxPieItems = 5;//mNumGen.nextInt(30)
        int MaxCount  = 0;
        int ItemCount = 0;
        //------------------------------------------------------------------------------------------
        // Generating data by a random loop
        //------------------------------------------------------------------------------------------
        for (int i = 0; i < MaxPieItems ; i++) {
        	ItemCount = 20;//ItemCount  = mNumGen.nextInt(100);
        	Item       = new PieDetailsItem();
        	//Item.Color = 0xff000000 + 256*256*mNumGen.nextInt(256) + 256*mNumGen.nextInt(256) + mNumGen.nextInt(256);//256*256*mNumGen.nextInt(256) + 256*mNumGen.nextInt(256) + mNumGen.nextInt(256)            	
        	//Tổng của Item.Count = MaxPieItems * ItemCount
        	switch(i)
        	{
	        	case 0:
	        	{
	        		Item.Color = -12649183;//xanh lá cây
	        		Item.Count = 50;
	        		Item.Label = "Valeur ";
	        		break;
	        	}
	        	case 1:
	        	{
	        		Item.Color = -302408;//hồng
	        		Item.Count = 10;
	        		break;
	        	}
	        	case 2:
	        	{
	        		Item.Color = -6037784;//xám nhẹ
	        		Item.Count = 20;
	        		break;
	        	}
	        	case 3:
	        	{
	        		Item.Color = -7780679;//tím
	        		Item.Count = 5;
	        		break;
	        	}
	        	case 4:
	        	{
	        		Item.Color = -2381911;//nâu nhẹ
	        		Item.Count = 15;
	        		break;
	        	}
        	}
        	MaxCount += ItemCount;
        	PieData.add(Item);       	
        	
        }
        //------------------------------------------------------------------------------------------
        // OverlayId  => Image to be drawn on top of pie to make it more beautiful!
        //------------------------------------------------------------------------------------------
        int OverlayId = R.drawable.cam_overlay_big;
        //------------------------------------------------------------------------------------------
        // Size => Pie size
        //------------------------------------------------------------------------------------------
        int Size = 146;
        //------------------------------------------------------------------------------------------
        // BgColor  => The background Pie Color
        //------------------------------------------------------------------------------------------
        int BgColor = Color.WHITE;
        //------------------------------------------------------------------------------------------
        // mBackgroundImage  => Temporary image will be drawn with the content of pie view
        //------------------------------------------------------------------------------------------
        Bitmap mBackgroundImage = Bitmap.createBitmap(200, 350, Bitmap.Config.RGB_565);
        //------------------------------------------------------------------------------------------
        // Generating Pie view
        //------------------------------------------------------------------------------------------
        ViewPieChart PieChartView = new ViewPieChart( this );
        PieChartView.setLayoutParams(new LayoutParams(Size, Size));
        PieChartView.setGeometry(Size, Size, 1, 1, 1, 1, OverlayId);
        PieChartView.setSkinParams(BgColor);
        PieChartView.setData(PieData, MaxCount);
        PieChartView.invalidate();
        //------------------------------------------------------------------------------------------
        // Draw Pie Vien on Bitmap canvas
        //------------------------------------------------------------------------------------------
        PieChartView.draw(new Canvas(mBackgroundImage));
        PieChartView = null;
        //------------------------------------------------------------------------------------------
        // Create a new ImageView to add to main layout
        //------------------------------------------------------------------------------------------
        ImageView mImageView = new ImageView(this);
	    mImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	    mImageView.setBackgroundColor(BgColor);
	    mImageView.setImageBitmap( mBackgroundImage );
	    //------------------------------------------------------------------------------------------
        // Finaly add Image View to target view !!!
        //------------------------------------------------------------------------------------------
	    LinearLayout TargetPieView =  (LinearLayout) findViewById(R.id.pie_container);
	    TargetPieView.setGravity(Gravity.CENTER);
	    TargetPieView.addView(mImageView);
	    
    }
	
    /** Called when the activity is first created. 
	DrawView drawView;
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        // setContentView(R.layout.main); 
        drawView = new DrawView(this);
        drawView.setBackgroundColor(Color.WHITE);
        setContentView(drawView);
    } 
	class DrawView extends View { 
	    Paint paint = new Paint(); 
	    public DrawView(Context context) { 
	        super(context); 
	        paint.setColor(Color.RED); 
	    } 
	    @Override 
	    public void onDraw(Canvas canvas) { 
	            canvas.drawLine(1, 1, 100, 100, paint); 
	            canvas.drawLine(100, 1, 1, 100, paint);
	            canvas.drawLine(1, 100, 1, 1, paint);
	            canvas.drawLine(1, 1, 150, 1, paint);
	            canvas.drawLine(10, 15, 40, 60, paint);
	            canvas.drawCircle(150, 150, 30, paint);
	    } 
	}  
	*/ 
	
	
}