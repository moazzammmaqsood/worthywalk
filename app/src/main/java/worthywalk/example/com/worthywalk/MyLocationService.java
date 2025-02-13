package worthywalk.example.com.worthywalk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.SystemClock;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;


import java.text.DecimalFormat;
import java.util.List;

import worthywalk.example.com.worthywalk.Models.User;
import worthywalk.example.com.worthywalk.Utilities.UserSingleton;

public class MyLocationService extends BroadcastReceiver {
//    User user=new User();
    User user = UserSingleton.getUser();

    static walksession walk=new walksession();
    static long oldtime,newtime;

    public static double kms=0;
    private static DecimalFormat df2 = new DecimalFormat("#.##");


    public static final String ACTION_PROCESS_UPDATE = "com.example.forgroundapp.UPDATELOCATION";

    @Override
    public void onReceive(Context context, Intent intent) {


        if (intent != null) {
            final String action = intent.getAction();
            user=(User) intent.getSerializableExtra("User");

            if (ACTION_PROCESS_UPDATE.equals(action)) {
                LocationResult result = LocationResult.extractResult(intent);


                if (result != null) {
                    Location location = result.getLastLocation();
                    if(location!=null) {
                        SensorForeground.loc.add(new LatLng(location.getLatitude(), location.getLongitude()));
                        //Long time= System.currentTimeMillis();
                        // calculatedistance(SensorForeground.loc,time);


                        String location_string = new StringBuilder("" + location.getLatitude())
                                .append("/")
                                .append(location.getLongitude())
                                .toString();
                        try {
                            WalkActivity.getInstance().UpdateTextViews();
                        } catch (Exception ex) {
                            Toast.makeText(context, location_string, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    public static double getDistance(List<LatLng> points){

        Double TotalDistance = 0.0 ;

        if(points.size()>1){

            float[] results = new float[1];
            for(int i = 0 ; i < points.size()-2 ; i++) {


                Location.distanceBetween(
                        points.get(i).latitude,
                        points.get(i).longitude,
                        points.get(i + 1).latitude,
                        points.get(i + 1).longitude,
                        results
                );
                TotalDistance += results[0];

            }
        }
        // Divide TotalDistance by 2 to get the accurate result
        return Double.parseDouble(df2.format(TotalDistance));

    }
    public static double getDiscardDistance(List<LatLng> points,int j){

        Double TotalDistance = 0.0 ;

        if(points.size()>1){

            float[] results = new float[1];
            for(int i = 0 ; i < points.size()-2 ; i++) {

                Location.distanceBetween(
                        points.get(i).latitude,
                        points.get(i).longitude,
                        points.get(i + 1).latitude,
                        points.get(i + 1).longitude,
                        results
                ); if (j == 0) {

                   if (results[0]<0);
                    else if (results[0] > 7) TotalDistance += results[0];

                } else if (j == 1) {
                    if (results[0]<0);

                   else if (results[0] > 20) TotalDistance += results[0];




//                    TotalDistance += results[0];
                }    else {
                    if (results[0] > 5 || results[0] < 0) TotalDistance += results[0];

                }
            }
        }
        // Divide TotalDistance by 2 to get the accurate result
        return Double.parseDouble(df2.format(TotalDistance/2));
    }
    static double delta_disace = 0;
    static double calorues = 0;
    public static double getCalories(double distance){

        //return Double.parseDouble(df2.format(distance/22));
        if(delta_disace - distance != 0)
        {
            long elapsedMillis = SystemClock.elapsedRealtime() - SensorForeground.getTime();
            User user = UserSingleton.getUser();
            delta_disace = distance ;
            elapsedMillis = elapsedMillis / 1000 +1;

            double x = (0.0215 * (distance/elapsedMillis * 96.5606) * 3 - 0.1765 * (distance/elapsedMillis * 96.5606)*2
                    + 0.8710 * distance/elapsedMillis * 96.5606 + 1.4577) * /*user.Weight/2.22 **/ elapsedMillis/60;

            // In 22 meter 1Kcal is burnt
            //Duration (in minutes)*(MET*3.5*weight in kg)/200

            System.out.println("Time = "+elapsedMillis+"User "+user.Weight);
            delta_disace = distance;
            calorues = x;
            //return Double.parseDouble(df2.format(distance/22));
            return Double.parseDouble(df2.format(x));
        }
        else
        {
            return Double.parseDouble(df2.format(calorues));
            //return calorues;
        }


    }

}