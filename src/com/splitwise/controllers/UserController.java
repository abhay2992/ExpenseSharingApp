package com.splitwise.controllers;

import com.splitwise.dtos.UserDTO;
import com.splitwise.exceptions.authentication.NotLoggedInException;
import com.splitwise.exceptions.authentication.PasswordDoesNotMatchException;
import com.splitwise.exceptions.validation.DuplicateUsernameException;
import com.splitwise.models.Expense;
import com.splitwise.models.Group;
import com.splitwise.models.User;
import com.splitwise.repositories.UserRepository;
import com.splitwise.services.authentication.AuthenticationContext;
import com.splitwise.services.authentication.PasswordEncoder;
import com.splitwise.services.settleupstrategy.user.SettleUserStrategy;

import java.util.List;

public class UserController {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder = null;

    public UserController(UserRepository userRepository, SettleUserStrategy settleUserStrategy) {
        this.userRepository = userRepository;
        this.settleUserStrategy = settleUserStrategy;
    }

    SettleUserStrategy settleUserStrategy;

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserDTO details) {
        User user = new User();

        if (userRepository.findByUsername(details.username).isPresent()) {
            throw new DuplicateUsernameException("Username already exists");
        }
        user.setFullname(details.fullname);
        user.setPhone(details.phone);
        user.setUsername(details.username);
        user.setHashedSaltedPassword(passwordEncoder.encode(details.password, user.getUsername()));

        userRepository.create(user);
        return user;
    }

    public void changePassword(AuthenticationContext authenticationContext, String oldPassword, String newPassword) {
        User currentlyLoggedInUser = authenticationContext
                .getCurrentlyLoggedInUser()
                .orElseThrow(() -> new NotLoggedInException("User needs to login to change password"));
        if (!passwordEncoder.encode(oldPassword, currentlyLoggedInUser.getUsername()).equals(currentlyLoggedInUser.getHashedSaltedPassword())) {
            throw new PasswordDoesNotMatchException("Incorrect Password.");
        }
        currentlyLoggedInUser.setHashedSaltedPassword(passwordEncoder.encode(newPassword, currentlyLoggedInUser.getUsername()));
        userRepository.save(currentlyLoggedInUser);
    }

    public void updateProfile(AuthenticationContext authenticationContext, UserDTO details) {
        User currenltyLoggedInUser = authenticationContext
                .getCurrentlyLoggedInUser()
                .orElseThrow(() -> new NotLoggedInException("User needs to login to update profile"));

        currenltyLoggedInUser.setFullname(details.fullname);
        currenltyLoggedInUser.setPhone(details.phone);

        userRepository.save(currenltyLoggedInUser);
    }

    public Double getMyTotalOwedAmount(AuthenticationContext authenticationContext) {
        User currenltyLoggedInUser = authenticationContext
                .getCurrentlyLoggedInUser()
                .orElseThrow(() -> new NotLoggedInException("User needs to login to see its total owed amount"));
        return currenltyLoggedInUser.getTotalOwedAmount();
    }

    public List<Expense> getExpenseHistory(AuthenticationContext authenticationContext) {
        User currenltyLoggedInUser = authenticationContext
                .getCurrentlyLoggedInUser()
                .orElseThrow(() -> new NotLoggedInException("User needs to login to see expense history"));
        return currenltyLoggedInUser.getExpenses();
    }

    public List<Group> getGroups(AuthenticationContext authenticationContext) {
        User currenltyLoggedInUser = authenticationContext
                .getCurrentlyLoggedInUser()
                .orElseThrow(() -> new NotLoggedInException("User needs to login to get groups"));
        return currenltyLoggedInUser.getGroups();
    }

    public String settleUpUser(AuthenticationContext authenticationContext){
        User currentlyLoggedInUser = authenticationContext
                .getCurrentlyLoggedInUser()
                .orElseThrow(() -> new NotLoggedInException("User needs to login to settle up"));
        return settleUserStrategy.settleUp(currentlyLoggedInUser);
    }

}
