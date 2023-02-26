package uz.pdp.lesson101.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {

    private String number;
    private Integer floor;
    private Integer size;
    private Integer hotelId;

}
