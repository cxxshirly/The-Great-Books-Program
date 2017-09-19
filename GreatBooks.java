//a Library Catalog search program which will allow the user to determine whether a particular book is found in librarys's computerized holdings.
//Written by < Xinxin Chen>
import java.util.*;
import java.io.*;
public class GreatBooks{
	
	public static void main(String[] arg){
		//Clean Screen before the program start
		clearScreen();
	//	int choice = 0;
		String choice = "choice";
		String key;
		String enter = "enter";
		Scanner sc = new Scanner(System.in);
		ArrayList<LibraryBook> books = new ArrayList<LibraryBook>(50);

		//open the File and input the books into books Arraylit, then sort them in order
		int numBooks = inputBooks(openFile(), books);
		sortRecords(books);

		//print out the total number of records that has been input 
		System.out.println("A total of " + numBooks + " books have been input and stored by title.");
		sc.nextLine();
		clearScreen();
		
		while(choice.compareTo("3") != 0 )
		{
			displayMenu();
			Scanner input = new Scanner(System.in);
			choice = input.nextLine();
			//a switch statement that can allow the user to choose what they what to do next
			switch(choice){
				//in case one, the user will be able to look at the books by press return
				case "1" : 
					clearScreen();
					displayRecords(books,numBooks);
					break;
				//case 2 allow user to search the book by title
				case "2" :
					clearScreen();
					displayMenu();
					System.out.println();
					System.out.print("Search Title >");
					Scanner input2 = new Scanner(System.in);
					key = input2.nextLine();
					searchTitle(books,key);
					clearScreen();
					break;
				case "3" :	
					// terminates the program
					System.out.println("Thank you! Have a nice day!!");
					break;
				default: 
					//case default when user hit an error  input
					System.out.println("You had made an error input." );
					System.out.println();
					System.out.println("Please Hit Return to Continue...");
					//  input.nextLine();
					enter = input.nextLine();
 					//  clearScreen();

					if(enter.equals("")){
						continue;	
					}		
					else{
						while(!enter.equals("")){
							System.out.println("You had made an error input!");
							System.out.println("Please hit return to continue ...");	
							//  input.nextLine();
							enter = input.nextLine();
							//  clearScreen();
				}
			}				
							
			}
		}// while
	}//main

	//a method that shows all the data file in the floder, and allow user to choose their desired file by typing in the filename
	public static String openFile(){
		//Get all files from directory
		File curDir = new File(".");
		String[] fileNames = curDir.list();
		ArrayList<String> data = new ArrayList<String>();
		
		//Find files which may hava data. (aka, are in the .dat format)
		for(String s:fileNames)
			if(s.endsWith(".dat"))
				data.add(s);

		Scanner File = new Scanner(System.in);
		String inputFile = " ";
		int numBooks;
	
		System.out.println("                THE BOOK SEARCH PROGRAM          ");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println();
		System.out.println("		What file is your book data Stored in? ");

		Boolean found = false;
		while(!found){

		System.out.println();
		System.out.println("		Here are the files in the current directory :");
		System.out.println();
		System.out.println(data);
		System.out.println();
		System.out.print("		Filename :");
		inputFile = File.nextLine();
		System.out.println();
		
			for (int i = 0;i < data.size(); i++){
				if(inputFile.equals(data.get(i))){
					found = true;
				}
				else {
					System.out.println("		** Can't open input file. Try again. **");
					found = false;
				}
			}	
		}			

		
		return inputFile;
	}
	
	//a method that input the books from the file and put them into a arrraylist books	
	public static int inputBooks(String inputFile, ArrayList<LibraryBook> books){
		int numBooks = 0;
		try{ 
			Scanner in = new Scanner(new File(inputFile));
			while(in.hasNext()){
				Scanner lsc = new Scanner(in.nextLine()).useDelimiter(";");
				
				// scanner class 
				String title = lsc.next();
				String name = lsc.next();
				int copyright = lsc.nextInt();
				double price = lsc.nextDouble();
				String genre = lsc.next();
				
				books.add(new LibraryBook(title,name,copyright,price,genre));
			//	printRecord(books,numBooks);
				numBooks++;
			}//while
		}//try
		
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	
		return numBooks;
	}//inputBooks

