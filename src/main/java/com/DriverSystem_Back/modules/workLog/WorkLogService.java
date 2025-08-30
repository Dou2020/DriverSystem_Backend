package com.DriverSystem_Back.modules.workLog;

import com.DriverSystem_Back.modules.workLog.dto.WorkLoggerRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkLogService  implements  IWorkLongService{
    @Autowired
    private WorkLogRepository workLogRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WorkLog save(WorkLoggerRequest request) {
        WorkLog  workLog=  this.modelMapper.map(request,WorkLog.class);
        return this.workLogRepository.save(workLog);
    }

    @Override
    public List<WorkLog> GetWorkLog() {
        return this.workLogRepository.findAll();
    }

    @Override
    public List<WorkLog> findWorkLog(Long id) {
//        Optional<WorkLog> workLogOpcional = this.workLogRepository.findByOrder(id);
//        if (workLogOpcional.isEmpty())
//            throw new HttpException("No existe registro de esta orden", HttpStatus.NOT_FOUND);
      return  this.workLogRepository.findByWorkOrderId(id);
    }
}
