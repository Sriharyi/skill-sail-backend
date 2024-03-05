package com.sriharyi.skillsail.service;

import com.sriharyi.skillsail.model.Bid;

import java.util.List;

public interface BidService {
    Bid createBid(Bid bid);

    List<Bid> getAllBids();

    Bid getBidById(String id);

    Bid updateBid(String id, Bid bid);

    void deleteBid(String id);
}
