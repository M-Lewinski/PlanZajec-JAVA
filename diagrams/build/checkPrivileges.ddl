USE PlanZajec;

DELIMITER $$
DROP FUNCTION IF EXISTS `checkStudent`$$
CREATE FUNCTION checkStudent()
RETURNS BOOLEAN
DETERMINISTIC
BEGIN
DECLARE result int;
SELECT count(*) INTO result FROM Studenci s
WHERE s.login = (SELECT userName());
IF result > 0 THEN
RETURN (TRUE);
ELSE
RETURN (FALSE);
END IF;
END$$
DELIMITER ;


DELIMITER $$
DROP FUNCTION IF EXISTS `checkProfessor`$$
CREATE FUNCTION checkProfessor()
RETURNS BOOLEAN
DETERMINISTIC
BEGIN
DECLARE result int;
SELECT count(*) INTO result FROM Prowadzacy p
WHERE p.login = (SELECT userName());
IF result > 0 THEN
RETURN (TRUE);
ELSE
RETURN (FALSE);
END IF;
END$$
DELIMITER ;


DELIMITER $$
DROP FUNCTION IF EXISTS `takeSpot`$$
CREATE FUNCTION takeSpot(id int)
RETURNS BOOLEAN
DETERMINISTIC
BEGIN
DECLARE result int;
DECLARE result2 int;
-- DECLARE currentLogin int;
-- SELECT userName() INTO currentLogin;
SELECT COUNT(*) INTO result2 FROM Studenci WHERE login = (SELECT userName());
IF result2 = 0 THEN
RETURN (FALSE);
END IF;
SELECT numer INTO result FROM Miejsca WHERE student IS NULL AND id_zajecia = id ORDER BY numer ASC LIMIT 1;
UPDATE Miejsca SET student = (SELECT userName()) WHERE numer = result;
INSERT INTO Obecnosci (typ,student,data,id_zajecia) SELECT 'Unknown',(SELECT userName()), data, id_zajecia FROM Zaplanowane_zajecia WHERE id_zajecia = id;
RETURN (TRUE);
END$$
DELIMITER ;


DELIMITER $$
DROP FUNCTION IF EXISTS `leaveSpot`$$
CREATE FUNCTION leaveSpot(id int)
RETURNS BOOLEAN
DETERMINISTIC
BEGIN
UPDATE Miejsca SET student = NULL WHERE student = (SELECT userName());
DELETE FROM Obecnosci WHERE id_zajecia = id AND student = (SELECT userName());
RETURN (TRUE);
END$$
DELIMITER ;
