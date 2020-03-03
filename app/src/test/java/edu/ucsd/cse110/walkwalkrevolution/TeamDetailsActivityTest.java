package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowLooper;


import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.fitness.Steps;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.MockRouteDao;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteService;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.MockUserDao;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserService;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserServiceFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.google.common.truth.Truth.assertThat;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(AndroidJUnit4.class)
@Config(sdk=28)
public class TeamDetailsActivityTest {

    TeamDetailsActivity teamDetailsActivity;
    RecyclerView rvUsers;

    RouteService routeService;
    RouteServiceFactory routeServiceFactory;
    UserService userService;
    UserServiceFactory userServiceFactory;

    @Before
    public void setUp() {
        routeService = Mockito.mock(RouteService.class);
        routeServiceFactory = Mockito.mock(RouteServiceFactory.class);
        userService = Mockito.mock(UserService.class);
        userServiceFactory = Mockito.mock(UserServiceFactory.class);

        when(userServiceFactory.createUserService())
                .thenReturn(userService);

        when(routeServiceFactory.createRouteService())
                .thenReturn(routeService);

        WalkWalkRevolution.setRouteServiceFactory(routeServiceFactory);
        WalkWalkRevolution.setUserServiceFactory(userServiceFactory);

        WalkWalkRevolution.createUserService();
        WalkWalkRevolution.createRouteService();

        Bundle bundle = new Bundle();
        bundle.putString("test", "");

        Intent intent = new Intent();
        intent.putExtras(bundle);

        TeamDetailsActivity teamDetailsActivity = Robolectric.buildActivity(TeamDetailsActivity.class, intent).create().get();
        rvUsers = teamDetailsActivity.findViewById(R.id.rvUsers);
    }


    @Test
    public void testNumberOfEntries(){
        assertThat(rvUsers.getAdapter().getItemCount() == 50);
    }

    @Test
    public void testCorrectEntries(){

    }

}
