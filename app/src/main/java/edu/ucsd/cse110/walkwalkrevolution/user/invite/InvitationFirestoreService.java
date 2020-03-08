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

import edu.ucsd.cse110.walkwalkrevolution.TeamInvitationActivity;

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

                                invite = new Invitation(document.getData());
                                act.displayInvitation(invite);
                                activeInviteId = document.getId();
                                return;
                            }
                            act.displayInvitation(null);
                        } else {
                            act.displayInvitation(null);
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

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
}
