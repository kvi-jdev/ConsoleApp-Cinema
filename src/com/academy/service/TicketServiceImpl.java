package com.academy.service;


import com.academy.repository.TicketRepositoryImpl;

public class TicketServiceImpl extends TicketRepositoryImpl{

    private static TicketRepositoryImpl repositoryOperations;

    public TicketServiceImpl(TicketRepositoryImpl ticketRepository) {
        TicketServiceImpl.repositoryOperations = ticketRepository;
    }
}
