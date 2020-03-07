package edu.ucsd.cse110.walkwalkrevolution.user.invite;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class InvitationFirestoreService implements InvitationService {

    private CollectionReference invites;

    private final String TAG = "InvitationFirestoreService";
    private final String INVITATION_KEY = "invitation";

    public InvitationFirestoreService(){
        invites = FirebaseFirestore.getInstance()
                .collection(INVITATION_KEY);
    }


    //Add an invite to the database. Should only be called from the Sender's phone.
    public void addInvite(Invitation invite){
        invites.add(invite.toMap()).addOnFailureListener(error -> {
            Log.e(TAG, error.getLocalizedMessage());
        });
    }

    @Override
    public Invitation getInvite(User user){
        ArrayList<Invitation> inv = new ArrayList<Invitation>();
        invites.orderBy(Invitation.TO, Query.Direction.ASCENDING).whereEqualTo(Invitation.TO, user.getEmail())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()){
                    Invitation invite = snapshotToInvitation(documentSnapshot);
                    Log.d(TAG, invite.getSender().toString() + "-->" + invite.getReceiver().toString());
                    inv.add(invite);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
        });
        return inv.isEmpty() ? null : inv.get(0);
    }


    //TODO: Confirm the invite on the receiver's end, then delete from the database.
    @Override
    public void confirmInvite(Invitation invite){
        invite.acceptInvite();
        //Delete the invite from the database.
    }

    private Invitation snapshotToInvitation(DocumentSnapshot documentSnapshot){
        User sender = null; //TODO: Retrieve sender
        User reciever = null; //TODO: Retrieve reciever
        Invitation invite = new Invitation(sender, reciever);
        return invite;
    }

}
