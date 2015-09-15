import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.util.*;


/**
 * Created by Vivian_DELL on 9/13/2015.
 */
public class CreateCorpora {
    public static List<TwitterInfo> twitterInfoList = new LinkedList<TwitterInfo>();
    public static List<String[]> twitterIsOrganization = new LinkedList<String[]>();
    public static List<String> individualFeatures = new LinkedList<String>();
    public static List<String> restaurantFeatures = new LinkedList<String>();
    public static List<String> individualNames = new LinkedList<String>();
    public static List<String> restaurantNames = new LinkedList<String>();
    public static List<String> restaurantNames_Special = new LinkedList<String>();


    public static void readFromFeatureTxt() throws Exception{

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/Feature.txt")));
            int count = 0;
            String s;
            String Handler = "";
            String URL = "";
            int IsOrganization = -1;
            String description = "";
            String name = "";
            String location = "";
            String timezone = "";
            String lang = "";
            int followerscount = -1;
            int friendscount = -1;
            double ratio = -1.0;
            int listedcount = -1;
            int favoritescount = -1;



            while ((s = br.readLine()) != null) {
            //    System.out.println(s);
                if(count==0) {
                    String[] str = s.split("\t");
                    Handler = str[0];
                    URL = str[1];
                    IsOrganization = Integer.valueOf(str[2]);
                    count++;
                    count = count%4;
                }else if(count==1){
                    description = s;
                    count++;
                    count = count%4;
                }else if(count==2){
                    String[] str = s.split("\t");
                    name = str[0];
                    location = str[1];
                    timezone = str[2];
                    lang = str[3];
                    count++;
                    count = count%4;
                }else if(count==3){
                    String[] str = s.split("\t");
                    followerscount = Integer.valueOf(str[0]);
                    friendscount = Integer.valueOf(str[1]);
                    ratio = Double.valueOf(str[2]);
                    listedcount = Integer.valueOf(str[3]);
                    favoritescount = Integer.valueOf(str[4]);
                    count++;
                    count = count%4;
                    TwitterInfo ti = new TwitterInfo(Handler,URL, IsOrganization, description, name, location, timezone,
                                lang, followerscount, friendscount, ratio, listedcount, favoritescount);
                    twitterInfoList.add(ti);


                }


            }
        }catch(Exception e){
            System.out.println(e);
        }


    }

    public static void downloadTwitterToFeatureTxt() throws Exception{

        createTwitterGoldenData();

        String featureTxt = "";

        final String cmd = "getall";
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(false);
        if(cmd.equals("getall")) {
            // app name: search4food ***** Twitter4Food
            cb.setOAuthConsumerKey("fHLtHBwm7StmfXzmLbjeKj1vr");
            cb.setOAuthConsumerSecret("alNxpHPMi5CCMgxYswUPK4WLK9QDdRIynT8qBQJsAk1jan3UJQ");
            cb.setOAuthAccessToken("2849556850-CaQNJ6zm0FdayB1boiZ9aPBcUhdrLGjMoDvOLG5");
            cb.setOAuthAccessTokenSecret("TxQMnwBGihWJNQCEQtHmdyHtNHloN7lvKcyspCRKgpqBC");
            // app name: search4food2
            //cb.setOAuthConsumerKey("yE7D3rO2t2MTh8qJIVuRCQ");
            //cb.setOAuthConsumerSecret("UxQ63nx6knphOfZsSdrSqGuIhKnoeFQPUGJFYXN2QU");
            //cb.setOAuthAccessToken("1928309594-1BaKz4G9leasxJ8zcutjH40zxsUgSAxzNOLDLzJ");
            //cb.setOAuthAccessTokenSecret("1fHLEA31PeIKNGjFFxLJkNDyFog8z70B8fvTubGbTYg");

        } else if(cmd.equals("sample")) {
            // app name: search4food-sample **** Twitter4Food-sample
            cb.setOAuthConsumerKey("naVETA8uLkSTKQoGILrhAzmHp");
            cb.setOAuthConsumerSecret("4srh4wbNAXFFC8Thh7yYs9GgFVNuuWY2U4xgdZ2B9wDZlnplqK");
            cb.setOAuthAccessToken("2849556850-7YcJ05wwvkgjtyvlNcal72hBkafGMAyd2SzlVTK");
            cb.setOAuthAccessTokenSecret("snWZO6Av7JKCbUwF0Wz2we0HnWYvm1lOT4ETW7rxm4tkb");
        }

        Twitter twitter = new TwitterFactory(cb.build()).getInstance();



        for(String[] str: twitterIsOrganization) {


            final String username = str[0];
            final String output = username + ".txt";  //// NAME OF OUTPUT FILE

            final PrintWriter pw = new PrintWriter(new FileOutputStream(output, true));


          //  System.out.println("---");

////////////////Right here the program really starts - Gets the user tweets////




            //  Twitter twitter = new TwitterFactory().getInstance();
            User user = twitter.showUser(username);
            if (user.getStatus() != null) {
                System.out.println("@" + user.getScreenName() + " - " + user.getStatus().getText());
                System.out.println("description=" + user.getDescription());//.replaceAll(""));
                System.out.println("getLocation=" + user.getLocation());
                System.out.println("getName=" + user.getName());
                System.out.println("getTimeZone=" + user.getTimeZone());
                System.out.println("getDescriptionURLEntities=" + user.getDescriptionURLEntities().toString());
                System.out.println("getFollowersCount=" + user.getFollowersCount());
                System.out.println("getFriendsCount=" + user.getFriendsCount());
                System.out.println("getLang=" + user.getLang());
                System.out.println("getURL=" + user.getURL());
                System.out.println("getListedCount=" + user.getListedCount());
                System.out.println("getFavouritesCount=" + user.getFavouritesCount());
                System.out.println("getProfileBannerURL=" + user.getProfileBannerURL());


           //     featureTxt += "---\n";
                featureTxt += "@" + user.getScreenName() + "\t" + user.getURL() + "\t" + str[1] + "\n";
                featureTxt += user.getDescription().replaceAll("\n"," ").replace("\r", " ") + " \n";
                featureTxt += user.getName() + "\t" + user.getLocation() + "\t" + user.getTimeZone() + "\t" + user.getLang() + "\n";
                featureTxt += user.getFollowersCount() + "\t" + user.getFriendsCount() + "\t" + (double)user.getFollowersCount()/(double)user.getFriendsCount()
                        + "\t" + user.getListedCount() + "\t" + user.getFavouritesCount() + "\n";
            } else {
                // the user is protected
                System.out.println("@" + user.getScreenName());
            }

//      User user = twitter.showUser(twitter.getId());
//      String description = user.getDescription();

            //     System.out.println("description=" + description);


//
//
//    for (int i = 1; i<30;i++) {//get the first i pages of 200 tweets (we expect i*200 tweets)
//      Paging paging = new Paging(i, 200); //200 is the max # of tweets per page
//  //"jamieoliver"; //here you change the wanted user
//      List<Status> statuses = twitter.getUserTimeline(username, paging);
//      for (Status status : statuses) {
//        System.out.println("text : " +status.getUser().getScreenName() + ": " + status.getText());
//
//         User u = status.getUser();
//         pw.println(
//          "@" + u.getScreenName() + "\t" +
//          c(u.getName()) + "\t" +
//          c(Long.toString(u.getId())) + "\t" +
//          c(u.getLocation()) + "\t" +
//          c(Integer.toString(u.getFollowersCount())) + "\t" +
//          c(Integer.toString(u.getUtcOffset())) + "\t" +
//          c(u.getTimeZone()) + "\t" +
//          c(u.getCreatedAt() != null ? u.getCreatedAt().toString() : null) + "\t" +
//          c(u.getLang()) + "\n" +
//           c(status.getCreatedAt() != null ? status.getCreatedAt().toString() : null) + "\t" +
//           geoLocationToString(status.getGeoLocation()) + "\t" +
//           placeToString(status.getPlace()) + "\n" +
//           c(status.getText()));
//         pw.flush();
//      //Thread.sleep(SLEEP * 5);
//    }
//  }
//
        }

        createFeatureFile(featureTxt);

    }

    public static List<String[]> createTwitterGoldenData(){
   // public static void main(String[] args){


        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/GoldenDataset.txt")));
            String s;
            while ((s = br.readLine()) != null) {
                String[] str = s.split("\t");
                System.out.println(str[0] + "\t" + str[1]);
                twitterIsOrganization.add(str);

            }
        }catch(Exception e){
            System.out.println(e);
        }
        return twitterIsOrganization;
    }



    public static List<String> createIndividualFeatures(){

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/IndividualFeatures.txt")));
            String s;
            while ((s = br.readLine()) != null){
                individualFeatures.add(s);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return individualFeatures;
    }

    public static List<String> createIndividualNames(){

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/IndividualNames.txt")));
            String s;
            while ((s = br.readLine()) != null){
                individualNames.add(s);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return individualNames;
    }

    public static List<String> createRestaurantFeatures(){

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/RestaurantFeatures.txt")));
            String s;
            while ((s = br.readLine()) != null){
                restaurantFeatures.add(s);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return restaurantFeatures;
    }

    public static List<String> createRestaurantNames(){

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/RestaurantNames.txt")));
            String s;
            while ((s = br.readLine()) != null){
                restaurantNames.add(s);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return restaurantNames;
    }


    public static List<String> createRestaurantNames_Special(){

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/RestaurantNames_Special.txt")));
            String s;
            while ((s = br.readLine()) != null){
                restaurantNames_Special.add(s);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return restaurantNames_Special;
    }


    public static void createFeatureFile(String txt){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("src/Feature.txt")));
            bw.write(txt);
            bw.flush();
            bw.close();
        }catch(Exception e){
            System.out.println(e);
        }

    }


    public static void initialize() throws Exception{
        readFromFeatureTxt();
        createIndividualNames();
        createRestaurantNames();
        createIndividualFeatures();
        createRestaurantFeatures();
        createRestaurantNames_Special();
    }
}
