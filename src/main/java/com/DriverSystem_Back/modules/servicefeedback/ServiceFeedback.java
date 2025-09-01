package com.DriverSystem_Back.modules.servicefeedback;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_feedback")
public class ServiceFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "work_order_id")
    private  Long workOrderId;
    @Column(name = "customer_id")
    private Long  customerId;
    private short  rating;
    private  String comment;
}
