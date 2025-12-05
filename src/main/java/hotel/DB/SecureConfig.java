package hotel.DB;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Properties;
import java.util.Scanner;

public class SecureConfig {
    
    private static final String CONFIG_FILE = "secure.config";
    private static Properties decryptedProps = null;
    
    private static SecretKeySpec generateKey(String masterPassword) throws Exception {
        byte[] salt = "HotelSecureSalt2025".getBytes(StandardCharsets.UTF_8);
        KeySpec spec = new PBEKeySpec(masterPassword.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] key = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(key, "AES");
    }
    
    private static String encrypt(String plainText, String masterPassword) throws Exception {
        SecretKeySpec secretKey = generateKey(masterPassword);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    private static String decrypt(String encryptedText, String masterPassword) throws Exception {
        SecretKeySpec secretKey = generateKey(masterPassword);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decrypted, StandardCharsets.UTF_8);
    }
    
    private static void loadConfig(String masterPassword) throws Exception {
        if (decryptedProps != null) {
            return;
        }
        
        Properties encryptedProps = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            encryptedProps.load(fis);
        }
        
        decryptedProps = new Properties();
        for (String key : encryptedProps.stringPropertyNames()) {
            String encryptedValue = encryptedProps.getProperty(key);
            String decryptedValue = decrypt(encryptedValue, masterPassword);
            decryptedProps.setProperty(key, decryptedValue);
        }
    }
    
    public static String getProperty(String key) {
        try {
            if (decryptedProps == null) {
                
                String masterPassword = System.getenv("MASTER_PASSWORD");
                if (masterPassword == null) {
                    masterPassword = System.getProperty("master.password");
                }
                if (masterPassword == null) {
                    throw new RuntimeException("MASTER_PASSWORD no está configurada. Configure la variable de entorno o propiedad del sistema.");
                }
                loadConfig(masterPassword);
            }
            
            if (decryptedProps != null && decryptedProps.containsKey(key)) {
                return decryptedProps.getProperty(key);
            }
            
            throw new RuntimeException("Propiedad '" + key + "' no encontrada en la configuración segura.");
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar configuración: " + e.getMessage(), e);
        }
    }
    

    public static void createConfigFile(String masterPassword) throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("HOST: ");
        String host = scanner.nextLine();
        System.out.print("PORT: ");
        String port = scanner.nextLine();
        System.out.print("DATABASE: ");
        String database = scanner.nextLine();
        
        Properties encryptedProps = new Properties();
        encryptedProps.setProperty("HOST", encrypt(host, masterPassword));
        encryptedProps.setProperty("PORT", encrypt(port, masterPassword));
        encryptedProps.setProperty("DATABASE", encrypt(database, masterPassword));
        
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            encryptedProps.store(fos, null);
        }
        
        System.out.println("\nArchivo de configuración creado exitosamente: " + CONFIG_FILE);
        System.out.println("Guarda tu contraseña maestra de forma segura!");
        System.out.println("\nLos usuarios ingresarán con sus credenciales en el LoginController.");
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("");
        System.out.print("Ingresa una contraseña maestra: ");
        String masterPassword = scanner.nextLine();
        
        if (masterPassword.length() < 8) {
            System.out.println("La contraseña debe tener al menos 8 caracteres");
            return;
        }
        
        try {
            createConfigFile(masterPassword);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
