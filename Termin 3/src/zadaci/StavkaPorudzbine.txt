package com.ftninformatika.modul2.restoran.model;

public class StavkaPorudzbine {
  private long id;
  private Artikal artikal;
  private int kolicina;
  
  public StavkaPorudzbine() {

  }

  public StavkaPorudzbine(long id, Artikal artikal, int kolicina) {
    this.id = id;
    this.artikal = artikal;
    this.kolicina = kolicina;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Artikal getArtikal() {
    return artikal;
  }

  public void setArtikal(Artikal artikal) {
    this.artikal = artikal;
  }

  public int getKolicina() {
    return kolicina;
  }

  public void setKolicina(int kolicina) {
    this.kolicina = kolicina;
  }
}
