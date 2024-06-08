package com.ftninformatika.modul2.restoran.model;

public class Kategorija {
  private long id;
  private String naziv;

  public Kategorija() {

  }

  public Kategorija(long id, String naziv) {
    this.id = id;
    this.naziv = naziv;
  }

  public long getId() {
    return id;
  } 

  public void setId(long id) {
    this.id = id;
  }

  public String getNaziv() {
    return naziv;
  }
  
  public void setNaziv(String naziv) {
    this.naziv = naziv;
  }
}
