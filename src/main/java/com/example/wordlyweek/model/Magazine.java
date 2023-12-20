package com.example.wordlyweek.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@Entity
@Table(name = "magazine")
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int magazineId;

    @Column(name = "title")
    private String magazineName;

    @Column(name = "publicationDate")
    private String publicationDate;

    @ManyToMany(mappedBy = "magazines")
    @JsonIgnoreProperties("magazines")
    private List<Writer> writers;

    public Magazine() {
    }

    public Magazine(int magazineId, String magazineName, String publicationDate, List<Writer> writers) {
        this.magazineId = magazineId;
        this.magazineName = magazineName;
        this.publicationDate = publicationDate;
        this.writers = writers;
    }

    public int getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(int magazineId) {
        this.magazineId = magazineId;
    }

    public String getMagazineName() {
        return magazineName;
    }

    public void setMagazineName(String magazineName) {
        this.magazineName = magazineName;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public List<Writer> getWriters() {
        return writers;
    }

    public void setWriters(List<Writer> writers) {
        this.writers = writers;
    }
}