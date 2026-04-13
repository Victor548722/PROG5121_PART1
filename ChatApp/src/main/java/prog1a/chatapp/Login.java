/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog1a.chatapp;

/**
 *
 * @author victor
 */
public class Login {

    public boolean checkUserName(String username) {
        //checking if the boolean contains "" and an underscore
        boolean hasUnderscore = username.contains("_");
        //checking if the if the length of the usernameis 5 characters or less
        boolean isCorrectLength = username.length() <= 5;
        //both of the above need to be correct/true for the username to be valid
        return hasUnderscore && isCorrectLength;
    }

    // Password requirement must be 8+ characters, have a capital letter, a number and a special character
    public boolean checkPasswordComplexity(String password) {
        //if it is to short then we return false immediatley 
        if (password.length() < 8) {
            return false;
        }
        boolean hasCap = false;
        boolean hasNum = false;
        boolean hasSpec = false;

        /* we analyse each character to of the password does it have a cap,num and special character. If all are true
        if all conditions are true the password will be captured successfully*/
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                //checking for a capital letter in the password and the code below is checking the other requirments 
                hasCap = true;
            } else if (Character.isDigit(c)) {
                hasNum = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpec = true;
            }
        }
        return hasCap && hasNum && hasSpec;
    }

    /* we are going to use a regular expression to check the format of the cell phone number. Capture characters inside
    inside this regular expression group so that we may use it later
    */
    public boolean checkCellPhoneNumber(String cellNumber) {
        String phonePatternRegex = "^\\+2[6-8]\\d{9}$";

        return cellNumber.matches(phonePatternRegex);
    }
    // we are going to compare login details entered against stored registration details
    public boolean loginUser(String storedUser, String storedPass,String loginUser, String loginPass){
        return storedUser.equals(loginUser)&& storedPass.equals(loginPass);
    }
    // will return a formatted success or failure message . String fname = firstname String lname = last name 
    public String returnLoginStatus(boolean isLoggedIn, String fName, String lName){
        if (isLoggedIn){
            return "Welcome " + fName + " " + lName + ", it is great to see you again!";
        }
        else {
            return "Username or password incorrect,please try again.";
        }
    }

}
