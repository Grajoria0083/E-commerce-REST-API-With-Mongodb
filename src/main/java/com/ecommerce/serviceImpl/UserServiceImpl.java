package com.ecommerce.serviceImpl;

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

        user.setId(sequences.getNextSequence("user"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getAddresss()!=null){
                List<Address> list = user.getAddresss();
                for (Address address:list){
                    address.setId(sequences.getNextSequence("address"));
                    address.setUser_id(user.getId());
                    addressRepo.save(address);
                }
            }
        user.setCreated_at(LocalDateTime.now().now());
        user.setUpdated_at(LocalDateTime.now().now());
        return userRepository.save(user);

    }

    @Override
    public User_details addUser_details(User_details userDetails) throws UserException {
        Optional<User> optionalUser = userRepository.findById(userDetails.getUser_id());
        if (optionalUser.isPresent()){
            userDetails.setId(sequences.getNextSequence("user_details"));
            userDetailsRepo.save(userDetails);
        }
        throw new RuntimeException("invalid user id");
    }

    @Override
    public User_details getUser_detailsById(Integer udId) throws UserException {
        Optional<User_details> optionalUser = userDetailsRepo.findById(udId);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        else {
            throw new RuntimeException("invalid user id");
        }
    }

    @Override
    public User_details updateUser_details(User_details userDetails) throws UserException {
        Optional<User_details> optionalUser = userDetailsRepo.findById(userDetails.getId());
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
            user.setUpdated_at(LocalDateTime.now().now());
            List<Address> list = user.getAddresss();
            System.out.println("List of address "+list);
            for (Address address:list){
                System.out.println(address.getAddress_1()+" ===== "+address.getId());
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
    public String Wallet(UserRequestModal urm) throws UserException {
        Optional<Wallet> wallet = walletRepository.findByUserId(urm.getUserId());
        if (wallet.isPresent()){
            if (wallet.get().getPassword().equals(urm.getPassword())){
                return "Your current balance is : " +wallet.get().getBalance();
            }
            throw new UserException("Invalid password!");
        }
        throw new UserException("Invalid user id!");
    }


//    ===========================================================

//    @Override
//    Wallet createWallet(Wallet wallet){
//        return walletRepository.save(wallet);
//    }
//
//
//    @Override
//    Integer Wallet(Integer userId, String password) throws UserException {
//        Optional<Wallet> wallet = walletRepository.findByUserId(userId);
//        if (wallet.isPresent() && wallet.get().getPassword().equals(password)){
//            return wallet.get().getBalance();
//        }
//        throw new UserException("Invalid user id!");
//    }


}
