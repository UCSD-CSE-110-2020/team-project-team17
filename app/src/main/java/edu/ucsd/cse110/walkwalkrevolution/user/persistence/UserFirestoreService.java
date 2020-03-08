package edu.ucsd.cse110.walkwalkrevolution.user.persistence;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.team.Team;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.invite.Invitation;

public class UserFirestoreService implements UserService{

    private CollectionReference users;

    private final String TAG = "UserFirestoreService";
    private final String USER_KEY = "user";

    public UserFirestoreService() {
        users = FirebaseFirestore.getInstance().collection(USER_KEY);
    }

    @Override
    public void addUser(User user){
        users.document(user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        User temp = snapshotToUser(document);
                        user.setTeamId(document.getString(User.TEAM));
                    } else {
                        users.document(user.getEmail()).set(user.toMap()).addOnFailureListener(error -> {
                            Log.e(TAG, error.getLocalizedMessage());
                        });
                        user.setTeamId(document.getString(user.getEmail()));
                        Log.d(TAG, "Document does not exist!");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    @Override
    public void refresh(){
        users.document(WalkWalkRevolution.getUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User t = snapshotToUser(document);
                        WalkWalkRevolution.getUser().setTeamId(t.getTeamId());
                        Log.d(TAG, "Document exists!");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    @Override
    public void getTeam(Team t, User user){
        users.orderBy(User.NAME, Query.Direction.ASCENDING).whereEqualTo(User.TEAM, user.getTeamId())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()){
                    User user = snapshotToUser(documentSnapshot);
                    Log.d(TAG, user.getName());
                    t.addUser(user);
                }
                t.notifyObservers();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
        });
    }

    private User snapshotToUser(DocumentSnapshot documentSnapshot){
        User user = new User(-1, -1);
        Log.d(TAG, documentSnapshot.getString(User.NAME));
        Log.d(TAG, documentSnapshot.getString(User.EMAIL));
        Log.d(TAG, documentSnapshot.getString(User.TEAM));
        user.setName(documentSnapshot.getString(User.NAME));
        user.setEmail(documentSnapshot.getString(User.EMAIL));
        if(documentSnapshot.contains(User.TEAM)){
            user.setTeamId(documentSnapshot.getString(User.TEAM));
        }
        return user;
    }

    //Added for functionality with Invitations --Justin
    //Assumed to work if users is arranged by email.
    @Override
    public void getReceiver(Invitation invitation, String userEmail){
        users.document(userEmail).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    User user = snapshotToUser(documentSnapshot);
                    Log.d(TAG, "User retrieved: " + user.getName() + ": " + user.getEmail());
                    invitation.setReceiver(user.getEmail());
                    WalkWalkRevolution.getInvitationService().addInvite(invitation);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getSender(Invitation invitation, String userEmail){
        users.document(userEmail).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    User user = snapshotToUser(documentSnapshot);
                    Log.d(TAG, "User retrieved: " + user.getName() + ": " + user.getEmail());
                    invitation.setSenderEmail(user.getEmail());
                    invitation.setSenderName(user.getName());
                    WalkWalkRevolution.getInvitationService().addInvite(invitation);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
        });
    }

}
