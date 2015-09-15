/**
 * Created by Vivian_DELL on 9/14/2015.
 */
import java.util.regex.*;

public class TwitterInfo {
    private String Handler;
    private String URL;
    private int IsOrganization;
    private String description;
    private String name;
    private String location;
    private String timezone;
    private String lang;
    private int followerscount;
    private int friendscount;
    private double ratio;
    private int listedcount;
    private int favoritescount;

    public static void main(String[] args) throws Exception{
        System.out.println("TwitterInfo");

        CreateCorpora.initialize();

        TwitterInfo ti = CreateCorpora.twitterInfoList.get(35);

        System.out.println(ti.getHandler() + "\t" + ti.getIsOrganization() + "\t" + ti.classify());
    }


    //algorithm
    public int classify(){
        if(description.trim().equals("null")){
            return 0;
        }
        int score = 0;
        for(String i: CreateCorpora.individualNames){
            if(Handler.trim().toLowerCase().equals(i.trim().toLowerCase())){
                System.out.println("Handler="+Handler+", score--");
                score --;
            }
            String[] names = name.split(" ");
            for(String n: names) {
                if (n.trim().toLowerCase().equals(i.trim().toLowerCase())) {
                    System.out.println("name="+n+", score--");
                    score--;
                }
            }
        }
        for(String i: CreateCorpora.restaurantNames){
          //  System.out.println("i="+i+", Handler="+Handler);
            if(Handler.trim().toLowerCase().equals(i.trim().toLowerCase())
                    ||Handler.trim().toLowerCase().indexOf(i.trim().toLowerCase())!=-1
            ||i.trim().toLowerCase().indexOf(Handler.trim().toLowerCase())!=-1){
                System.out.println("Handler="+Handler+", score++");
                score ++;
            }
            String[] names = name.split(" ");
            for(String n: names) {
//                Pattern p = Pattern.compile("[.,\"\\?!:']");
//                Matcher m = p.matcher(n);
//                n = m.replaceAll("");
            //    n = n.replaceAll(",", "").replaceAll(":", "").replaceAll("\\?", "").replaceAll("!", "").replaceAll(",", "");
                if (n.trim().toLowerCase().equals(i.trim().toLowerCase())) {
                    System.out.println("name="+n+", score++");
                    score++;
                }
            }
        }

        for(String i: CreateCorpora.restaurantNames_Special){
            //  System.out.println("i="+i+", Handler="+Handler);
            if(Handler.trim().toLowerCase().equals(i.trim().toLowerCase())
                    ||Handler.trim().toLowerCase().indexOf(i.trim().toLowerCase())!=-1
                    ||i.trim().toLowerCase().indexOf(Handler.trim().toLowerCase())!=-1){
                System.out.println("Handler="+Handler+", score++");
                score ++;
            }
            String[] names = name.split(" ");
            for(String n: names) {
//                Pattern p = Pattern.compile("[.,\"\\?!:']");
//                Matcher m = p.matcher(n);
//                n = m.replaceAll("");
                //    n = n.replaceAll(",", "").replaceAll(":", "").replaceAll("\\?", "").replaceAll("!", "").replaceAll(",", "");
                if (n.trim().toLowerCase().equals(i.trim().toLowerCase())||
                n.trim().toLowerCase().indexOf(i.trim().toLowerCase())!=-1) {
                    System.out.println("name="+n+", score++");
                    score++;
                }
            }
        }
        if(score>0) return 1;
        if(score<0) return 0;

        //score==0
        for(String i: CreateCorpora.individualFeatures){

        //    System.out.println("i="+i);
            String[] ds = description.split(" ");
            for(String d: ds) {
                Pattern p = Pattern.compile("[.,\"\\?!:']");
                Matcher m = p.matcher(d);
                d = m.replaceAll("");
           //     System.out.println("d="+d);
                if (d.trim().toLowerCase().equals(i.trim().toLowerCase())) {
                    System.out.println("description="+d+", score--");
                    score--;
                }
            }
        }
        for(String i: CreateCorpora.restaurantFeatures){

            String[] names = name.split(" ");
            String[] ds = description.split(" ");
            for(String d: ds) {
                if (d.trim().toLowerCase().equals(i.trim().toLowerCase())) {
                    System.out.println("description="+d+", score++");
                    score++;
                }
                //the "est" mode
//                if (d.trim().toLowerCase().indexOf("est")!=-1) {
//                    System.out.println("description="+d+", score++");
//                    score++;
//                }
            }
        }
        if(score>0) return 1;
        if(score<0) return 0;

        else return -1;


    }

    public TwitterInfo(String handler, String URL, int isOrganization, String description, String name, String location, String timezone, String lang, int followerscount, int friendscount, double ratio, int listedcount, int favoritescount) {
        Handler = handler;
        this.URL = URL;
        IsOrganization = isOrganization;
        this.description = description;
        this.name = name;
        this.location = location;
        this.timezone = timezone;
        this.lang = lang;
        this.followerscount = followerscount;
        this.friendscount = friendscount;
        this.ratio = ratio;
        this.listedcount = listedcount;
        this.favoritescount = favoritescount;
    }

    public String getHandler() {
        return Handler;
    }

    public void setHandler(String handler) {
        Handler = handler;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getIsOrganization() {
        return IsOrganization;
    }

    public void setIsOrganization(int isOrganization) {
        IsOrganization = isOrganization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getFollowerscount() {
        return followerscount;
    }

    public void setFollowerscount(int followerscount) {
        this.followerscount = followerscount;
    }

    public int getFriendscount() {
        return friendscount;
    }

    public void setFriendscount(int friendscount) {
        this.friendscount = friendscount;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public int getListedcount() {
        return listedcount;
    }

    public void setListedcount(int listedcount) {
        this.listedcount = listedcount;
    }

    public int getFavoritescount() {
        return favoritescount;
    }

    public void setFavoritescount(int favoritescount) {
        this.favoritescount = favoritescount;
    }



}
