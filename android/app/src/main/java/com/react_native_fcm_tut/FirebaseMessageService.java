package com.react_native_fcm_tut;

import android.util.Log;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;
import io.invertase.firebase.Utils;

public class FirebaseMessageService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMsgService";
    public static final String MESSAGE_EVENT = "messaging-message";
    public static final String REMOTE_NOTIFICATION_EVENT = "notifications-remote-notification";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, remoteMessage.getFrom());
        if (remoteMessage.getNotification() != null) {
            // It's a notification, pass to the Notifications module
            Intent notificationEvent = new Intent(REMOTE_NOTIFICATION_EVENT);
            notificationEvent.putExtra("notification", remoteMessage);
      
            // Broadcast it to the (foreground) RN Application
            LocalBroadcastManager
              .getInstance(this)
              .sendBroadcast(notificationEvent);
        } else if (remoteMessage.getData() != null) {
                try {
                    Map<String, String> messageMap = remoteMessage.getData();
    
                    if (Utils.isAppInForeground(this.getApplicationContext())) {
                        Intent messagingEvent = new Intent(MESSAGE_EVENT);
                        messagingEvent.putExtra("message", remoteMessage);
                        // Broadcast it so it is only available to the RN Application
                        LocalBroadcastManager
                          .getInstance(this)
                          .sendBroadcast(messagingEvent);
                    }
                  } catch (Throwable t) {
                    // your error handling logic
                  }
            }
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    /**
     *
     * @param token The new token.
     */
    public void sendRegistrationToServer(String token) {
    }

}