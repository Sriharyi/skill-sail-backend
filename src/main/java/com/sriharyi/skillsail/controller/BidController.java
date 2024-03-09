package com.sriharyi.skillsail.controller;

import com.sriharyi.skillsail.dto.BidDto;
import com.sriharyi.skillsail.dto.BidRequest;
import com.sriharyi.skillsail.model.Bid;
import com.sriharyi.skillsail.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/bids")
public class BidController {

    private final BidService bidService;

    @PostMapping
    public ResponseEntity<BidDto> createBid(@RequestBody BidRequest bidRequest) {
        BidDto createdBid = bidService.createBid(bidRequest);
        return new ResponseEntity<>(createdBid, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Bid>> getAllBids() {
        List<Bid> bids = bidService.getAllBids();
        return new ResponseEntity<>(bids, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bid> getBidById(@PathVariable String id) {
        Bid bid = bidService.getBidById(id);
        return new ResponseEntity<>(bid, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bid> updateBid(@PathVariable String id, @RequestBody Bid bid) {
        Bid updatedBid = bidService.updateBid(id, bid);
        return new ResponseEntity<>(updatedBid, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBid(@PathVariable String id) {
        bidService.deleteBid(id);
        return new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);
    }

//    @GetMapping("/freelancer/{freelancerId}")
//    public ResponseEntity<List<Bid>> getBidsByFreelancerId(@PathVariable String freelancerId) {
//        List<Bid> bids = bidService.getBidsByFreelancerId(freelancerId);
//        return new ResponseEntity<>(bids, HttpStatus.OK);
//    }
//
//    @GetMapping("/project/{projectId}")
//    public ResponseEntity<List<Bid>> getBidsByProjectId(@PathVariable String projectId) {
//        List<Bid> bids = bidService.getBidsByProjectId(projectId);
//        return new ResponseEntity<>(bids, HttpStatus.OK);
//    }
//
//    @GetMapping("/project/{projectId}/freelancer/{freelancerId}")
//    public ResponseEntity<Bid> getBidByProjectIdAndFreelancerId(@PathVariable String projectId, @PathVariable String freelancerId) {
//        Bid bid = bidService.getBidByProjectIdAndFreelancerId(projectId, freelancerId);
//        return new ResponseEntity<>(bid, HttpStatus.OK);
//    }
//
//    @GetMapping("/project/{projectId}/freelancer/{freelancerId}/status/{status}")
//    public ResponseEntity<Bid> getBidByProjectIdAndFreelancerIdAndStatus(@PathVariable String projectId, @PathVariable String freelancerId, @PathVariable String status) {
//        Bid bid = bidService.getBidByProjectIdAndFreelancerIdAndStatus(projectId, freelancerId, status);
//        return new ResponseEntity<>(bid, HttpStatus.OK);
//    }


}
