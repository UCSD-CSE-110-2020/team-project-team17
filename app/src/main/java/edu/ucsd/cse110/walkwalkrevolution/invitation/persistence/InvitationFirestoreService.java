package edu.ucsd.cse110.walkwalkrevolution.invitation.persistence;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.invitation.Invitation;
import edu.ucsd.cse110.walkwalkrevolution.invitation.Invitations;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserFirestoreService;

public class InvitationFirestoreService implements InvitationService {

    public CollectionReference invitations;

    private final String TAG = "InvitationFirestoreService";
    private final String INVITATION_KEY = "invitation";

    public InvitationFirestoreService(){
        invitations = FirebaseFirestore.getInstance().collection(INVITATION_KEY);
    }

    @Override
    public void addInvitation(Invitation invitation){
        Log.d(TAG, "Adding invitation");
        ((UserFirestoreService)WalkWalkRevolution.getUserService()).users
                .whereEqualTo(User.EMAIL, invitation.getTo()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().size() == 0){
                        Log.d(TAG, "No User Found");
                        Toast.makeText(WalkWalkRevolution.getContext(), "Invitation Failed: Invalid Email",
                                Toast.LENGTH_LONG).show();
                    } else {
                        invitations.add(invitation.toMap()).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "Invitation Sent");
                                Toast.makeText(WalkWalkRevolution.getContext(), "Invitation Sent",
                                        Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(error -> {
                            Log.e(TAG, error.getLocalizedMessage());
                        });
                    }
                }
            }
        });
    }

    @Override
    public void getInvitations(Invitations t, User user){
        invitations.whereEqualTo(Invitation.TO, user.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()) {
                        Map<String, Object> data = document.getData();
                        Log.d(TAG, document.getId() + "=>" + data);
                        Invitation invitation = snapshotToInvite(document);
                        Log.d(TAG, document.getId() + "=>" + invitation.toMap());
                        invitation.setFirestoreId(document.getId());
                        t.add(invitation);
                    }
                    t.notifyObservers();
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    @Override
    public void acceptInvite(Invitation invitation){
        ((UserFirestoreService)WalkWalkRevolution.getUserService())
                .users.document(invitation.getFrom()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String teamId = document.getString(User.TEAM);
                    Log.d(TAG, "teamId: " + teamId);
                    Map<String, String> data = WalkWalkRevolution.getUser().toMap();
                    data.put(User.TEAM, teamId);
                    ((UserFirestoreService)WalkWalkRevolution.getUserService())
                            .users.document(WalkWalkRevolution.getUser().getEmail()).set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    WalkWalkRevolution.getUser().setTeamId(teamId);
                                    deleteInvite(invitation);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e(TAG, e.getLocalizedMessage());
                                }
                            });
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    @Override
    public void deleteInvite(Invitation invitation){
        invitations.document(invitation.getFirestoreId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                });
    }

    private Invitation snapshotToInvite(DocumentSnapshot documentSnapshot){
        String to = documentSnapshot.getString(Invitation.TO);
        String from = documentSnapshot.getString(Invitation.FROM);
        String sender = documentSnapshot.getString(Invitation.SENDER);
        return new Invitation(to, from, sender);
    }

}
