package Interfaces;

import java.util.ArrayList;
import java.io.IOException;

public interface Displayable<T> {
    void printHeader();
    void displayAttributes(T object);
    void printAll(ArrayList<T> lists, String key);
    String promptForSearchKey();
    void pressAnyKeyPrompt();
    void pressAnyKeyPromptForSearch(String key);
}