	//print out the bookrecord
	public static void printRecord(ArrayList<LibraryBook> books, int location){
			
		System.out.println("Record #" + (location + 1) + ":\n");
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println("Title:          " + books.get(location).getTitle());
		System.out.println("Author's Name:  " + books.get(location).getAuthor());
		System.out.println("Copyright:      " + books.get(location).getCopyright());
		System.out.println("Price:          " + books.get(location).getPrice());
		System.out.println("Genre:          " + books.get(location).getGenre()  );       
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n");
	}//printRecord

	//display the record one by one at a time
	public static void displayRecords(ArrayList<LibraryBook> books, int numBooks){
		String enter = "enter";
		Scanner input = new Scanner(System.in);

		for (int i = 0; i < numBooks; i++){
			printRecord(books,i);
		
			System.out.print("Please Hit Return to Continue or M for Menu...");	
			enter = input.nextLine();
		
			//when user hit return the record continue to display
			if(enter.equals("")){
				clearScreen();
				continue;	
			}	
			//when user hit "m" or "M",the program return to the menu bar
			else if(enter.equals("m") || enter.equals("M")){
				clearScreen();
				break;
			}	
			else{
				//error when the user hit the error input
				while(!enter.equals("")){
					System.out.println("You had made an error input!");
					System.out.print("Please hit return to continue ...");	
					enter = input.nextLine();
					clearScreen();
				}
			}				
		}
	}

	//a method that sort the record in the file
	public static void sortRecords(ArrayList<LibraryBook> books){
		int minIndex, index, j;
		LibraryBook temp; 
		int pass = 0;
	
		//Selection Sort on Arraylist	
		for(index = 0; index < books.size()-1; index++){
			minIndex = index;
			for(j = minIndex+1; j < books.size(); j++)
				if(books.get(j).getTitle().compareTo(books.get(minIndex).getTitle()) < 0)
					minIndex = j;

			if (minIndex != index){
				temp = books.get(index);
				books.set(index,books.get(minIndex));
				books.set(minIndex,temp);
			}
		}
	}//sortRecord

	//use a binary Search to search books by title	
	public static int binarySearch(ArrayList<LibraryBook> books, String key){
		
		int first = 0, last = books.size()-1, middle, location;
		boolean found = false;
		do{
			middle = (first + last)/2;
			if(key.compareTo(books.get(middle).getTitle()) == 0)
				found = true;
			else if(key.compareTo(books.get(middle).getTitle()) < 0)
				last = middle - 1;
			else 
				first = middle + 1;
		} while ((! found) && (first <= last));
		location = middle;
		return (found ? location : -1);	
	}

	//method that print out the seacrhing result
	public static void searchTitle(ArrayList<LibraryBook> books,String key){
		String enter1 = "enter";
		Scanner input1 = new Scanner(System.in);
		int loc = binarySearch(books,key);
		if(loc != -1){
			clearScreen();
			System.out.println("Book Found in Alphbetized List in :");
			System.out.println();
			printRecord (books,loc);
			System.out.print("Please hit return to continue ...");	
			enter1 = input1.nextLine();
			clearScreen();
		}
		else{
			//enter return if the  book was not found
			System.out.println("Sorry, the book was not found. ");
			System.out.println("Please Hit Return to Continue...");
			enter1 = input1.nextLine();
		
			if(enter1.equals("")){
			
			}		
			else{
				while(!enter1.equals("")){
					System.out.println("You had made an error input!");
					System.out.print("Please hit return to continue ...");	
					enter1 = input1.nextLine();
				}
			}
		}
	}

	//method that display the Menu bar
	public static void displayMenu(){
	
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");	
		System.out.println("              THE GREAT BOOKS SEARCH PROGRAM                    ");
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println();
		System.out.println("1) Display all book records                                     ");
		System.out.println("2) Search for a book by Title                                   ");
		System.out.println("3) Exit Search Program                                          ");
		System.out.println();
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println();
		System.out.println("Please Enter Your Choice > ");
		
	}
	
	//method that clear the screen
	public static void clearScreen(){
		System.out.println("\u001b[H\u001b[2J");
	}
	
}//Great Books
