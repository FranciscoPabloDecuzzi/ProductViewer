package com.ml.android.productviewer.util.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
/**
 * Created by FranciscoPablo on 27/10/13.
 */
public class ProcessDialog {

	//Private members
	private ProgressDialog _progressDialog;
	private boolean _isShow = false;
	private String _title;
	private String _message;	
	private Context Ctx;
	OnKeyListener _keylistener=new DialogInterface.OnKeyListener() {
	    @Override
	    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
	      
	    	//Con esto evitamos que Dialog se cierre ante cualquier evento de tecla.
	        return true; 
	    }
	};
	public ProcessDialog(Context ctx)	{
		Ctx = ctx;
		
		
	}
	//Activities Events
	public void onPause() {
		dismissInternalDialog();
	}	
	
	public void onRestart() {
	
		if (_progressDialog == null && _message != null && !"".equals(_message))
			showProgressDialog();
	}	
	
	public void onSaveInstanceState(Bundle savedInstance){
   		if (_progressDialog != null && !_progressDialog.isShowing()){
			dismissInternalDialog();
		}
   		if (_progressDialog != null){
   			//savedInstance.putBoolean("progressDialog", _progressDialog != null);
   			_isShow = _progressDialog != null;
//   			savedInstance.putString("title", _title);
//			savedInstance.putString("message", _message);
			
			dismissInternalDialog();
   		}
   		
   	}
   	
   	public void onRestoreInstanceState(Bundle savedInstance){
   		
		//if(savedInstance.getBoolean("progressDialog")){
   		if(_isShow){
//			_title = savedInstance.getString("title");			
//			_message = savedInstance.getString("message");
			showProgressDialog();
		}
   	}
		
	
	
	//Public Methods
	public void dissmis() {
		
		dismissInternalDialog();
		_message = null;
		_title = null;
		_isShow = false;
	}

	public void show(String title, String message) {
		_title = title;
		_message = message;		
		showProgressDialog();
	}
	
	
	public void rePaintProgressDialog() {
		
		showProgressDialog();
	}
	//Private Methods
	private void showProgressDialog(){
		if(_message != null && _progressDialog == null){
			_progressDialog = ProgressDialog.show(Ctx, _title, _message, true, false);	
			_progressDialog.setOnKeyListener(_keylistener);
			_isShow = true;
		}
	}
	 
	private void dismissInternalDialog() {
		
		if(_progressDialog != null){
			if(_progressDialog.isShowing()){
				_progressDialog.cancel();
				_progressDialog.dismiss();
			}
			_progressDialog = null;
		}
	}
	
}
