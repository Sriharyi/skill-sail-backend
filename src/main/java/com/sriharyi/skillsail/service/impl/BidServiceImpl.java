package com.sriharyi.skillsail.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sriharyi.skillsail.dto.BidDto;
import com.sriharyi.skillsail.dto.BidRequest;
import com.sriharyi.skillsail.dto.EmployerBidResponse;
import com.sriharyi.skillsail.dto.FreelancerBidResponse;
import com.sriharyi.skillsail.exception.BidNotFoundException;
import com.sriharyi.skillsail.exception.ProjectNotFoundException;
import com.sriharyi.skillsail.model.Bid;
import com.sriharyi.skillsail.model.FreeLancerProfile;
import com.sriharyi.skillsail.model.Project;
import com.sriharyi.skillsail.model.enums.BidStatus;
import com.sriharyi.skillsail.model.enums.ProjectStatus;
import com.sriharyi.skillsail.repository.BidRepository;
import com.sriharyi.skillsail.repository.ProjectRepository;
import com.sriharyi.skillsail.service.BidService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;

    private final ProjectRepository projectRepository;

    @Override
    public BidDto createBid(BidRequest bidRequest) {
        Bid bid = convertToEntity(bidRequest);
        Bid savedBid = bidRepository.save(bid);
        return convertToDto(savedBid);
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
        if (bid.isDeleted()) {
            throw new BidNotFoundException("Bid not found");
        }
        return bid;
    }

    @Override
    public Bid updateBid(String id, Bid bid) {
        Bid existingBid = bidRepository.findById(id).orElseThrow(
                () -> new BidNotFoundException("Bid not found"));
        if (existingBid.isDeleted()) {
            throw new BidNotFoundException("Bid not found");
        }
        bid.setId(id);
        return bidRepository.save(bid);
    }

    @Override
    public void deleteBid(String id) {
        Bid existingBid = bidRepository.findById(id).orElseThrow(
                () -> new BidNotFoundException("Bid not found"));
        if (existingBid.isDeleted()) {
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
                .bidStatus( bid.getBidStatus().name())
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
                .bidStatus(BidStatus.PENDING)
                .deleted(false)
                .build();
    }

    @Override
    public List<EmployerBidResponse> getBidsByProjectId(String projectId) {
        List<Bid> bids = bidRepository.findAllByProjectId_Id(projectId);
        return convertToEmployerBidResponse(bids);

    }

    private List<EmployerBidResponse> convertToEmployerBidResponse(List<Bid> bids) {
        List<EmployerBidResponse> response = bids.stream().map(
                (bid) -> {
                    //TODO: Add rating from rating service calculate average rating for freelancer
                    //Rating rating = ratingService.getRatingByFreelancerId(bid.getFreelancerId().getId());          
                    EmployerBidResponse bidResponse = EmployerBidResponse.builder()
                            .id(bid.getId())
                            .freelancerName(bid.getFreelancerId().getDisplayName())
                            .bidAmount(bid.getBidAmount())
                            .proposal(bid.getProposal())
                            .build();
                    return bidResponse;

                }).toList();
        return response;
    }

    @Override
    public Boolean hireFreelancer(String id) {
        Bid existingBid = bidRepository.findById(id).orElseThrow(
                () -> new BidNotFoundException("Bid not found"));
        if (existingBid.isDeleted()) {
            throw new BidNotFoundException("Bid not found");
        }
        existingBid.setBidStatus(BidStatus.ACCEPTED);
        bidRepository.save(existingBid);
        String projectId = existingBid.getProjectId().getId();
        //update project status
        Project project = projectRepository.findById(projectId).orElseThrow(
            () -> new ProjectNotFoundException("Project not found"));
        
        project.setStatus(ProjectStatus.ACTIVE);
        project.setSelectedFreelancerId(existingBid.getFreelancerId());
        project.setBidAmount(existingBid.getBidAmount());
        projectRepository.save(project);


        List<Bid> otherBids = bidRepository.findAllByProjectId_Id(projectId);

        for (Bid bid : otherBids){
            if(bid.getBidStatus().equals(BidStatus.PENDING))
            {
                bid.setBidStatus(BidStatus.REJECTED);
            }
        }
        bidRepository.saveAll(otherBids);
        return true;
    }
    
    @Override
    public List<FreelancerBidResponse> getBidsByFreelancerId(String freelancerId) {
        List<Bid> bids = bidRepository.findAllByFreelancerId_Id(freelancerId);
        return convertToFreelancerBidResponse(bids);
    }

    private List<FreelancerBidResponse> convertToFreelancerBidResponse(List<Bid> bids) {
        List<FreelancerBidResponse> response = bids.stream().map(
                (bid) -> {
                    Project project = projectRepository.findById(bid.getProjectId().getId()).orElseThrow(
                            () -> new ProjectNotFoundException("Project not found"));
                    FreelancerBidResponse bidResponse = FreelancerBidResponse.builder()
                            .id(bid.getId())
                            .projectId(bid.getProjectId().getId())
                            .projectName(bid.getProjectId().getTitle())
                            .companyName(project.getEmployerProfileId().getCompanyName())
                            .BidDeadline(bid.getProjectId().getBidDeadline())
                            .bidAmount(bid.getBidAmount())
                            .status(bid.getBidStatus().name())
                            .build();
                    return bidResponse;

                }).toList();
        return response;
    }

    @Override
    public Boolean isAlreadyBidByProjectIdAndFreelancerId(String projectId, String freelancerId) {
        Bid bid = bidRepository.findByProjectId_IdAndFreelancerId_Id(projectId, freelancerId);
        if(bid != null) {
            return true;
        }
        return false;
    }


    
}
