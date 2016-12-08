package dataBase.subjects;

public class Zajecie {
  private Long id;
  private Long rocznik;
  private Long dzien;
  private Long godzina;
  private Long tydzien;
  private String rodzaj;
  private Long liczba_godzin;
  private Long grupa;
  private Long podgrupa;
  private String przedmiot;
  private String login_prowadzacego;
  private String sala;
  private String budynek;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getRocznik() {
    return rocznik;
  }

  public void setRocznik(Long rocznik) {
    this.rocznik = rocznik;
  }

  public Long getDzien() {
    return dzien;
  }

  public void setDzien(Long dzien) {
    this.dzien = dzien;
  }

  public Long getGodzina() {
    return godzina;
  }

  public void setGodzina(Long godzina) {
    this.godzina = godzina;
  }

  public Long getTydzien() {
    return tydzien;
  }

  public void setTydzien(Long tydzien) {
    this.tydzien = tydzien;
  }

  public String getRodzaj() {
    return rodzaj;
  }

  public void setRodzaj(String rodzaj) {
    this.rodzaj = rodzaj;
  }

  public Long getLiczba_godzin() {
    return liczba_godzin;
  }

  public void setLiczba_godzin(Long liczba_godzin) {
    this.liczba_godzin = liczba_godzin;
  }

  public Long getGrupa() {
    return grupa;
  }

  public void setGrupa(Long grupa) {
    this.grupa = grupa;
  }

  public Long getPodgrupa() {
    return podgrupa;
  }

  public void setPodgrupa(Long podgrupa) {
    this.podgrupa = podgrupa;
  }

  public String getPrzedmiot() {
    return przedmiot;
  }

  public void setPrzedmiot(String przedmiot) {
    this.przedmiot = przedmiot;
  }

  public String getLogin_prowadzacego() {
    return login_prowadzacego;
  }

  public void setLogin_prowadzacego(String login_prowadzacego) {
    this.login_prowadzacego = login_prowadzacego;
  }

  public String getSala() {
    return sala;
  }

  public void setSala(String sala) {
    this.sala = sala;
  }

  public String getBudynek() {
    return budynek;
  }

  public void setBudynek(String budynek) {
    this.budynek = budynek;
  }
}
