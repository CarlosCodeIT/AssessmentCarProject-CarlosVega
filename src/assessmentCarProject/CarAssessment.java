// Carlos Vega
// 10/13/2026
// CPSC 39
// CarAssessment main, CSV Loader.

package assessmentCarProject;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class CarAssessment {
	
	public static ArrayList<Car> loadCars(String filename) {
		ArrayList<Car> cars = new ArrayList<>();
		
		try {
			Scanner sc = new Scanner(new File(filename));
			
			// Skip header
			if (sc.hasNextLine()) {
				sc.nextLine();
			}
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				
				if(line.isEmpty()) {
					continue;
				}
				
				String[] parts = line.split(",");
				
				if (parts.length != 7) {
					System.out.println("Skipping malformed row: " + line);
					continue;
				}
				
				try {
					int carID = Integer.parseInt(parts[0].trim());
				}
			}
		}
	}

}
