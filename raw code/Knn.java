import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// @author Arda Sarıdoğan
// group name : 736f6c6f 
// 

class Person { // person class with constructors
	double social;
	double algs;
	double gpa;
	double age;
	int status;
	Double meanSoc;
	Double meanInt;
	Double distance;
	public Person(double social, double algs, double gpa, double age, int status) {
		
		this.social = social;
		this.algs = algs;
		this.gpa = gpa;
		this.age = age;
		this.status = status;
	}
	public Person(double meanSoc,double meanInt, int status) {
		this.meanInt = meanInt;
		this.meanSoc = meanSoc;
		this.status = status;
		
	};
	
	public Person(double distance, int status) {
		this.distance = distance;
		this.status = status;
	}
	public Person(double social, double algs, double gpa, double age) {
		
		this.social = social;
		this.algs = algs;
		this.gpa = gpa;
		this.age = age;
		
	}
	
}


public class Knn {
	static ArrayList<Person> perHolder = new ArrayList<Person>(); // holds person object
	
	public static void main(String[] args) throws FileNotFoundException {
		File file =  new File("/home/asd/Desktop/211pro/cmpe211/rec.txt"); //change this for your own pc 
		Scanner scan = new Scanner(file);
		int lineCount = 0;
		
		while(scan.hasNext()) { // reads the file 
			
			if(lineCount >= 1) { // to not the read the first line because it is not related with the program 
				String [] temp= scan.nextLine().split(","); // splits the data via comma and puts them into an array
				double tempInt = (Double.parseDouble(temp[1]) + Double.parseDouble(temp[2])) / 2; // takes the mean of numbers to find social point
				double tempSoc = (Double.parseDouble(temp[0]) + Double.parseDouble(temp[3])) / 2; // takes the mean of numbers to find intellectual point	
				int tempStat = Integer.parseInt(temp[4]); // gets the status 
				perHolder.add(new Person(tempSoc,tempInt,tempStat)); // stores them into a person object and adds it to the arraylist
				lineCount++; // adds up to the line count
			}else {
				scan.nextLine(); // scans the next line
				lineCount++;// adds up to the line count
			}
			
			
		}
		
		double K = Math.sqrt(perHolder.size()); // finds the k

		KnnAlg(perHolder, new Person(3.2,3,3,3),Math.round(K)); // test of the program
		KnnAlg(perHolder, new Person(3,3,2.9,2.7),Math.round(K));// test of the program
		

	}
	
	public static void KnnAlg(ArrayList<Person> X , Person u , double k) { // knn algorithm
		double perSoc = (u.age + u.social) / 2; // finds the social point of the new person
		double perInt = (u.gpa + u.algs) / 2;	// finds the intellectual point of the new person	
		int countOne = 0;
		int countZero = 0;
		ArrayList<Person> sorted = new  ArrayList<Person>(); 
		
		for(int i = 0; i < X.size();i++) {
			sorted.add(new Person(Euclidean(perInt, X.get(i).meanInt , perSoc,X.get(i).meanSoc),X.get(i).status)); // finds the euclidean distance between new person object and others and stores it on arraylist
		}
		
		sort(sorted,0,sorted.size()-1); // sorts the distances and keeps the statuses same
		
		for(int j = 0; j < k ; j++) { // loops the distances for k amount 
			if(sorted.get(j).status == 1) {
				countOne++;
			}else if(sorted.get(j).status == 0) {
				countZero++;
			}
		}
		
		if(countOne > countZero) { // results the program
			System.out.println("Consider him for the job");
		}else {
			System.out.println("Do not consider him");
		}
		System.out.println("Amounts of 1 : " + countOne); // prints the 0 amount 
		System.out.println("Amounts of 0 : " + countZero); // prints the 1 amount 
	}
	
	public static double Euclidean(double x1,double x2,double y1,double y2) { //finds euclidean distance
		
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
		
	}
	
	public static int partition(ArrayList<Person> arr , int low, int high) //part of the quicksort
    { 
        double pivot = arr.get(high).distance;  
        int i = (low-1); 
        for (int j=low; j<high; j++) 
        { 
          
            if (arr.get(j).distance < pivot) 
            { 
                i++; 
  
                int tempStat = arr.get(i).status;
                double temp = arr.get(i).distance; 
                arr.get(i).distance = arr.get(j).distance; 
                arr.get(i).status = arr.get(j).status;
                arr.get(j).status = tempStat; 
                arr.get(j).distance = temp; 
            } 
        } 
  
         
        double temp = arr.get(i+1).distance; 
        int tempStat = arr.get(i+1).status;
        arr.get(i+1).distance = arr.get(high).distance;
        arr.get(i+1).status = arr.get(high).status;
        arr.get(high).status = tempStat;
        arr.get(high).distance= temp; 
  
        return i+1; 
    } 
  
  
    
    public static void sort(ArrayList<Person> arr, int low, int high)  //quicksort
    { 
        if (low < high) 
        { 
            
            int pi = partition(arr, low, high); 
  
            
            sort(arr, low, pi-1); 
            sort(arr, pi+1, high); 
        } 
    } 
	
	
}
