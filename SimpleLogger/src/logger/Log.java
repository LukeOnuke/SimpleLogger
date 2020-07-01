/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author lukak
 */
public class Log {

    //Singlton setup
    private static Log instance = null;

    private Log() {
    }

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }
    //--------------
    //Data stored in the variable (instance)
    private ArrayList<String> data = new ArrayList<>();
    private String severity = "< none >";
    private File filepath;

    /**
     * Set the <b>default severity</b> of the log messages. By default the default severity is set to <b>none</b>.
     * @param logSeverity The severity that is going to become default
     */
    public void setSeverity(LogSeverity logSeverity) {
        severity = "< " + logSeverity.toString().toLowerCase() + " >";
    }

    /**
     * Log a message with the default severity
     * @param message Message that is going to be logged
     */
    public void log(String message) {
        String pattern = "[ dd-MM-yyyy | HH:mm:ss ] ";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        data.add(date + severity + " : " + message);
    }

    /**
     * Log a message with a severity that is diffrent from the default severity
     * @param message The message logged
     * @param logSeverity The severity of the message
     */
    public void log(String message, LogSeverity logSeverity) {
        String pattern = "[ dd-MM-yyyy | HH:mm:ss ] ";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        data.add(date + "< " + logSeverity.toString().toLowerCase() + " >" + " : " + message);
    }

    /**
     * Write the log into a file
     * @param comment The comment that is written in the logfile
     * @throws IOException 
     */
    public void write(String comment) throws IOException {
        String check = "";
        String pattern = "dd-MM-yyyy--HH-mm-ss-";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());

        String filename = date + "log.xmlog";
        File file = new File("log\\" + filename);
        filepath = file; //Set the filepath global variable
        //Check if logs\ doesnt exist and create the dir if it doesnt exist
        File test = new File("log\\");
        if (!test.exists()) {
            test.mkdir();
        }

        file.createNewFile();
        FileOutputStream fw = new FileOutputStream(file);
        //Write to file
        fw.write(getBytesAndNewLine("<Comments>"));
        fw.write(getBytesAndNewLine("\tLog file " + new SimpleDateFormat("HH:mm:ss  dd-MM-yyyy").format(new Date())));
        fw.write(getBytesAndNewLine("\t" + comment));
        fw.write(getBytesAndNewLine("</Comments>"));
        fw.write(getBytesAndNewLine("<Data>"));
        fw.write(getBytesAndNewLine("\tElapsed time running : " + String.valueOf(System.nanoTime() / 1000000000) + "ms " + String.valueOf(System.nanoTime() * 0.000000000000) + "seconds"));
        fw.write(getBytesAndNewLine("\tTime and date : " + new SimpleDateFormat("HH:mm:ss  dd-MM-yyyy").format(new Date())));
        fw.write(getBytesAndNewLine("\tOperating system : " + System.getProperty("os.name")));
        fw.write(getBytesAndNewLine("\tOperating system architecture : " + System.getProperty("os.arch")));
        fw.write(getBytesAndNewLine("\tUser username : " + System.getProperty("user.name")));
        fw.write(getBytesAndNewLine("\tJava version : " + System.getProperty("java.version")));
        fw.write(getBytesAndNewLine("</Data>"));
        fw.write(getBytesAndNewLine("<Messages>"));
        //Write the message elements
        for (String row : data) {
            fw.write(getBytesAndNewLine("\t<Message>"));

            ArrayList<String> rows = new ArrayList<>(Arrays.asList(row.split("\n")));
            row = "";
            //For as many rows in the messages there are execute
            for (int i = 0; i < rows.size(); i++) {
                String rawrow = rows.get(i);
                String seperator = "";
                
                if (rows.size() != 1 && !(i == rows.size() - 1)) {
                    seperator = "\n";
                }

                if (i != 0) {
                    row += "\t\t" + rawrow + seperator;
                } else {
                    row += rawrow + seperator;
                }
            }

            fw.write(getBytesAndNewLine("\t \t" + row));
            //Add to the ckeck string
            check += "\t \t" + row;

            fw.write(getBytesAndNewLine("\t</Message>"));
        }
        fw.write(getBytesAndNewLine("</Messages>"));

        //Write the sha digested message
        byte[] checksum = logger.io.BinaryIo.hash(logger.io.BinaryIo.read(file.getPath()));
        fw.write("<Checksum>".getBytes());
        fw.write(checksum);
        fw.write("</Checksum>".getBytes());

        fw.close();
    }

    /**
     * Get the filepath of the log
     * @return File representing the log directory
     */
    public File getFile() {
        return filepath;
    }

    private static byte[] getBytesAndNewLine(String string) {
        return (string + System.lineSeparator()).getBytes();
    }

}
