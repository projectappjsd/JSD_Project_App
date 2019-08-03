package s2017s40.kr.hs.mirim.seoulapp_jsd;

public class Coords {
    double latitude;
    double longitude;
    Coords(){
    }
    Coords(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
