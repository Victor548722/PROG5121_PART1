/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog1a.chatapp;



import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import prog1a_part2.chatapp.Message;

/**
 *
 * @author victor
 */
public class MessageTest {
    
    Message message;
    
    @BeforeEach
    public void setUp() {
        message = new Message();
        // Reset static metrics before each test run
        Message.currentMessageCount = 0;
        Message.totalMessagesSent = 0;
    }
    
    @Test
    public void testMessageBody() {
        assertEquals("Message ready to send.", message.validateMessageLength("this is less than 250 characters"));
        assertEquals("Message exceeds 250 characters by " + 10 + "; please reduce the size.", message.validateMessageLength("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Dapibus tempor leo fermentum viverra aenean morbi maecenas dapibus placerat nisi amet massa gravida faucibus purus. Posuere litora  jee."));
        assertNotEquals("Message ready to send.", message.validateMessageLength("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Dapibus tempor leo fermentum viverra aenean morbi maecenas dapibus placerat nisi amet massa gravida faucibus purus. Posuere litora  jee."));
        assertNotEquals("Message exceeds 250 characters by " + 10 + "; please reduce the size.", message.validateMessageLength("this is less than 250 characters"));
    }


    @Test
    public void testRecipientNumber() {
        // --- Success Case (Valid SA layout) ---
        assertEquals("Cell phone number successfully captured.", message.checkRecipientCell("+27186930027"));
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", message.checkRecipientCell("+2718693002"));

        // --- Failure Case (Invalid layout) ---
        String invalidCell = "08575975889";
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", message.checkRecipientCell(invalidCell));
        assertNotEquals("Cell phone number successfully captured.", message.checkRecipientCell(invalidCell));
    }

    @Test
    public void testMessageHash() {
        // --- Testing exact string rules based on Test Case 1 ---
        // ID: "0012345678", Index: 0, Message: "Hi Mike, can you join us for dinner tonight?"
        // Hash components: "00" + ":" + "0" + ":" + "HI" + "TONIGHT?" -> "00:0:HITONIGHT?"
        
        String ID = message.generateRandomID();
        
        String generatedHash = message.createMessageHash(ID, 0, "Hi Mike, can you join us for dinner tonight?");
        
        assertEquals(ID.substring(0, 2) + ":0:HITONIGHT?", generatedHash);
        assertNotEquals(ID.substring(0, 2) + ":0:INVALIDHASH", generatedHash);
    }

    @Test
    public void testMessageIDCreation() {
        String generatedID = Message.generateRandomID();
        
        // --- Verifying structural integrity constraints ---
        assertTrue(message.checkMessageID(generatedID));
        assertFalse(message.checkMessageID(null));
        assertFalse(message.checkMessageID("ABC1234567")); // Contain letters
        assertFalse(message.checkMessageID("123456789012")); // Too long
    }

    @Test
    public void testMessageSentPipeline() {
        // --- Option 0: Send ---
        assertEquals("Message successfully sent.", message.SentMessage(0));
        assertEquals(1, message.returnTotalMessagess());

        // --- Option 1: Disregard ---
        assertEquals("Press 0 to delete the message.", message.SentMessage(1));
        
        // --- Option 2: Store ---
        assertEquals("Message successfully stored.", message.SentMessage(2));
        
        // --- Asserting incorrect outcome strings ---
        assertNotEquals("Message successfully sent.", message.SentMessage(1));
        assertNotEquals("Message successfully stored.", message.SentMessage(0));
    }
}

