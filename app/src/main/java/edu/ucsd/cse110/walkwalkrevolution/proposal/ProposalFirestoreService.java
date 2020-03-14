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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.ProposeScreenActivity;
import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteUtils;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteFirestoreService;
import edu.ucsd.cse110.walkwalkrevolution.user.User;


public class ProposalFirestoreService implements ProposalService {
    public static Route proposedRoute = null;
    public static String userProposed = "";
    public static Boolean scheduled = false;

    private CollectionReference proposals;
    private FirebaseFirestore db;

    private ProposalSubject psub;
    private ProposalObserver pob;


    String response;


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
    public void scheduleWalk(Route route, String teamId, String userId, String date) {
        String serialized;
        try {
            serialized = RouteUtils.serialize(route);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("route", serialized);
        data.put("teamId", teamId);
        data.put("userId", userId);
        data.put("scheduled", true);
        data.put("date", date);

        //data.putAll(route.getResponses());
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
                                    String date = (String) document.get("date");
                                    try {
                                        Log.d(TAG, ""+document.get("route"));
                                        proposedRoute = RouteUtils.deserialize((String) document.get("route"));
                                    } catch (Exception e) {
                                        throw new RuntimeException(e.getLocalizedMessage());
                                    }
                                    act.displayRouteDetail(proposedRoute, date);
                                }
                                else {
                                    userProposed = "";
                                    proposedRoute = null;
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
    public void addProposal(Route route, String teamId, String userId, Date date) {
        String serialized;
        try {
            serialized = RouteUtils.serialize(route);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("route", serialized);
        data.put("teamId", teamId);
        data.put("userId", userId);
        data.put("scheduled", false);
        data.put("date", new SimpleDateFormat("yyyy/MM/dd hh:mm a").format(date));

        psub.listen();
        //data.putAll(route.getResponses());
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
    public void editProposal(Route route, String teamId, String userId, String date, ProposeScreenActivity act) {
        String serialized;
        try {
            serialized = RouteUtils.serialize(route);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        Map<String, Object> data = new HashMap<>();
        data.put("route", serialized);
        data.put("teamId", teamId);
        data.put("userId", userId);
        data.put("scheduled", this.scheduled);
        data.put("date", date);

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
                                                    act.updateScreen();
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
    public String getResponse(String teamId, String userId) {
        proposals.whereEqualTo("teamId", teamId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document: task.getResult()) {
                                if (document.exists()) {
                                    response = document.getString(userId);
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
