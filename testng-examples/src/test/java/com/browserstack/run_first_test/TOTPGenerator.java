package com.browserstack.run_first_test;
import org.jboss.aerogear.security.otp.Totp;

public class TOTPGenerator {
    /**
     * Method is used to get the TOTP based on the security token
     * @return
     */
    public static String getTwoFactorCode(){
//Replace with your security key copied from step 12
        Totp totp = new Totp("bnb7m4um4nskpu7k3g44ht7kuimic4hi"); // 2FA secret key
        String twoFactorCode = totp.now(); //Generated 2FA code here
        return twoFactorCode;
    }
}