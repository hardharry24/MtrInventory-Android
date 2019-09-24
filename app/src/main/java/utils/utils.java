package utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.widget.EditText;

import androidx.core.app.NotificationCompat;

import com.ezra.motortradeinventory.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import activities.UserLogin;

import static android.content.Context.NOTIFICATION_SERVICE;

public class utils {
    public static String IP = "192.168.88.1:81";
    public static String IMG = "http://"+ utils.IP+"/MotorTradeInventory/Images/";
    public static DecimalFormat moneyFormat = new DecimalFormat("#,###.00");
    public static SimpleDateFormat dateComplete = new SimpleDateFormat("dd MMMM, yyyy");
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    public static SimpleDateFormat dateTimeDB = new SimpleDateFormat("yyyy-M-d h:mm:s");

    public static SimpleDateFormat daily = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat monthly = new SimpleDateFormat("MMMM");
    public static SimpleDateFormat yearly = new SimpleDateFormat("yyyy");

    public static String getText(EditText txt)
    {
        return txt.getText().toString();
    }

    public static void notification(String title, String body, Context ctx)
    {
        NotificationManager notificationManager = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(ctx, UserLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(ctx,100,intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Bitmap bigIcon = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.ic_notifications_black_24dp);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(body);
        bigText.setSummaryText(title);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(bigText)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setLargeIcon(bigIcon)
                .setAutoCancel(true);
        int notifyId = 001;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId1 = "1";
            String channelName = "channel1";
            NotificationChannel channel = new NotificationChannel(channelId1,channelName,NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.setShowBadge(true);
            channel.enableVibration(true);

            builder.setChannelId(channelId1);
            if (notificationManager != null)
            {
                notificationManager.createNotificationChannel(channel);
            }
        }else
        {
            builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);

        }
        if (notificationManager != null)
        {
            notificationManager.notify(notifyId,builder.build());
        }
    }
}
