package com.ecommerce.controller;


import com.ecommerce.DTO.LoginModal;
import com.ecommerce.DTO.OrderFilterRequestModal;
import com.ecommerce.DTO.UserDTO;
import com.ecommerce.Exception.OrderException;
import com.ecommerce.Exception.UserException;
import com.ecommerce.model.*;
import com.ecommerce.service.AdminService;
import com.ecommerce.service.LoginService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminControllerThymeleaf {

    @Autowired
    UserService userService;


    @Autowired
    LoginService loginService;


    @Autowired
    OrderService orderService;


    @Autowired
    AdminService adminService;


    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("greeting", "Welcome to our dynamic website!");
        return "hello";
    }


    @GetMapping("/adminLogin")
    public String adminLogin(Model model) throws UserException {
        model.addAttribute("LoginModal", new LoginModal());
        return "admin";
    }


    @PostMapping("/adminDashboard")
    public String adminDashboard( Model model, @ModelAttribute("LoginModal")LoginModal loginModal) throws UserException {

        User user = loginService.login(loginModal);
        System.out.println("loginModal "+loginModal.getUsername()+loginModal.getPassword());
        System.out.println("user "+user);
        if (user!=null) {
            model.addAttribute("login", user);
            model.addAttribute("recordcounts", adminService.viewRecordCount());
        }
        else {
            return "redirect:/adminLogin";
        }
        return "index";
    }

    @GetMapping("/getOrders")
    public String getAllOrders(Model model) throws OrderException {
        List<Order> orders = orderService.getOrders();
        model.addAttribute("ordersDetails", orders);
        return "tables";
    }

    @GetMapping("/getOrderDetail/{id}")
    public String getOrderDetail(Model model, @PathVariable("id") Integer id) throws OrderException {
        CartCheckout cartCheckout = orderService.getOrderById(id).getOrderDetails();
        if (cartCheckout!=null)
            model.addAttribute("productList", orderService.getOrderById(id).getOrderDetails().getProductList());
        return "productDetails";
    }


    @GetMapping("/orderFilter")
    public String orderFiler(Model model) throws OrderException {
        model.addAttribute("OrderFilterRequestModal", new OrderFilterRequestModal());
        return "orderFilter";
    }

    @PostMapping("/orderFilterRequest")
    public String getOrderDetail(Model model, @ModelAttribute("OrderFilterRequestModal") OrderFilterRequestModal ofrm) throws OrderException {
        System.out.println("ofrm "+ofrm.getOrderStatus()+ofrm.getPaymentStatus());
        model.addAttribute("ordersDetails", orderService.orderFilter(ofrm));
        return "tables";
    }

    @GetMapping("/adminDashboard")
    public String getBack(Model model) throws OrderException {
        model.addAttribute("recordcounts", adminService.viewRecordCount());
        return "index";
    }

    @GetMapping("/tables")
    public String tables(Model model) throws OrderException {
        return "tables";
    }


    @GetMapping("/getUsers")
    public String getAllUsers(Model model) throws UserException {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "user";
    }

    @GetMapping("/userRequst")
    public String saveUser(Model model) throws UserException {
        model.addAttribute("userDTO", new UserDTO());
        return "addUserForm";
    }

    @PostMapping("/saveUser")
    public String saveUser(Model model, @ModelAttribute("user") UserDTO userDTO) throws UserException {
        System.out.println("user "+userDTO);
        User u = userService.addUser(dataSet(userDTO));
        model.addAttribute("user", u);
        return "redirect:/getUsers";
    }

    @GetMapping("/updateUser/{id}")
    public String updateUser(Model model, @ModelAttribute("user") @PathVariable("id") Integer id) throws UserException {
        UserDTO userDTO = dataUpdate(userService.getUserById(id));
        System.out.println("userDTO "+userDTO);
        model.addAttribute("data", userDTO);
        return "updateUserForm";
    }

    @PostMapping("/updateUsers")
    public String updateUser(Model model, @ModelAttribute("data") UserDTO userDTO) throws UserException {
      //  System.out.println("userId "+id);
        System.out.println("user "+userDTO);
        User user = dataSet(userDTO);
        user.setId(userDTO.getUserId());
        User u = userService.updateUser(user);
        model.addAttribute("user", u);
        return "redirect:/getUsers";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(Model model, @PathVariable("id") Integer id) throws UserException {
        User users = userService.deleteUser(id);
        model.addAttribute("users", users);
        return "redirect:/getUsers";
    }





    private User dataSet(UserDTO userDTO){
        User user = new User();
        user.setFirst_name(userDTO.getFirst_name());
        user.setLast_name(userDTO.getLast_name());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        user.setPassword(userDTO.getPassword());
        Address address = new Address();
        address.setAddress_1(userDTO.getAddress_1());
        address.setAddress_2(userDTO.getAddress_2());
        address.setCity(userDTO.getCity());
        address.setState(userDTO.getState());
        address.setCountry(userDTO.getCountry());
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        user.setAddresss(addressList);
        return user;
    }

    private UserDTO dataUpdate(User userDTO){
        UserDTO user = new UserDTO();
        user.setUserId(userDTO.getId());
        user.setFirst_name(userDTO.getFirst_name());
        user.setLast_name(userDTO.getLast_name());
        user.setEmail(userDTO.getEmail());
        user.setMobile(userDTO.getMobile());
        user.setPassword(userDTO.getPassword());
        user.setAddress_1(userDTO.getAddresss().get(0).getAddress_1());
        user.setAddress_2(userDTO.getAddresss().get(0).getAddress_2());
        user.setCity(userDTO.getAddresss().get(0).getCity());
        user.setCountry(userDTO.getAddresss().get(0).getCountry());
//        System.out.println("dataUpdate "+user);
        return user;
    }

}
