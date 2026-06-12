/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package prog1a_part2.chatapp;

import java.util.Scanner;
import javax.swing.JOptionPane;

public class ChatApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // instantiating auth as an object of the log in class
        Login auth = new Login();
        Message msgEngine = new Message();
        //variables to store registration data, reserving a spot for these variables
        String regUser = "", regPass = "", fName = "", lName = "", cell = "";
        boolean isRegistered = false;

        System.out.println("Quick Chat");

        while (true) {
            System.out.println("\n ---Main Menu---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.println("Please choose option from option 1 to 3");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.println("Please enter your first name.");
                fName = scanner.nextLine();
                System.out.println("Please enter your last name.");
                lName = scanner.nextLine();
                // our system.out.println will be long because it is instructive with regards to the username requirements
                while (true) {
                    System.out.println("Please create a Username with five or less characters as well as an underscore.");
                    regUser = scanner.nextLine();
                    if (auth.checkUserName(regUser)) {
                        System.out.println("Username successfully captured.");
                        break;
                    } else {
                        System.out.println("Username format incorrect.");
                    }
                }
//if the above requirments are correct when creating a username we can proceed 
                while (true) {
                    System.out.println("Please create a password.");
                    regPass = scanner.nextLine();
                    if (auth.checkPasswordComplexity(regPass)) {
                        System.out.println("Password successfully captured!");
                        break;
                    } else {
                        System.out.println("Password is not correctly formatted.");
                    }
                }
                // prompting the user to give their cell no and if incorrect telling them what format to use
                while (true) {
                    System.out.println("Please enter South African Cell number starting with +27");
                    cell = scanner.nextLine();
                    if (auth.checkCellPhoneNumber(cell)) {
                        System.out.println("Cell phone number successfully added.");
                        break;
                    } else {
                        System.out.println("Invalid cell phone number format.");
                    }
                }
                isRegistered = true;
// here we saying they can log in if choice = 2 and prompting them to register if they have not done so
            } else if (choice.equals("2")) {
                if (!isRegistered) {
                    System.out.println("Please register first, select option 1.");
                } else {
                    System.out.println("\n--- LOGIN ---");
                    System.out.print("Username: ");
                    String logUser = scanner.nextLine();
                    System.out.print("Password: ");
                    String logPass = scanner.nextLine();
//11 setting the condtions for if part 2 of poe i.e. if reg user was successful then see below code note 
                    boolean success = auth.loginUser(regUser, regPass, logUser, logPass);
                    System.out.println("\n" + auth.returnLoginStatus(success, fName, lName));
//11 if the reg user was correct then we launch the QuickChatHub method call with msgEngine in its parameters
                    if (success) {
                        // Launch the required Main Application Menu
                        runQuickChatHub(msgEngine);
                    }
                }
            } else if (choice.equals("3")) {
                System.out.println("Goodbye from Quick Chat!");
                break;
            } else {
                System.out.println("Invalid choice. Enter 1 to 3.");
            }
        }
        scanner.close();
    }

    // this will display the numeric loop processing part and the display message for our app
    public static void runQuickChatHub(Message msgEngine) {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
// the flow of logic will happen based on what the user selects
        while (true) {
            String menuMsg = "--- QuickChat Hub Menu ---\n"
                    + "1) Send Messages\n"
                    + "2) Show recently sent messages\n"
                    + "3) Quit\n\n"
                    + "Enter choice (1-3):";

            String choiceInput = JOptionPane.showInputDialog(null, menuMsg);
//catch null and exit constants so that if user clicks x or cancel the code doesnt crash
            if (choiceInput == null || choiceInput.equals("3")) {
                break;
            }
//menu input tree where user makes there selection
            if (choiceInput.equals("1")) {
                processMessagePipeline(msgEngine);
            } else if (choiceInput.equals("2")) {
                JOptionPane.showMessageDialog(null, "Coming Soon.");
            } else {
                // incase user inputs a choice outside of the prompt ranges
                JOptionPane.showMessageDialog(null, "Invalid choice. Please select 1, 2, or 3.");
            }
        }
    }

    /**
     * here, we limit the number of iterations for message pipeline the user can
     * select from 1 to 10. it was not specified what would be the maximum
     * number of messages they can send we ask the user how many times they
     * would like the iteration to run
     */
    public static void processMessagePipeline(Message msgEngine) {
        String batchInput = JOptionPane.showInputDialog(null, "How many messages would you like to process?");
        if (batchInput == null || batchInput.trim().isEmpty()) {
            return;
        }

        int requestedCount;
        try {
            requestedCount = Integer.parseInt(batchInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number format.");
            return;
        }

        for (int i = 0; i < requestedCount; i++) {
            if (Message.currentMessageCount >= 10) {
                JOptionPane.showMessageDialog(null, "Memory Full! Maximum limit of 10 records reached.");
                break;
            }
//Sets the exact index location used to update all parallel array metrics
            int targetIdx = Message.currentMessageCount;

            // Generate a  valid, random 10-digit ID
            String tempID = Message.generateRandomID();
            //Continuously verifies that the generated random ID complies with constraints
            while (!msgEngine.checkMessageID(tempID)) {
                tempID = Message.generateRandomID();
            }

            Message.messageID[targetIdx] = tempID;
            Message.messageNumber[targetIdx] = targetIdx + 1;

            // Used for when we enter the reciepient numberRecipient Collection Loop
            while (true) {
                String inputPhone = JOptionPane.showInputDialog(null, "Enter Recipient Number (+27xxx xxx xxx):");
                if (inputPhone == null) {
                    return;
                }
//Verifies matching SA layout patterns before committing to the recipient array
                if (msgEngine.checkRecipientCell(inputPhone).equals("Cell phone number successfully captured.")) {
                    Message.recipient[targetIdx] = inputPhone;//Shifts 0-based array index tracking up by +1 so that people may view it
                    break;
                }
                JOptionPane.showMessageDialog(null, "Invalid format. Must align with format: +27 followed by 9 digits.");
            }

            while (true) {
                String inputText = JOptionPane.showInputDialog(null, "Type Message Content (Max 250 characters):");
                if (inputText == null) {
                    return;
                }
// char length verification 
                if (inputText.length() <= 250) {
                    Message.message[targetIdx] = inputText;
                    break;
                }
                JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
            }

            // Map hash using exact string rule specifications
            Message.hash[targetIdx] = msgEngine.createMessageHash(
                    Message.messageID[targetIdx],
                    Message.messageNumber[targetIdx],
                    Message.message[targetIdx]
            );

            // here we are prompting the user for input
            String actionInput = JOptionPane.showInputDialog(null,
                    "Message captured. Choose an option:\n0) Send\n1) Disregard\n2) Store");
//passing 'null' into JOptionPane component fields centers the generated popup directly on the monitor.
            if (actionInput != null && !actionInput.trim().isEmpty()) {
                try {
                    int choice = Integer.parseInt(actionInput.trim());

                    if (choice == 2) {
                        // Call the simulated JSON writer method 
                        Message.writeMessageToJSON(
                                Message.messageID[targetIdx],
                                Message.hash[targetIdx],
                                Message.recipient[targetIdx],
                                Message.message[targetIdx]
                        );
                        JOptionPane.showMessageDialog(null, "Message successfully stored.");
                    } else if (choice == 0 || choice == 1) {
                        // Handle Send (0) and Disregard (1) via the Message engine class
                        String outputStatus = msgEngine.SentMessage(choice);
                        JOptionPane.showMessageDialog(null, outputStatus);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid selection. Message disregarded.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input format. Message disregarded.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No option selected. Message disregarded.");
            }

            // will display all required summary details are per requested in assignment 
            if (Message.messageID[targetIdx] != null) {
                String summary = "--- Message Finalized Status ---\n"
                        + "ID: " + Message.messageID[targetIdx] + "\n"
                        + "Hash: " + Message.hash[targetIdx] + "\n"
                        + "Recipient: " + Message.recipient[targetIdx] + "\n"
                        + "Content: " + Message.message[targetIdx];
                JOptionPane.showMessageDialog(null, summary);
                //Increments global pointer index
                Message.currentMessageCount++;
            }
        }

        //show a total summary report of messages sent in the session
        JOptionPane.showMessageDialog(null, msgEngine.printMessages());
        JOptionPane.showMessageDialog(null, "Total Sent Session Messages: " + msgEngine.returnTotalMessagess());
    }

}
