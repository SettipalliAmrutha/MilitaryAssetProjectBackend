package com.militaryasset.controller;

import com.militaryasset.model.Assignment;
import com.militaryasset.model.User;
import com.militaryasset.repository.AssignmentRepository;
import com.militaryasset.repository.UserRepository;
import com.militaryasset.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditService auditService;

    @PostMapping
    public Assignment assignAsset(@RequestBody Assignment assignment) {
        assignment.setDate(LocalDate.now());
        Assignment saved = assignmentRepository.save(assignment);
        auditService.log("Assigned " + assignment.getQuantity() + " of " + assignment.getAsset().getName() +
                " to " + assignment.getAssignedTo().getUsername());
        return saved;
    }

    @GetMapping
    public List<Assignment> getAssignments(
            @RequestParam String role,
            @RequestParam(required = false) Long baseId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        
        if (role.equals("ADMIN")) {
            return assignmentRepository.findAll();
        }

        
        if (role.equals("BASE_COMMANDER") && baseId != null) {
            return assignmentRepository.findByAssignedTo_Base_Id(baseId);
        }

        
        if (role.equals("LOGISTICS_OFFICER")) {
            return List.of();
        }

        return List.of();
    }
}

