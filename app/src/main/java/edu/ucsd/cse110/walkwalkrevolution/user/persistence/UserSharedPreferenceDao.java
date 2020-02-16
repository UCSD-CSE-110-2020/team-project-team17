package edu.ucsd.cse110.walkwalkrevolution.user.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.UserUtils;

import static android.content.Context.MODE_PRIVATE;

public class UserSharedPreferenceDao implements BaseUserDao {

    private static final String SP_USER = "USER";
    public static final long USER_ID = 1;

    @Override
    public void addUser(User user) {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_USER, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        String jsonString;

        try {
            jsonString = UserUtils.serialize(user);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Route");
        }

        editor.putString(Long.toString(USER_ID), jsonString);

        editor.apply();
    }

    @Override
    public User getUser(long id){
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_USER, MODE_PRIVATE);

        String jsonString = sp.getString(Long.toString(USER_ID), "");

        if(jsonString == "") return null;

        try {
            return UserUtils.deserialize(jsonString);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Route JsonString");
        }
    }

}
