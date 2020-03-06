package edu.ucsd.cse110.walkwalkrevolution.user.invite;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
    @Override
    public void addInvite(Invitation invite){
        invites.add(invite.toMap()).addOnFailureListener(error -> {
            Log.e(TAG, error.getLocalizedMessage());
        });
    }

    //TODO: Confirm the invite on the receiver's end, then delete from the database.
    @Override
    public void confirmInvite(Invitation invite){
        invite.acceptInvite();
        //Delete the invite from the database.
    }

    @Override
    public Invitation getInvite(){ return null; }

}
