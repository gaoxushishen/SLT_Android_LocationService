package com.example.slt_android_locationservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 服务 开机后，即可以用来上传用户地理位置的服务
 * 
 * @author Admin
 *
 */
public class MainActivity extends Activity implements OnClickListener {

	private Button startBtn;
	private Button stopBtn;
	private Button bindBtn;
	private Button unbindBtn;
	private ServiceConnection sc;
	private boolean isBind;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startBtn = (Button) findViewById(R.id.button1);
		stopBtn = (Button) findViewById(R.id.button2);
		bindBtn =  (Button) findViewById(R.id.button3);
		unbindBtn =  (Button) findViewById(R.id.button4);
		sc = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {

			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				LocalService.LocalBinder mBinder = (LocalService.LocalBinder) service;
				Log.e("---MainActivity---", "onServiceConnected");
			}
		};

		startBtn.setOnClickListener(this);
		stopBtn.setOnClickListener(this);
		bindBtn.setOnClickListener(this);
		unbindBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent mIntent = new Intent(MainActivity.this, LocalService.class);

		switch (v.getId()) {
		case R.id.button1:
			startService(mIntent);
			break;
		case R.id.button2:
			stopService(mIntent);
			break;
		case R.id.button3:
			bindService(mIntent, sc, Context.BIND_AUTO_CREATE);//自动绑定SERVice，可能会出现多个service
			isBind = true;
			break;
		case R.id.button4:
			if(isBind){
				unbindService(sc);
				isBind = false;
			}
			break;
		default:
			break;
		}
	}
//作业：
//实现一个服务。5秒钟实现更换一次壁纸。
}
