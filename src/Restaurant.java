public class Restaurant {
    private double longitude;
    private double latitude;
    private String name;
    private String address;
    private String phoneNumber;
    private String website;

    public Restaurant(double longitude, double latitude, String name, String address, String phoneNumber, String website){
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.website = website;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getWebsite(){
        return website;
    }

}
