// 代码生成时间: 2025-09-18 19:08:10
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/*
 * This class is a Struts2 action class that provides password encryption and decryption functionality.
 * It follows Java best practices for code structure, error handling, and maintenance.
 */
public class PasswordEncryptionDecryption extends ActionSupport {

    private String password;
    private String encryptedPassword;
    private String decryptedPassword;

    public String execute() {
        try {
            if (password != null && !password.isEmpty()) {
                // Generate a secret key
                SecretKey key = generateSecretKey();
                // Encrypt the password
                encryptedPassword = encrypt(password, key);
                // Decrypt the password
                decryptedPassword = decrypt(encryptedPassword, key);
            } else {
                addActionError("Password cannot be empty.");
                return ERROR;
            }
        } catch (Exception e) {
            addActionError("Error in encryption/decryption: " + e.getMessage());
            return ERROR;
        }

        return SUCCESS;
    }

    /*
     * Generates a 128-bit AES secret key
     */
    private SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128, new SecureRandom());
        return keyGenerator.generateKey();
    }

    /*
     * Encrypts the given password using the provided key
     */
    private String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /*
     * Decrypts the given encrypted password using the provided key
     */
    private String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    // Getters and setters for properties
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getDecryptedPassword() {
        return decryptedPassword;
    }

    public void setDecryptedPassword(String decryptedPassword) {
        this.decryptedPassword = decryptedPassword;
    }
}
