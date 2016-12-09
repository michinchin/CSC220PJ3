// DO NOT ADD NEW METHODS OR NEW DATA FIELDS!

package csc220project3.PJ3;

import javafx.scene.input.KeyCode;

class Cashier {

   // cashier id and current customer which is served by this cashier 
   private int cashierID;
   private Customer serveCustomer;

   // start time and end time of current interval
   private int startFreeTime;//start time when cashier is not serving customer
   private int endFreeTime;//end time when cashier starts to serve customer
   private int startBusyTime;//start time when cashier starts to serve customer
   private int endBusyTime;//end time when cashier is done serving customer

   // for keeping statistical data
   private int totalFreeTime;
   private int totalBusyTime;
   private int totalCustomers;

   // Constructor
   Cashier()
   {
	this(-1);
        this.startFreeTime = 0;
        this.startBusyTime = 0;
        this.endFreeTime = 0;
        this.endBusyTime = 0;
        this.totalBusyTime = 0;
        this.totalFreeTime = 0;
        this.totalCustomers = 0;
        
   }


   // Constructor with cashier id
   Cashier(int cashierId)
   {
       this.cashierID = cashierId;
       // add statements
   }

   // accessor methods

   int getCashierID() 
   {
	return cashierID;
   }

   Customer getCurrentCustomer() 
   {
	return serveCustomer;
   }

   int getEndBusyTime() 
   {
	return endBusyTime; 
   }


   // mutator methods

   void setStartFreeTime (int time)
   {
        startFreeTime=time;
   }

   void setStartBusyTime (int time)
   {

        startBusyTime=time;
   }

   void setEndFreeTime (int time)
   {
  	endFreeTime   = time;
   }

   void setEndBusyTime (int time)
   {
  	endBusyTime   = time;
   }
 
   void setCurrentCustomer(Customer aCustomer) 
   {
	serveCustomer=aCustomer;
   }


   // update statistical data 
   void updateTotalFreeTime()
   {
       totalFreeTime += endFreeTime-startFreeTime;
       
    // add statements
   }

   void updateTotalBusyTime()
   {
       totalBusyTime += endBusyTime-startBusyTime;
       
	// add statements
   }

   void updateTotalCustomers()
   {
       totalCustomers ++;
	// add statements
   }


   // Start serving a customer
   void startServeCustomer(Customer aCustomer, int currentTime)
   {
       setEndFreeTime(currentTime);
       updateTotalFreeTime();
       setStartBusyTime(currentTime);
       setEndBusyTime(aCustomer.getServiceTime()+currentTime);
       setCurrentCustomer(aCustomer);
       aCustomer.setWaitTime(currentTime-aCustomer.getArrivalTime());
       aCustomer.setFinishTime(currentTime + aCustomer.getServiceTime());
       updateTotalCustomers();
        // goal  : start serving aCustomer at currentTime
        //
	// steps : set endFreeTime, update TotalFreeTime
	//         set startBusyTime, endBusyTime, serveCustomer
  	//         set aCustomer waitTime, finsihTime 
	//         update totalCustomers
	
	// add statements
   }


   // End serving a customer
   Customer endServeCustomer()
   {
       updateTotalBusyTime();
       setStartFreeTime(endBusyTime);
       
  	// goal  : end serving aCustomer at endBusyTime
        //
	// steps : update TotalBusyTime, set startFreeTime
  	//         return serveCustomer 
	
	// add statements
        return serveCustomer; 
	
   }


   // functions for printing statistics :
   void printStatistics () 
   {
  	// print cashier statistics, see project statement

  	System.out.println("\t\tCashier ID             : "+cashierID);
  	System.out.println("\t\tTotal free time        : "+totalFreeTime);
  	System.out.println("\t\tTotal busy time        : "+totalBusyTime);
  	System.out.println("\t\tTotal # of customers   : "+totalCustomers);
  	if (totalCustomers > 0)
  	    System.out.format("\t\tAverage checkout time  : %.2f%n\n",(totalBusyTime*1.0)/totalCustomers);
   }

   public String toString()
   {
	return "CashierID="+cashierID+":startFreeTime="+startFreeTime+":endFreeTime="+endFreeTime+
	       ":startBusyTime="+startBusyTime+":endBusyTime="+endBusyTime+ 
	       ":totalFreeTime="+totalFreeTime+":totalBusyTime="+totalBusyTime+ 
	       ":totalCustomer="+totalCustomers+">>serveCustomer:"+serveCustomer;
   }

   public static void main(String[] args) {
        // quick check
        Customer mycustomer = new Customer(1,5,15);
	Cashier mycashier = new Cashier(5);
	mycashier.setStartFreeTime(0);
        System.out.println(mycashier);
        mycashier.startServeCustomer(mycustomer, 20);
        System.out.println("\n"+mycashier);
	mycashier.endServeCustomer();
        System.out.println("\n"+mycashier);
        System.out.println("\n\n");
	mycashier.printStatistics();

   }

};

