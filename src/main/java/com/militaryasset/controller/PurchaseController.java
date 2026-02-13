package com.militaryasset.controller;

import com.militaryasset.model.Asset;
import com.militaryasset.model.Base;
import com.militaryasset.model.Purchase;
import com.militaryasset.repository.AssetRepository;
import com.militaryasset.repository.BaseRepository;
import com.militaryasset.repository.PurchaseRepository;
import com.militaryasset.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private AuditService auditService;

    @PostMapping
    public Purchase addPurchase(@RequestBody Purchase purchase) {
        purchase.setDate(LocalDate.now());
        Purchase saved = purchaseRepository.save(purchase);
        auditService.log("Purchased " + purchase.getQuantity() + " of " + purchase.getAsset().getName());
        return saved;
    }

    @GetMapping
    public List<Purchase> getPurchases(
            @RequestParam String role,
            @RequestParam(required = false) Long baseId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        
        if (role.equals("ADMIN")) {
            return purchaseRepository.findAll();
        }

        
        if (role.equals("BASE_COMMANDER") && baseId != null) {
            return purchaseRepository.findByBase_Id(baseId);
        }

        
        if (role.equals("LOGISTICS_OFFICER")) {
            return purchaseRepository.findAll();
        }

        return List.of();
    }

}


