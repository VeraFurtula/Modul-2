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
  
  @Override
  public String toString() {
    return "Kategorija [id=" + id + ", naziv=" + naziv + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Kategorija other = (Kategorija) obj;
    if (id != other.id)
      return false;
    if (naziv == null) {
      if (other.naziv != null)
        return false;
    } else if (!naziv.equals(other.naziv))
      return false;
    return true;
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
