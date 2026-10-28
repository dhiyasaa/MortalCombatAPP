package id.myryan.tutorialcombomortalkombat1.Karakter;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import id.myryan.tutorialcombomortalkombat1.R;

public class KarakterActivity extends AppCompatActivity {
    private RecyclerView rvCharacter;
    private AdapterKarakter adapterKarakter;
    private List<modelKarakter> listKarakter;
    private RequestQueue requestQueue;
    private static final String URL_API = "http://192.168.36.186/mortal-kombat-api/api/karakter.php?action=getKarakter";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karakter);

        // Inisialisasi UI
        rvCharacter = findViewById(R.id.rvCharacter);
        ImageView backIcon = findViewById(R.id.backIcon);

        // Inisialisasi variabel
        listKarakter = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        // Setup RecyclerView
        rvCharacter.setLayoutManager(new GridLayoutManager(this, 4)); // 4 kolom dalam grid
        adapterKarakter = new AdapterKarakter(this, listKarakter);
        rvCharacter.setAdapter(adapterKarakter);

        // Back button handler
        backIcon.setOnClickListener(v -> onBackPressed());

        // Load data dari API
        loadDataFromAPI();
    }

    private void loadDataFromAPI() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_API, null,
                response -> {
                    listKarakter.clear();
                    try {
                        // Ambil array dari response
                        if (response.has("data")) {
                            JSONArray karakterArray = response.getJSONArray("data");
                            for (int i = 0; i < karakterArray.length(); i++) {
                                JSONObject karakterObject = karakterArray.getJSONObject(i);

                                // Parsing data JSON ke modelKarakter
                                modelKarakter karakter = new modelKarakter();
                                karakter.setId(karakterObject.optString("id", ""));
                                karakter.setNama_karakter(karakterObject.optString("nama_karakter", "Unknown"));
                                karakter.setDeskripsi_karakter(karakterObject.optString("deskripsi_karakter", "No description"));
                                karakter.setGambar_karakter(karakterObject.optString("gambar_karakter", ""));
                                karakter.setSuara_karakter(karakterObject.optString("suara_karakter", ""));
                                karakter.setLore_karakter(karakterObject.optString("lore_karakter", ""));
                                karakter.setJenis_teknik(karakterObject.optString("jenis_teknik", ""));
                                karakter.setNama_teknik(karakterObject.optString("nama_teknik", ""));
                                karakter.setTeknis(karakterObject.optString("teknis", ""));

                                // Tambahkan ke list
                                listKarakter.add(karakter);
                            }
                        } else {
                            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                    // Perbarui RecyclerView setelah data diperoleh
                    adapterKarakter.notifyDataSetChanged();
                },
                error -> {
                    String errorMessage = "Error loading data";
                    if (error.getMessage() != null) {
                        errorMessage += ": " + error.getMessage();
                    }
                    Toast.makeText(KarakterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                });

        // Tambahkan request ke antrian
        requestQueue.add(jsonObjectRequest);
    }
}
