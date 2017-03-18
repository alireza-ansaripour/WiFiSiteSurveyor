package wifisurveyor.core.Database;

import java.awt.*;
import java.awt.geom.Point2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by alireza on 3/15/17.
 */
public class DBManager {
    private Connection c = null;
    private Statement stmt = null;
    private String user = "survey_app";
    private String password = "surveyMIGirim10";

    /**
     * creates a JDBC connection to database
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public DBManager() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        this.c = DriverManager
                .getConnection("jdbc:postgresql://git.ce.sharif.edu:5432/site_survey_db", user,password);
        this.c.setAutoCommit(false);
        this.stmt = c.createStatement();
    }

    /**
     * inserts an AP data to database
     * @param coordinate
     * @param log_time
     * @param plan
     * @param user_name
     * @param survey_name
     * @param mac
     * @param channel
     * @param ssid
     * @param signalPower
     * @throws SQLException
     */
    public void insert(Point2D coordinate, String log_time, String  plan, String  user_name, String survey_name, String mac, int channel, String ssid, String  signalPower) throws SQLException {
        String query = String.format("INSERT INTO survey_data (coordinate, log_time, floor_plan, user_name, survey_name, mac, channel, ssid, readings) VALUES (%s, '%s', '%s', '%s', '%s', macaddr('%s'), %d, '%s', '{%s}');","point(" + coordinate.getX() + "," + coordinate.getY() + ")", log_time, plan, user_name, survey_name, mac, channel, ssid, signalPower);
        stmt.execute(query);
        c.commit();
    }

    /**
     * deletes a point from a plan
     * @param coordinate
     * @param plan
     * @param username
     * @param survey_name
     */
    public void delete(Point coordinate, String plan, String username, String survey_name){
        //TODO : implement this method
    }
    public Object[] select(String plan){
        //TODO : implement this method
        return  null;
    }
    public Object[] select(Point coordinate, String plan, String username, String survey_name){
        //TODO : implement this method
        return  null;
    }
}
