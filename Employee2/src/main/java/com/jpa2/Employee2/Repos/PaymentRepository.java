package com.jpa2.Employee2.Repos;

import com.jpa2.Employee2.Entities.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment,Integer> {

}
