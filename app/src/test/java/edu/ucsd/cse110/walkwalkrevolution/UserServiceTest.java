package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteService;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserService;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserServiceFactory;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class UserServiceTest {

    RouteService routeService;
    RouteServiceFactory routeServiceFactory;
    UserService userService;
    UserServiceFactory userServiceFactory;
    GoogleSigninClientFactory gscf;

    Intent intent;

    @Before
    public void setup(){
        intent = new Intent(ApplicationProvider.getApplicationContext(), DummyActivity.class);
        routeService = Mockito.mock(RouteService.class);
        routeServiceFactory = Mockito.mock(RouteServiceFactory.class);
        userService = Mockito.mock(UserService.class);
        userServiceFactory = Mockito.mock(UserServiceFactory.class);
        gscf = Mockito.mock(GoogleSigninClientFactory.class);

        when(userServiceFactory.createUserService())
                .thenReturn(userService);

        when(routeServiceFactory.createRouteService())
                .thenReturn(routeService);

        WalkWalkRevolution.setRouteServiceFactory(routeServiceFactory);
        WalkWalkRevolution.setUserServiceFactory(userServiceFactory);
        WalkWalkRevolution.setGoogleSignInClientFactory(gscf);
    }

    @Test
    public void testSubscribe(){
        ActivityScenario<DummyActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            verify(userServiceFactory).createUserService();
        });
    }

}
