package com.example.simplenotif;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
	
	public static int NOTIFICATION_ID = 1;
	public static String CHANNEL_ID = "channel_01";
	public static CharSequence CHANNEL_NAME = "dicoding channel";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); // Membuat NotificationManager
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
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
	}
}
