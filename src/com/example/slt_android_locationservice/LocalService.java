package com.example.slt_android_locationservice;

import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * 定位服务
 * 1-服务必须要继承自Service
 * 2-Service 和 Activity一样，都是存在在主线程中的，
 * 因此，我们要将非耗时的操作放入service中，防止阻塞UI。ANR警告。
 * 3-service分为2类：系统内部单独进程服务（远程服务） 和  在主进程上依附的服务（本地服务）
 * 4-Service分为2类：前台服务（会发出通知）和后台服务（不发通知，天气预报）
 * 
 * 5-开启服务有2种方式：1-startService 2-bindService
 * 两者的区别：bindService依赖于绑定的组件对象，当组件被销毁，则对应的服务也会被销毁；尽管组件会销毁，服务会关闭。但是请主动解绑服务。
 * startService不一样
 * 
 * @author Admin
 *
 */
public class LocalService extends Service{

	private IBinder binder = new LocalService.LocalBinder();//用于通信(Activity 和 Service通信)
	private Timer timer;
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	//创建服务，只调用一次
	@Override
	public void  onCreate() {
		Log.e("----localservice-----", "onCreate");
		//每隔5秒钟就上传一下用户定位记录信息
		//三个参数
		//1-task：定时执行的任务
		//2-delay:延时
		//3-period:周期（毫秒）
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				Log.e("----localservice-----", "我现在无锡市");
			}
		}, 0, 1000 * 5);
	}
	
	//开始服务，开始后服务即运行了。可以多次调用
	@Override
	public void onStart(Intent intent,int startId) {
		super.onStart(intent, startId);
		Log.e("----localservice-----", "onStart");
	}
	
	//自从安卓2.0以后，onStartCommand代替了onStart
	@Override
	public int onStartCommand(Intent intent,int flags, int startId) {
		Log.e("----localservice-----", "onStartCommand");
		return START_NOT_STICKY;
		//START_STICKY粘性：service所在的进程被杀死后，系统将自动重新创建服务。
	//START_NOT_STICKY:service所在的进程被杀死后，系统不会自动重新创建服务。
		//START_STICKY_COMPATIBILITY:START_STICKY兼容版本，确保服务肯定会被重启
	}

	//销毁服务，只调用一次
	@Override
	public void onDestroy(){
		super.onDestroy();
		timer.cancel();
		Log.e("----localservice-----", "onDestroy");
	}
	
	public  class LocalBinder extends Binder{
		//返回当前的本地服务
		LocalService getService(){
			return LocalService.this;
		}
	}
	
}
