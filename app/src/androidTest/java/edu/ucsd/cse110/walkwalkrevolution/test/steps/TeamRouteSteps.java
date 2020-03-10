package edu.ucsd.cse110.walkwalkrevolution.test.steps;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ucsd.cse110.walkwalkrevolution.RoutesActivity;
import edu.ucsd.cse110.walkwalkrevolution.R;
import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.EmptyActivity;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteRecycleView.RoutesAdapter;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.MockRouteDao;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteService;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserService;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserServiceFactory;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class TeamRouteSteps {

    private ActivityTestRule<RoutesActivity> mActivityTestRule = new ActivityTestRule<>(RoutesActivity
            .class);

    private Map<String, String> nameIdMap = new HashMap<>();

    private RouteService routeService;
    private RouteServiceFactory routeServiceFactory;
    private UserService userService;
    private UserServiceFactory userServiceFactory;

    private User self;

    public TeamRouteSteps() {
    }

    @Before
    public void setup() {
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


        self = new User();
        self.setEmail("dummyEmail");

        WalkWalkRevolution.setRouteDao(new MockRouteDao());
        WalkWalkRevolution.setUser(self);


        Intents.init();
        nameIdMap.put("team", "team_button");
    }

    @After
    public void tearDown() {
        mActivityTestRule.getActivity().finish();
        Intents.release();
    }

    @Given("a routes activity")
    public void aMainActivity() {
        System.out.println("STARTING ROUTEACTIVITY");
        mActivityTestRule.launchActivity(null);
        assertThat(mActivityTestRule.getActivity(), notNullValue());
    }

    public static int getLayoutIdFromString(String resName) {
        try {
            Field idField = R.id.class.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @When("^the user enters (\\d+) in the (.*) text field$")
    public void theUserEntersANumberInTheEdittextFieldWithId(int number, String id) throws Throwable {
        int layoutId = getLayoutIdFromString(nameIdMap.get(id));
        onView(withId(layoutId))
                .check(matches(isDisplayed()))
                .perform(typeText("" + number));
    }

    @And("^there's a route with the title (.*)")
    public void thereExistsARoute(String title) throws Throwable {
        Route route = new Route(title, new EmptyActivity());
        route.setUserId("xxxx");
        route.setFirestoreId(title);

        User user = new User(1,1,"a","b");

        doAnswer(invocation -> {
            RecyclerView recyclerView = (RecyclerView)
                    mActivityTestRule.getActivity().findViewById(R.id.routes);
            ((RoutesAdapter)recyclerView.getAdapter()).getRoutes().update(Arrays.asList(user, self));
            return null;
        }).when(userService).getTeam(any(), any());
        doAnswer(invocation -> {
            RecyclerView recyclerView = (RecyclerView)
                    mActivityTestRule.getActivity().findViewById(R.id.routes);
            ((RoutesAdapter)recyclerView.getAdapter()).getRoutes().add(route);
            ((RoutesAdapter)recyclerView.getAdapter()).getRoutes().notifyObservers();
            return null;
        }).when(routeService).getRoutes(any(), any());
    }

    @And("^the list contains a route with (\\d+) steps")
    public void containsRouteWithSteps(int steps){
        assertNotEquals(0, getRVCount());
        onView(withId(R.id.routes)).check(matches(hasDescendant(withText(Integer.toString(steps)))));
    }

    @And("^the user walked (\\d+) steps with (.*)")
    public void recordOwnStep(int steps, String id){
        Activity activity = new Walk();
        activity.setDetail(Walk.STEP_COUNT, Integer.toString(steps));
        System.out.println(id);
        Route r = new Route(id, activity);
        r.setFirestoreId(id);
        WalkWalkRevolution.getRouteDao().addTeamRoute(r);

        RecyclerView recyclerView = (RecyclerView)
                mActivityTestRule.getActivity().findViewById(R.id.routes);
        ((RoutesAdapter)recyclerView.getAdapter()).getRoutes().updateOverridenRoutes();
        System.out.println(((RoutesAdapter)recyclerView.getAdapter()).getRoutes()
                .getOverridenRoutes().get(id).toMap());
    }

    @And("^the user clicks the (.*) button$")
    public void theUserClicksTheButton(String id) throws Throwable {
        int layoutId = getLayoutIdFromString(nameIdMap.get(id));
        onView(withId(layoutId))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    @Then("^the list contains a route with the title (.*)")
    public void containsRouteWithTitle(String title){
        assertNotEquals(0, getRVCount());
        onView(withId(R.id.routes)).check(matches(hasDescendant(withText(title))));
    }

//    @Then("^the answer is (\\d+)$")
//    public void theAnswerIs(int number) throws Throwable {
//        onView(withId(R.id.answer))
//                .check(matches(isDisplayed()))
//                .check(matches(withText(Integer.toString(number))));
//    }

    @Then("^the list is empty")
    public void listIsEmpty() throws Throwable {
        assertEquals(0, getRVCount());
    }

    private int getRVCount(){
        RecyclerView recyclerView = (RecyclerView)
                mActivityTestRule.getActivity().findViewById(R.id.routes);
        return recyclerView.getAdapter().getItemCount();
    }
}