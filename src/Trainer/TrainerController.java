package Trainer;

import java.util.ArrayList;

public class TrainerController {
    private ArrayList<Trainer> trainers;
    private ArrayList<Trainer> results;

    public boolean addTrainer(Trainer t) {
        return trainers.add(t);
    }
}
