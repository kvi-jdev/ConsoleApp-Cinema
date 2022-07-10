package com.academy.service;

import com.academy.repository.FilmRepositoryImpl;


public class FilmServiceImpl extends FilmRepositoryImpl {

    private static FilmRepositoryImpl repositoryOperations;

    public FilmServiceImpl(FilmRepositoryImpl repositoryOperations) {
        FilmServiceImpl.repositoryOperations = repositoryOperations;
    }


}
