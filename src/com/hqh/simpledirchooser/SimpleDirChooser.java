package com.hqh.simpledirchooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.hqh.simpledirchooser.R;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SimpleDirChooser extends ListActivity {

	private List<String> items = null;
	private List<String> paths = null;
	public static String PATH_KEY="path";
	private String rootPath = "/";
	private String curPath = "/";
	private TextView mPathView;
	private Button mCancelButton;
	private Button mConfirmButton;
	
	public static String TAG="DirectoryChooser";


	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fileselect);
		
		initialization();
		getFileDir(rootPath);
		
	}

	private void initialization()
	{
		mPathView=(TextView)findViewById(R.id.mPath);
		mCancelButton=(Button)findViewById(R.id.cancel_button);
		mConfirmButton=(Button)findViewById(R.id.confirm_button);
		
		mCancelButton.setOnClickListener(mListener);
		mConfirmButton.setOnClickListener(mListener);
	}
	
	private OnClickListener mListener=new OnClickListener()
	{

		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			if(v.getId()==R.id.cancel_button)
			{
				finish();
			}else{
				returnResult(curPath);
			}	
		}
	};
	private void getFileDir(String filePath)
	{
		items = new ArrayList<String>();
		paths = new ArrayList<String>();
		curPath=filePath;
		mPathView.setText(curPath);
		
		File f = new File(filePath);
		File[] files = f.listFiles();

		if (!filePath.equals(rootPath)) {
			items.add("b1");
			paths.add(rootPath);
			items.add("b2");
			paths.add(f.getParent());
		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			items.add(file.getName());
			paths.add(file.getPath());
		}
		setListAdapter(new MyAdapter(this, items, paths));
	}

	
	private void returnResult(String path)
	{
		Log.i(TAG,"the path is "+path);
		Intent intent=new Intent();
		intent.putExtra(PATH_KEY, path);
		setResult(RESULT_OK,intent);
		finish();
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		File file = new File(paths.get(position));
		if (file.isDirectory()) {	
			getFileDir(paths.get(position));
		} else 
		{
			returnResult(file.getAbsolutePath());
		}
	}


}
