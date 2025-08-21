package com.DriverSystem_Back.modules.workAssignment;

import com.DriverSystem_Back.exception.HttpException;
import com.DriverSystem_Back.modules.workAssignment.dtio.WorkAssignmentRequest;
import com.DriverSystem_Back.modules.workorder.WorkOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkAssignmentService  implements IWorkAssignmentService {
   @Autowired
   private WorkAssignmentRepository workAssignmentRepository;

   @Autowired
   private ModelMapper modelMapper;


    @Override
    public WorkAssignment findById(Long id) {
        return null;
    }

    @Override
    public WorkAssignment save(WorkAssignmentRequest  request) {
        WorkAssignment workAssignment = this.modelMapper.map(request, WorkAssignment.class);
        this.workAssignmentRepository.save(workAssignment);
        return null;
    }

    @Override
    public List<WorkAssignment> findAll() {
        return this.workAssignmentRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        Optional<WorkAssignment> workAssignment = this.workAssignmentRepository.findById(id);
        if(workAssignment.isEmpty())
            throw  new HttpException("THE Work assigmnet not existe", HttpStatus.NOT_FOUND);
        this.workAssignmentRepository.deleteById(id);
    }

    @Override
    public WorkAssignment update(WorkAssignmentRequest request) {
        WorkAssignment  workAssignment  = this.workAssignmentRepository.findById(request.id())
                .orElseThrow(() -> new HttpException("Work assigment no encontrado!", HttpStatus.NOT_FOUND));
        workAssignment.setWorkOrderId(request.workOrderId());
        workAssignment.setAssigneeId(request.assigneeId());
        workAssignment.setRole(request.role());
        workAssignment.setReleasedAt(request.releasedAt());
        return this.workAssignmentRepository.save(workAssignment);
    }
}
