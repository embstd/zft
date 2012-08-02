package com.kinview.camera;   
  
import java.io.IOException;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.kinview.config.print;
  
public class CameraPreView extends SurfaceView implements SurfaceHolder.Callback, Camera.AutoFocusCallback {   
  
	private SurfaceHolder holder = null;   
    private Camera mCamera = null;   
    private ActivityCamera activity = null;
    private int zoom=1;
    private int zoomMax=10;
    private int zoomMin=1;
    
    public CameraPreView(Context context) {   
        super(context);
        activity = (ActivityCamera)context;
        holder = this.getHolder();   
        holder.addCallback(this);   
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
    }
    
    public void restart(){
    	mCamera.startPreview();
    }
    
    public void setFlashMode(String mode){
    	Camera.Parameters parameters = mCamera.getParameters();
    	parameters.setFlashMode(mode);
    	parameters.setPictureFormat(PixelFormat.JPEG);
    	mCamera.setParameters(parameters); 
    }
    
    public void surfaceChanged(SurfaceHolder holder, int format, int width,   
            int height) { 
    	if(mCamera==null)
    		return;
        Camera.Parameters parameters = mCamera.getParameters();   
        parameters.setPictureFormat(PixelFormat.JPEG);//设置图片格式  
        parameters.setFlashMode(activity.flashMode);

//        parameters.setPictureSize(320, 240);//设置分辨率   
//        parameters.setPictureSize(800, 600);	//
        parameters.setPictureSize(640, 480);
//        parameters.setRotation(270);
//        parameters.setSceneMode("portrait");//"landscape"
//        parameters.set("rotate", 90);	
//        parameters.setRotation(90);
//        parameters.set("taking-picture-zoom", 8);
//        parameters.setZoom(8);
        
        parameters.set("jpeg-quality", 60);
        mCamera.setParameters(parameters); 
        mCamera.startPreview();//开始预览   
        
    }   
  
    public void surfaceCreated(SurfaceHolder holder) {   
        mCamera = Camera.open();//启动服务
        try {   
            mCamera.setPreviewDisplay(holder);//设置预览   
        } catch (IOException e) {   
            mCamera.release();//释放   
            mCamera = null;   
        } 
        
        
//        设置监听
//        print.out("设置监听 执行了");
//        mCamera.setZoomChangeListener(new OnZoomChangeListener() {
//			@Override
//			public void onZoomChange(int zoomValue, boolean stopped, Camera camera) {
//				// TODO Auto-generated method stub
//				print.out("OnZoomChangeListener is call");
////				camera.
////				camera.set
//			}
//		});
    }   
  
    public void surfaceDestroyed(SurfaceHolder holder) {  
        mCamera.stopPreview();//停止预览   
        try {
			mCamera.release();
			mCamera = null;
		} catch (Exception e) {
			if(mCamera!=null){
				mCamera.release();//释放   
				mCamera = null;
			}
		}
		finally{
			if(mCamera!=null){
				mCamera.release();//释放   
	            mCamera = null;   
			}
		}
    }   
  
    //拍照后输出图片   
    public Camera.PictureCallback pic = new Camera.PictureCallback(){   
        public void onPictureTaken(byte[] data, Camera camera) {
            activity.setPicture(BitmapFactory.decodeByteArray(data, 0, data.length));   
        }   
    };   
    
    public void takePicture(){
    	mCamera.takePicture(null, null,pic);  
    }
    
    public void zoomIn(){
    	
    	boolean b1 = mCamera.getParameters().isZoomSupported();
    	boolean b2 = mCamera.getParameters().isSmoothZoomSupported();
    	
//    	mCamera.getParameters().set
//    	
    	print.out("isZoomSupported="+b1);
    	print.out("isSmoothZoomSupported="+b2);
    	
    	
    	int max = mCamera.getParameters().getMaxZoom();
    	print.out("max room="+max);
    	
    	print.out("zoomIn is call");
    	zoom--;
    	print.out("zoom="+zoom);
    	if(zoom<=zoomMin){
    		zoom= zoomMin;
    	}
    	print.out("zoom="+zoom);
//    	mCamera.startSmoothZoom(zoom);
//    	Camera.Parameters localParameters = mCamera.getParameters();
//    	mCamera.getParameters().set("taking-picture-zoom", zoom);
//    	localParameters.set("taking-picture-zoom", zoom);
//    	mCamera.setParameters(localParameters);
//    	mCamera.getParameters().setZoom(zoom);
    	mCamera.getParameters().set("zoom", zoom/10);
//    	mCamera.startSmoothZoom(zoom);
//    	mCamera.getParameters().setZoom(zoom);
//    	mCamera.startSmoothZoom(zoom*10);
//    	mCamera.notifyAll();
//    	this.
    }
    
//    class Afc implements Camera.AutoFocusCallback{
//
//		@Override
//		public void onAutoFocus(boolean success, Camera camera) {
//			// TODO Auto-generated method stub
//			
//		}
//    	
//    }

    public void zoomOut(){
//    	mCamera.cancelAutoFocus();
    	print.out("zoomOut is call");
    	zoom++;
    	print.out("zoom="+zoom);
    	if(zoom>=zoomMax){
    		zoom= zoomMax;
    	}
    	
//    	mCamera.startSmoothZoom(zoom);
//    	mCamera.getParameters().set("taking-picture-zoom", zoom);
//    	Camera.Parameters localParameters = mCamera.getParameters();
//    	mCamera.getParameters().set("taking-picture-zoom", zoom);
//    	localParameters.set("taking-picture-zoom", zoom);
//    	mCamera.setParameters(localParameters);
//    	mCamera.getParameters().setZoom(zoom);
    	mCamera.getParameters().set("zoom", zoom/10);
//    	mCamera.startSmoothZoom(zoom);
//    	mCamera.getParameters().setZoom(zoom); 	
//    	mCamera.notifyAll();
    }
    
	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		// TODO Auto-generated method stub
		print.out("onAutoFocus is call");
		
		camera.getParameters().set("taking-picture-zoom", zoom);
	}
}  
