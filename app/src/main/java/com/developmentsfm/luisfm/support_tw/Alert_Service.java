package com.developmentsfm.luisfm.support_tw;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

public class Alert_Service extends Service implements MediaPlayer.OnCompletionListener{

    MediaPlayer mediaPlayer;

    public Alert_Service() {




    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        AudioManager manager = (AudioManager)getSystemService(this.AUDIO_SERVICE);
        manager.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);

        Uri s = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mediaPlayer = MediaPlayer.create(this, s);// raw/s.mp3
        mediaPlayer.setOnCompletionListener( this);

        Toast.makeText(Alert_Service.this, "Servicio creado...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }

        Toast.makeText(Alert_Service.this, "Servicio iniciado...", Toast.LENGTH_SHORT).show();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        Toast.makeText(Alert_Service.this, "Servicio destruido...", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        mediaPlayer.start();
    }
}
