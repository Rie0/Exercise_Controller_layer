package org.crud.bankmanagement.Controller;

import org.crud.bankmanagement.Api.ApiResponse;
import org.crud.bankmanagement.Model.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/bank_management")
public class Customer_Controller {

    ArrayList<Customer> customers = new ArrayList<Customer>();
    static int idCounter = 1;


    @GetMapping("/get/customers")
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    @PostMapping("post/customer")
    public ApiResponse addCustomer(@RequestBody Customer customer) {
        customer.setId(idCounter);
        customers.add(customer);
        idCounter++;
        return new ApiResponse("200","Customer added successfully.");
    }

    @PutMapping("update/customer/{index}")
    public ApiResponse updateCustomer(@PathVariable int index, @RequestBody Customer customer) {
     boolean found = false;
       if (customer.getId() == 0){ //if no new id is given returns the original id
           customer.setId(customers.get(index).getId());
       }
       for (Customer c : customers) {
           if (c.getId() == customer.getId()){
               return new ApiResponse("200","ID already taken.");
           }
       }
            customers.set(index,customer);
            return new ApiResponse("200","Customer updated successfully.");
    }

    @PutMapping("update/customer/{index}/deposit/{depositAmount}")
    public ApiResponse updateCustomerDeposit(@PathVariable int index, @PathVariable double depositAmount) {
        try {
            isAmountValid(depositAmount);
            customers.get(index).setBalance(customers.get(index).getBalance()+depositAmount);
            return new ApiResponse("200","Customer updated successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ApiResponse("200","Customer not updated successfully.");
        }
    }

    @PutMapping("update/customer/{index}/withdraw/{withdrawAmount}")
    public ApiResponse updateCustomerWithdraw(@PathVariable int index,@PathVariable double withdrawAmount) {
       try {
           isAmountValid(withdrawAmount);
           if (withdrawAmount > customers.get(index).getBalance()) {
               return new ApiResponse("200", "Not enough balance.");
           } else {
               customers.get(index).setBalance(customers.get(index).getBalance() - withdrawAmount);
               return new ApiResponse("200", "Balance updated successfully.");
           }
       }catch (Exception e) {
           System.out.println(e.getMessage());
           return new ApiResponse("200", "Customer not updated successfully.");
       }
       }

    @DeleteMapping("delete/customer/{index}")
    public ApiResponse deleteCustomer(@PathVariable int index) {
        customers.remove(index);
        return new ApiResponse("200","Customer deleted successfully.");
    }


    //methods
    public void isAmountValid(double amount) throws Exception{
        if (amount<0){
            throw new Exception("Negative amount not accepted");
        }else if (amount==0){
            throw new Exception("Zero amount not accepted");
        }
    }

}
