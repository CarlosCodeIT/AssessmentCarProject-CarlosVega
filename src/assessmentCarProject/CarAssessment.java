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
		
		// Working list (first 2000 cars)
		ArrayList<Car> working = new ArrayList<>(cars.subList(0, 2000));
		System.out.println("Working list size: " + working.size());
		
		Scanner input = new Scanner(System.in);
		boolean running = true;
		
		while (running) {
			System.out.println("\n===== CAR MENU =====");
			System.out.println("1. Sort cars by Mileage");
			System.out.println("2. Search for cars by Mileage");
			System.out.println("3. Display statistics");
			System.out.println("4. Exit");
			System.out.print("Choose an option: ");
			
			int choice = input.nextInt();
			input.nextLine();
			
			switch (choice) {
			
			// Sort by mileage
			case 1:
			    insertionSortByMileage(working);
			    System.out.println("Cars sorted by mileage.");
			
			    System.out.println("\nFirst 10 cars after sorting:");
			    for (int i = 0; i < 10; i++) {
				    System.out.println(working.get(i));
			    }
			    break;
			// Search by mileage
			case 2:
				System.out.print("Enter mileage to search: ");
				double target = input.nextDouble();
				int index = binarySearchMileage(working, target);
				
				if (index != -1) {
					System.out.println("\nCars with mileage" + target + ":");
					displayAllMatchingMileage(working, target);
				} else {
					System.out.println("No cars found with mileage: " + target);
				}
				break;
			// Statistics (Extra credit)
			case 3: 
				displayStatistics(working);
				break;
			//Exit
			case 4:
				running = false;
				System.out.println("Exiting program...");
				break;
				
			default:
				System.out.println("Invalid choice. Try again.");
				
			}
		}
		
		input.close();
	}
	
	// Insertion sort
	public static void insertionSortByMileage(ArrayList<Car> working) {
		
		for (int i = 1; i < working.size(); i++) {
			Car key = working.get(i);
			double keyMileage = key.getMileageKmpl();
			
			int j = i - 1;
			
			// Shift cars with larger mileage to the right
			while (j >= 0 && working.get(j).getMileageKmpl() > keyMileage) {
				working.set(j + 1, working.get(j));
				j--;
			}
			
			working.set(j + 1, key);
		}
	}
	
	//binary search
	public static int binarySearchMileage(ArrayList<Car> working, double targetMileage) {
		int left = 0;
		int right = working.size() - 1;
		
		while (left <= right) {
			int mid = (left + right) / 2;
			double midMileage = working.get(mid).getMileageKmpl();
			
			if (midMileage == targetMileage) {
				return mid; // found
			}
			else if (midMileage < targetMileage) {
				left = mid + 1;
			}
			else {
				right = mid - 1;
			}
		}
		
		return -1; // not found
	}
	
	//Display all matching mileage
	public static void displayAllMatchingMileage(ArrayList<Car> working, double mileage) {
		boolean found = false;
		
		for(Car c : working) {
			if (c.getMileageKmpl() ==  mileage) {
				System.out.println(c);
				found = true;
			}
		}
		
		if (!found) {
			System.out.println("No cars found with mileage: " + mileage);
		}
	}
	
	//Statistics (Extra credit)
	public static void displayStatistics(ArrayList<Car> working) {
		
		double totalMileage = 0;
		
		for (Car c : working) {
			totalMileage += c.getMileageKmpl();
		}
		
		double avgMileage = totalMileage / working.size();
		
		System.out.println("\n===== Statistics =====");
		System.out.println("Average mileage of working list: " + avgMileage);
		
	}

}
