package edu.ucsd.cse110.walkwalkrevolution.fitness;

import java.time.LocalDateTime;

//From Lab4
public interface FitnessService {
    int getRequestCode();
    void setup();
    void updateStepCount();
}
