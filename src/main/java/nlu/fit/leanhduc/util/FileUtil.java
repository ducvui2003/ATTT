package nlu.fit.leanhduc.util;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
    public static void writeStringToFile(String path, String content) throws IOException {
        Files.write(Paths.get(path), content.getBytes());
    }

    public static void saveKeyToFile(SecretKey key, String fileName) {
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             DataOutputStream dataOut = new DataOutputStream(fileOut)) {

            byte[] keyBytes = key.getEncoded(); // Get the raw bytes of the key
            dataOut.writeInt(keyBytes.length);  // Write the length of the byte array first
            dataOut.write(keyBytes);            // Write the actual byte array

            System.out.println("SecretKey saved to " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SecretKey loadKeyFromFile(String fileName, String algorithm) {
        try (FileInputStream fileIn = new FileInputStream(fileName);
             DataInputStream dataIn = new DataInputStream(fileIn)) {

            int length = dataIn.readInt();              // Read the length of the byte array
            byte[] keyBytes = new byte[length];
            dataIn.readFully(keyBytes);                 // Read the byte array

            return new SecretKeySpec(keyBytes, algorithm);  // Recreate the SecretKey object

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
