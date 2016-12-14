-- DROP DATABASE PlanZajec;
CREATE DATABASE PlanZajec;
USE PlanZajec;
--
set foreign_key_checks = 0;

-- Tablica uzytkownikow (Prowadzacy + Studenci)
CREATE TABLE Uzytkownicy(
  login VARCHAR(20) NOT NULL, -- PRIMARY KEY
  imie VARCHAR(20) NOT NULL,
  nazwisko VARCHAR(30) NOT NULL,
  haslo VARCHAR(30) NOT NULL
);


-- TABLICA PROWADZĄCYCH
CREATE TABLE Prowadzacy(
  login VARCHAR(20) NOT NULL, -- PRIMARY KEY FOREIGN KEY
  tytul VARCHAR(20)
);


-- TABLICA STUDENTÓW
CREATE TABLE Studenci(
  login VARCHAR(20) NOT NULL, -- PRIMARY KEY FOREIGN KEY
  kierunek VARCHAR(20) NOT NULL, -- FOREIGN KEY
  semestr INTEGER NOT NULL, -- FOREIGN KEY
  grupa INTEGER NOT NULL,
  indeks INTEGER NOT NULL
);


-- TABLICA WYDZIAŁÓW
CREATE TABLE Wydzialy(
  nazwa VARCHAR(20) NOT NULL -- PRIMARY KEY
);


-- TABLICA KIERNUKÓW
CREATE TABLE Kierunki(
  nazwa VARCHAR(20) NOT NULL, -- PRIMARY KEY
  nazwa_wydzialu VARCHAR(20) NOT NULL -- FOREIGN KEY
);


-- TABLICA SEMESTRÓW
CREATE TABLE Semestry(
  numer INTEGER NOT NULL -- PRIMARY KEY
);


-- TABLICA PRZEDMIOTÓW
CREATE TABLE Przedmioty(
  nazwa VARCHAR(20) NOT NULL, -- PRIMARY KEY
  kierunek VARCHAR(20) NOT NULL, -- FOREIGN KEY
  red INTEGER,
  green INTEGER,
  blue INTEGER
);

-- TABLICA ZAJĘĆ
CREATE TABLE Zajecia(
  id INTEGER NOT NULL AUTO_INCREMENT, -- PRIMARY KEY
  rocznik INTEGER NOT NULL,
  dzien INTEGER NOT NULL,
  godzina INTEGER NOT NULL,
  tydzien INTEGER NOT NULL,
  rodzaj VARCHAR(20) NOT NULL,
  liczba_godzin INTEGER NOT NULL,
  grupa INTEGER,
  podgrupa INTEGER,

  przedmiot VARCHAR(20) NOT NULL, -- FOREIGN KEY
  login_prowadzacego VARCHAR(20) NOT NULL, -- FOREIGN KEY
  sala VARCHAR(20) NOT NULL, -- FOREIGN KEY
  budynek VARCHAR(20) NOT NULL, -- FOREIGN KEY
  PRIMARY KEY (id)
);


-- TABLICA SAL
CREATE TABLE Sale(
  sala VARCHAR(20) NOT NULL, -- PRIMARY KEY
  budynek VARCHAR(20) NOT NULL, -- PRIMARY KEY
  liczba_miejsc INTEGER NOT NULL
);


-- TABLICA MIEJSC
CREATE TABLE Miejsca(
  numer INTEGER NOT NULL AUTO_INCREMENT, -- PRIMARY KEY potrzebny do weryfikacji miejsca. W rzeczywistości miejsca nie są ponumerowane
  id_zajecia INTEGER NOT NULL, -- PRIMARY KEY FOREIGN KEY
  student VARCHAR(20),  -- FOREIGN KEY
  PRIMARY KEY (numer,id_zajecia)
);



-- TABLICA ZAPLANOWANYCH ZAJĘĆ
CREATE TABLE Zaplanowane_zajecia(
  data DATE NOT NULL, -- PRIMARY KEY
  id_zajecia INTEGER NOT NULL -- PRIMARY KEY FOREIGN KEY
);


-- TABLICA OBECNOSCI
CREATE TABLE Obecnosci(
  typ VARCHAR(20) NOT NULL,
  student VARCHAR(20) NOT NULL, -- PRIMARY KEY FOREIGN KEY
  data DATE NOT NULL, -- PRIMARY KEY FOREIGN KEY
  id_zajecia INTEGER NOT NULL -- PRIMARY KEY FOREIGN KEY
);


