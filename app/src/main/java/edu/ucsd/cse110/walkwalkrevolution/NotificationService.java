package edu.ucsd.cse110.walkwalkrevolution;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
//        need to implement this if you want to do something when you receive a notification while app is in the foreground.
    }
}
