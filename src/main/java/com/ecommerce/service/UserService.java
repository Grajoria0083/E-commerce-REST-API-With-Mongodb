package com.ecommerce.service;

import com.ecommerce.Exception.UserException;
import com.ecommerce.model.User;
import com.ecommerce.DTO.UserRequestModal;
import com.ecommerce.model.User_details;
import com.ecommerce.model.Wallet;

import java.util.List;

public interface UserService {

    public User addUser(User user) throws UserException;

    public User_details addUser_details(User_details userDetails) throws UserException;

    public User_details getUser_detailsById(Integer udId) throws UserException;

    public User_details updateUser_details(User_details userDetails) throws UserException;

    public User updateUser(User user) throws UserException;

    public List<User> getUsers() throws UserException;

    public User getUserById(Integer Id) throws UserException;

    public String updateUserPassword(UserRequestModal urm) throws UserException;

    public User deleteUser(Integer Id) throws UserException;

    Wallet createWallet(Wallet wallet) throws UserException;

//    Integer checkBalance(Integer userId, String pw) throws UserException;
    String checkBalance(UserRequestModal urm) throws UserException;


}
