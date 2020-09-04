package worthywalk.example.com.worthywalk.Utilities;

import android.content.Context;

import worthywalk.example.com.worthywalk.Models.User;

public class UserSingleton
{
    private static User user = null;

    private UserSingleton()
    {

    }

    public static User getUser()
    {
        if(user == null)
        {
            user = new User();
            return user;
        }
        else
        {
            return user;
        }
    }

    public static UserSingleton Instance= null;
    static SessionManagement sessionManagement;



    public static UserSingleton getInstance(Context context){
        if(Instance==null){
            Instance=new UserSingleton();
            sessionManagement=new SessionManagement(context);
            user=sessionManagement.UserDetails();

        }
        return Instance;
    }


    public static void setUser(User users){
        user=users;
    }

}