ALTER TABLE Uzytkownicy ADD CONSTRAINT Uzytkownicy_PK PRIMARY KEY (login);

ALTER TABLE Prowadzacy ADD CONSTRAINT Prowadzacy_PK PRIMARY KEY (login);
ALTER TABLE Prowadzacy ADD CONSTRAINT Prowadzacy_FK FOREIGN KEY (login) REFERENCES Uzytkownicy (login);

ALTER TABLE Wydzialy ADD CONSTRAINT Wydzialy_PK PRIMARY KEY (nazwa);

ALTER TABLE Kierunki ADD CONSTRAINT Kierunki_FK FOREIGN KEY (nazwa_wydzialu) REFERENCES Wydzialy (nazwa);
ALTER TABLE Kierunki ADD CONSTRAINT Kierunki_PK PRIMARY KEY (nazwa, nazwa_wydzialu);

ALTER TABLE Semestry ADD CONSTRAINT Semestry_PK PRIMARY KEY (numer);

ALTER TABLE Studenci ADD CONSTRAINT Studenci_login_FK FOREIGN KEY (login) REFERENCES Uzytkownicy (login);
ALTER TABLE Studenci ADD CONSTRAINT Studenci_kierunek_FK FOREIGN KEY (kierunek) REFERENCES Kierunki (nazwa);
ALTER TABLE Studenci ADD CONSTRAINT Studenci_semestr_FK FOREIGN KEY (semestr) REFERENCES Semestry (numer);
CREATE UNIQUE INDEX index_unique ON Studenci (indeks);
ALTER TABLE Studenci ADD CONSTRAINT Studenci_PK PRIMARY KEY (login);

ALTER TABLE Przedmioty ADD CONSTRAINT Przedmioty_FK FOREIGN KEY (kierunek) REFERENCES Kierunki (nazwa);
ALTER TABLE Przedmioty ADD CONSTRAINT Przedmioty_PK PRIMARY KEY (nazwa);

ALTER TABLE Sale ADD CONSTRAINT Sale_PK PRIMARY KEY (sala, budynek);

ALTER TABLE Zajecia ADD CONSTRAINT Zajecia_przedmiot_FK FOREIGN KEY (przedmiot) REFERENCES Przedmioty (nazwa);
ALTER TABLE Zajecia ADD CONSTRAINT Zajecia_login_prowadzacego_FK FOREIGN KEY (login_prowadzacego) REFERENCES Prowadzacy (login);
ALTER TABLE Zajecia ADD CONSTRAINT Zajecia_sala_FK FOREIGN KEY (sala, budynek) REFERENCES Sale (sala, budynek);
-- ALTER TABLE Zajecia ADD CONSTRAINT Zajecia_budynek_FK FOREIGN KEY (budynek) REFERENCES Sale (budynek);
-- ALTER TABLE Zajecia ADD CONSTRAINT Zajecia_PK PRIMARY KEY (id);

ALTER TABLE Miejsca ADD CONSTRAINT Miejsca_id_zajecia_FK FOREIGN KEY (id_zajecia) REFERENCES Zajecia (id);

ALTER TABLE Miejsca ADD CONSTRAINT Miejsca_student_FK FOREIGN KEY (student) REFERENCES Studenci (login);
-- ALTER TABLE Miejsca ADD CONSTRAINT Miejsca_PK PRIMARY KEY (numer,id_zajecia);

ALTER TABLE Zaplanowane_zajecia ADD CONSTRAINT Zaplanowane_zajecia_FK FOREIGN KEY (id_zajecia) REFERENCES Zajecia (id);
ALTER TABLE Zaplanowane_zajecia ADD CONSTRAINT Zaplanowane_zajecia_PK PRIMARY KEY (data, id_zajecia);

ALTER TABLE Obecnosci ADD CONSTRAINT Obecnosci_data_FK FOREIGN KEY (data) REFERENCES Zaplanowane_zajecia (data);
ALTER TABLE Obecnosci ADD CONSTRAINT Obecnosci_id_zajecia_FK FOREIGN KEY (id_zajecia) REFERENCES Zaplanowane_zajecia (id_zajecia);
ALTER TABLE Obecnosci ADD CONSTRAINT Obecnosci_FK FOREIGN KEY (student) REFERENCES Studenci (login);
ALTER TABLE Obecnosci ADD CONSTRAINT Obecnosci_PK PRIMARY KEY (student,data,id_zajecia);
