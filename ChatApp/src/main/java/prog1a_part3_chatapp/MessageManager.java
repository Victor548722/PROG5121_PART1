/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog1a_part3_chatapp;

/**
 *
 * @author victo
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MessageManager {
    // Dynamic array list so that we can use our arrays without knowing a fixed data size in advance//
    private ArrayList<Message> allMessages = new ArrayList<>();

    public void addMessage(Message msg) {
        allMessages.add(msg);
        if (msg.getStatusFlag().equals("Stored")) {
            writeMessageToJSON(msg);
        }
    }

    public ArrayList<Message> getAllMessages() {
        return allMessages;
    }

    // Filter helpers to populate arrays , will look at the conditions to see if meets a specific criteria//
    public ArrayList<Message> getSentMessages() {
        ArrayList<Message> sent = new ArrayList<>();
        for (Message m : allMessages) {
            if (m.getStatusFlag().equalsIgnoreCase("Sent")) sent.add(m);
        }
        return sent;
    }
// this will create a temporary array list for our disregarded messages//
    public ArrayList<Message> getDisregardedMessages() {
        ArrayList<Message> disregarded = new ArrayList<>();
        for (Message m : allMessages) {
            if (m.getStatusFlag().equalsIgnoreCase("Disregard") || m.getStatusFlag().equalsIgnoreCase("Disregarded")) {
                disregarded.add(m);
            }
        }
        return disregarded;
    }
// same as aboove line 32 just for the stored messages//
    public ArrayList<Message> getStoredMessages() {
        ArrayList<Message> stored = new ArrayList<>();
        for (Message m : allMessages) {
            if (m.getStatusFlag().equalsIgnoreCase("Stored")) stored.add(m);
        }
        return stored;
    }

    // The sender (current user) and recipient of all stored messages//
    public String getStoredSendersAndRecipients(String currentUserName) {
        ArrayList<Message> stored = getStoredMessages();
        if (stored.isEmpty()) return "No stored messages found.";
        StringBuilder sb = new StringBuilder("--- Stored Messages (Sender & Recipient) ---\n");
        for (Message m : stored) {
            sb.append("Sender: ").append(currentUserName)
              .append(" -> Recipient: ").append(m.getRecipient()).append("\n");
        }
        return sb.toString();
    }

    // Here we have the longest stored message // 
    public String getLongestStoredMessage() {
        ArrayList<Message> stored = getStoredMessages();
        if (stored.isEmpty()) return "No stored messages available.";
        Message longest = stored.get(0);
        for (Message m : stored) {
            if (m.getMessageContent().length() > longest.getMessageContent().length()) {
                longest = m;
            }
        }
        return longest.getMessageContent();
    }

    // here we are searching for the method ID have run tests on this to see if it works//
    public String searchByMessageID(String id) {
        for (Message m : allMessages) {
            if (m.getMessageID().equals(id)) {
                return m.getMessageContent();
            }
        }
        return "Message ID not found.";
    }

    // Searching for all stored messages by a recipient//
    public String searchByRecipient(String recipientNum) {
        StringBuilder sb = new StringBuilder();
        for (Message m : allMessages) {
            if (m.getRecipient().equals(recipientNum)) {
                sb.append(m.getMessageContent()).append("\n");
            }
        }
        return sb.isEmpty() ? "No messages found for this recipient." : sb.toString().trim();
    }

    // Here we delete a message using its hash//
    public String deleteMessageByHash(String hash) {
        for (int i = 0; i < allMessages.size(); i++) {
            if (allMessages.get(i).getMessageHash().equalsIgnoreCase(hash)) {
                String content = allMessages.get(i).getMessageContent();
                allMessages.remove(i);
                rewriteJSONFile(); // Keeps your local text database synchronized
                return "Message: \"" + content + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // full report display//
    public String generateFullReport() {
        ArrayList<Message> stored = getStoredMessages();
        if (stored.isEmpty()) return "No stored records to report.";
        StringBuilder sb = new StringBuilder("--- Full Stored Message Report ---\n");
        for (Message m : stored) {
            sb.append("Hash: ").append(m.getMessageHash()).append("\n")
              .append("Recipient: ").append(m.getRecipient()).append("\n")
              .append("Message: ").append(m.getMessageContent()).append("\n")
              .append("-----------------------------------\n");
        }
        return sb.toString();
    }

    // JSON handling utilities//
    private void writeMessageToJSON(Message msg) {
        StringBuilder jsonEntry = new StringBuilder();
        jsonEntry.append("{\n")
                 .append("  \"MessageID\": \"").append(msg.getMessageID()).append("\",\n")
                 .append("  \"MessageHash\": \"").append(msg.getMessageHash()).append("\",\n")
                 .append("  \"Recipient\": \"").append(msg.getRecipient()).append("\",\n")
                 .append("  \"Message\": \"").append(msg.getMessageContent().replace("\"", "\\\"")).append("\"\n")
                 .append("}\n");
        try (FileWriter file = new FileWriter("Jsonnew.json", true)) {
            file.write(jsonEntry.toString());
        } catch (IOException ignored) {}
    }

    private void rewriteJSONFile() {
        try (FileWriter file = new FileWriter("Jsonnew.json", false)) { // Clear file and overwrite
            for (Message m : getStoredMessages()) {
                writeMessageToJSON(m);
            }
        } catch (IOException ignored) {}
    }

    //  loading stored items from the txt database on boot execution//
    public void loadMessagesFromJSON() {
        try (BufferedReader br = new BufferedReader(new FileReader("Jsonnew.json"))) {
            String line;
            String id = "", hash = "", recipient = "", content = "";
            int count = 1;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("\"MessageID\"")) id = line.split("\"")[3];
                if (line.startsWith("\"MessageHash\"")) hash = line.split("\"")[3];
                if (line.startsWith("\"Recipient\"")) recipient = line.split("\"")[3];
                if (line.startsWith("\"Message\"")) {
                    content = line.split("\"")[3];
                    Message loadedMsg = new Message(id, count++, recipient, content, "Stored");
                    allMessages.add(loadedMsg);
                }
            }
        } catch (IOException ignored) {} 
    }
}
