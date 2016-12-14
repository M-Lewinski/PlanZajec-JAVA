USE PlanZajec;

CREATE OR REPLACE VIEW UzytkownicyView
AS
SELECT login,imie,nazwisko FROM Uzytkownicy;

CREATE ROLE IF NOT EXISTS 'Uzytkownik';
GRANT SELECT,UPDATE ON UzytkownicyView TO Uzytkownik;

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
