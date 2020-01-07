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
    public String id;
    public int amount;
    public String created;
    public String status;
    public String transaction_id;
}