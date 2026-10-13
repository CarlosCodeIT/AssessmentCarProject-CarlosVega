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
					String carID = parts[0].trim();
					String brand = parts[1].trim();
					String model = parts[2].trim();
					int year = Integer.parseInt(parts[3].trim());
					String fuelType = parts[4].trim();
					String color = parts[5].trim();
					double mileage = Double.parseDouble(parts[6].trim());
					
					cars.add(new Car(carID, brand, model, year, fuelType, color, mileage));
					
				} catch (Exception e) {
					System.out.println("Skipping malformed numeric row: " + line);
				}
			}
			
			sc.close();
			
		} catch (Exception e) {
			System.out.println("Could not open file: " + filename);
		}
		
		System.out.println("Total cars loaded: " + cars.size());
		return cars;
	}
	
	public static void main(String[] args) {
		ArrayList<Car> cars = loadCars("Car_data.csv");
		
		ArrayList<Car> working = new ArrayList<>(cars.subList(0, 2000));
		System.out.println("Working list size: " + working.size());
	}

}
