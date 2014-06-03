package com.BulaMeihsner.eyetracker;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

import com.BulaMeihsner.data.FPoint;

import android.graphics.Bitmap;

public class ChessboardDetectorHelper {
	
	// capturing - czy kalibruje
	public static Bitmap findChessboard(Bitmap camera, boolean capturing)
	{
    	Mat image = new Mat();
    	Mat imageScale = new Mat();
    	Utils.bitmapToMat(Bitmap.createScaledBitmap(camera, 160, 120, false),imageScale);
    	Utils.bitmapToMat(camera, image);
     
    	if(capturing)
    	{
	    	ChessboardDetector det = new ChessboardDetector();
	    	imageScale = det.detect(imageScale);
	    	
	    	//odczytanie �rodka szachownicy (malowanie �rodka szachownicy wewn�trz metody)
	    	FPoint p = det.getMid();
	    	long currentTime = System.currentTimeMillis();

	    	Core.rectangle(image, new Point(p.x*4-3,p.y*4-3), new Point(p.x*4+2, p.y*4+2), new Scalar(0,255,0), 2);

	    	//TODO Tutaj b�dziemy zapisywa� odczyty 
	    	//Timestamp ts = new Timestamp(currentTime,p); 
	    	//buffer.addTimestamp(ts);
    	}
    	
    	//Tutaj dodamy metod� kt�ra wy�wietla miejsce gdzie patrzy osoba
    	
    	Utils.matToBitmap(image, camera);
    	
    	return camera;
	}
}
