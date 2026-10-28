package id.myryan.tutorialcombomortalkombat1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import id.myryan.tutorialcombomortalkombat1.Karakter.KarakterActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set layout utama
        setContentView(R.layout.activity_main);

        // Cari view root dengan ID yang benar
        View mainView = findViewById(R.id.main);
        if (mainView == null) {
            Log.e(TAG, "View dengan ID 'main' tidak ditemukan. Periksa file layout Anda.");
            return; // Menghentikan jika layout tidak valid
        }

        // Atur padding untuk system bars
        ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Tombol Character
        Button characterButton = findViewById(R.id.character_button);
        if (characterButton == null) {
            Log.e(TAG, "Button dengan ID 'character_button' tidak ditemukan. Periksa file layout Anda.");
        } else {
            characterButton.setOnClickListener(v -> {
                // Pindah ke CharacterActivity
                Intent intent = new Intent(MainActivity.this, KarakterActivity.class);
                startActivity(intent);
            });
        }

        // Tombol Settings
        Button settingsButton = findViewById(R.id.settings_button);
        if (settingsButton == null) {
            Log.e(TAG, "Button dengan ID 'settings_button' tidak ditemukan. Periksa file layout Anda.");
        } else {
            settingsButton.setOnClickListener(v -> {
                // Pindah ke SettingsActivity
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            });
        }
    }
}
