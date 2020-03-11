package edu.ucsd.cse110.walkwalkrevolution.proposal;

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
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.ProposeScreenActivity;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteFirestoreService;
import edu.ucsd.cse110.walkwalkrevolution.user.User;


public class ProposalFirestoreService implements ProposalService {
    public static Route proposedRoute = null;
    public static String userProposed = "";
    public static Boolean scheduled = false;

    private CollectionReference proposals;
    private FirebaseFirestore db;
<<<<<<< HEAD
    private ProposalSubject psub;
    private ProposalObserver pob;
=======

    String response;

>>>>>>> feature/respond_proposal
    private final String TAG = "ProposalFirestoreService";
    private final String PROPOSAL_KEY = "proposal";
    private final String ROUTE_KEY = "route";

    public ProposalFirestoreService(){
        db = FirebaseFirestore.getInstance();
        proposals = db.collection(PROPOSAL_KEY);
        psub = new ProposalSubject();
    }

    public ProposalSubject getSub() {
        return psub;
    }

    @Override
    public void scheduleWalk(Route route, String teamId, String userId) {
        Map<String, Object> data = new HashMap<>();
        data.put("route", route.toMap());
        data.put("teamId", teamId);
        data.put("userId", userId);
        data.put("scheduled", true);
        proposals.whereEqualTo("teamId", teamId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    proposals.document(document.getId())
                                            .set(data)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully written! Scheduled");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error writing document", e);
                                                }
                                            });
                                }
                                else {
                                    Log.d(TAG, "No such document");
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void getProposalRoute(String teamId, ProposeScreenActivity act) {
       proposals.whereEqualTo("teamId", teamId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            pob = new ProposalObserver(act);
                            psub.addObserver(pob);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    scheduled = (Boolean) document.get("scheduled");
                                    userProposed = (String) document.get("userId");
                                    proposedRoute = new Route((Map<String, Object>) document.get("route"));
                                    act.displayRouteDetail(proposedRoute);
                                }
                                else {
                                    Log.d(TAG, "No such document");
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void addProposal(Route route, String teamId, String userId) {
        Map<String, Object> data = new HashMap<>();
        data.put("route", route.toMap());
        data.put("teamId", teamId);
        data.put("userId", userId);
        data.put("scheduled", false);
        psub.listen();
        data.putAll(route.getResponses());
        proposals.add(data)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                psub.notifyObs();
                psub.unListen();
            }
        })
                .addOnFailureListener(error -> {
            Log.e(TAG, error.getLocalizedMessage());
        });
    }



    @Override
    public void editProposal(Route route, String teamId, String userId, boolean scheduled) {
        proposals.whereEqualTo("teamId", teamId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    Map<String, Object> data = new HashMap<>();
                                    data.putAll(route.getResponses());
                                    data.put("scheduled", scheduled);
                                    proposals.document(document.getId()).set(data, SetOptions.merge())
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully edited!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error editing document", e);
                                                }
                                            });
                                } else {
                                    Log.d(TAG, "No such document");
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    @Override
    public void withdrawProposal(String teamId, ProposeScreenActivity act) {
        Log.d(TAG,  "dc");
        proposals.whereEqualTo("teamId", teamId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    Log.d("MONKEYS", "dc");
                                    proposals.document(document.getId())
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                    proposedRoute = null;
                                                    userProposed = "";
                                                    act.renderPage();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error deleting document", e);
                                                }
                                            });
                                }
                                else {
                                    Log.d("MONKEYS", "noiooo");
                                    Log.d(TAG, "No such document");
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public String getResponse(String teamId, String userEmail) {
        proposals.whereEqualTo("teamId", teamId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document: task.getResult()) {
                                if (document.exists()) {
                                    response = document.getString(userEmail);
                                }
                                else {
                                    Log.d(TAG, "No such document");
                                }
                            }
                        }
                        else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        return response;
    }

}
