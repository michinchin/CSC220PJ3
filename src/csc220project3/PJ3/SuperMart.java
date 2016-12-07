package csc220project3.PJ3;

import java.util.*;
import java.io.*;
import jdk.nashorn.internal.ir.BreakNode;

// You may add new functions or data fields in this class 
// You may modify any functions or data members here
// You must use Customer, Cashier and CheckoutArea classes
// to implement SuperMart simulator

class SuperMart {

  // input parameters
  private int numCashiers, customerQLimit;
  private int chancesOfArrival, maxServiceTime;
  private int simulationTime, dataSource;
  // statistical data
  private int numGoaway, numServed, totalWaitingTime;
  private String fileName;
  // internal data
  private int counter;	             // customer ID counter
  private CheckoutArea checkoutarea; // checkout area object
  private Customer customer;
  private Scanner dataFile;	     // get customer data from file
  private Random dataRandom;	     // get customer data using random function

  // most recent customer arrival info, see getCustomerData()
  private boolean anyNewArrival;  
  private int serviceTime;

  // initialize data fields
  private SuperMart()
  {
//      this.numGoaway = 0;
//      this.numServed = 0;
//      this.totalWaitingTime = 0;
//
      this.counter = 0;
//      
//      this.checkoutarea = null;
        dataRandom = new Random();
//      
//      
	// add statements
  }

  private void openDataFile(String filename){
      File fn = new File(filename);
      String path = fn.getAbsolutePath();
      System.out.println(path);
      
      try{
          dataFile = new Scanner(fn);
      }catch(FileNotFoundException e){
          System.out.println("File not found!");
      } 
  }
 
  private void setupParameters()
  {   
      System.out.println("\t***  Enter Simulation Parameters  ***\n");
      Scanner input = new Scanner(System.in);
      System.out.print("Enter simulation time (positive integer)       : ");
      simulationTime = input.nextInt();
      System.out.print("Enter the number of cashiers                   : ");
      numCashiers = input.nextInt();
      System.out.print("Enter chances (0% < & <= 100%) of new customer : ");
      chancesOfArrival = input.nextInt();
      System.out.print("Enter maximum service time of customers        : ");
      maxServiceTime = input.nextInt();
      System.out.print("Enter customer queue limit                     : ");
      customerQLimit = input.nextInt();
      System.out.print("Enter 0/1 to get data from random/file         : ");
      dataSource = input.nextInt();

      if(dataSource == 1){
          System.out.print("Enter filename                                 : ");
          fileName = input.next();
          openDataFile(fileName);
      }
      //read data from file
      

          
	// read input parameters from users
	// setup dataFile or dataRandom
	// add statements  
  }

  // Refer to step 1 in doSimulation()
  private void getCustomerData()
  {
        if(dataSource == 1){
         try{
             anyNewArrival = (((dataFile.nextInt()%100)+1) <= chancesOfArrival);
             serviceTime = ((dataFile.nextInt())%maxServiceTime)+1;
         }catch(NoSuchElementException e){
             System.out.println(e);
         }catch(NullPointerException p){
             System.out.print(p); 
         }
      }else if(dataSource == 0){
         anyNewArrival = ((dataRandom.nextInt(100)+1) <= chancesOfArrival);
         serviceTime = dataRandom.nextInt(maxServiceTime)+1;
      }
	// get next customer data : from file or random number generator
	// set anyNewArrival and serviceTime
	// see Readme file for more info
	// add statements
  }

  private void doSimulation()
  {
	// add statements
        System.out.println("\t***  Start Simulation  ***");
        System.out.println("Customer #1 to #" + numCashiers + " are ready...");

	// Initialize CheckoutArea
        checkoutarea = new CheckoutArea(numCashiers, customerQLimit);

	// Time driver simulation loop
  	for (int currentTime = 0; currentTime < simulationTime; currentTime++) {


    		// Step 1: any new customer enters the checkout area?
    		getCustomerData();

                System.out.println("---------------------------------------------");
                System.out.println("Time: " + currentTime);
                
    		if (anyNewArrival) {
                    counter++;

                    customer = new Customer(counter, serviceTime, currentTime);
                    System.out.println("\tCustomer #"+ customer.getCustomerID() + " arrives with checkout time: " + customer.getServiceTime() + " units");

                    if(checkoutarea.isCustomerQTooLong()){
                        numGoaway++;
                        System.out.println("\tCustomer #" + customer.getCustomerID() + " has impatiently left the building");
                    }
                    else{
                        checkoutarea.insertCustomerQ(customer);
                        System.out.println("\tCustomer #" + customer.getCustomerID() + " decides to wait patiently to buy groceries");
                        customer.setWaitTime(currentTime-customer.getArrivalTime());
                    }
      		    // Step 1.1: setup customer data
      		    // Step 1.2: check customer waiting queue too long?
		    //           if customer queue is too long, update numGoaway
		    //           else goto customer queue
    		} else {
      		    System.out.println("\tNo new customer!");
    		}
                
    // Step 2: free busy cashiers that are done at currenttime, add to free cashierQ
                if (checkoutarea.sizeBusyCashierQ() != 0 && (checkoutarea.peekBusyCashierQ().getEndBusyTime() == currentTime)){
    			while(!checkoutarea.emptyBusyCashierQ() && (checkoutarea.peekBusyCashierQ().getEndBusyTime() == currentTime)){
    				Cashier busycashier = checkoutarea.removeBusyCashierQ();
    				System.out.println("\tCustomer #" + busycashier.endServeCustomer().getCustomerID() + " is done");
    		        System.out.println("\tCashier  #"+ busycashier.getCashierID()  + " is free");
    		        checkoutarea.insertFreeCashierQ(busycashier);
    		       
    		    }
    		}
                
                
          // Step 3: get free cashiers to serve waiting customers at currenttime
           
                if(checkoutarea.sizeFreeCashierQ() != 0 && checkoutarea.sizeCustomerQ() != 0){
                    Cashier cashierServing = checkoutarea.removeFreeCashierQ();
                    Customer servedCustomer = checkoutarea.removeCustomerQ();
                    cashierServing.startServeCustomer(customer, currentTime);
                    checkoutarea.insertBusyCashierQ(cashierServing);
                    System.out.println("\tCustomer #" + servedCustomer.getCustomerID() + " goes to checkout.");
                    System.out.println("\tCashier #" + cashierServing.getCashierID() +" is now serving customer #" + customer.getCustomerID() + " for " + customer.getServiceTime() + " units.");
                    numServed++;
                    
                    totalWaitingTime += (servedCustomer.getWaitTime());


                }else{
                    
                }
               
  	} // end simulation loop

  }

  private void printStatistics()
  {
      
	// add statements into this method!
	// print out simulation results
	// see the given example in project statement
        // you need to display all free and busy cashiers


  	// need to free up all customers in queue to get extra waiting time.
  	// need to free up all cashiers in queue to get extra free & busy time.
  }

  // *** main method to run simulation ****


  public static void main(String[] args) {
   	SuperMart runSuperMart=new SuperMart();
   	runSuperMart.setupParameters();
   	runSuperMart.doSimulation();
   	runSuperMart.printStatistics();
  }

}
