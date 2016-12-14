CREATE USER 'checkLogin'@'localhost';

GRANT EXECUTE ON FUNCTION PlanZajec.checkUser TO 'checkLogin'@'localhost';
