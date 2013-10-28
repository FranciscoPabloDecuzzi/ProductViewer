package com.ml.android.productviewer.util.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
/**
 * Created by FranciscoPablo on 27/10/13.
 */
public class InfoDialog {

	private Context Ctx;
	private OnClickListener _handlerOk;
	private OnClickListener _handlerError;
	private android.app.AlertDialog _alertDialog;
	private boolean _isShow = false;
	private String _title;
	private String _message;	
	private String _butonPositive;
	private String _butonNegative;
	private boolean _appVisible = true;
	public InfoDialog(Context ctx)	{
		Ctx = ctx;		
	}
	
	//Activities Events
	public void onPause() {
		dismissDialog();
		_appVisible = false;
	}	
	
	public void onRestart() {
		_appVisible = true;
		if (_alertDialog == null && _message != null && !"".equals(_message)){
			showDialog();
   		}
	}
	
	public void onSaveInstanceState(Bundle savedInstance){
   		
		if (_alertDialog != null && !_alertDialog.isShowing()){
			dismissDialog();
		}
   		if (_alertDialog != null){
   			//savedInstance.putBoolean("alertDialog", _alertDialog != null);
   			_isShow = _alertDialog != null;
//   		savedInstance.putString("title", _title);
//			savedInstance.putString("message", _message);
//			savedInstance.putString("butonPositive", _butonPositive);
//			savedInstance.putString("butonNegative", _butonNegative);
			
			
			dismissDialog();
   		}
   		
   	}
   	
   	public void onRestoreInstanceState(Bundle savedInstance){
   	   		
		if(_isShow){
//			_title = savedInstance.getString("title");
//			_message = savedInstance.getString("message");
//			_butonPositive = savedInstance.getString("butonPositive");
//			_butonNegative = savedInstance.getString("butonNegative");
			
			showDialog();
		}
   	}
		
	
	
	//Public Methods
   	public void dismiss() {
		dismissDialog();
		_message = null;
		_title = null;
		_butonPositive = null;
		_butonNegative = null;	
		_isShow = false;	
	}
	
   	public void show(String message, String butonPositive) {
		show("", message, null, butonPositive, null, "");
	}
   	
	public void show(String title, String message, OnClickListener handlerOk, String butonPositive, OnClickListener handlerError, String butonNegative) {
		_title = title;
		_message = message;
		_butonPositive = butonPositive;
		_butonNegative = butonNegative;
		_handlerOk = handlerOk;
		_handlerError = handlerError;
		showDialog();
	}
	
	
	//Private Methods
	private void showDialog(){
		
		if(_message != null && _alertDialog == null && _appVisible)
		{
			android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Ctx);
			builder.setCancelable(false);
			builder.setMessage(_message);
			if(!"".equals(_title))
				builder.setTitle(_title);
			
			
			if(!"".equals(_butonPositive)){
				if(_handlerOk != null) 
					builder.setPositiveButton(_butonPositive, _handlerOk);
				else
					builder.setPositiveButton(_butonPositive, this.handlerOk);
			}
	       if(_handlerError != null && !"".equals(_butonNegative))
		       builder.setNegativeButton(_butonNegative, _handlerError);
	       
			_alertDialog = builder.create();
			if(!_alertDialog.isShowing())
				_alertDialog.show();

			_isShow = true;
		}
	}
	private void dismissDialog() {
		
		if(_alertDialog != null){
			if(_alertDialog.isShowing())
				_alertDialog.dismiss();
			
			_alertDialog = null;
		}
	}
	
	OnClickListener handlerOk = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dismiss();
		}
	  };
}
