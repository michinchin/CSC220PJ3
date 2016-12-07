// DO NOT ADD NEW METHODS OR NEW DATA FIELDS!

package csc220project3.PJ3;

class Customer
{
    private int customerID;
    private int serviceTime;
    private int arrivalTime;
    private int finishTime;
    private int waitTime;

    // default constructor
    Customer()
    {
    }

    // constructor to set customerID, serviceTime and arrivalTime
    Customer(int customerid, int servicetime, int arrivaltime)
    {
  	customerID  = customerid;
  	serviceTime = servicetime;
  	arrivalTime = arrivaltime;
    }

    int getServiceTime() 
    {
  	return serviceTime; 
    }

    int getArrivalTime() 
    {
  	return arrivalTime; 
    }

    int getCustomerID() 
    {
  	return customerID; 
    }

    int getFinishTime() 
    {
  	return finishTime; 
    }

    int getWaitTime() 
    {
  	return waitTime; 
    }

    void setWaitTime(int time) 
    {
  	waitTime=time; 
    }

    void setFinishTime(int time) 
    {
  	finishTime=time; 
    }

    public String toString()
    {
    	return "customerID="+customerID+":serviceTime="+
               serviceTime+":arrivalTime="+arrivalTime+
	       ":watiTime="+waitTime+":finishTime="+finishTime;

    }

    public static void main(String[] args) {
        // quick check!
	Customer mycustomer = new Customer(1,5,35);
        mycustomer.setWaitTime(5);	
        mycustomer.setFinishTime(45);	
	System.out.println("Customer Info --> "+mycustomer);

    }
}
