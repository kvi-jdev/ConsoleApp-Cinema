package com.academy.service;


import com.academy.repository.UserRepositoryImpl;

public class UserServiceImpl extends UserRepositoryImpl {

    private static UserRepositoryImpl repositoryOperations;

    public UserServiceImpl(UserRepositoryImpl repositoryOperations) {
        UserServiceImpl.repositoryOperations = repositoryOperations;
    }



}