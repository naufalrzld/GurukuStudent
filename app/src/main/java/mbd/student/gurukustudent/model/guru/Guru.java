package mbd.student.gurukustudent.model.guru;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Naufal on 13/02/2018.
 */

public class Guru {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("no_tlp")
    @Expose
    private String noTlp;
    @SerializedName("sosmed")
    @Expose
    private Object sosmed;
    @SerializedName("kemampuan")
    @Expose
    private String kemampuan;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("harga")
    @Expose
    private Integer harga;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Guru(String username, String firstName, String lastName, String email, String noTlp, Object sosmed, String kemampuan, String deskripsi, Integer harga, String createdAt, String updatedAt) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.noTlp = noTlp;
        this.sosmed = sosmed;
        this.kemampuan = kemampuan;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoTlp() {
        return noTlp;
    }

    public void setNoTlp(String noTlp) {
        this.noTlp = noTlp;
    }

    public Object getSosmed() {
        return sosmed;
    }

    public void setSosmed(Object sosmed) {
        this.sosmed = sosmed;
    }

    public String getKemampuan() {
        return kemampuan;
    }

    public void setKemampuan(String kemampuan) {
        this.kemampuan = kemampuan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
