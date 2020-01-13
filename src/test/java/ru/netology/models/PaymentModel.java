package ru.netology.models;

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
    public int amount;
    String created;
    public String status;
    public String transaction_id;
}