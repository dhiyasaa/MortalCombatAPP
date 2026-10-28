package id.myryan.tutorialcombomortalkombat1.Karakter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import id.myryan.tutorialcombomortalkombat1.DetailKarakterActivity;
import id.myryan.tutorialcombomortalkombat1.R;

public class AdapterKarakter extends RecyclerView.Adapter<AdapterKarakter.KarakterViewHolder> {

    private final Context context;
    private final List<modelKarakter> listKarakter;

    public AdapterKarakter(Context context, List<modelKarakter> listKarakter) {
        this.context = context;
        this.listKarakter = listKarakter;
    }

    @NonNull
    @Override
    public KarakterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_karakter, parent, false);
        return new KarakterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KarakterViewHolder holder, int position) {
        modelKarakter karakter = listKarakter.get(position);
        holder.namaKarakter.setText(karakter.getNama_karakter());

        // Decode Base64 image
        String base64Image = karakter.getGambar_karakter();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.gambarKarakter.setImageBitmap(decodedBitmap);
        } else {
            holder.gambarKarakter.setImageResource(R.drawable.placeholder);
        }

        // Set OnClickListener for item
        holder.itemView.setOnClickListener(v -> {
            // Play character voice
            playVoice(karakter.getSuara_karakter());

            // Intent to DetailKarakterActivity
            Intent intent = new Intent(context, DetailKarakterActivity.class);

            // Pass character details
            intent.putExtra("id_karakter", karakter.getId());
            intent.putExtra("nama_karakter", karakter.getNama_karakter());
            intent.putExtra("deskripsi_karakter", karakter.getDeskripsi_karakter());
            intent.putExtra("gambar_karakter", karakter.getGambar_karakter());
            intent.putExtra("suara_karakter", karakter.getSuara_karakter());
            intent.putExtra("lore_karakter", karakter.getLore_karakter());
            intent.putExtra("jenis_teknik", karakter.getJenis_teknik());
            intent.putExtra("nama_teknik", karakter.getNama_teknik());
            intent.putExtra("teknis", karakter.getTeknis());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listKarakter.size();
    }

    // Decode and play voice from Base64 string
    private void playVoice(String base64Voice) {
        if (base64Voice != null && !base64Voice.isEmpty()) {
            try {
                // Decode Base64 to binary data
                byte[] decodedVoice = Base64.decode(base64Voice, Base64.DEFAULT);

                // Write binary data to temporary file
                File tempFile = File.createTempFile("temp_voice", ".mp3", context.getCacheDir());
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(decodedVoice);
                fos.close();

                // Play audio using MediaPlayer
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(tempFile.getAbsolutePath());
                mediaPlayer.prepare();
                mediaPlayer.start();

                // Cleanup: Delete temp file after playback
                mediaPlayer.setOnCompletionListener(mp -> tempFile.delete());
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Failed to play voice", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Voice data not available", Toast.LENGTH_SHORT).show();
        }
    }

    // ViewHolder class
    public static class KarakterViewHolder extends RecyclerView.ViewHolder {
        TextView namaKarakter;
        ImageView gambarKarakter;

        public KarakterViewHolder(@NonNull View itemView) {
            super(itemView);
            namaKarakter = itemView.findViewById(R.id.tvNama); // Pastikan id sesuai dengan layout item_karakter.xml
            gambarKarakter = itemView.findViewById(R.id.ivAvatar); // Pastikan id sesuai dengan layout item_karakter.xml
        }
    }
}
