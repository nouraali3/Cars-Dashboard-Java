/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author user
 */
public class Position {
    int posID, tripID;
    double latitude, longitude, altitude;
    Date timeStamp;

    public Position(int posID, int tripID, double latitude, double longitude, double altitude, String timeStamp) {
        this.posID = posID;
        this.tripID = tripID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        
        DateFormat formatter = new SimpleDateFormat("yy-mm-dd hh:mm:ss");
        try 
        {
            this.timeStamp = (Date)formatter.parse(timeStamp);
        }
        catch (ParseException ex) 
        {
            System.err.println("error in converting string to date of format HH:mm:ss, error is "+ex);
        }
        
        
    }

    public Position() {
    }

    public int getPosID() {
        return posID;
    }

    public void setPosID(int posID) {
        this.posID = posID;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        DateFormat formatter = new SimpleDateFormat("yy-mm-dd hh:mm:ss");
        try 
        {
            this.timeStamp = (Date)formatter.parse(timeStamp);
        }
        catch (ParseException ex) 
        {
            System.err.println("error in converting string to date of format HH:mm:ss, error is "+ex);
        }
    }
    
    public double getDifference(Position secondPosition)
    {
        
        double latDif = this.getLatitude()-secondPosition.getLatitude();
        double longDif = this.getLongitude()-secondPosition.getLongitude();
        //double altDif = this.getAltitude()-secondPosition.getAltitude();
//        return Math.sqrt( Math.pow(latDif, 2) + Math.pow(longDif, 2) +Math.pow(altDif, 2) );
        return Math.sqrt( Math.pow(latDif, 2) + Math.pow(longDif, 2) );
    }

    @Override
    public String toString() {
        return "Position{" + "posID=" + posID + ", tripID=" + tripID + ", latitude=" + latitude + ", longitude=" + longitude + ", altitude=" + altitude + ", timeStamp=" + timeStamp + '}';
    }
    
    
    
}
