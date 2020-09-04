package worthywalk.example.com.worthywalk.Models;

import java.io.Serializable;

/**
 * Created by moazzam on 10/14/2018.
 */

public class leaderinfo implements Serializable{
    public String name;
    public String distance;
    String ids;
    public int pos;
    public leaderinfo(String name, String distance, String ids,int pos) {
        this.name = name;
        this.distance = distance;
        this.ids = ids;

        this.pos=pos;
    }

    public leaderinfo() {

    }
}
