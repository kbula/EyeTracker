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
    	Utils.bitmapToMat(camera, image);
    	
    	if(capturing)
    	{
	    	ChessboardDetector det = new ChessboardDetector();
	    	image = det.detect(image);
	    	
	    	//odczytanie �rodka szachownicy (malowanie �rodka szachownicy wewn�trz metody)
	    	FPoint p = det.getMid();
	    	long currentTime = System.currentTimeMillis();
	    	
	    	//TODO Tutaj b�dziemy zapisywa� odczyty 
	    	//Timestamp ts = new Timestamp(currentTime,p); 
	    	//buffer.addTimestamp(ts);
    	}
    	
    	//Tutaj dodamy metod� kt�ra wy�wietla miejsce gdzie patrzy osoba
    	
    	Utils.matToBitmap(image, camera);
    	
    	return camera;
	}
}
