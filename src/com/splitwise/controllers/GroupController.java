package com.splitwise.controllers;

import com.splitwise.exceptions.authentication.NotLoggedInException;
import com.splitwise.exceptions.notfound.GroupNotFoundException;
import com.splitwise.exceptions.notfound.UserNotFoundException;
import com.splitwise.exceptions.security.NotGroupOwnerException;
import com.splitwise.models.Group;
import com.splitwise.models.User;
import com.splitwise.repositories.GroupRepository;
import com.splitwise.repositories.UserRepository;
import com.splitwise.services.authentication.AuthenticationContext;
import com.splitwise.services.settleupstrategy.group.SettleGroupStrategy;

public class GroupController {
    GroupRepository groupRepository;
    UserRepository userRepository;
    SettleGroupStrategy settleGroupStrategy;

    public GroupController(GroupRepository groupRepository, UserRepository userRepository,
                           SettleGroupStrategy settleGroupStrategy) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.settleGroupStrategy = settleGroupStrategy;
    }

    public Group addGroup(AuthenticationContext authenticationContext, String name){
        User currenltyLoggedInUser = authenticationContext
                .getCurrentlyLoggedInUser()
                .orElseThrow(() -> new NotLoggedInException("User needs to login to create group"));
        Group group =new Group(currenltyLoggedInUser, name);
        group.getMembers().add(currenltyLoggedInUser);
        groupRepository.save(group);
        return group;
    }

    public void addMemberToGroup(AuthenticationContext authenticationContext, Long groupId, Long userId){
        User currenltyLoggedInUser = authenticationContext
                .getCurrentlyLoggedInUser()
                .orElseThrow(() -> new NotLoggedInException("User needs to login to add member to the group"));
        Group group = groupRepository
                .findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId.toString()));

        if(!group.getOwner().equals(currenltyLoggedInUser))
            throw new NotGroupOwnerException(currenltyLoggedInUser.toString());

        User userToAdd = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));

        group.getMembers().add(userToAdd);
        groupRepository.save(group);
    }

    public String settleUpGroup(AuthenticationContext authenticationContext, Long groupId){
        User currenltyLoggedInUser = authenticationContext
                .getCurrentlyLoggedInUser()
                .orElseThrow(() -> new NotLoggedInException("User needs to login to add member to the group"));
        Group group = groupRepository
                .findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId.toString()));

        if(!group.getOwner().equals(currenltyLoggedInUser))
            throw new NotGroupOwnerException(currenltyLoggedInUser.toString());

        return settleGroupStrategy.settleUp(group);
    }
}
