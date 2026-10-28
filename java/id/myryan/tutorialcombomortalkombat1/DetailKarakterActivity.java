package id.myryan.tutorialcombomortalkombat1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailKarakterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_karakter);

        // Retrieve data from Intent
        String namaKarakter = getIntent().getStringExtra("nama_karakter");
        String deskripsiKarakter = getIntent().getStringExtra("deskripsi_karakter");
        String base64Gambar = getIntent().getStringExtra("gambar_karakter");
        String loreKarakter = getIntent().getStringExtra("lore_karakter");

        // Bind views
        TextView tvNama = findViewById(R.id.tvNama);
        TextView tvDeskripsi = findViewById(R.id.tvDeskripsi);
        ImageView ivKarakter = findViewById(R.id.ivKarakter);
        TextView tvLore = findViewById(R.id.tvLoreDetails);
        ImageView backIcon = findViewById(R.id.ivBack);

        // Combo section
        TextView tvComboTitle = findViewById(R.id.tvComboTitle);
        LinearLayout layoutCombo = findViewById(R.id.layoutCombo);
        Button btnCombo1 = findViewById(R.id.btnCombo1);
        Button btnCombo2 = findViewById(R.id.btnCombo2);
        Button btnCombo3 = findViewById(R.id.btnCombo3);
        TextView tvCombo1Details = findViewById(R.id.tvCombo1Details);
        TextView tvCombo2Details = findViewById(R.id.tvCombo2Details);
        TextView tvCombo3Details = findViewById(R.id.tvCombo3Details);

        // Fatality section
        TextView tvFatalityTitle = findViewById(R.id.tvFatality);
        LinearLayout layoutFatality = findViewById(R.id.layoutFatality);
        Button btnFatality1 = findViewById(R.id.btnFatality1);
        Button btnFatality2 = findViewById(R.id.btnFatality2);
        TextView tvFatality1Details = findViewById(R.id.tvFatality1Details);
        TextView tvFatality2Details = findViewById(R.id.tvFatality2Details);

        // Animality section
        TextView tvAnimalityTitle = findViewById(R.id.tvAnimality);
        LinearLayout layoutAnimality = findViewById(R.id.layoutAnimality);
        Button btnAnimality = findViewById(R.id.btnAnimality);
        TextView tvAnimalityDetails = findViewById(R.id.tvAnimalityDetails);

        // Lore section (added section)
        TextView tvLoreTitle = findViewById(R.id.tvLore);
        LinearLayout layoutLore = findViewById(R.id.layoutLore);
        TextView tvLoreDetails = findViewById(R.id.tvLoreDetails);

        // Set data to views
        tvNama.setText(namaKarakter);
        tvDeskripsi.setText(deskripsiKarakter);
        tvLoreDetails.setText(loreKarakter);

        // Back button handler
        backIcon.setOnClickListener(v -> onBackPressed());

        // Handling Combo visibility
        tvComboTitle.setOnClickListener(v -> {
            if (layoutCombo.getVisibility() == View.GONE) {
                layoutCombo.setVisibility(View.VISIBLE); // Show Combo
            } else {
                layoutCombo.setVisibility(View.GONE); // Hide Combo
            }
        });

        // Handle Combo button clicks to show technical details
        btnCombo1.setOnClickListener(v -> {
            tvCombo1Details.setVisibility(tvCombo1Details.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        btnCombo2.setOnClickListener(v -> {
            tvCombo2Details.setVisibility(tvCombo2Details.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        btnCombo3.setOnClickListener(v -> {
            tvCombo3Details.setVisibility(tvCombo3Details.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        // Handling Fatality visibility
        tvFatalityTitle.setOnClickListener(v -> {
            if (layoutFatality.getVisibility() == View.GONE) {
                layoutFatality.setVisibility(View.VISIBLE); // Show Fatality
            } else {
                layoutFatality.setVisibility(View.GONE); // Hide Fatality
            }
        });

        // Handle Fatality button clicks to show technical details
        btnFatality1.setOnClickListener(v -> {
            tvFatality1Details.setVisibility(tvFatality1Details.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        btnFatality2.setOnClickListener(v -> {
            tvFatality2Details.setVisibility(tvFatality2Details.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        // Handling Animality visibility
        tvAnimalityTitle.setOnClickListener(v -> {
            if (layoutAnimality.getVisibility() == View.GONE) {
                layoutAnimality.setVisibility(View.VISIBLE); // Show Animality
            } else {
                layoutAnimality.setVisibility(View.GONE); // Hide Animality
            }
        });

        // Handle Animality button clicks to show technical details
        btnAnimality.setOnClickListener(v -> {
            tvAnimalityDetails.setVisibility(tvAnimalityDetails.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        // Handling Lore Visibility
        tvLoreTitle.setOnClickListener(v -> {
            if (layoutLore.getVisibility() == View.GONE) {
                layoutLore.setVisibility(View.VISIBLE); // Show Animality
            } else {
                layoutLore.setVisibility(View.GONE); // Hide Animality
            }
        });

        // Set character image if available
        if (base64Gambar != null && !base64Gambar.isEmpty()) {
            byte[] decodedString = Base64.decode(base64Gambar, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ivKarakter.setImageBitmap(decodedBitmap);
        } else {
            ivKarakter.setImageResource(R.drawable.placeholder); // Default image if not available
        }
    }
}