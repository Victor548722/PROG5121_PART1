/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package prog1a.chatapp;

import java.util.Scanner;

/**
 *
 * @author victor
 */
public class ChatApp {//1

    public static void main(String[] args) {//2
        Scanner scanner = new Scanner(System.in);
// instantiating auth as an object of the log in class
        Login auth = new Login();            
            
//variables to store registration data, reserving a spot for these variables
        String regUser = "", regPass = "", fName = "", lName = "", cell = "";
        boolean isRegistered = false;

        System.out.println("Welcome to RChat");

        while (true) {//3
            System.out.println("\n ---Main Menu---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.println("Please choose option from option 1 to 3");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {//4
                System.out.println("Please enter your first name.");
                fName = scanner.nextLine();
                System.out.println("Please enter your last name.");
                lName = scanner.nextLine();

                // our system.out.println will be long because it is instructive with regards to the username requirements
                while (true) {//5
                    System.out.println("Please create a Username with five or less characters as well as an underscore.");
                    regUser = scanner.nextLine();

                    if (auth.checkUserName(regUser)) {//6
                        System.out.println("Username successfully captured.");
                        break;//6 below brace ends 6
                    } else {//7
                        System.out.println("Username format incorrect.");
                    }//7
                }//5
                //if the above requirments are correct when creating a username we can proceed 
                while (true) {//8
                    System.out.println("Please create a password.");
                    regPass = scanner.nextLine();
                    if (auth.checkPasswordComplexity(regPass)) {//9
                        System.out.println("Password successfully captured!");
                        break;//9 below brace before else ends 9
                    } else {//10
                        System.out.println("Password is not correctly formatted.");// added in when neatening up code
                        System.out.println("Password must be 8 or more characters.");
                        System.out.println("Password must contain a Capital letter.");
                        System.out.println("Password must contain a number.");
                        System.out.println("Password must contain a special character.");
                    }//10
                }//8
                // prompting the user to give their cell no and if incorrect telling them what format to use
                while (true) {//11
                    System.out.println("Please enter South African Cell number starting with +27");
                    cell = scanner.nextLine();

                    if (auth.checkCellPhoneNumber(cell)) {//12
                        System.out.println("Cell phone number successfully added.");
                        break;//12 below curly brace ends 12
                    } else {//13
                        System.out.println("Invalid cell phone number, please enter +27xx xxx xxxx");
                        System.out.println("Please use international code +27 followed by 9 digit cell phone number.");
                    }//13

                }//11
                isRegistered = true;
            }//4
            // here we saying they can log in if choice = 2 and prompting them to register if they have not done so
            else if (choice.equals("2")) {//14 this is an else if 
                if (!isRegistered) {//15
                    System.out.println("Please register first, select option 1.");
                }//15
                else {//16
                    System.out.println("\n--- LOGIN ---");
                    System.out.print("Username: ");
                    String logUser = scanner.nextLine();
                    System.out.print("Password: ");
                    String logPass = scanner.nextLine();

                    boolean success = auth.loginUser(regUser, regPass,
                            logUser, logPass);
                    System.out.println("\n"
                            + auth.returnLoginStatus(success, fName, lName));
                }//16
            }//14
            //option 3 to exit
            else if (choice.equals("3")) { // 17
                System.out.println("Goodbye from RChat!");
                break;
            }//17
            else {//18 catch all
                System.out.println("Invalid choice. Enter 1 to 3.");
            }//18
        }//3
        
        scanner.close();
    }//2
}//1
