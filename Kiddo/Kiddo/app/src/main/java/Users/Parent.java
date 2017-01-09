package Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import UI.Classroom;

/**
 * Created by Jason Qian on 12/11/2016.
 */

public class Parent extends User{

    public Parent (String username, String password, String email, String fName, String lName){
        super(username, email);
        PreparedStatement query = null;
        String queryString = "SELECT * FROM Parents WHERE user = ? and pass = ?";
        PreparedStatement statement = null;
        String sqlString = "INSERT into Parents (email, uname, pass, fName, lName) VALUES(?, ?, ?, ?, ?)";
        try {
            query = con.prepareStatement(queryString);
            query.setString(1, username);
            query.setString(2, password);
            ResultSet result = query.executeQuery();

            if (!result.isBeforeFirst()) {
                statement = con.prepareStatement(sqlString);
                statement.setString(1, email);
                statement.setString(2, username);
                statement.setString(3, password);
                statement.setString(4, fName);
                statement.setString(5, lName);
                statement.executeUpdate();
                con.commit();
            }
        }catch (SQLException e ) {
            if (con != null) {
                try {
                    con.rollback();
                } catch(SQLException ex) {
                }
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public Parent (String username, String password, String email, String fName, String lName, int studentID){
        super(username, email);
        PreparedStatement query = null;
        String queryString = "SELECT * FROM Parents WHERE user = ? and pass = ?";
        PreparedStatement statement = null;
        String sqlString = "INSERT into Parents (email, uname, pass, fName, lName, idStudent) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            query = con.prepareStatement(queryString);
            query.setString(1, username);
            query.setString(2, password);
            ResultSet result = query.executeQuery();

            if (!result.isBeforeFirst()) {
                statement = con.prepareStatement(sqlString);
                statement.setString(1, email);
                statement.setString(2, username);
                statement.setString(3, password);
                statement.setString(4, fName);
                statement.setString(5, lName);
                statement.setInt(6, studentID);
                statement.executeUpdate();
                con.commit();
            }
        }catch (SQLException e ) {
            if (con != null) {
                try {
                    con.rollback();
                } catch(SQLException ex) {
                }
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static Parent login(String username, String password) throws UserDoesNotExistException{
        Parent parent = null;
        PreparedStatement statement = null;
        String sqlString = "SELECT * FROM Parents WHERE user = ? and pass = ?";
        try {
            statement = con.prepareStatement(sqlString);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if (!result.isBeforeFirst()) {
                throw new UserDoesNotExistException();
            }
            parent = new Parent(result.getString("user"), result.getString("pass"), result.getString("email"), result.getString("fName"), result.getString("lName"), result.getInt("idStudent"));
            parent.setID(result.getInt("idParent"));
        }catch (SQLException e ) {
            if (con != null) {
                try {
                    con.rollback();
                } catch(SQLException ex) {
                }
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                }
            }
            return parent;
        }
    }

    public void joinClass (Classroom classroom){
        try{
            classroom.joinParent(this);
        }
        catch (Exception e){
            //what do??
        }
    }

    public void updateInfo(String username, String password){
        PreparedStatement statement = null;
        String sqlString = "UPDATE Parents SET uname = ?, pass = ? WHERE idParent = ?";

        try {
            statement = con.prepareStatement(sqlString);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, this.getID());
            statement.executeUpdate();
            con.commit();
        }catch (SQLException e ) {
            if (con != null) {
                try {
                    con.rollback();
                } catch(SQLException ex) {
                }
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public ResultSet parentInfo(){
        PreparedStatement statement = null;
        String sqlString = "SELECT * FROM Parents WHERE idParent = ?";

        try {
            statement = con.prepareStatement(sqlString);
            statement.setInt(1, this.getID());
            return statement.executeQuery();
        }catch (SQLException e ) {
            if (con != null) {
                try {
                    con.rollback();
                } catch(SQLException ex) {
                }
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                }
            }
        }
        return null;
    }

    public ResultSet childrenInfo(){
        PreparedStatement statement = null;
        String sqlString = "SELECT * FROM Students WHERE idStudent = ?";
        PreparedStatement query1 = null;
        String query1String = "SELECT Students.idStudent FROM Students, Parents WHERE Students.idParent1 = ? OR Students.idParent2 = ?";

        try {
            query1 = con.prepareStatement(query1String);
            query1.setInt(1, this.getID());
            query1.setInt(2, this.getID());
            ResultSet result = query1.executeQuery();

            statement = con.prepareStatement(sqlString);
            statement.setInt(1, result.getInt("idStudent"));
            return statement.executeQuery();
        }catch (SQLException e ) {
            if (con != null) {
                try {
                    con.rollback();
                } catch(SQLException ex) {
                }
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                }
            }
        }
        return null;
    }

    public ResultSet allMessages(){
        PreparedStatement statement = null;
        String sqlString = "SELECT * FROM Messages WHERE Messages.idSender = ? or Messages.idRecipient = ?";
        
        try {
            statement = con.prepareStatement(sqlString);
            statement.setInt(1, this.getID());
            return statement.executeQuery();
        }catch (SQLException e ) {
            if (con != null) {
                try {
                    con.rollback();
                } catch(SQLException ex) {
                }
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                }
            }
        }
        return null;
    }
}
