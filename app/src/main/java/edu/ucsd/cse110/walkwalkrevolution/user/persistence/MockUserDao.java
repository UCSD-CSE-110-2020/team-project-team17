package edu.ucsd.cse110.walkwalkrevolution.user.persistence;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.UserUtils;

public class MockUserDao implements BaseUserDao {

    public Map<Long, String> persisted;

    public MockUserDao(){
        this.persisted = new HashMap<>();
    }

    @Override
    public void addUser(User user){
        try {
            persisted.put(user.getId(), UserUtils.serialize(user));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public User getUser(long userId){
        if(persisted.containsKey(userId)) {
            try {
                String jsonString = persisted.get(userId);
                User user = UserUtils.deserialize(jsonString);
                return user;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }

}
