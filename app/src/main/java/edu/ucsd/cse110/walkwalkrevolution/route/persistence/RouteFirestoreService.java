package edu.ucsd.cse110.walkwalkrevolution.route.persistence;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class RouteFirestoreService implements RouteService {

    private CollectionReference routes;

    private final String TAG = "RouteFirestoreService";
    private final String ROUTE_KEY = "route";

    public RouteFirestoreService(){
        routes = FirebaseFirestore.getInstance()
                .collection(ROUTE_KEY);
    }

    @Override
    public void addRoute(Route route) {
        routes.add(route.toMap()).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(error -> {
            Log.e(TAG, error.getLocalizedMessage());
        });
    }

    //TODO: FIX W/ OBSERVER PATTERN (FIRESTORE IS ASYNC)
    @Override
    public Route getRoute(String routeId) {
        List<Route> r = new ArrayList<>();
        routes.document(routeId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Route route = snapshotToRoute(documentSnapshot);
                Log.d(TAG, "Route retrieved: " + route.getTitle());
                r.add(route);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
        });
        return r.isEmpty() ? null : r.get(0);
    }

    private Route snapshotToRoute(DocumentSnapshot documentSnapshot){
        Route.Builder builder = new Route.Builder();
        if(documentSnapshot.contains(Route.TITLE)){
            builder.setTitle(documentSnapshot.getString(Route.TITLE));
        }
        if(documentSnapshot.contains(Route.LOCATION)){
            builder.setTitle(documentSnapshot.getString(Route.LOCATION));
        }
        if(documentSnapshot.contains(Route.NOTES)){
            builder.setTitle(documentSnapshot.getString(Route.NOTES));
        }
        if(documentSnapshot.contains(Route.DESCRIPTION_TAGS)){
            builder.setTitle(documentSnapshot.getString(Route.DESCRIPTION_TAGS));
        }
        if(documentSnapshot.contains(Route.USER_ID)){
            builder.setUserId(documentSnapshot.getString(Route.USER_ID));
        }
        return builder.build();
    }

    //TODO: FIX W/ OBSERVER PATTERN (FIRESTORE IS ASYNC)
    @Override
    public List<Route> getRoutes(User user) {
        List<Route> r = new ArrayList<>();
        routes.orderBy(Route.TITLE, Query.Direction.ASCENDING).whereEqualTo(Route.USER_ID, user.getEmail())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()){
                    Route route = snapshotToRoute(documentSnapshot);
                    r.add(route);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
        });
        return r;
    }

}
