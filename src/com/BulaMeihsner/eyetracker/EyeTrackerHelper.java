package com.BulaMeihsner.eyetracker;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import android.graphics.Bitmap;

public class EyeTrackerHelper {
	
	public static Bitmap findEye(Bitmap camera , int threshold)
	{
		
    	Mat image = new Mat();
    	Mat imageScale = new Mat();
    	Utils.bitmapToMat(Bitmap.createScaledBitmap(camera, 160, 120, false),imageScale);
    	Utils.bitmapToMat(camera, image);
    	int[] xy  = EyeDetect.getContours(imageScale, threshold);
    	Core.circle(image, new Point(xy[0]*4,xy[1]*4), 25,new Scalar(0,0,255),-10);
    	Utils.matToBitmap(image, camera);
    	
    	return camera;
	}
}
