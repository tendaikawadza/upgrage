package io.getarrays.securecapita.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;


@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_DEFAULT)
public class PurchaseRequests {
    private Long id;;

// what different signature and filename, i saw something online , can we use this i never use this sir must to learn before use
    // ok lets learn it, so for today
    private String productName;
    private Date date;
    private String productCode;
    private int Quantity;
    private String receiverEmail;
    private String signature;
    private String fileName;
    private String fileType;

    private String value;


    //signature here
    @Lob
    private byte[] data;

    private String imagePath;
    private String imageUrl; // Corrected field name



}
