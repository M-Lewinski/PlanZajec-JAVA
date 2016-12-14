USE PlanZajec;

CREATE OR REPLACE VIEW UzytkownicyView
AS
SELECT login,imie,nazwisko FROM Uzytkownicy;
