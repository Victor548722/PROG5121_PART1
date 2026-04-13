package prog1a.chatapp;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author victor
 */
public class User {
    // Fields to store user information
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    // Constructor to initialize a new User
    public User(String username, String password, 
                String fName, String lName) { // Start 2
        this.username = username;
        this.password = password;
        this.firstName = fName;
        this.lastName = lName;
    } // End 2

    // Getters to retrieve the information safely
    public String getUsername() { // Start 3
        return username;
    } // End 3

    public String getPassword() { // Start 4
        return password;
    } // End 4

    public String getFirstName() { // Start 5
        return firstName;
    } // End 5

    public String getLastName() { // Start 6
        return lastName;
    } // End 6
    
}
