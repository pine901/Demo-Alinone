package com.gamegards.allinonev3.Utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Build;

import static android.content.Context.MODE_PRIVATE;
import static com.gamegards.allinonev3._TeenPatti.PublicTable.MY_PREFS_NAME;

public class SoundPool {

    Activity context;
    android.media.SoundPool soundspool;
    int sound_id;
    public SoundPool(Activity context) {
        this.context = context;
        Initiazalition();
    }

    public void Initiazalition() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundspool = createNewSoundPool();
        } else {
            soundspool =  createOldSoundPool();
        }

    }

    public void PlaySound(int sound){
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String value = prefs.getString("issoundon", "0");

        if (value.equals("1")) {
            int soundId = soundspool.load(context, sound, 1);
            soundspool.play(soundId, 1, 1, 0, 0, 1);
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public android.media.SoundPool createNewSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        android.media.SoundPool sounds = new android.media.SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

        return sounds;
    }

    public android.media.SoundPool createOldSoundPool(){
        android.media.SoundPool sounds = new android.media.SoundPool(5, AudioManager.STREAM_MUSIC,0);
        return sounds;
    }

}
