package com.ecommerce.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordCountDAO {

    private Integer totalResisterdUser;
    private Integer totalUsersHavePlacedOders;
    private Integer totalOders;
    private Integer totalSuccessfullOrders;
    private Integer totalProgressingOrders;
    private Integer totalFailedOrders;
    private Integer totalPayment;
    private Integer totalSuccessPayment;
    private Integer totalPendingPayment;
    private Integer totalFailedPayment;

}
