//package Trainer;
//
//import Pokemon.PokemonView;
//import Pokemon.Pokemon;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class TrainerView {
//    private Scanner scanner = new Scanner(System.in);
//    private int trainerId;
//    private String name, sex, hometown, description;
//    private LocalDate birthDate;
//    private PokemonView pokemonView = new PokemonView();
//
//    public Trainer promptTrainerData(){
//        System.out.print("Trainer ID: ");
//        trainerId = scanner.nextInt();
//        scanner.nextLine();
//        System.out.print("Name: ");
//        name = scanner.nextLine();
//        System.out.print("Sex: ");
//        sex = scanner.nextLine();
//        System.out.print("Hometown: ");
//        hometown = scanner.nextLine();
//        System.out.print("Description: ");
//        description = scanner.nextLine();
//        System.out.print("Birthdate (yyyy-mm-dd): ");
//        birthDate = LocalDate.parse(scanner.nextLine());
//
//        return new Trainer(trainerId, name, sex, hometown, description, birthDate);
//    }
//
//    public void successfulTrainerAddMessage(String name){
//        System.out.println("Successfully added " + name + "!");
//    }
//
//    public void displayAllTrainers(ArrayList<Trainer> trainers){
//        if (trainers.isEmpty()){
//            System.out.println();
//        }
//        for (Trainer t: trainers){
//            System.out.println("\n======================================================");
//            System.out.println("Trainer: " + t.getName() + " (ID: " + t.getTrainerId() + ")");
//            System.out.println("Sex: " + t.getSex());
//            System.out.println("Hometown: " + t.getHometown());
//            System.out.println("Description: " + t.getDescription());
//            System.out.println("Money: " + t.getMoney());
//
//            System.out.println("\n>>Active Pokemon Lineup: ");
//            if (t.getLineup().isEmpty()){
//                System.out.println("No pokemon in the active lineup.");
//            }
//            else{
//                pokemonView.printHeader();
//                for (Pokemon p: t.getLineup()){
//                    pokemonView.displayAttributes(p);
//                }
//            }
//
//            System.out.println("\n>>Stored Pokemon: ");
//            if (t.getStorage().isEmpty()){
//                System.out.println("No pokemon in the storage.");
//            }
//            else{
//                pokemonView.printHeader();
//                for (Pokemon p: t.getStorage()){
//                    pokemonView.displayAttributes(p);
//                }
//            }
//
//            System.out.println("\n>>Items: ");
//            if (t.getItems().isEmpty()){
//                System.out.println("This trainer currently has no item on hand.");
//            }
//        }
//    }
//
//    public String promptForSearchKey() {
//        System.out.println("\nYou are in the process of searching a trainer!");
//        System.out.println("---------------------------------------------");
//        System.out.println("Format in searching for birthdate: yyyy-mm-dd");
//        System.out.print("Enter a search keyword: ");
//        return scanner.nextLine();
//    }
//
//    public void pressAnyKeyPrompt(){
//        System.out.print("Displayed all trainer/s available in the Pokedex.\nPress Enter to continue...");
//        scanner.nextLine();
//    }
//
//    public void pressAnyKeyPromptForSearch(String key){
//        System.out.print("Displayed all trainer/s containing the word/number '" + key + "' in the Pokedex.\nPress Enter to continue...");
//        scanner.nextLine();
//    }
//}