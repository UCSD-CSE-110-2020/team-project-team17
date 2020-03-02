package edu.ucsd.cse110.walkwalkrevolution.user.persistence;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class UserFirestoreService implements UserService{

    private CollectionReference users;

    private final String TAG = "UserFirestoreService";
    private final String USER_KEY = "user";

    public UserFirestoreService() {
        users = FirebaseFirestore.getInstance().collection(USER_KEY);
    }

    @Override
    public void addUser(User user){
        if(getUser(user.getEmail()) == null)
            users.document(user.getEmail()).set(user.toMap());
    }

    @Override
    public User getUser(String email){
        List<User> u = new ArrayList<>();
        users.document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User route = snapshotToUser(documentSnapshot);
                u.add(route);
            }
        });
        return u.isEmpty() ? null : u.get(0);
    }

    private User snapshotToUser(DocumentSnapshot documentSnapshot){
        User user = new User(-1, -1);
        user.setName(documentSnapshot.getString(User.NAME));
        user.setEmail(documentSnapshot.getString(User.EMAIL));
        return user;
    }

}
