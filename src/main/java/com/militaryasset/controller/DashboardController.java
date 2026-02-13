package com.militaryasset.controller;

import com.militaryasset.model.Assignment;
import com.militaryasset.model.Base;
import com.militaryasset.model.Purchase;
import com.militaryasset.model.Transfer;
import com.militaryasset.model.User;
import com.militaryasset.repository.AssetRepository;
import com.militaryasset.repository.AssignmentRepository;
import com.militaryasset.repository.BaseRepository;
import com.militaryasset.repository.PurchaseRepository;
import com.militaryasset.repository.TransferRepository;
import com.militaryasset.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private UserRepository userRepository;

    
     @GetMapping("/summary")
    public Map<String, Object> getDashboardSummary(java.security.Principal principal) {

        String username = principal.getName();

        // FIX HERE
        com.militaryasset.model.User user =
                userRepository.findByUsername(username).orElseThrow();

        String role = user.getRole();
        Long baseId = (user.getBase() != null) ? user.getBase().getId() : null;

        Map<String, Object> response = new HashMap<>();
        List<com.militaryasset.model.Asset> assets;

        if (role.equals("ADMIN") || role.equals("LOGISTICS_OFFICER")) {
            assets = assetRepository.findAll();
        } 
        else if (role.equals("BASE_COMMANDER") && baseId != null) {
            Base base = baseRepository.findById(baseId).orElse(null);
            assets = assetRepository.findByBase(base);
        } 
        else {
            assets = List.of();
        }

        int totalQuantity = assets.stream().mapToInt(a -> a.getQuantity()).sum();

        response.put("totalAssets", assets.size());
        response.put("totalQuantity", totalQuantity);
        response.put("role", role);
        response.put("baseId", baseId);

        return response;
    }
    @GetMapping("/net-movement")
    public Map<String, Object> getNetMovement(
            @RequestParam String role,
            @RequestParam(required=false) Long baseId) {

        Base base = (baseId != null) ? baseRepository.findById(baseId).orElse(null) : null;

        List<Purchase> purchases;
        List<Assignment> assignments;
        List<Transfer> transfersIn;
        List<Transfer> transfersOut;

        if (role.equals("ADMIN") || role.equals("LOGISTICS_OFFICER")) {

            purchases = purchaseRepository.findAll();
            assignments = assignmentRepository.findAll();
            transfersIn = transferRepository.findAll();
            transfersOut = transferRepository.findAll();

        } else if (role.equals("BASE_COMMANDER") && base != null) {

            purchases = purchaseRepository.findByBase(base);
            assignments = assignmentRepository.findByAsset_Base(base);
            transfersIn = transferRepository.findByToBase(base);
            transfersOut = transferRepository.findByFromBase(base);

        } else {
            purchases = List.of();
            assignments = List.of();
            transfersIn = List.of();
            transfersOut = List.of();
        }

        int totalPurchased = purchases.stream().mapToInt(Purchase::getQuantity).sum();
        int totalAssigned = assignments.stream().mapToInt(Assignment::getQuantity).sum();
        int totalExpended = assignments.stream()
                .filter(Assignment::isExpended)
                .mapToInt(Assignment::getQuantity)
                .sum();

        int totalTransferredIn = transfersIn.stream().mapToInt(Transfer::getQuantity).sum();
        int totalTransferredOut = transfersOut.stream().mapToInt(Transfer::getQuantity).sum();

        int netMovement = totalPurchased + totalTransferredIn
                - totalAssigned - totalExpended - totalTransferredOut;

        Map<String, Object> response = new HashMap<>();

        response.put("openingBalance", totalPurchased);
        response.put("closingBalance",
                totalPurchased + totalTransferredIn - totalExpended - totalTransferredOut);
        response.put("netMovement", netMovement);
        response.put("assigned", totalAssigned);
        response.put("expended", totalExpended);
        response.put("totalPurchased", totalPurchased);
        response.put("totalTransferredIn", totalTransferredIn);
        response.put("totalTransferredOut", totalTransferredOut);

        return response;
    }


}

