package dataBase.actors;

import dataBase.MySql;
import dataBase.SqlObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Uzytkownik extends SqlObject {
    private String login;
    private String imie;
    private String nazwisko;
    private String haslo;
    private String host = "localhost";

    public Uzytkownik() {
    }

    public Uzytkownik(String login, String imie, String nazwisko, String haslo) {
        this.login = login;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.haslo = haslo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    @Override
    public void addObjectToBase(PreparedStatement stmt) throws SQLException {
        try {
            stmt.setString(1, this.login);
            stmt.setString(2, this.imie);
            stmt.setString(3, this.nazwisko);
            stmt.setString(4, this.haslo);
            this.createNewUser();
        } catch (SQLException e) {
            System.err.println("BLAD W Tworzeniu");
            throw e;
        }
    }

    private void createNewUser() throws SQLException {
        String SQL = "CREATE USER ?@? IDENTIFIED BY ?";
        Connection connect = MySql.getInstance().getConnect();
        try (PreparedStatement stmt = connect.prepareStatement(SQL);) {
            stmt.setString(1, this.login);
            stmt.setString(2, this.host);
            stmt.setString(3, this.haslo);
            stmt.execute();
            String[] SQLs = {
                    "GRANT SELECT ON UzytkownicyView TO ?@?",
                    "GRANT SELECT ON Wydzialy TO ?@?",
                    "GRANT SELECT ON Kierunki TO ?@?",
                    "GRANT SELECT ON Zajecia TO ?@?",
                    "GRANT SELECT ON Zaplanowane_zajecia TO ?@?",
                    "GRANT SELECT ON Semestry TO ?@?",
                    "GRANT SELECT ON Sale TO ?@?",
                    "GRANT SELECT ON Przedmioty TO ?@?",
                    "GRANT SELECT ON V_Obecnosci TO ?@?",
                    "GRANT SELECT ON Obecnosci TO ?@?",
                    "GRANT SELECT ON Studenci TO ?@?",
                    "GRANT SELECT ON Prowadzacy TO ?@?",
                    "GRANT SELECT ON Miejsca TO ?@?",
                    "GRANT EXECUTE ON FUNCTION PlanZajec.userName TO ?@?",
                    "GRANT EXECUTE ON FUNCTION PlanZajec.checkStudent TO ?@?",
                    "GRANT EXECUTE ON FUNCTION PlanZajec.checkProfessor TO ?@?",
                    "GRANT EXECUTE ON FUNCTION PlanZajec.takeSpot TO ?@?",
                    "GRANT EXECUTE ON FUNCTION PlanZajec.leaveSpot TO ?@?"
            };
            this.grantPrivilages(connect, SQLs);
        } catch (SQLException e) {
            throw e;
        }
    }

    protected void grantPrivilages(Connection connect, String SQL) throws SQLException {
        try (PreparedStatement stmt = connect.prepareStatement(SQL)) {
            stmt.setString(1, this.login);
            stmt.setString(2, this.host);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    protected void grantPrivilages(Connection connect, String[] SQLs) throws SQLException {
        for (String SQL :
                SQLs) {
            try {
                this.grantPrivilages(connect, SQL);
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    @Override
    public void deleteObjectFromBase(PreparedStatement stmt) throws SQLException {
        try {
            stmt.setString(1, this.login);
            this.deleteUser();
        } catch (SQLException e) {
            System.err.println("Failure while deleting user");
            throw e;
        }
    }

    @Override
    public String getDeleteSQL() {
        String SQL = "DELETE FROM Uzytkownicy WHERE login = ?";
        return SQL;
    }

    @Override
    public String getInsertSQL() {
        String SQL = "INSERT INTO Uzytkownicy " +
                "VALUES(?,?,?,?)";
        return SQL;
    }

    public void deleteUser() throws SQLException{
        String SQL = "DROP USER ?@?";
        Connection connect = MySql.getInstance().getConnect();
        try (PreparedStatement stmt = connect.prepareStatement(SQL);) {
            stmt.setString(1,this.login);
            stmt.setString(2,this.host);
            stmt.execute();
        }
        catch (SQLException e){
            throw e;
        }
    }

    @Override
    public String toString() {
        return this.imie + " " + this.nazwisko;
    }
}
