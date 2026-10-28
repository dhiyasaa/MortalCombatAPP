package id.myryan.tutorialcombomortalkombat1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Inisialisasi tombol dan ikon kembali
        ImageView backIcon = findViewById(R.id.back_icon);
        Button themeButton = findViewById(R.id.btn_theme);
        Button updateButton = findViewById(R.id.btn_update);
        Button ratingButton = findViewById(R.id.btn_rating);
        Button infoPrivacyButton = findViewById(R.id.btn_info_privacy);

        // Fungsi kembali ke aktivitas sebelumnya
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Fungsi tombol lainnya
        themeButton.setOnClickListener(v -> Toast.makeText(SettingsActivity.this, "Theme clicked!", Toast.LENGTH_SHORT).show());
        updateButton.setOnClickListener(v -> Toast.makeText(SettingsActivity.this, "Update clicked!", Toast.LENGTH_SHORT).show());
        ratingButton.setOnClickListener(v -> Toast.makeText(SettingsActivity.this, "Rating clicked!", Toast.LENGTH_SHORT).show());
        infoPrivacyButton.setOnClickListener(v -> Toast.makeText(SettingsActivity.this, "Informasi & Privasi clicked!", Toast.LENGTH_SHORT).show());
    }
}
