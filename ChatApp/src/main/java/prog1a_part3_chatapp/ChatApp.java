/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog1a_part3_chatapp;

/**
 *
 * @author victo
 */
import java.util.Scanner;
import javax.swing.JOptionPane;
import prog1a.chatapp.Login;

public class ChatApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login auth = new Login();
        
        // Instantiate our new ArrayList manager//
        MessageManager manager = new MessageManager();
        // Load any existing messages stored in the JSON file database right at startup
        manager.loadMessagesFromJSON();

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

            } else if (choice.equals("2")) {
                if (!isRegistered) {
                    System.out.println("Please register first, select option 1.");
                } else {
                    System.out.println("\n--- LOGIN ---");
                    System.out.print("Username: ");
                    String logUser = scanner.nextLine();
                    System.out.print("Password: ");
                    String logPass = scanner.nextLine();

                    boolean success = auth.loginUser(regUser, regPass, logUser, logPass);
                    System.out.println("\n" + auth.returnLoginStatus(success, fName, lName));

                    if (success) {
                        // Pass both the backend manager engine and the logged-in username for reporting
                        runQuickChatHub(manager, regUser);
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

    // menu with user interface option//
    public static void runQuickChatHub(MessageManager manager, String currentUserName) {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
//I have kept the "fourth menu in our chatapp as quit as it looks more natural//
        while (true) {
            String menuMsg = "--- QuickChat Hub Menu ---\n"
                    + "1) Send Messages\n"
                    + "2) Show recently sent messages\n"
                    + "3) Stored Messages Options\n"
                    + "4) Quit\n\n"
                    + "Enter choice (1-4):";

            String choiceInput = JOptionPane.showInputDialog(null, menuMsg);

            if (choiceInput == null || choiceInput.equals("4")) {
                break;
            }

            if (choiceInput.equals("1")) {
                processMessagePipeline(manager);
            } else if (choiceInput.equals("2")) {
                // summary of sent items//
                StringBuilder sentSummary = new StringBuilder("--- Session Sent Messages ---\n");
                for (Message m : manager.getSentMessages()) {
                    sentSummary.append("[").append(m.getMessageID()).append("] -> ").append(m.getMessageContent()).append("\n");
                }
                JOptionPane.showMessageDialog(null, sentSummary.toString());
            } else if (choiceInput.equals("3")) {
                // this is for our sub menu//
                runStoredMessagesSubMenu(manager, currentUserName);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid choice. Please select 1, 2, 3, or 4.");
            }
        }
    }

    // Implements features a, b, c, d, e, and f //
    public static void runStoredMessagesSubMenu(MessageManager manager, String currentUserName) {
        while (true) {
            String subMenuMsg = "--- Stored Messages Sub-Menu ---\n"
                    + "a) Display sender and recipient of all stored messages\n"
                    + "b) Display the longest stored message\n"
                    + "c) Search for a message ID\n"
                    + "d) Search for all messages regarding a particular recipient\n"
                    + "e) Delete a message using the message hash\n"
                    + "f) Display full details report\n"
                    + "g) Back to Main Hub\n\n"
                    + "Select an operation (a-g):";

            String choice = JOptionPane.showInputDialog(null, subMenuMsg);
            if (choice == null || choice.equalsIgnoreCase("g")) {
                break;
            }

            switch (choice.toLowerCase()) {
                case "a":
                    JOptionPane.showMessageDialog(null, manager.getStoredSendersAndRecipients(currentUserName));
                    break;
                case "b":
                    JOptionPane.showMessageDialog(null, "Longest Message:\n\"" + manager.getLongestStoredMessage() + "\"");
                    break;
                case "c":
                    String searchID = JOptionPane.showInputDialog(null, "Enter Message ID to search:");
                    if (searchID != null) {
                        JOptionPane.showMessageDialog(null, "Result:\n" + manager.searchByMessageID(searchID));
                    }
                    break;
                case "d":
                    String searchRecipient = JOptionPane.showInputDialog(null, "Enter Recipient Number (+27...):");
                    if (searchRecipient != null) {
                        JOptionPane.showMessageDialog(null, "Messages found:\n" + manager.searchByRecipient(searchRecipient));
                    }
                    break;
                case "e":
                    String targetedHash = JOptionPane.showInputDialog(null, "Enter Message Hash to completely delete:");
                    if (targetedHash != null) {
                        String result = manager.deleteMessageByHash(targetedHash);
                        JOptionPane.showMessageDialog(null, result);
                    }
                    break;
                case "f":
                    JOptionPane.showMessageDialog(null, manager.generateFullReport());
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid selection. Please enter a letter from a to g.");
            }
        }
    }

    // Refactored message pipeline utilizing object instantiations instead of raw indexes//
    public static void processMessagePipeline(MessageManager manager) {
        String batchInput = JOptionPane.showInputDialog(null, "How many messages would you like to process?");
        if (batchInput == null || batchInput.trim().isEmpty()) {
            return;
        }

        int requestedCount;
        try {
            requestedCount = Integer.parseInt(batchInput.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number format.");
            return;
        }

        // We use a dummy message object here just to access your validation methods safely//
        Message validator = new Message("00", 0, "+27000000000", "Placeholder", "Sent");

        for (int i = 0; i < requestedCount; i++) {
            // Generate valid temporary ID
            String tempID = Message.generateRandomID();
            while (!validator.checkMessageID(tempID)) {
                tempID = Message.generateRandomID();
            }

            int simulatedMsgNum = manager.getAllMessages().size() + 1;

            // recieptient number input loop//
            String inputPhone = "";
            while (true) {
                inputPhone = JOptionPane.showInputDialog(null, "Enter Recipient Number (+27xxx xxx xxx):");
                if (inputPhone == null) return;

                if (validator.checkRecipientCell(inputPhone).equals("Cell phone number successfully captured.")) {
                    break;
                }
                JOptionPane.showMessageDialog(null, "Invalid format. Must align with format: +27 followed by 9 digits.");
            }

            // our message loop//
            String inputText = "";
            while (true) {
                inputText = JOptionPane.showInputDialog(null, "Type Message Content (Max 250 characters):");
                if (inputText == null) return;

                if (inputText.length() <= 250) {
                    break;
                }
                JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
            }

            // Our selection prompt//
            String actionInput = JOptionPane.showInputDialog(null,
                    "Message captured. Choose an option:\n0) Send\n1) Disregard\n2) Store");

            String finalStatusFlag = "Disregarded"; // Fallback state
            if (actionInput != null && !actionInput.trim().isEmpty()) {
                try {
                    int choice = Integer.parseInt(actionInput.trim());
                    if (choice == 0) {
                        finalStatusFlag = "Sent";
                        JOptionPane.showMessageDialog(null, "Message successfully sent.");
                    } else if (choice == 1) {
                        finalStatusFlag = "Disregarded";
                        JOptionPane.showMessageDialog(null, "Message disregarded.");
                    } else if (choice == 2) {
                        finalStatusFlag = "Stored";
                        JOptionPane.showMessageDialog(null, "Message successfully stored.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid selection. Message set to Disregarded.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid choice pattern. Message set to Disregarded.");
                }
            }

            // constructing a dymanic object and saving into the array//
            Message activeMessage = new Message(tempID, simulatedMsgNum, inputPhone, inputText, finalStatusFlag);
            manager.addMessage(activeMessage);

            // display final status report on the screen//
            String summary = "--- Message Finalized Status ---\n"
                    + "ID: " + activeMessage.getMessageID() + "\n"
                    + "Hash: " + activeMessage.getMessageHash() + "\n"
                    + "Recipient: " + activeMessage.getRecipient() + "\n"
                    + "Status: " + activeMessage.getStatusFlag() + "\n"
                    + "Content: " + activeMessage.getMessageContent();
            JOptionPane.showMessageDialog(null, summary);
        }

        JOptionPane.showMessageDialog(null, "Total Sent Session Messages: " + manager.getSentMessages().size());
    }
}