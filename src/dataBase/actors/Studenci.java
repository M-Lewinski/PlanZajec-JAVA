package dataBase.actors;

public class Studenci {
  private String login;
  private String kierunek;
  private Long semestr;
  private Long grupa;
  private Long indeks;

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getKierunek() {
    return kierunek;
  }

  public void setKierunek(String kierunek) {
    this.kierunek = kierunek;
  }

  public Long getSemestr() {
    return semestr;
  }

  public void setSemestr(Long semestr) {
    this.semestr = semestr;
  }

  public Long getGrupa() {
    return grupa;
  }

  public void setGrupa(Long grupa) {
    this.grupa = grupa;
  }

  public Long getIndeks() {
    return indeks;
  }

  public void setIndeks(Long indeks) {
    this.indeks = indeks;
  }
}
