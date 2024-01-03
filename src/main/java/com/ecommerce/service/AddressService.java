package com.ecommerce.service;

import com.ecommerce.Exception.AddressException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Address;
import com.ecommerce.model.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AddressService {

    public User addAddressToUSer(@RequestBody Address address) throws AddressException, UserException;

    public User updateAddressToUSer(@RequestBody Address address) throws AddressException, UserException;

    public List<Address> getAddresses() throws AddressException;

}
