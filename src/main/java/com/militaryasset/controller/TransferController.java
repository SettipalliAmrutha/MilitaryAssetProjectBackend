package com.militaryasset.controller;

import com.militaryasset.model.Asset;
import com.militaryasset.model.Base;
import com.militaryasset.model.Transfer;
import com.militaryasset.repository.AssetRepository;
import com.militaryasset.repository.BaseRepository;
import com.militaryasset.repository.TransferRepository;
import com.militaryasset.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private AuditService auditService;

    @PostMapping
    public Transfer addTransfer(@RequestBody Transfer transfer) {
        transfer.setDate(LocalDate.now());
        Transfer saved = transferRepository.save(transfer);
        auditService.log("Transferred " + transfer.getQuantity() + " of " + transfer.getAsset().getName() +
                " from " + transfer.getFromBase().getName() + " to " + transfer.getToBase().getName());
        return saved;
    }

    @GetMapping
    public List<Transfer> getTransfers(
            @RequestParam String role,
            @RequestParam(required = false) Long baseId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        
        if (role.equals("ADMIN")) {
            return transferRepository.findAll();
        }

        
        if (role.equals("BASE_COMMANDER") && baseId != null) {
            Base base = baseRepository.findById(baseId).orElse(null);
            return transferRepository.findByFromBaseOrToBase(base, base);
        }

        
        if (role.equals("LOGISTICS_OFFICER")) {
            return transferRepository.findAll();
        }

        return List.of();
    }
}


