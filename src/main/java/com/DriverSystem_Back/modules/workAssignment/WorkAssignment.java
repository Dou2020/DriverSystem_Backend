package com.DriverSystem_Back.modules.workAssignment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "work_assignment")
public class WorkAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="work_Order_id")
    private Long workOrderId;
    @Column(name ="assignee_id")
    private Long assigneeId;
    private Long role;
    @Column(name ="assigned_at")
    private OffsetDateTime assignedAt;
    @Column(name ="released_at")
    private OffsetDateTime releasedAt;
}

