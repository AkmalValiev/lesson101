package uz.pdp.lesson101.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson101.entity.Hotel;
import uz.pdp.lesson101.repository.HotelRepository;
import uz.pdp.lesson101.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;
    @GetMapping
    public List<Hotel> getHotels(){
        return hotelRepository.findAll();
    }

    @GetMapping("/{id}")
    public Hotel getHotel(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()){
            return optionalHotel.get();
        }
        return new Hotel();
    }

    @PostMapping
    public String addHotel(@RequestBody Hotel hotel){
        if (!hotel.getName().isEmpty()){
            boolean byName = hotelRepository.existsByName(hotel.getName());
            if (!byName) {
                Hotel hotel1 = new Hotel();
                hotel1.setName(hotel.getName());
                hotelRepository.save(hotel1);
                return "Hotel qo'shildi!";
            }else {
                return "Bunday nomli hotel mavjud, boshqa nom kiriting!";
            }
        }
        return "Hotel nomini kiriting!";
    }

    @PutMapping("/{id}")
    public String editHotel(@PathVariable Integer id, @RequestBody Hotel hotel){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()){
            Hotel hotel1 = optionalHotel.get();
            if (!hotel.getName().isEmpty()){
                boolean byName = hotelRepository.existsByName(hotel.getName());
                if (!byName){
                    hotel1.setName(hotel.getName());
                    hotelRepository.save(hotel1);
                    return "Hotel taxrirlandi!";
                }else {
                    return "Bunday nomli hotel mavjud, boshqa nom kiriting!";
                }
            }else {
                return "Hotel nomini kiriting!";
            }
        }
        return "Kiritilgan id li hotel topilmadi!";
    }

    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()){
            Hotel hotel = optionalHotel.get();
            boolean byHotelId = roomRepository.existsByHotel_Id(id);
            if (!byHotelId){
                hotelRepository.delete(hotel);
                return "Hotel o'chirildi!";
            }else {
                return "Avval bu hotelga tegishli room larni o'chiring!";
            }
        }
        return "Kiritilgan id li hotel topilmadi!";
    }

}
