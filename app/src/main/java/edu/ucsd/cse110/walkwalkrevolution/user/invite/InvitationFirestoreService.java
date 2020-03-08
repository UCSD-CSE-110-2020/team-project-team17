package edu.ucsd.cse110.walkwalkrevolution.user.invite;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.ucsd.cse110.walkwalkrevolution.TeamInvitationActivity;
import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class InvitationFirestoreService implements InvitationService {
    public static Invitation invite;
    private static String activeInviteId;

    private CollectionReference invites;
    private FirebaseFirestore db;

    private final String TAG = "InvitationFirestoreService";
    private final String INVITATION_KEY = "invitation";

    public InvitationFirestoreService(){
        db = FirebaseFirestore.getInstance();
        invites = FirebaseFirestore.getInstance()
                .collection(INVITATION_KEY);
    }


    //Add an invite to the database. Should only be called from the Sender's phone.
    public void addInvite(Invitation invite){
        invites.add(invite.toMap()).addOnFailureListener(error -> {
            Log.e(TAG, error.getLocalizedMessage());
        });
    }

    //Retrieve invitation sent to userEmail and display it to TeamInvitationActivity
    // TODO: Retrieve an invitation and display on the screen.
    @Override
    public void getInvite(String userEmail, TeamInvitationActivity act){
        invites.whereEqualTo(Invitation.TO, userEmail )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                DocumentReference docRef = db.collection(INVITATION_KEY).document((String) document.get(Invitation.TO));
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Log.d(TAG, "DocumentSnapshot data: ");

                                                invite = new Invitation(document.getData());
                                                act.displayInvitation(invite);
                                                activeInviteId = document.getId();

                                            } else {
                                                Log.d(TAG, "No such document");
                                            }
                                        } else {
                                            Log.d(TAG, "get failed with ", task.getException());
                                        }
                                    }
                                });
                            }
                        } else {
                            act.displayInvitation(null);
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        // Query invitation for invites send to this user.
        /*invites.orderBy(Invitation.TO, Query.Direction.ASCENDING).whereEqualTo(Invitation.TO, user.getEmail())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()){
                    Invitation invite = snapshotToInvitation(documentSnapshot);
                    Log.d(TAG, invite.getSender().toString() + "-->" + invite.getReceiver().toString());
                    invitations.addInvitation(invite);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
        });*/
    }


    //TODO: Test this.
    @Override
    public void deleteInvite(){
        invites.document(activeInviteId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        activeInviteId = "";
                        invite = null;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

//    private Invitation snapshotToInvitation(DocumentSnapshot documentSnapshot){
//        User sender = null;
//        User reciever = WalkWalkRevolution.getUser();
//        if(sender == null){
//            Log.e(TAG, "Could not generate sender data.");
//            return null;
//        }
//        Invitation invite = new Invitation(sender, reciever);
//        return invite;
//    }

}
