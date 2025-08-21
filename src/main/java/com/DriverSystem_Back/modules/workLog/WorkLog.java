package com.DriverSystem_Back.modules.workLog;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "work_log")
public class WorkLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "work_order_id")
    private Long workOrderId;
    @Column(name ="author_id")
    private Long autorId;
    @Column( name = "log_type")
    private String logType;
    private String note;
    private BigDecimal hours;
}


