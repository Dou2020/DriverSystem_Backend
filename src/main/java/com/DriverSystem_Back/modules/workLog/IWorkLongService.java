package com.DriverSystem_Back.modules.workLog;

import com.DriverSystem_Back.modules.workLog.dto.WorkLoggerRequest;

import java.util.List;

public interface IWorkLongService {
    public WorkLog save(WorkLoggerRequest workLog);
    public List<WorkLog> GetWorkLog();
    public List<WorkLog> findWorkLog(Long id);
}
