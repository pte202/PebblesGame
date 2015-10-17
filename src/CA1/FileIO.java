package CA1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {

	public static class ReadCVS {

//		  public static void main(String[] args) {
//
//			ReadCVS file = new ReadCVS();
//			String myFile = file.getFile();
//			file.readFile(myFile);
//			System.out.println(file.readFile(myFile));
//
//		  }

		public String getFile() {
			
//			Scanner keyboardInput = new Scanner(System.in);
//			System.out.println("Enter the file path");
//			String input = keyboardInput.nextLine();
			
//			return input;
			String test = "C:/Users/Niko216/Desktop/example_file_2.csv";
			return test;
		}
			
		  public ArrayList<String> readFile(String input) {
			  
			BufferedReader fileContents = null;
			String line = "";
			String cvsSplitBy = ",";
			ArrayList<String> weights = new ArrayList<String>();

			try {

				fileContents = new BufferedReader(new FileReader(input));
				line = fileContents.readLine();
				String[] temp = line.split(cvsSplitBy);
				while ((line = fileContents.readLine()) != null) {

			        // use comma as separator
				String[] country = line.split(cvsSplitBy);
				System.out.println(country.toString());}


				for(String i: temp ){
//					use comma as separator	
					weights.add(i);
//					System.out.println(temp);
					System.out.println(i);

				}
				

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fileContents != null) {
					try {
						fileContents.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			for (String i:  weights){
				System.out.println("item "+i);
			}
			return weights;
		  }
		}
	}



