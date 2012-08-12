package report.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ViewRectangleColor extends View{

	Paint paint = new Paint();
	
	public ViewRectangleColor(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        canvas.drawRect(30, 30, 80, 80, paint);
        paint.setStrokeWidth(0);
        paint.setColor(Color.CYAN);
        canvas.drawRect(33, 60, 77, 77, paint );
        paint.setColor(Color.YELLOW);
        canvas.drawRect(33, 33, 77, 60, paint );

    }

}
