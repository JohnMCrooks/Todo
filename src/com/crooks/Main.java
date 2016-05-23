package com.crooks;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void createItem(Scanner scanner, ArrayList<ToDoItem> items){
        //Create new item
        System.out.println("Enter your ToDo Item");
        String text = scanner.nextLine();
        ToDoItem item = new ToDoItem(false, text);
        items.add(item);
    }

    public static void toggleItem(Scanner scanner, ArrayList<ToDoItem> items){
        //Toggle items state
        System.out.println("Enter Item number to toggle:\n");
        String numStr = scanner.nextLine();
        try {
            int num = Integer.valueOf(numStr);
            ToDoItem tempItem = items.get(num - 1);
            tempItem.isDone = !tempItem.isDone;
        }catch (NumberFormatException e){
            System.out.println("You must use a number!\n");
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("The number you selected isn't valid.\n");
        }
    }

    public static void listItems(ArrayList<ToDoItem> items) {
        //List items
        int i = 1;

        for (ToDoItem toDoItem: items) {
            String checkbox = "[ ]";
            if (toDoItem.isDone){
                checkbox = "[x]";
            }
            System.out.println(checkbox + " " + i + " " + toDoItem.text);
            i++;
        }
    }

    public static void main(String[] args) {

        ArrayList<ToDoItem> items = new ArrayList<ToDoItem>();
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("1: Create New Item");
            System.out.println("2: Toggle Item");
            System.out.println("3: List Items");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    createItem(scanner, items);
                    break;

                case "2":
                    toggleItem(scanner, items);
                    break;

                case "3":
                    listItems(items);
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
