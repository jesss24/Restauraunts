import java.util.*;
import java.io.*;
import java.lang.*;
public class RestaurantRadomizer {
    public static void main(String[] args) throws FileNotFoundException {
        String zip = zipCode();
        while(zip.equalsIgnoreCase("Invalid zip code!")) {
            System.out.println(zip);
            zip = zipCode();
        }
        double miles = miles();
        while(miles == -1){
            miles = miles();
        }
        ArrayList<Restaurant> track = new ArrayList<Restaurant>();
        ArrayList<Restaurant> restaurantList = restaurants();
        ArrayList<Restaurant> restaurauntsIn = compares(zip, restaurantList, miles);
        randomRestauraunt(restaurauntsIn, track);
        System.out.println();
        continueSearch(restaurauntsIn, track);
        /*for(int i = 0; i < track.size(); i++){
            System.out.println(track.get(i).getName());
            System.out.println(restaurauntsIn.get(i).getName());
            System.out.println();
        }*/

    }

    public static String zipCode() throws FileNotFoundException{
        Scanner s = new Scanner(System.in);
        System.out.print("What is your zip code? ");
        String zip = s.next();
        Scanner read = new Scanner(new File("src/ZipCodes.csv"));
        String location = "";
        String line = "";
        while(read.hasNext() && !zip.equalsIgnoreCase(location)){
            line = read.next();
            location = line.substring(0, line.indexOf(","));
        }
        if(zip.equalsIgnoreCase(location)) {
            String latLon = line.substring(line.indexOf(",") + 1);
            return (latLon.substring(latLon.indexOf(",") + 1)
                    + ", " + latLon.substring(0, latLon.indexOf(",")));
        }
        else
            return "Invalid zip code!";
    }

    public static double miles() {
        Scanner s = new Scanner(System.in);
        System.out.print("How many miles do you want the restauraunt within? ");
        if (!s.hasNextDouble()){
            System.out.println("Invalid!");
            return -1;
        }
        return s.nextDouble();

    }

    public static ArrayList<Restaurant> restaurants() throws FileNotFoundException {
        Scanner s = new Scanner(new File("src/RestaurauntList.csv"));
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();

        while (s.hasNextLine()) {
            String line = s.nextLine();
            String lon = line.substring(0, line.indexOf(","));
            Scanner longitude = new Scanner(lon);
            double longi = longitude.nextDouble();

            String second = line.substring(line.indexOf(",") + 1);
            String lat = second.substring(0, second.indexOf(","));
            Scanner latitude = new Scanner(lat);
            double lati = latitude.nextDouble();

            String third = second.substring(second.indexOf(",") + 1);
            String resName = third.substring(0, third.indexOf(","));

            String address;
            String fourth = third.substring(third.indexOf(",") + 1);
            if (fourth.indexOf("\"") != -1) {
                fourth = third.substring(third.indexOf(",") + 2);
                address = fourth.substring(0, fourth.indexOf("\""));
            } else {
                address = fourth.substring(0, fourth.indexOf(","));
            }

            String fifth;
            String number;
            if (fourth.indexOf("\"") != -1) {
                //fourth = third.substring(third.indexOf(",") + 2);
                fifth = fourth.substring(fourth.indexOf("\"") + 2);
                number = fifth.substring(0, fifth.indexOf(","));
            } else {
                fifth = fourth.substring(fourth.indexOf(",") + 1);
                number = fifth.substring(0, fifth.indexOf(","));
            }

            String website = fifth.substring(fifth.indexOf(",") + 1);

            Restaurant rest = new Restaurant(longi, lati, resName, address, number, website);
            restaurants.add(rest);
        }
        return restaurants;
    }

    public static void randomRestauraunt(ArrayList<Restaurant> res, ArrayList<Restaurant> track) {
        if (track.size() == res.size()){
            System.out.println("Those are all the restaurants in the area! For more options, expand your search radius.");
        }
        else if(!(res.size() == track.size())) {
            int rand = (int) (Math.random() * res.size());
            ArrayList<Integer> random = new ArrayList<Integer>();
            boolean repeat = true;

            while(repeat) {
                rand = (int) (Math.random() * res.size());
                int repeats = 0;
                if(track.size() == 0)
                    repeat = false;
                for (int i = 0; i < track.size(); i++) {
                    if (track.get(i).getName().equalsIgnoreCase(res.get(rand).getName())) {
                        repeats++;
                    }
                }
                if(repeats == 0)
                    repeat = false;
            }

            //System.out.println("Location: " + res.get(rand).getLongitude() + ", " + res.get(rand).getLatitude());
            track.add(res.get(rand));
            System.out.println();
            System.out.println("Name: " + res.get(rand).getName());
            System.out.println("Address: " + res.get(rand).getAddress());
            System.out.println("Phone Number: " + res.get(rand).getPhoneNumber());
            System.out.println("Website: " + res.get(rand).getWebsite());
        }

    }

    public static double distance(double lat1, double lat2, double lon1, double lon2) {
        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in miles.
        double r = 3956;

        // calculate the result
        return(c * r);
    }

    public static ArrayList<Restaurant> compares(String zip, ArrayList<Restaurant> restaurants, double miles){
        String lon = zip.substring(0, zip.indexOf(","));
        Scanner s = new Scanner(lon);
        double zipLon = s.nextDouble();

        String lat = zip.substring(zip.indexOf(",") + 1);
        Scanner scan = new Scanner(lat);
        double zipLat = scan.nextDouble();

        ArrayList<Restaurant> restaurantsIn = new ArrayList<Restaurant>();

        for(int i = 0; i < restaurants.size(); i++){
            double dist = distance(restaurants.get(i).getLatitude(), zipLat, restaurants.get(i).getLongitude(), zipLon);
            if(dist <= miles)
                restaurantsIn.add(restaurants.get(i));
        }

        return restaurantsIn;
    }

    public static void continueSearch(ArrayList<Restaurant> rest, ArrayList<Restaurant> track) {
        Scanner s = new Scanner(System.in);
        System.out.println();
        if (track.size() == rest.size()) {
            System.out.println("Those are all the restaurants in the area! For more options, expand your search radius.");
        } else {
            System.out.print("Would you like a different restauraunt? (1 for yes, 2 for no) ");
            if (s.hasNextInt()) {
                int newRest = s.nextInt();
                if (newRest == 1) {
                    randomRestauraunt(rest, track);
                    continueSearch(rest, track);
                } else if (newRest == 2) {
                    System.out.println();
                    System.out.println("Hope you enjoy the restauraunt!");
                } else {
                    System.out.println("Invalid!");
                    continueSearch(rest, track);
                }
            }
        }
    }
}
