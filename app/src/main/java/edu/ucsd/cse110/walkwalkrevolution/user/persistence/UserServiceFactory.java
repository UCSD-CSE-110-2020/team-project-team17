package edu.ucsd.cse110.walkwalkrevolution.user.persistence;

public class UserServiceFactory {

    public UserService createUserService(){
        return new UserFirestoreService();
    }

}
