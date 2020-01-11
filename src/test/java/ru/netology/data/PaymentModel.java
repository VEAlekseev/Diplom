package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaymentModel {
    String id;
    int amount;
    String created;
    String status;
    String transaction_id;
}