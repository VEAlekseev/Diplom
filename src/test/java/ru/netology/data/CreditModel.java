package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreditModel {
    private String id;
    private String bank_id;
    private String created;
    private String status;
}