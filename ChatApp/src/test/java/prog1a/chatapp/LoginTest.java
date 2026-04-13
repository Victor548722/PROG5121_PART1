/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog1a.chatapp;

/**
 *
 * @author victor
 */
import org.junit.jupiter.api.Test;
import prog1a.chatapp.Login;

import static org.junit.Assert.*;

public class LoginTest { // Start

    Login auth = new Login();

    @Test
    public void testUsernameCorrect() { // Test 1
        // Should return TRUE (Correct length and underscore)
        boolean result = auth.checkUserName("kyl_1");
        boolean result2 = auth.checkUserName("notcorrect$");
        assertTrue(result);
        assertFalse(result2);
    } // End 1

    @Test
    public void testUsernameIncorrect() { // Test 2
        // Should return FALSE (Too long or no underscore)
        boolean result = auth.checkUserName("kyle_longname");
        assertFalse(result);
    } // End 2

    @Test
    public void testLoginSuccess() { // Test 3
        // Should return TRUE
        boolean result = auth.loginUser("user", "pass", "user", "pass");
        assertTrue(result);
    } // End 3

    @Test
    public void testLoginFailure() { // Test 4
        // Should return FALSE
        boolean result = auth.loginUser("user", "pass", "user", "wrong");
        assertFalse(result);
    } // End 4

} // End