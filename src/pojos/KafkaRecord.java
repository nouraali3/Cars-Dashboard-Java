
package pojos;


public class KafkaRecord 
{
    
    int record_number;
    int position_id;
    int trip_id;
    int beaglebone_id;
    double latitude;
    double longitude;
    double altitude;
    double velocity;

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

    public int getBeaglebone_id() {
        return beaglebone_id;
    }

    public void setBeaglebone_id(int beaglebone_id) {
        this.beaglebone_id = beaglebone_id;
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

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    @Override
    public String toString() {
        return "KafkaRecord{" + "trip_id=" + trip_id + ", beaglebone_id=" + beaglebone_id + ", latitude=" + latitude + ", longitude=" + longitude + ", altitude=" + altitude + ", velocity=" + velocity + '}';
    }
    
    
    
}
