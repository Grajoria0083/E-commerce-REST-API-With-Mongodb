package com.ecommerce.serviceImpl;

import com.ecommerce.DTO.UserRequestModal;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.*;
import com.ecommerce.repository.AddressRepo;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.repository.User_detailsRepo;
import com.ecommerce.repository.WalletRepository;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    User_detailsRepo userDetailsRepo;

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    CustomSequences sequences;


    @Autowired
    WalletRepository walletRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public User addUser(User user) throws UserException {

        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (!optionalUser.isPresent()){
            user.setId(sequences.getNextSequence("user"));
            user.setRole("ROLE_"+user.getRole().toUpperCase());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreated_at(LocalDateTime.now());
            if (user.getAddresss()!=null){
                List<Address> list = user.getAddresss();
                for (Address address:list){
                    address.setId(sequences.getNextSequence("address"));
                    address.setUser_id(user.getId());
                    addressRepo.save(address);
                }
            }
            return userRepository.save(user);
        }
        throw new UserException("this email is registered already!");

    }

    @Override
    public UserDetails addUser_details(UserDetails userDetails) throws UserException {
        Optional<User> optionalUser = userRepository.findById(userDetails.getUserId());
        if (optionalUser.isPresent()){
            Optional<UserDetails> optionalUserDetails = userDetailsRepo.findByUserId(userDetails.getUserId());
            if (optionalUserDetails.isPresent()){
                throw new UserException("this user details is filled already");
            }
            userDetails.setId(sequences.getNextSequence("user_details"));
            return userDetailsRepo.save(userDetails);
        }
        throw new UserException("invalid user id");
    }

    @Override
    public UserDetails getUser_detailsById(Integer udId) throws UserException {
        Optional<UserDetails> optionalUser = userDetailsRepo.findById(udId);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        else {
            throw new RuntimeException("invalid user id");
        }
    }

    @Override
    public UserDetails updateUser_details(UserDetails userDetails) throws UserException {
        Optional<UserDetails> optionalUser = userDetailsRepo.findById(userDetails.getId());
        if (optionalUser.isPresent()){
            return userDetailsRepo.save(userDetails);
        }
        else {
            throw new RuntimeException("invalid user id");
        }
    }

    @Override
    public User updateUser(User user) throws UserException {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()){
            user.setCreated_at(optionalUser.get().getCreated_at());
            List<Address> list = user.getAddresss();
            System.out.println("List of address "+list);
            for (Address address:list){
                if (address.getId()==null){
                    address.setId(sequences.getNextSequence("address"));
                }
                addressRepo.save(address);
            }
            return userRepository.save(user);
        }
        else {
            throw new UserException("invalid user id");
        }
    }

    @Override
    public List<User> getUsers() throws UserException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()){
            throw new UserException("No User is registered!");
        }
        return users;
    }

    @Override
    public User getUserById(Integer id) throws UserException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        else {
            throw new RuntimeException("invalid user id");
        }
    }

    @Override
    public String updateUserPassword(UserRequestModal urm) throws UserException {

        Optional<User> optionalUser = userRepository.findById(urm.getUserId());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return  "password updated successfully!";
        }
        throw new UserException("invalid id");
    }

    @Override
    public User deleteUser(Integer id) throws UserException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            List<Address> addressesList = user.getAddresss();
            for (Address  address:addressesList){
                addressRepo.deleteById(address.getId());
            }
            userRepository.deleteById(id);
            return optionalUser.get();
        }
        else {
            throw new UserException("invalid id");
        }
    }

    @Override
    public Wallet createWallet(Wallet wallet) throws UserException {
        Optional<Wallet> optionalWallet = walletRepository.findByUserId(wallet.getUserId());
        if (optionalWallet.isPresent()){
            Wallet wallet1 = optionalWallet.get();
            wallet1.setBalance(wallet1.getBalance() + wallet.getBalance());
            return walletRepository.save(wallet1);
        }
        else {
            wallet.setId(sequences.getNextSequence("wallet"));
            return walletRepository.save(wallet);
        }

    }



    @Override
    public String checkBalance(UserRequestModal urm) throws UserException {
        Optional<Wallet> wallet = walletRepository.findByUserId(urm.getUserId());
        if (wallet.isPresent()){
            if (wallet.get().getPassword().equals(urm.getPassword())){
                return "Your current balance is : " +wallet.get().getBalance();
            }
            throw new UserException("Invalid password!");
        }
        throw new UserException("Invalid user id!");
    }



}
