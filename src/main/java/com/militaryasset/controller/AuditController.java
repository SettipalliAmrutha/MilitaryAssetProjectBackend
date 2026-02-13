package com.militaryasset.controller;

import com.militaryasset.model.AuditLog;
import com.militaryasset.repository.AuditLogRepository;
import com.militaryasset.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private AuditService auditService;

   
    @GetMapping
    public List<AuditLog> getAllAuditLogs(@RequestParam String role) {

        if (role.equals("ADMIN")) {
            return auditLogRepository.findAll();
        }

        return List.of(); 
    }

}


