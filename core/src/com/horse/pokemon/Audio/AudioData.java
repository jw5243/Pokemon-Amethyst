package com.horse.pokemon.Audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public enum AudioData {
    OPENING_MOVIE("Sounds\\001 Opening Movie.mp3"), INTRODUCTION("Sounds\\002 Introduction.mp3"), START_SPECIAL_REPORT("Sounds\\003 Special Report_ _Search for the R.mp3"),
    TWINLEAF_TOWN_DAYTIME("Sounds\\004 Twinleaf Town (Day).mp3"), BATTLE_WILD_POKEMON("Sounds\\009 Battle! (Wild Pokemon).mp3");
    
    private String audioPath;
    private Sound  audio;
    private long   audioID;
    
    AudioData(String audioPath) {
        setAudioPath(audioPath);
        initializeAudio();
    }
    
    public Sound getAudio() {
        return audio;
    }
    
    public void setAudio(Sound audio) {
        this.audio = audio;
    }
    
    public long getAudioID() {
        return audioID;
    }
    
    public void setAudioID(long audioID) {
        this.audioID = audioID;
    }
    
    public String getAudioPath() {
        return audioPath;
    }
    
    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }
    
    public void playAudio() {
        playAudio(1.0f);
    }
    
    public void playAudio(float volume) {
        initializeAudio();
        setAudioID(getAudio().play(volume));
    }
    
    public void initializeAudio() {
        setAudio(Gdx.audio.newSound(Gdx.files.internal(getAudioPath())));
    }
}
