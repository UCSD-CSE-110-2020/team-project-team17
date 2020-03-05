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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteFirestoreService;
import edu.ucsd.cse110.walkwalkrevolution.user.User;


public class ProposalFirestoreService implements ProposalService {

    private CollectionReference proposals;
    private FirebaseFirestore db;

    private final String TAG = "ProposalFirestoreService";
    private final String PROPOSAL_KEY = "proposal";
    private final String ROUTE_KEY = "route";

    public ProposalFirestoreService(){
        db = FirebaseFirestore.getInstance();
        proposals = db.collection(PROPOSAL_KEY);
    }

    @Override
    public void addProposal(String routeId, String userId) {
        CollectionReference routes = db.collection(ROUTE_KEY);
        Map<String, Object> data = new HashMap<>();
        data.put("routeId", routeId);
        data.put("teamId", userId);
        proposals.add(data)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
            }
        })
                .addOnFailureListener(error -> {
            Log.e(TAG, error.getLocalizedMessage());
        });
    }

    //TODO: FIX
 //   @Override
//    public Route getProposal(User user) {
//        String teamId = user.
//   getTeamId();
//        String routeId = "";
//        Query query = proposals.whereEqualTo("teamId", teamId);
//        query.get()
//        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d(TAG, document.getId() + " => " + document.getData());
//                    }
//                } else {
//                    Log.d(TAG, "Error getting documents: ", task.getException());
//                }
//            }
//        });
//
//    }

//    private Route snapshotToRoute(DocumentSnapshot documentSnapshot){
//        Route.Builder builder = new Route.Builder();
//        if(documentSnapshot.contains(Route.TITLE)){
//            builder.setTitle(documentSnapshot.getString(Route.TITLE));
//        }
//        if(documentSnapshot.contains(Route.LOCATION)){
//            builder.setTitle(documentSnapshot.getString(Route.LOCATION));
//        }
//        if(documentSnapshot.contains(Route.NOTES)){
//            builder.setTitle(documentSnapshot.getString(Route.NOTES));
//        }
//        if(documentSnapshot.contains(Route.DESCRIPTION_TAGS)){
//            builder.setTitle(documentSnapshot.getString(Route.DESCRIPTION_TAGS));
//        }
//        if(documentSnapshot.contains(Route.USER_ID)){
//            builder.setUserId(documentSnapshot.getString(Route.USER_ID));
//        }
//        return builder.build();
//    }

}
