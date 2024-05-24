package com.salespoint.api.services;

import org.springframework.stereotype.Service;

import com.salespoint.api.dtos.CustomerDto;
import com.salespoint.api.entities.CustomerEntity;

@Service
public interface CustomerService {

    public Iterable<CustomerEntity> getAllCustomers();

    public CustomerEntity getCustomerById(Long id);

    public CustomerEntity getCustomerByPhone(String phone);

    public CustomerEntity createCustomer(CustomerDto customerDto);

    public CustomerEntity updateCustomer(Long id, CustomerDto customerDto);

    public void deleteCustomer(Long id);
}
