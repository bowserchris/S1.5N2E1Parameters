package n2e1;

import java.util.Scanner;

public class App {
	
	public static void main(String[] args) {
		
		AlphaDirectory2 directory = new AlphaDirectory2();
		
		directory.createPathStream(inputString("What´s the directory you wish to access?"));		//"user.dir"
		
		directory.outputDirectoryToTxtFile(inputString("What´s the directory you wish to access the file from?"), inputString("What´s the text file you wish to access?"));				//"user.dir", "javapropertiestesttext.txt"
		
	}
	
	static String inputString(String message) {				//request a string input
		Scanner input = new Scanner(System.in);
		System.out.println(message);
		return input.nextLine();
	}

}



/*Exercise 1

Run exercise 3 from the previous level by parameterizing all methods in a 
configuration file.

You can use a Java Properties file, or the Apache Commons Configuration
 library if you prefer.

From the previous exercise, parameterize the following:

    Directory to read.
    Name and directory of the resulting TXT file.

Level 3
- Exercise 1

Create a utility that encrypts and decrypts the files resulting from the 
previous levels.

Use AES algorithm in ECB or CBC working mode with PKCS5Padding padding method.
 Either javax.crypto or org.apache.commons.crypto can be used.
 * 
 * 
 * 
 * 
 */

