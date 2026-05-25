/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog1a_part2.chatapp;

import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author victo
 */
// Parallel Arrays
public class Message {
// data is stored across multiple arrays and is linked by the common indext "targetIdx"
//the static modifier makes them visible throughout the class or attaches the components to the class rather than creating objects and instances

    public static String[] messageID = new String[10];
    public static int[] messageNumber = new int[10];
    public static String[] recipient = new String[10];
    public static String[] message = new String[10];
    public static String[] hash = new String[10];
    public static int currentMessageCount = 0;
    public static int totalMessagesSent = 0;

    //Check for 250 characters, return specific success/failure strings
    public String validateMessageLength(String msg) {
        if (msg.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = msg.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    //Recipient number validation with the feedback
    public String checkRecipientCell(String cellNumber) {
        // Matches +27 followed by 9 digits
        if (cellNumber != null && cellNumber.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public boolean checkMessageID(String msgID) {
        return msgID != null && msgID.length() <= 10 && msgID.matches("\\d+");
    }

    public static String generateRandomID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    public String createMessageHash(String msgID, int msgNum, String msgContent) {
        String firstTwo = msgID.substring(0, 2);
        String[] words = msgContent.trim().split("\\s+");
        String first = words[0];
        String last = words[words.length - 1];
        return (firstTwo + ":" + msgNum + ":" + first + last).toUpperCase();
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 0:
                totalMessagesSent++; // Increment only on Send
                return "Message successfully sent.";
            case 1:
                return "Press 0 to delete the message.";
            case 2:
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }

    public String printMessages() {
        // Logic to return formatted string of all sent messages
        return "Report generated.";
    }

    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    public void storeMessage(int index) {
        // JSON storage implementation used to simulate 
    }

    public static void writeMessageToJSON(String messageID, String hash, String recipient, String message) {
        StringBuilder jsonEntry = new StringBuilder();
        jsonEntry.append("{\n");
        jsonEntry.append("  \"MessageID\": \"").append(messageID).append("\",\n");
        jsonEntry.append("  \"MessageHash\": \"").append(hash).append("\",\n");
        jsonEntry.append("  \"Recipient\": \"").append(recipient).append("\",\n");
        // .replace handles quotes inside the message content so the JSON doesn't break
        jsonEntry.append("  \"Message\": \"").append(message.replace("\"", "\\\"")).append("\"\n");
        jsonEntry.append("}\n");

        try (FileWriter file = new FileWriter("Jsonnew.json", true)) { // 'true' for append mode
            file.write(jsonEntry.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing file: " + e.getMessage());
        }
    }
}
