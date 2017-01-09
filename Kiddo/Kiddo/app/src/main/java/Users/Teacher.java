package Users;

import UI.Calendar;
import UI.Classroom;
import UI.CalendarEvent;
import java.sql.* ;

/**
 * Created by Jason Qian on 12/11/2016.
 */

public class Teacher extends User{

    public Teacher (String username, String password, String email, String fName, String lName){
        super(username, email);
        PreparedStatement query = null;
        String queryString = "SELECT * FROM Teachers WHERE user = ? and pass = ?";
        PreparedStatement statement = null;
        String sqlString = "INSERT into Teachers (email, uname, pass, fName, lName) VALUES(?, ?, ?, ?, ?)";
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

    public static Teacher login(String username, String password) throws UserDoesNotExistException{
        Teacher teacher = null;
        PreparedStatement statement = null;
        String sqlString = "SELECT * FROM Teachers WHERE user = ? and pass = ?";
        try {
            statement = con.prepareStatement(sqlString);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if (!result.isBeforeFirst() ) {
                throw new UserDoesNotExistException();
            }
            teacher = new Teacher(result.getString("user"), result.getString("pass"), result.getString("email"), result.getString("fName"), result.getString("lName"));
            teacher.setID(result.getInt("idTeacher"));
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
            return teacher;
        }
    }

    public void addParent(Classroom classroom, Parent parent){
        classroom.addParent(parent);
    }

    public void createCalendarEvent(Calendar calendar, String name, String location, String startTime, String endTime, String comments){
        new CalendarEvent(calendar, name, location, startTime, endTime, comments);
    }

    public void editCalendarEvent(CalendarEvent event, String name, String location, String startTime, String endTime, String comments){
        event.update(name, location, startTime, endTime, comments);
    }

    public void deleteCalendarEvent(CalendarEvent event){
        event.delete();
    }

    public ResultSet teacherInfo(){
        PreparedStatement statement = null;
        String sqlString = "SELECT * FROM Teachers WHERE idTeacher = ?";

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

    public ResultSet allClasses(){
        PreparedStatement statement = null;
        String sqlString = "SELECT * FROM Classes WHERE Classes.idTeacher = ?";

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
