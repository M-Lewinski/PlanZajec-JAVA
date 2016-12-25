USE PlanZajec;

CREATE OR REPLACE VIEW UzytkownicyView
AS
SELECT login,imie,nazwisko FROM Uzytkownicy;

DELIMITER $$
DROP FUNCTION IF EXISTS `userName`$$
CREATE FUNCTION userName()
  RETURNS VARCHAR(20)
DETERMINISTIC
  BEGIN
    DECLARE userLogin VARCHAR(20);
    DECLARE result VARCHAR(20);
    SELECT USER() INTO userLogin;
    SELECT SUBSTRING_INDEX(userLogin,'@',1) INTO result;
    RETURN (result);
  END$$
DELIMITER ;

# CREATE OR REPLACE VIEW Konto
# AS
# SELECT * FROM Uzytkownicy
# WHERE login = (SELECT userName());

CREATE OR REPLACE VIEW V_Obecnosci
AS
  SELECT * FROM Obecnosci
  WHERE student = (SELECT userName());

# CREATE ROLE IF NOT EXISTS Uzytkownik;
# GRANT SELECT ON UzytkownicyView TO Uzytkownik;
# GRANT UPDATE ON Konto TO Uzytkownik;

DELIMITER $$
DROP FUNCTION IF EXISTS `changePassword`$$
CREATE FUNCTION changePassword(oldPassword VARCHAR(20),newPassword VARCHAR(20))
  RETURNS BOOLEAN
DETERMINISTIC
  BEGIN
    DECLARE userPassword VARCHAR(20);
    DECLARE  nameU VARCHAR(20);
    SELECT USER() INTO nameU;
    SELECT haslo FROM Uzytkownicy INTO userPassword;
    IF (oldPassword LIKE userPassword) THEN
        UPDATE Uzytkownicy SET haslo = newPassword
          WHERE login = (SELECT  userName());
      RETURN (TRUE);
    END IF;
    RETURN (FALSE);
  END$$
DELIMITER ;
