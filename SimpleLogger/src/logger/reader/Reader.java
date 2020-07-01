package logger.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import logger.io.BinaryIo;

/**
 *
 * @author lukak
 *
 * Reader class that contains methods for reading log files and verifing their
 * certificates and validity
 */
public class Reader {

    /**
     * Reads the <b>messages</b> stored in the log and returns them in a
     * ArrayList
     *
     * @param filePath <b>Filepath</b> of the log that is going to be read
     * @return A ArrayList of type string that contain all the messages
     * @throws IOException When a internal IOException ocures
     */
    public static ArrayList<String> readMessages(String filePath) throws IOException {
        byte[] rawFile = BinaryIo.read(filePath); //read the file
        String file = new String(rawFile); //create a string with the contents of the file
        String messages = file.substring(file.indexOf("<Messages>") + 12).split("</Messages>")[0]; //Isolate a string that contains all the <Message> elements
        String message = "";
        ArrayList<String> messageArray = new ArrayList<>();

        //Initilise the indexes
        int startIndex = 0; //The min index used in start index
        int endIndex = 0; //The min index used in end index

        //While there are still more <Message> elements in the string execute
        while (startIndex != -1 || endIndex != -1) {
            //Get if its the last <Message> element and splice the isolated string so that only the message string remains and put that into the ArrayList
            if (messages.indexOf("</Message>", endIndex) == -1) {
                message = messages.substring(messages.indexOf("<Message>", startIndex) + 12).split("\n")[0].replaceAll("\t", "");
                messageArray.add(message);
                break;
            }
            //Splice the isolated string so that it returns only the message string from the xmlog
            message = messages.substring(messages.indexOf("<Message>", startIndex) + 12, messages.indexOf("</Message>", endIndex)).replaceAll("\t", "");
            //Start index math
            startIndex += Math.abs(messages.indexOf("<Message>", startIndex) + 1);
            //End index math
            endIndex += Math.abs(messages.indexOf("</Message>", endIndex) + 1);
            //Ad the spliced string
            messageArray.add(message);
        }

        return messageArray;
    }

    /**
     * Checks if the file has been changed since its creation
     *
     * @param filePath <b>Filepath</b> of the log that is going to be read
     * @return <b>true</b> if the file hasn't been changed since its creation or
     * <b>false</b> if the file has been changed since its creation
     * @throws IOException IOException When a internal IOException ocures
     */
    public static boolean notTamperedCheck(String filePath) throws IOException {
        byte[] rawFile = BinaryIo.read(filePath); //Read the file
        String[] splitFile = new String(rawFile).split("<Checksum>"); //Split the string containing the file contents on "<Checksum>" regex
        //Return the result of a equals operation that comperes the newly hashed file and the hash written in the file 
        return (new String(BinaryIo.hash((splitFile[0]).getBytes())) + "</Checksum>").equals(splitFile[1]);
    }

    /**
     * Reads the <b>user info</b> stored in the log and returns it in a
     * ArrayList
     *
     * @param filePath <b>Filepath</b> of the log that is going to be read
     * @return ArrayList with strings that contain the user info as elements
     * @throws IOException IOException When a internal IOException ocures
     */
    public static ArrayList<String> getInfo(String filePath) throws IOException {
        ArrayList<String> dataArr;
        String file = new String(BinaryIo.read(filePath)); //Read the file
        String data = file.substring(file.indexOf("<Data>") + 8).split("</Data>")[0]; //Isolate the data elements
        dataArr = new ArrayList<>(Arrays.asList(data.replaceAll("\t", "").split("\n"))); //Yeet the data elements into a ArrayList
        return dataArr; //Return the arraylist
    }

    /**
     * Reads the <b>user info</b> stored in the log and returns it in a
     * ArrayList
     *
     * @param filePath <b>Filepath</b> of the log that is going to be read
     * @return ArrayList with strings that contain the comments as elements
     * @throws IOException IOException When a internal IOException ocures
     */
    public static ArrayList<String> getComments(String filePath) throws IOException {
        ArrayList<String> dataArr;
        String file = new String(BinaryIo.read(filePath)); //Read the file
        String data = file.substring(file.indexOf("<Comments>") + 12).split("</Comments>")[0]; //Isolate the comments
        dataArr = new ArrayList<>(Arrays.asList(data.replaceAll("\t", "").split("\n"))); //Yet the comments into a ArrayList
        return dataArr;
    }
}
