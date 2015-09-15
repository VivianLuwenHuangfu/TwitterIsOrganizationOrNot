/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileOutputStream;
import java.io.PrintWriter;

import java.util.*;


public class Main {
  private static final int SLEEP_FOR_FOOD = 500;
  private static final int SLEEP_FOR_EMOTICONS = 2000;
  private static final int SLEEP = SLEEP_FOR_EMOTICONS;

  private static String placeToString(Place p) {
        if(p == null) return "NIL";
        StringBuilder os = new StringBuilder();
        os.append(c(p.getPlaceType()));
        os.append("/" + c(p.getFullName()));
        os.append("/" + c(p.getCountryCode()));
        os.append("/" + c(p.getBoundingBoxType()));
        GeoLocation [][] gs = p.getBoundingBoxCoordinates();
        if(gs != null) {
          for(int i = 0; i < gs.length; i ++) {
            for(int j = 0; j < gs[i].length; j ++) {
              os.append("/" + geoLocationToString(gs[i][j]));
            }
          }
        }
        return os.toString();
      }

  private static String geoLocationToString(GeoLocation g) {
        if(g == null) return "NIL";
        return c(Double.toString(g.getLatitude())) + "|" + c(Double.toString(g.getLongitude()));
  }   

  public static String c(String s) {
      if(s == null) return "NIL";
      if(s.length() == 0) return "NIL";
      return s.replaceAll("[\\t\\n\\r]+", " ");
  }
  public static void main(String[] args) throws Exception {
//    if(args.length != 2) {
//      System.err.println("Usage: Twitter4Food getall|sample <output file>");
//      System.exit(1);
//    }
//    final String cmd = args[0];
//    final String output = args[1];  //// NAME OF OUTPUT FILE

   //   CreateCorpora.downloadTwitterToFeatureTxt();

      CreateCorpora.initialize();




//prediction

      int correct = 0;
      int ambiguous = 0;

      int organizationNum = 0;
      int correctPredictedOrganizationNum = 0;

      for(TwitterInfo ti: CreateCorpora.twitterInfoList){
      //    System.out.println("*****************************************************************************");
          System.out.println(ti.getHandler() + "\t" + ti.getIsOrganization() + "\t" + ti.classify());
          System.out.println("*****************************************************************************");
          if(ti.classify()==-1){
              ambiguous ++;
          }
          if(ti.getIsOrganization()==1){
              organizationNum++;
              if(ti.classify()==1){
                  correctPredictedOrganizationNum++;
              }
          }
          if(ti.getIsOrganization() == ti.classify()){
              correct ++;
          }
      }

      System.out.println("ambiguous="+ambiguous+"\t"+ CreateCorpora.twitterInfoList.size() + "\t"
      + (double)ambiguous/(double)CreateCorpora.twitterInfoList.size());

      System.out.println("correct="+correct+"\t"+ CreateCorpora.twitterInfoList.size() + "\t"
              + (double)correct/(double)CreateCorpora.twitterInfoList.size());

      System.out.println("coverage="+correctPredictedOrganizationNum+"\t"+ organizationNum + "\t"
              + (double)correctPredictedOrganizationNum/(double)organizationNum);
  }

}
