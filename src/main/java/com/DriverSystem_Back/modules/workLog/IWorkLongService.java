package com.DriverSystem_Back.modules.workLog;

import com.DriverSystem_Back.modules.workLog.dto.WorkLogRequest;

import java.util.List;

public interface IWorkLongService {
    public WorkLog save(WorkLogRequest workLog);
    public List<WorkLog> GetWorkLog();
    public List<WorkLog> findWorkLog(Long id);
}
