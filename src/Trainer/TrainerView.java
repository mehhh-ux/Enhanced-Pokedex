package Trainer;

import java.time.LocalDate;
import java.util.Scanner;

public class TrainerView {
    private Scanner scanner = new Scanner(System.in);
    private int trainerId;
    private String name, sex, hometown, description;
    private LocalDate birthDate;

    public Trainer promptTrainerData(){
        System.out.print("Trainer ID: ");
        trainerId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Name: ");
        name = scanner.nextLine();
        System.out.print("Sex: ");
        sex = scanner.nextLine();
        System.out.print("Hometown: ");
        hometown = scanner.nextLine();
        System.out.print("Description: ");
        description = scanner.nextLine();
        System.out.print("Birthdate (yyyy-mm-dd): ");
        birthDate = LocalDate.parse(scanner.nextLine());

        return new Trainer(trainerId, name, sex, hometown, description, birthDate);
    }

    public void successfulTrainerAddMessage(String name){
        System.out.println("Successfully added " + name + "!");
    }
}
