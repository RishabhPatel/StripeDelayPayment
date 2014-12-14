package helper

import com.stripe._
import com.stripe.Stripe._
import com.stripe.exception.StripeException
import com.stripe.model._
import com.typesafe.config._
import java.util.HashMap

object Stripe {  
  
  def stripePayment(customerId : String , Amount :Int ) : HashMap[String,String] = {
    
		///sceret key stripe
        Stripe.apiKey = "sceret key put here"
        //create charge  
        val chargeMap  =  new HashMap[String, Object]
        chargeMap put("amount", java.lang.Integer.valueOf(Amount))
        chargeMap put("currency", "usd")
        chargeMap put("customer",customerId)       
       
        
        val	striperesponse =  new HashMap[String,String]
        //handle exception
        try {          
           val  charge = Charge.create(chargeMap)            
                     
           striperesponse put("transcationId",charge.getId)
           striperesponse put("transcationPaid",charge.getPaid.toString)
           striperesponse put("Error","0")
           striperesponse put("Message","Transcation Successfully.")
           
        }catch{
          
        case ex: Exception => {
          
        	//Logger.info("exception = %s" format ex)
        		
        	striperesponse put("Error","1")
        	striperesponse put("Message","Your card number is incorrect.")
        		
         }
         case ex: com.stripe.exception.APIConnectionException =>{
           
            //Logger.info("exception = %s" format ex)
        		
        	striperesponse put("Error","2") 
            striperesponse put("Message","API connection Error.")
        	 	 
         }
         
         
      }  
    
    return striperesponse
    
  }
  
  def createCoustomer(cardNumber : String, cardName : String , Expmonth: Int, Expyear:Int ,  Address1: String , Address2: String ,  Zipcode: String , City : String , State : String , Country : String ) : HashMap[String,String] = {
  
	  	///sceret key stripe
        Stripe.apiKey = conf.getString("kreatelab.Stripe.apiKey")
        //create charge  map   
        val customerParams  =  new HashMap[String, Object]
        
        customerParams put("description", "Customer for test@example.com")        
        
        //println(cardNumber);
        
        //create card map
        val customerMap  = new HashMap[String,Object]       
        customerMap put("number", java.lang.String.valueOf(cardNumber))
        customerMap put("exp_month", java.lang.Integer.valueOf(Expmonth))
        customerMap put("exp_year", java.lang.Integer.valueOf(Expyear))
        customerMap put("name  ", cardName)
        customerMap put("address_line1", Address1)
        customerMap put("address_line2", Address2)
        customerMap put("address_zip", Zipcode)
        customerMap put("address_city", City)
        customerMap put("address_state", State)
        customerMap put("address_country", Country)
        customerParams put("card", customerMap)
        
        val	striperesponse =  new HashMap[String,String]
        //handle exception
        try {          
           val  customer = Customer.create(customerParams)           
                     
           striperesponse put("transcationId",customer.getId)
           striperesponse put("Error","0")
           striperesponse put("Message","Transcation Successfully.")
           
        }catch{
          
        case ex: Exception => {
          
        	//Logger.info("exception = %s" format ex)
        		
        	striperesponse put("Error","1")
        	striperesponse put("Message","Your card number is incorrect.")
        		
         }
         case ex: com.stripe.exception.APIConnectionException =>{
           
            //Logger.info("exception = %s" format ex)
        		
        	striperesponse put("Error","2") 
            striperesponse put("Message","API connection Error.")
        	 	 
         }
         
         
      }  
    
    return striperesponse
  }

}