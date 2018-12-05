package com.example.yinchaoyin.mygcm;

import com.android.quickgcm.GCMManager;
import com.android.quickgcm.MyFirebaseMessagingService.Callback;
import com.google.firebase.messaging.RemoteMessage;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	 private String tag="MyFirebaseMessagingService";
	    private String msgBody;
	    private callback mCallback;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);

	        mCallback=new callback();
	        GCMManager.getInstance().setCallback(mCallback);
	      
	    }
	    /*
	    * 推送回调
	    * */
	 
	    private class callback implements Callback {

	        @Override
	        public void onMessageReceived(RemoteMessage remoteMessage) {
	            msgBody=remoteMessage.getNotification().getBody();
	            Log.d(tag,"msgBody&&&"+msgBody);
	            sendNotification(msgBody);
	        }

	        @Override
	        public void onMessageSent(String s) {
	            Log.d(tag,"onMessageSent"+s);
	        }

	        @Override
	        public void onDeletedMessages() {
	            Log.d(tag,"onDeletedMessages");
	        }

	        @Override
	        public void onNewToken(String token) {
	            Log.d(tag,"token&&&"+token);
	        }
	    }
	    //发送消息栏推送
	    private void sendNotification(String messageBody) {
	        Intent intent = new Intent(this, MainActivity.class);
	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
	                PendingIntent.FLAG_ONE_SHOT);

	        String channelId = getString(R.string.default_notification_channel_id);
	        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	        NotificationCompat.Builder notificationBuilder =
	                new NotificationCompat.Builder(this, channelId)
	                        .setSmallIcon(R.drawable.ic_launcher_background)
	                        .setContentTitle(getString(R.string.fcm_message))
	                        .setContentText(messageBody)
	                        .setAutoCancel(true)
	                        .setSound(defaultSoundUri)
	                        .setContentIntent(pendingIntent);

	        NotificationManager notificationManager =
	                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

	        // Since android Oreo notification channel is needed.
	        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
	            NotificationChannel channel = new NotificationChannel(channelId,
	                    "Channel human readable title",
	                    NotificationManager.IMPORTANCE_DEFAULT);
	            notificationManager.createNotificationChannel(channel);
	        }*/

	        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
	    }

}
