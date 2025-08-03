package com.personal.bankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmailDetails {
    private String recipientEmail;
    private String messageBody;
    private String subject;
    private String attachment;
}
