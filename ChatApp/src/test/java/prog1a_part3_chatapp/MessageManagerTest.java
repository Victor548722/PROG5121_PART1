/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package prog1a_part3_chatapp;


import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class MessageManagerTest {
    
    private MessageManager manager;

    // This runs automatically before every single test to give us a clean, fresh slate
    @BeforeEach
    public void setUp() {
        manager = new MessageManager();
        
        // Populate the system with the exact Test Data 1-5 from your assignment sheets
        // Format: Message(ID, MessageNumber, Recipient, Content, StatusFlag)
        Message msg1 = new Message("0111111111", 1, "+27834557896", "Did you get the cake?", "Sent");
        Message msg2 = new Message("0222222222", 2, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        Message msg3 = new Message("0333333333", 3, "+27834484567", "Yohoooo, I am at your gate.", "Disregarded");
        Message msg4 = new Message("0838884567",  4, "+27838884567", "It is dinner time !", "Sent");
        Message msg5 = new Message("0555555555", 5, "+27838884567", "Ok, I am leaving without you.", "Stored");
        
        // Load them directly into our arraylist manager engine
        manager.addMessage(msg1);
        manager.addMessage(msg2);
        manager.addMessage(msg3);
        manager.addMessage(msg4);
        manager.addMessage(msg5);
    }

    /**
     * Test 1: Sent Messages array correctly populated
     * Expected returns: "Did you get the cake?", "It is dinner time !"
     */
    @Test
    public void testSentMessagesPopulated() {
        ArrayList<Message> sentMessages = manager.getSentMessages();
        
        // Assert that we have exactly 2 messages flagged as "Sent"
        assertEquals(2, sentMessages.size());
        
        // Assert that the contents match the test criteria perfectly
        assertEquals("Did you get the cake?", sentMessages.get(0).getMessageContent());
        assertEquals("It is dinner time !", sentMessages.get(1).getMessageContent());
    }

    /**
     * Test 2: Display the longest Message
     * Expected return: "Where are you? You are late! I have asked you to be on time."
     */
    @Test
    public void testGetLongestStoredMessage() {
        String expectedLongest = "Where are you? You are late! I have asked you to be on time.";
        String actualLongest = manager.getLongestStoredMessage();
        
        assertEquals(expectedLongest, actualLongest);
    }

    /**
     * Test 3: Search for messageID
     * Target: Test data message 4 ("0838884567")
     * Expected return: "It is dinner time !"
     */
    @Test
    public void testSearchByMessageID() {
        String targetID = "0838884567";
        String expectedContent = "It is dinner time !";
        String actualContent = manager.searchByMessageID(targetID);
        
        assertEquals(expectedContent, actualContent);
    }

    /**
     * Test 4: Search all the messages sent or stored regarding a particular recipient
     * Target Recipient: +27838884567
     * Expected returns: Message 2 content and Message 5 content combined
     */
    @Test
    public void testSearchByRecipient() {
        String targetRecipient = "+27838884567";
        
        // Our method returns them separated by a newline flag
        String expectedOutput = "Where are you? You are late! I have asked you to be on time.\n" 
                              + "It is dinner time !\n" 
                              + "Ok, I am leaving without you.";
                              
        String actualOutput = manager.searchByRecipient(targetRecipient);
        
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * Test 5: Delete a message using a message hash
     * Target: Test Message 2
     * Expected return text verification statement
     */
    @Test
    public void testDeleteMessageByHash() {
        // Find the hash generated automatically for our target message 2
        String message2Hash = manager.getAllMessages().get(1).getMessageHash();
        
        String expectedResponse = "Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.";
        String actualResponse = manager.deleteMessageByHash(message2Hash);
        
        assertEquals(expectedResponse, actualResponse);
        
        // Double-check validation: Assert total message counts dropped down to 4 items remaining
        assertEquals(4, manager.getAllMessages().size());
    }

    /**
     * Test 6: Display Report
     * Assures formatting criteria matches data expectations safely
     */
    @Test
    public void testGenerateFullReport() {
        String report = manager.generateFullReport();
        
        // Assert that our core structure strings exist natively within the output template block
        assertNotNull(report);
        assertTrue(report.contains("--- Full Stored Message Report ---"));
        assertTrue(report.contains("Where are you? You are late!"));
        assertTrue(report.contains("Ok, I am leaving without you."));
    }
}