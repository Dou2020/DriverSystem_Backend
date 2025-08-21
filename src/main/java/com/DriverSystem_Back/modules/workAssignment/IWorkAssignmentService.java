package com.DriverSystem_Back.modules.workAssignment;

import com.DriverSystem_Back.modules.workAssignment.dtio.WorkAssignmentRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IWorkAssignmentService {
    public WorkAssignment findById(Long id);
    public WorkAssignment save(WorkAssignmentRequest workAssignment);
    public List<WorkAssignment> findAll();
    public void deleteById(Long id);
    public WorkAssignment update(WorkAssignmentRequest workAssignment);
}
