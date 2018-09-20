
package pojos;


public class KafkaRecord 
{
    int trip_id;
    int record_number;
    int position_id;
    double latitude;
    double longitude;
    double altitude;
    double speed;
    double rpm;
    double fuel;
    
    

    public int getRecord_number() {
        return record_number;
    }

    public void setRecord_number(int record_number) {
        this.record_number = record_number;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }
    
    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getRpm() {
        return rpm;
    }

    public void setRpm(double rpm) {
        this.rpm = rpm;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    @Override
    public String toString() {
        return "KafkaRecord{" + "trip_id=" + trip_id + ", record_number=" + record_number + ", position_id=" + position_id + ", latitude=" + latitude + ", longitude=" + longitude + ", altitude=" + altitude + ", speed=" + speed + ", rpm=" + rpm + ", fuel=" + fuel + '}';
    }

    
    
    
    
    
    
}
