package com.sriharyi.skillsail.service.impl;


import com.sriharyi.skillsail.dto.BidDto;
import com.sriharyi.skillsail.dto.BidRequest;
import com.sriharyi.skillsail.exception.BidNotFoundException;
import com.sriharyi.skillsail.model.Bid;
import com.sriharyi.skillsail.model.FreeLancerProfile;
import com.sriharyi.skillsail.model.Project;
import com.sriharyi.skillsail.repository.BidRepository;
import com.sriharyi.skillsail.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BidServiceImpl  implements BidService {

    private final BidRepository bidRepository;

    @Override
    public BidDto createBid(BidRequest bidRequest) {
        Bid bid = convertToEntity(bidRequest);
        Bid savedBid = bidRepository.save(bid);
        return  convertToDto(savedBid);
    }

    @Override
    public List<Bid> getAllBids() {
        List<Bid> bids = bidRepository.findAll();
        return bids;
    }

    @Override
    public Bid getBidById(String id) {
        Bid bid = bidRepository.findById(id).orElseThrow(
                () -> new BidNotFoundException("Bid not found"));
        if(bid.isDeleted()) {
            throw new BidNotFoundException("Bid not found");
        }
        return bid;
    }

    @Override
    public Bid updateBid(String id, Bid bid) {
        Bid existingBid = bidRepository.findById(id).orElseThrow(
                () -> new BidNotFoundException("Bid not found"));
        if(existingBid.isDeleted()) {
            throw new BidNotFoundException("Bid not found");
        }
        bid.setId(id);
        return bidRepository.save(bid);
    }

    @Override
    public void deleteBid(String id) {
        Bid existingBid = bidRepository.findById(id).orElseThrow(
                () -> new BidNotFoundException("Bid not found"));
        if(existingBid.isDeleted()) {
            throw new BidNotFoundException("Bid not found");
        }
        existingBid.setDeleted(true);
        bidRepository.save(existingBid);
    }

    protected BidDto convertToDto(Bid bid) {
        return BidDto.builder()
                .id(bid.getId())
                .projectId(bid.getProjectId().getId())
                .freelancerId(bid.getFreelancerId().getId())
                .bidAmount(bid.getBidAmount())
                .proposal(bid.getProposal())
                .accepted(bid.isAccepted())
                .build();
    }

    protected Bid convertToEntity(BidRequest bidRequest) {
        Project project = Project.builder().id(bidRequest.getProjectId()).build();
        FreeLancerProfile freeLancerProfile = FreeLancerProfile.builder().id(bidRequest.getFreelancerId()).build();

        return Bid.builder()
                .projectId(project)
                .freelancerId(freeLancerProfile)
                .bidAmount(bidRequest.getBidAmount())
                .proposal(bidRequest.getProposal())
                .accepted(false)
                .deleted(false)
                .build();
    }
}
