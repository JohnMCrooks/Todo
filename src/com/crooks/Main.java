package com.crooks;

import org.h2.tools.Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void createItem(Scanner scanner, Connection conn) throws SQLException {
        //Create new item
        System.out.println("Enter your ToDo Item");
        String text = scanner.nextLine();
//        ToDoItem item = new ToDoItem(false, text);
//        items.add(item);
        insertTODO(conn, text);                             //Added SQL method isntead

    }

    public static void toggleItem(Scanner scanner, Connection conn) throws SQLException {
        //Toggle items state
        System.out.println("Enter Item number to toggle:\n");
        String numStr = scanner.nextLine();
        try {
            int num = Integer.valueOf(numStr);
//            ToDoItem tempItem = items.get(num - 1);
//            tempItem.isDone = !tempItem.isDone;
            toggleTODO(conn,num);                            //Added SQL method instead
        }catch (NumberFormatException e){
            System.out.println("You must use a number!\n");
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("The number you selected isn't valid.\n");
        }
    }

    public static void listItems(Connection conn) throws SQLException {
        //List items
//        int i = 1;
        ArrayList<ToDoItem> items = selectTODO(conn);   //uses SQL method to populate items list.
        for (ToDoItem toDoItem: items) {
            String checkbox = "[ ]";
            if (toDoItem.isDone){
                checkbox = "[x]";
            }
            System.out.printf("%s %s - %s\n", checkbox, toDoItem.id,toDoItem.text);
//            i++;
     }
    }

    public static void insertTODO(Connection conn, String text) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO todos VALUES(NULL, ?, FALSE)");
        stmt.setString(1, text);     //define first value and what you want it to be
        stmt.execute();              //execute task

    }

    public static ArrayList<ToDoItem> selectTODO(Connection conn) throws SQLException {      //Display all of the info in the table
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos");
        ResultSet results = stmt.executeQuery();
        ArrayList<ToDoItem> items = new ArrayList<>();
        while(results.next()){                              //Looping through the results and populating the Array List
            int id = results.getInt("id");
            String text = results.getString("text");
            boolean isDone = results.getBoolean("is_done");  //pulling all 3 item attributes
            ToDoItem item = new ToDoItem(id,isDone,text);    //creating an object
            items.add(item);                                //throwing object in array list
        }
        return items;                                       //   returning completed array list
    }

    public static void toggleTODO(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(" UPDATE todos SET is_done = NOT is_done WHERE id = ?");
        stmt.setInt(1,id);
        stmt.execute();
    }

    public static void main(String[] args) throws SQLException {

        Server.createWebServer().start();

        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        Statement stmt = conn.createStatement();

        stmt.execute("CREATE TABLE IF NOT EXISTS todos(id IDENTITY, text VARCHAR, is_done BOOLEAN)");  //Create table in database


        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("1: Create New Item");
            System.out.println("2: Toggle Item");
            System.out.println("3: List Items");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    createItem(scanner, conn);
                    break;

                case "2":
                    toggleItem(scanner, conn);
                    break;

                case "3":
                    listItems(conn);
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
