/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog1a_part3_chatapp;

/**
 *
 * @author victo
 */
import java.util.Random;

public class Message {
    // Private encapsulation fields
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageContent;
    private String messageHash;
    private String statusFlag; // "Sent", "Disregarded", "Stored"

    // Constructor to initialize a clean Message Object
    public Message(String messageID, int messageNumber, String recipient, String messageContent, String statusFlag) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageContent = messageContent;
        this.statusFlag = statusFlag;
        this.messageHash = createMessageHash(messageID, messageNumber, messageContent);
    }

    // logic validation//
    public String validateMessageLength(String msg) {
        if (msg.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = msg.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }
// these have been explained in previous parts of assignments they are self explanatory at this point//
    public String checkRecipientCell(String cellNumber) {
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
        String firstTwo = (msgID != null && msgID.length() >= 2) ? msgID.substring(0, 2) : "00";
        String[] words = msgContent.trim().split("\\s+");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 0 ? words[words.length - 1] : "";
        return (firstTwo + ":" + msgNum + ":" + first + last).toUpperCase();
    }

    //Getters and setters//
    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageContent() { return messageContent; }
    public String getMessageHash() { return messageHash; }
    public String getStatusFlag() { return statusFlag; }
    public void setStatusFlag(String statusFlag) { this.statusFlag = statusFlag; }
}
