package com.DriverSystem_Back.modules.workstatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name ="work_status")
@AllArgsConstructor
@NoArgsConstructor
public class WorkStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String  name;
    private String  code;
}
