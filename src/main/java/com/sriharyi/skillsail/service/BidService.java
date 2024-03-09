package com.sriharyi.skillsail.service;

import java.util.List;

import com.sriharyi.skillsail.dto.BidDto;
import com.sriharyi.skillsail.dto.BidRequest;
import com.sriharyi.skillsail.model.Bid;

public interface BidService {

    BidDto createBid(BidRequest bidRequest);

    List<Bid> getAllBids();

    Bid getBidById(String id);

    Bid updateBid(String id, Bid bid);

    void deleteBid(String id);
}
