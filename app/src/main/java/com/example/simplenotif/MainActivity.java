package com.example.simplenotif;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
	
	public static int NOTIFICATION_ID = 1;
	public static String CHANNEL_ID = "channel_01";
	public static CharSequence CHANNEL_NAME = "dicoding channel";
	
	NotificationCompat.Builder mBuilder;
	NotificationManager mNotificationManager;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	// Menjalankan aksi setelah delay berakhir
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		}
	};
	
	public void sendNotification(View view){
		
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://dicoding.com")); // Intent untuk akses ke website
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,0); // Create PendingIntent dengan membawa Intent di dalamnya, lalu memberikan action jika notif diclick
		
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); // Membuat NotificationManager
		
		mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
													  .setContentIntent(pendingIntent) // Set Pending intent ke Notification Builder
													  .setSmallIcon(R.drawable.ic_notifications_white_48px) // Icon tsb muncul di status bar
													  .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_white_48px)) // Icon tsb muncul di sebelah kiri dari text notif
													  .setContentTitle(getResources().getString(R.string.content_title)) // Judul dari notification
													  .setContentText(getResources().getString(R.string.content_text)) // Text di bawah title
													  .setSubText(getResources().getString(R.string.subtext)) // Muncul di samping text app title
													  .setAutoCancel(true) // Berguna utk menghapus notif stlh di tekan
				;
		
		/*
		Line ini berguna untuk menambahkan notification channel untuk OS Android Oreo ke atas
		 */
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
			// Create or update
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
					CHANNEL_NAME,
					NotificationManager.IMPORTANCE_DEFAULT); // Membuat notification channel object
			
			mBuilder.setChannelId(CHANNEL_ID);
			
			if(mNotificationManager != null){
				mNotificationManager.createNotificationChannel(channel); // memberi notifikasi terhadap pengguna (khusus Oreo+)
			}
		}
		
		/*
		Line ini berguna untuk menambahkan notification channel untuk OS Android dibawah Oreo
		 */
		
		Notification notification = mBuilder.build(); // Membuat object notification dgn build method
		
		if(mNotificationManager != null){
			mNotificationManager.notify(NOTIFICATION_ID, notification); // memberi notifikasi terhadap pengguna
		}
		
		//untuk menjalankan delay selama 5 detik dalam pengiriman notif
		new Handler().postDelayed(runnable, 5000);
	}
	
}
