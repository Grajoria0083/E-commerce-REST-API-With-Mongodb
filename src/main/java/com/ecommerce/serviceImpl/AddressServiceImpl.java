package com.ecommerce.serviceImpl;

import com.ecommerce.Exception.AddressException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.Address;
import com.ecommerce.model.CustomSequences;
import com.ecommerce.model.User;
import com.ecommerce.repository.AddressRepo;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {


    @Autowired
    AddressRepo addressRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    CustomSequences sequences;




    @Override
    public User addAddressToUSer(Address address) throws AddressException, UserException {
        address.setId(sequences.getNextSequence("address"));
        Optional<User> optionalUser = userRepo.findById(address.getUser_id());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.getAddresss().add(address);
            userRepo.save(user);
            addressRepo.save(address);
            return user;
        }
        throw new UserException("Invalid user id!");
    }


    @Override
    public User updateAddressToUSer(Address address) throws AddressException, UserException {
        Optional<User> optionalUser = userRepo.findById(address.getUser_id());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            Address address1 = addressRepo.findById(address.getId()).get();
            addressRepo.deleteById(address.getId());
            List<Address> addressList = user.getAddresss();
            addressList.remove(address1);
            addressList.add(address);
            userRepo.save(user);
            addressRepo.save(address);
            return user;
        }
        throw new UserException("Invalid user id!");
    }

    @Override
    public List<Address> getAddresses() throws AddressException {
        return addressRepo.findAll();
    }
}
