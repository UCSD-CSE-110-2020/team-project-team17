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
    private final String TO = "to";
    private final String FROM = "from";

    public InvitationFirestoreService(){
        invites = FirebaseFirestore.getInstance()
                .collection(INVITATION_KEY);
    }

    //TODO: From invite sender's POV, allow them to make invite and send to database
    @Override
    public void addInvite(Invitation invite){
        invites.add(invite.toMap()).addOnFailureListener(error -> {
            Log.e(TAG, error.getLocalizedMessage());
        });
    }

    //TODO: From invite receiver's POV, allow them to accept/decline invite
    @Override
    public void confirmInvite(Invitation invite){
        WalkWalkRevolution.getUser().setTeamId(invite.getSenderTeamId());
    }

}
