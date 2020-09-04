package worthywalk.example.com.worthywalk.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import worthywalk.example.com.worthywalk.Models.User;

import static worthywalk.example.com.worthywalk.Utilities.Constants.MyPREFERENCES;

public class SessionManagement {

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Gson gson;




    public SessionManagement(Context context) {

        this.sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor=sharedpreferences.edit();
        gson=new Gson();

    }


    public void setUserDetails(User user){
        String userjson=gson.toJson(user);
        editor.putString("User",userjson);
        editor.commit();
    }

    public User UserDetails(){
        String userjson= sharedpreferences.getString("User","a");
        User usermodel;
        if(!userjson.equals("a")){
            usermodel=gson.fromJson(userjson,User.class);

        }else return null;

        return usermodel;
    }






}
