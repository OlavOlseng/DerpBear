package world;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.pooling.arrays.IntArray;
import org.jbox2d.pooling.arrays.Vec2Array;

import rendering.LineDrawer;
import util.GameConstants;

public class dbDebugDraw extends DebugDraw{

	LineDrawer ldr;
	private final Vec2 temp = new Vec2();
  private final static IntArray xIntsPool = new IntArray();
  private final static IntArray yIntsPool = new IntArray();
  private final static Vec2Array vecPool = new Vec2Array();
	public dbDebugDraw(LineDrawer ldr) {
		super(new OBBViewportTransform());
		// TODO Auto-generated constructor stub
		this.ldr = ldr;
	}

	
	
	
	private Vec2 temp1 = new Vec2();
	private Vec2 temp2 = new Vec2();
	@Override
	public void drawCircle(Vec2 center, float radius, Color3f color) {
		
	
		Vec2[] points = vecPool.get(20);
		
		generateCirle(center, radius, points, 20);
		
		for(int i = 0; i < points.length-1;i++){
			
			drawSegment(points[i], points[i+1], color);
		}
		
		drawSegment(points[19], points[0], color);
		
	}

	@Override
	public void drawPoint(Vec2 arg0, float arg1, Color3f arg2) {
		// TODO Auto-generated method stub
		
	}

	
	  private final Vec2 sp1 = new Vec2();
	  private final Vec2 sp2 = new Vec2();
	@Override
	public void drawSegment(Vec2 p1, Vec2 p2, Color3f color) {
		// TODO Auto-generated method stub
		
		 	getWorldToScreenToOut(p1, sp1);
		    getWorldToScreenToOut(p2, sp2);
		
		  
		    ldr.addLine(sp1.x*GameConstants.PIXELSCALE, sp1.y*GameConstants.PIXELSCALE, sp2.x*GameConstants.PIXELSCALE, sp2.y*GameConstants.PIXELSCALE,color.x,color.y,color.z);
		 
		
	}

	@Override
	public void drawSolidCircle(Vec2 center, float radius, Vec2 arg2, Color3f arg3) {
		// TODO Auto-generated method stub
		drawCircle(center, radius, arg3);
	}

	@Override
	public void drawSolidPolygon(Vec2[] vertices, int vertexCount, Color3f color) {
		// TODO Auto-generated method stub
	    int[] xInts = xIntsPool.get(vertexCount);
	    int[] yInts = yIntsPool.get(vertexCount);

	    for (int i = 0; i < vertexCount; i++) {
	      getWorldToScreenToOut(vertices[i], temp);
	      xInts[i] = (int) temp.x;
	      yInts[i] = (int) temp.y;
	    }

	    // outside
	    drawPolygon(vertices, vertexCount, color );
		
	}

	@Override
	public void drawString(float arg0, float arg1, String arg2, Color3f arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawTransform(Transform arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void generateCirle(Vec2 argCenter, float argRadius, Vec2[] argPoints, int argNumPoints) {
	    float inc = MathUtils.TWOPI / argNumPoints;

	    for (int i = 0; i < argNumPoints; i++) {
	      argPoints[i].x = (argCenter.x + MathUtils.cos(i * inc) * argRadius);
	      argPoints[i].y = (argCenter.y + MathUtils.sin(i * inc) * argRadius);
	    }
	  }
}
