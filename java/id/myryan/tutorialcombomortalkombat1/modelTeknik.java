package id.myryan.tutorialcombomortalkombat1;

import android.os.Parcel;
import android.os.Parcelable;

public class modelTeknik implements Parcelable {
    private String karakter_id;
    private String jenisTeknik;
    private String namaTeknik;
    private String teknis;

    // Constructors, getters, setters, etc.

    protected modelTeknik(Parcel in) {
        karakter_id = in.readString();
        jenisTeknik = in.readString();
        namaTeknik = in.readString();
        teknis = in.readString();
    }

    public static final Creator<modelTeknik> CREATOR = new Creator<modelTeknik>() {
        @Override
        public modelTeknik createFromParcel(Parcel in) {
            return new modelTeknik(in);
        }

        @Override
        public modelTeknik[] newArray(int size) {
            return new modelTeknik[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(karakter_id);
        dest.writeString(jenisTeknik);
        dest.writeString(namaTeknik);
        dest.writeString(teknis);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters and setters for the fields
    public String getKarakter_id() {
        return karakter_id;
    }

    public void setKarakter_id(String karakter_id) {
        this.karakter_id = karakter_id;
    }

    public String getJenisTeknik() {
        return jenisTeknik;
    }

    public void setJenisTeknik(String jenisTeknik) {
        this.jenisTeknik = jenisTeknik;
    }

    public String getNamaTeknik() {
        return namaTeknik;
    }

    public void setNamaTeknik(String namaTeknik) {
        this.namaTeknik = namaTeknik;
    }

    public String getTeknis() {
        return teknis;
    }

    public void setTeknis(String teknis) {
        this.teknis = teknis;
    }
}
