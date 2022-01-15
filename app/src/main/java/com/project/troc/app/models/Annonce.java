package com.project.troc.app.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.*;

import java.util.Objects;

public class Annonce {

    @Expose
    @SerializedName("id")
    private String id;

    @Expose
    @SerializedName("titre")
    private String titre;

    @Expose
    @SerializedName("descreption")
    private String descreption;

    public Annonce (String id, String titre, String descreption) throws Exception {
        setCode(id);
        setName(titre);
        setEmail(descreption);
    }

    public String getCode() {
        return id
                ;
    }

    public String getName() {
        return titre;
    }

    public String getEmail() {
        return descreption;
    }

    public void setCode(String id) throws Exception {
        if (id.length() != 5)
            throw  new  Exception("id must be 5 characters");

        this.id = id;
    }

    public void setName(String titre) throws Exception {
        if (titre.length() > 50)
            throw  new  Exception("titre must not be more than 50 characters");

        this.titre = titre;
    }

    public void setEmail(String descreption) throws Exception {
        if (descreption.length() > 50)
            throw  new  Exception("descreption must not be more than 50 characters");

        this.descreption = descreption;
    }

    @Override
    public String toString() {
        return titre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Annonce annonce = (Annonce) o;

        return Objects.equals(id,annonce.id ) &&
                Objects.equals(titre, annonce.titre) &&
                Objects.equals(descreption, annonce.descreption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titre, descreption);
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
