package n2e1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class AlphaDirectory2 {
	
	private Path workingDir;
	private DirectoryStream<Path> pathStream;
	private ArrayList<Path> pathList;
	
	public AlphaDirectory2() {
		this.workingDir = null;
		this.pathStream = null;
		this.pathList = new ArrayList<>(); 
	}
	
	//Paths.get(System.getProperty("user.dir")); this is the test path for now
	public void createPathStream(String path) {		//usuario elije el destino y se utiliza como parametro
		
		try {
			
			workingDir = Paths.get(System.getProperty(path)); 	//busca el directorio y busca el contenido aqui dentro
			pathStream = Files.newDirectoryStream(workingDir);	//crea el stream para poder escribir la informacion del archivo
						
			for(Path p : pathStream) {		//mientras el stream va coje la informacion y lo añade a un arraylist
				pathList.add(p);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//coje el arraylist y imprime la informacion basica en manera alfabetica
	public void printPathStream() {
		Collections.sort(pathList);
		for (int i = 0; i < pathList.size(); i++) {
			System.out.println(pathList.get(i).toAbsolutePath());	
		}
		System.out.println();
	}
	
	public int printPathStreamAndAttr(int count) {		//empieza desde 0 como inicial, pero el parametro es general para poder seguir contando hasta el final del array
		if (count == pathList.size()) {		//condicion basica para salir del metodo recursivo. cuando llega al ultimo indice del array, sale
			return count;
		}
		System.out.print(pathList.get(count).toAbsolutePath());		//imprime el directorio
		try {
			BasicFileAttributes attr = Files.readAttributes(pathList.get(count), BasicFileAttributes.class);	//coje los atributos de cada unidad en el array
			if (attr.isDirectory()) {		//si es una carpeta imprime al final la letra d
				System.out.println("(D)");
			} else if (attr.isRegularFile()) {
				System.out.println("(F)");	//si es un archivo imprime al final la letra f
			}
			System.out.println(" is a directory (D)? -> " + attr.isDirectory());		//devuelve true or false para ambas
			System.out.println(" is a file (F)? -> " + attr.isRegularFile());
			System.out.println(" when was it last modified? -> " + attr.lastModifiedTime());	//ultima vez modificada
			
			System.out.println();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		printPathStreamAndAttr(count + 1);		//suma uno mas cada vez que va por el metodo y asi hasta el ultimo del size del array
		return count;
	}
	
	//////Exercise 3////// del nivel anterior
	//cojiendo del ejercicio anterior y utilizando un outputstream para crear un archivo java properties. 
	public void outputDirectoryToTxtFile(String directory, String textFile) {		//from https://www.geeksforgeeks.org/write-hashmap-to-a-text-file-in-java/
		BufferedWriter bw = null;
		
		try {

			File file = new File(directory);		//usuario escribe donde quiere que sea el directorio
			bw = new BufferedWriter( new FileWriter(file));
			
			Properties p = new Properties();
			OutputStream os = new FileOutputStream(textFile);
				
			//crear afuera para utilizar cada vez en el for loop
			Path absPath = null;
			FileTime time = null;
			String isAFile = "";
			String isADirectory = "";
			for (int i = 0; i < pathList.size(); i++) {

				absPath = pathList.get(i).toAbsolutePath();
				try {
					BasicFileAttributes attr = Files.readAttributes(pathList.get(i), BasicFileAttributes.class);
					if (attr.isDirectory()) {
						isADirectory = "Is a Directory (D)";
						isAFile = "Not a File";
						p.setProperty(isADirectory, isAFile);
					} else if (attr.isRegularFile()) {
						isAFile = "Is a File (F)";
						isADirectory = "Not a Directory";
						p.setProperty(isADirectory, isAFile);
					}	
					time = attr.creationTime();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
				bw.write("The path is: " + absPath
						+"\n is a directory " + isADirectory
						+"\n is a file " + isAFile
						+"\n when was it last modified? ->" + time);
				bw.newLine();
				bw.flush();			
				p.store(os, null);
			}	 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
			}
		}
		System.out.println("File created successfully.");
	}
		
	
	//esto ya esta hecho en parametros del ejercicio anterior? el metodo createpathstream coje el directorio y aqui el archivo para abrir no?
	public void readTXTFileFromDirectory(String filePath) {		//from https://www.geeksforgeeks.org/reading-text-file-into-java-hashmap/
		BufferedReader br = null;

		try {

			File file = new File(filePath); 	//usuario escoje el texto del directorio que quiere utilizar
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
				}
			}
		}

	}
}
