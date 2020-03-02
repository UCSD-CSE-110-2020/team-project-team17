package edu.ucsd.cse110.walkwalkrevolution;

import org.junit.Before;
import org.junit.Test;

import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.BaseUserDao;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.MockUserDao;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class BaseUserDaoTest {

    BaseUserDao dao;

    @Before
    public void setup() {
        WalkWalkRevolution.setUserDao(new MockUserDao());
        dao = WalkWalkRevolution.getUserDao();
    }

    @Test
    public void addValidRoute() {
        User actual = new User(1, 72, "", "");
        dao.addUser(actual);

        User persisted = dao.getUser(1);
        assertNotNull(persisted);
        assertEquals(actual.getId(), persisted.getId());
        assertEquals(actual.getHeight(), persisted.getHeight());
    }

}
