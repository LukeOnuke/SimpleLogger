package logger.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
/**
 * Class containing the read, write and hash methods used for binary input and output
 *
 * @author lukak
 */
public class BinaryIo {

    /**
     * Reads the file
     * @param filePath File path of the file
     * @return byte[] of the contents of the file
     * @throws IOException When something bad happens
     */
    public static byte[] read(String filePath) throws IOException {
        File file = new File(filePath);
        return Files.readAllBytes(file.toPath());
    }

    /**
     * Writes a file to storage
     * @param filePath The path of the file
     * @param data Data that is going to be written in to the file
     * @throws FileNotFoundException When the file is not found
     * @throws IOException When something bad happens
     */
    public static void write(String filePath, byte[] data) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(data);
        fos.close();
    }
    
    /**
     * Hash the bytes using the sha-512 algorithm
     * @param bytes The bytes that are going to be hashed
     * @return Hashed bytes
     */
    public static byte[] hash(byte[] bytes){
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA-512/256");
        } catch (NoSuchAlgorithmException ex) {
            
        }
        
        bytes = Arrays.copyOf(bytes, bytes.length * 10);
        
        return sha.digest(bytes);
    }
}
