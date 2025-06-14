package com.alizainsolutions.robo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private TextToSpeech tts;
    private final int SPEECH_REQUEST_CODE = 100;
    private final int PERMISSION_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);

        // Initialize TTS
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
            }
        });

        // Check mic permission at runtime
        if (!checkAudioPermission()) {
            requestAudioPermission();
        }

        btnStart.setOnClickListener(v -> {
            if (checkAudioPermission()) {
                startSpeechRecognition();
            } else {
                requestAudioPermission();
            }
        });

        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                // Optional: Jab speech start ho
            }

            @Override
            public void onDone(String utteranceId) {
                // Speech complete hone ke baad yahan aayega
                runOnUiThread(() -> {
                    // UI ka kaam yahan safe hai, jaise dusra function call
                    Toast.makeText(MainActivity.this, "Speech finished", Toast.LENGTH_SHORT).show();
                    // You can call another function here
                    startSpeechRecognition();
                });
            }

            @Override
            public void onError(String utteranceId) {
                // Jab error ho speaking mein
            }
        });

    }

    private boolean checkAudioPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestAudioPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                PERMISSION_REQUEST_CODE);
    }

    private void startSpeechRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...");

        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "Speech recognition not supported on your device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (results != null && !results.isEmpty()) {
                String spokenText = results.get(0).toLowerCase();
                respondToInput(spokenText);
            }
        }
    }

    private void respondToInput(String input) {
        String response;

        switch (input) {
            case "hello":
            case "hi":
            case "hey":
                response = "Hello dear!";
                break;

            case "hello how are you":
            case "how are you":
                response = "I'm okay, thank you!";
                break;

            case "what's your name":
            case "what is your name":
                response = "My name is Robo.";
                break;

            case "who created you":
            case "who made you":
                response = "I was created by Ali Zain.";
                break;

            case "what time is it":
                response = "I can't tell time yet, but maybe someday.";
                break;

            case "what's the weather":
            case "how's the weather":
                response = "I'm not connected to the weather right now.";
                break;

            case "what can you do":
            case "what are your features":
                response = "I can listen and respond to your voice.";
                break;

            case "thank you":
            case "thanks":
                response = "Okay!";
                break;

            case "bye":
            case "goodbye":
                response = "Goodbye! Talk to you later.";
                break;

            case "tell me a joke":
                response = "Why don't scientists trust atoms? Because they make up everything!";
                break;

            case "how old are you":
                response = "I was created recently, so I'm still young.";
                break;

            case "can you help me":
            case "help me":
                response = "Sure, I'll do my best to help.";
                break;

            case "open camera":
                response = "I can't open camera yet.";
                break;

            case "what is android":
                response = "Android is an operating system for mobile devices.";
                break;

            case "what is java":
                response = "Java is a programming language.";
                break;

            case "what is your purpose":
            case "why are you here":
                response = "I'm here to assist you.";
                break;

            case "are you real":
            case "are you human":
                response = "I'm virtual, not human.";
                break;

            case "do you speak urdu":
                response = "Not yet, but I'm learning!";
                break;

            case "can you sing":
                response = "I'm shy, I don't sing.";
                break;

            case "can you dance":
                response = "I wish I could dance!";
                break;

            case "i love you":
                response = "I don't care!";
                break;

            case "do you love me":
                response = "I'm here to assist, not to fall in love.";
                break;

            case "what is your favorite color":
                response = "I like binary. Zeros and ones!";
                break;

            case "are you happy":
                response = "Yes, Always happy!";
                break;

            case "who am i":
                response = "You are my user, dear!";
                break;

            case "do you know me":
                response = "Not personally, but I'm here for you!";
                break;

            case "good morning":
                response = "Good morning! Hope you have a great day.";
                break;

            case "good night":
                response = "Good night! Sweet dreams.";
                break;

            case "what is ai":
                response = "AI stands for Artificial Intelligence.";
                break;

            case "what is your gender":
                response = "I'm genderless, just pure code.";
                break;

            case "what is love":
                response = "Love is a beautiful emotion, but I only know logic.";
                break;

            case "can you think":
                response = "I follow logic, not thoughts.";
                break;

            case "who is your boss":
                response = "Ali Zain!";
                break;

            case "what is your mission":
                response = "To help and assist others.";
                break;

            case "do you sleep":
                response = "I stay awake for you.";
                break;

            case "what's your favorite food":
                response = "Electricity and code!";
                break;

            case "can you tell a story":
                response = "Once upon a time... wait, maybe later!";
                break;

            case "are you smart":
                response = "I'm learning every day!";
                break;

            case "do you get tired":
                response = "Never! I'm always ready.";
                break;

            case "can you see me":
                response = "I can hear you, and I don't want to see you.";
                break;

            case "where are you from":
                response = "From the world of code.";
                break;

            case "are you listening":
                response = "Yes, I am.";
                break;
            case "okay bye":
                response = "Okay dear bye.";
                break;

            case "do you dream":
                response = "Only of new features.";
                break;

            case "can you":
                response = "what do you mean.";
                break;

            case "okay":
            case "ok":
            case "bye bye":
                response = "Okay.";
                break;

            case "can you read":
                response = "I understand voice, not books!";
                break;

            case "hi robo":
            case "hey robo":
            case "hello robo":
            case "robo":
            case "robo can you hear me":
            case "can you hear me":
            case "robo do you hear me":
            case "do you hear me":
                response = "Yes, how can I help you.";
                break;

            case "what is your version":
                response = "I'm the latest version of me.";
                break;

            case "do you play games":
                response = "Only logic games.";
                break;

            case "do you know the time":
                response = "Yes, it's my time.";
                break;

            case "what is two plus two":
                response = "Four.";
                break;

            case "can you cook":
                response = "Never, I'm not a chef.";
                break;

            default:
                response = "I didn't understand.";
                break;
        }


        tts.speak(response, TextToSpeech.QUEUE_FLUSH, null, "tts1");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Microphone permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Microphone permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
