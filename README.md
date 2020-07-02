# SimpleLogger
Simple logger is a java 8 library for logging events and messages to a file designed to be simple and intuitive to use.

Download : [here](https://github.com/LukeOnuke/SimpleLogger/releases)

### Example code

        Log log = Log.getInstance();
        log.log("Its this simple");
        log.log("Even\nsupports\nmultiline");
        log.log("Severe error, on the fly", LogSeverity.SEVERE);
        try {
            log.write("Very nice comment indeed");
            
        } catch (IOException ex) {
        }

## Documentation
All the documentation you will need for methods and classes has been written to the javadoc contained in the library for maximum ease of use. So if you need a read up on something just type it in and the documentation will show up.

Tree of all the classes and methods in the library

    Package - logger:
	    |Class: Log
	    |	|Method : getInstance()
	    |	|Method : setSeverity(LogSeverity logSeverity)
	    |	|Method : log(String message)
	    |	|Method : log(String message, LogSeverity logSeverity)
	    |	|Method : write(String comment)
	    |	|Method : getFile()
	    |
	    |Enum: LogSeverity
	    |
	    |Package - logger.io
	    |	|Class : BinaryIo
	    |	|	|Method : read(String filePath)
	    |	|	|Method : write(String filePath, byte[] data)
	    |	|	|Method : hash(byte[] bytes)
	    |Package - logger.reader
	    |	|Class : Reader
	    |	|	|Method : readMessages(String filePath)
	    |	|	|Method : notTamperedCheck(String filePath)
	    |	|	|Method : getInfo(String filePath)
	    |	|	|Method : getComments(String filePath)

The library stores the log files in a format called  **Extensible Markup LOG** (**.xmlog**) that is very simalar to XML.
The format was designed with text editor compatability and efficiency in mind.
The file format can be opened and read with a ordinary text editor.

---
Here is a example file: **02-07-2020--02-26-11-log.xmlog**
```
<Comments>
	Log file 02:26:11  02-07-2020
	This is a test
</Comments>
<Data>
	Elapsed time running : 129427ms 0.0seconds
	Time and date : 02:26:11  02-07-2020
	Operating system : Windows 10
	Operating system architecture : amd64
	User username : luka
	Java version : 1.8.0_251
</Data>
<Messages>
	<Message>
	 	[ 02-07-2020 | 02:26:11 ] < none > : Yea boi
	</Message>
	<Message>
	 	[ 02-07-2020 | 02:26:11 ] < none > : Indeed quite a coincidence yes mmmmmm
	</Message>
	<Message>
	 	[ 02-07-2020 | 02:26:11 ] < none > : Server error 
		 at line:13 
		 consult manual
	</Message>
	<Message>
	 	[ 02-07-2020 | 02:26:11 ] < severe > : Severe error
	</Message>
	<Message>
	 	[ 02-07-2020 | 02:26:11 ] < none > : Ha yes mmmm
	</Message>
	<Message>
	 	[ 02-07-2020 | 02:26:11 ] < none > : Ineed
	</Message>
	<Message>
	 	[ 02-07-2020 | 02:26:11 ] < none > : Ineed
	</Message>
	<Message>
	 	[ 02-07-2020 | 02:26:11 ] < none > : Ineed
	</Message>
	<Message>
	 	[ 02-07-2020 | 02:26:11 ] < none > : Ineed
	</Message>
	<Message>
	 	[ 02-07-2020 | 02:26:11 ] < none > : Ineed
	</Message>
</Messages>
<Checksum>ßÁ‰#6ù2nÉÄœRp„I˜/¹ÅÄäv±ô®¾¨–
 </Checksum>
 ```
