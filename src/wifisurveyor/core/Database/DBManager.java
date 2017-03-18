package wifisurveyor.core.Database;

import org.postgresql.geometric.PGpoint;

import java.awt.*;
import java.awt.geom.Point2D;
import java.sql.*;
import java.util.ArrayList;

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
        System.out.println("connecting to database ....");
        Class.forName("org.postgresql.Driver");
        this.c = DriverManager
                .getConnection("jdbc:postgresql://git.ce.sharif.edu:5432/site_survey_db", user,password);
        this.c.setAutoCommit(false);
        this.stmt = c.createStatement();
        System.out.println("finished ....");
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
    public Point2D[] getPoints(String floor_plan,String username, String survey_name) throws SQLException {

        String query = String .format("SELECT coordinate from survey_data WHERE floor_plan = '%s' and user_name = '%s' and survey_name='%s';", floor_plan, username, survey_name);
        ResultSet resultSet = stmt.executeQuery(query);
        ArrayList<Point2D> point2DS = new ArrayList<>();
        while (resultSet.next()){
            String tmp = resultSet.getString(1);
            System.out.println(tmp);
            String pointString = tmp.substring(1, tmp.length()-1);
            String[] parts = pointString.split(",");
            Point2D p = new Point2D.Float(Float.parseFloat(parts[0]), Float.parseFloat(parts[1]));
            if(point2DS.size() == 0)
                point2DS.add(p);
            else {
                Point2D last = point2DS.get(point2DS.size()-1);
                if(p.getX() != last.getX() || p.getY() != last.getY())
                    point2DS.add(p);
            }
        }
        System.out.println(point2DS.get(0).getX() == point2DS.get(1).getX());
        return point2DS.toArray(new Point2D[point2DS.size()]);
    }
    public Object[] getPointData(Point coordinate, String plan, String username, String survey_name){

        //TODO : implement this method
        return  null;
    }

}
