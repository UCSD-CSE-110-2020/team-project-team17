package edu.ucsd.cse110.walkwalkrevolution.route.persistence;

import android.app.Activity;
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
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteUtils;
import edu.ucsd.cse110.walkwalkrevolution.route.Routes;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public class RouteFirestoreService implements RouteService {

    public CollectionReference routes;

    private final String TAG = "RouteFirestoreService";
    private final String ROUTE_KEY = "route";

    public RouteFirestoreService(){
        routes = FirebaseFirestore.getInstance()
                .collection(ROUTE_KEY);
    }

    @Override
    public void addRoute(Activity activity, Route route) {
        routes.add(route.toMap()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    route.setFirestoreId(task.getResult().getId());
                    WalkWalkRevolution.getRouteDao().addRoute(route);
                    Log.d(TAG, WalkWalkRevolution.getRouteDao().getRoute(route.getId()).getFirestoreId());
                    activity.finish();
                } else {
                    Log.e(TAG, task.getException().getLocalizedMessage());
                }
                }
        });
    }

    @Override
    public void updateRoute(Route route) {
        Log.d(TAG, route.getFirestoreId());
        Map<String, Object> data = route.toMap();
        routes.document(route.getFirestoreId()).set(data).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
        });
    }

    public static Route snapshotToRoute(DocumentSnapshot documentSnapshot){
//        Route.Builder builder = new Route.Builder();
//        if(documentSnapshot.contains(Route.TITLE)){
//            builder.setTitle(documentSnapshot.getString(Route.TITLE));
//        }
//        if(documentSnapshot.contains(Route.LOCATION)){
//            builder.setLocation(documentSnapshot.getString(Route.LOCATION));
//        }
//        if(documentSnapshot.contains(Route.NOTES)){
//            builder.setNotes(documentSnapshot.getString(Route.NOTES));
//        }
//        if(documentSnapshot.contains(Route.DESCRIPTION_TAGS)){
//            builder.setDescription(documentSnapshot.getString(Route.DESCRIPTION_TAGS));
//        }
//        if(documentSnapshot.contains(Route.USER_ID)){
//            builder.setUserId(documentSnapshot.getString(Route.USER_ID));
//        }
        Route route;
        try {
            route = RouteUtils.deserialize(documentSnapshot.getString(Route.ROUTE));
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        route.setUserId(documentSnapshot.getString(Route.USER_ID));
        route.setFirestoreId(documentSnapshot.getId());
        return route;
    }

    @Override
    public void getRoutes(Routes rList, User user) {
        Log.d(TAG, "Getting Routes for " +user.getEmail());
        routes.orderBy(Route.TITLE, Query.Direction.ASCENDING).whereEqualTo(Route.USER_ID, user.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for(QueryDocumentSnapshot document: task.getResult()) {
                        Log.d(TAG, user.getEmail() +":"+document.getString(Route.ROUTE));
                        Map<String, Object> data = document.getData();
                        Log.d(TAG, document.getId() + "=>" + data);
                        Route route = snapshotToRoute(document);
                        Log.d(TAG, document.getId() + "=>" + route.toMap());
                        rList.add(route);
                    }
                    rList.notifyObservers();
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

}
