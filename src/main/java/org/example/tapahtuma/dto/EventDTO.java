package org.example.tapahtuma.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    private Long id;           // Tapahtuman tunniste
    private String name;       // Tapahtuman nimi
    private String date;       // Päivämäärä merkkijonona (esim. "2024-12-15")
    private boolean teamEvent; // Onko joukkutapahtuma
    private String creatorEmail;  // Tapahtuman luojan sähköposti


}



