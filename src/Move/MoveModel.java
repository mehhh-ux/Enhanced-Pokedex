package Move;

public class MoveModel {
    private String name;
    private String description;
    private String classification;
    private String type1;
    private String type2;

    public MoveModel(String name, String description, String classification, String type1) {
        this.name = name;
        this.description = description;
        this.classification = classification;
        this.type1 = type1;
    }

    public MoveModel(String name, String description, String classification, String type1, String type2) {
        this(name, description, classification, type1);
        this.type2 = type2;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public String getClassification() {
        return classification;
    }
    public String getType1() {
        return type1;
    }
    public String getType2() {
        return type2;
    }

    public void printHeader() {
        System.out.printf("%-30s%-5s%-10s%-10s%-60s\n", "Name", "Class", "Type 1",
                "Type 2", "Description");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }

    public void displayMoveAttributes() {
        System.out.printf("%-30s%-5s%-10s%-10s%-70s\n",
                name, classification,
                type1, type2 != null ? type2 : "------", reduceDescription(60));
    }

    public String reduceDescription(int maxLength) {
        if (description.length() > maxLength) {
            return description.substring(0, maxLength - 3) + "...";
        } else {
            return description;
        }
    }
}
