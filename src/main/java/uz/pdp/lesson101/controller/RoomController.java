package uz.pdp.lesson101.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.lesson101.dto.RoomDto;
import uz.pdp.lesson101.entity.Hotel;
import uz.pdp.lesson101.entity.Room;
import uz.pdp.lesson101.repository.HotelRepository;
import uz.pdp.lesson101.repository.RoomRepository;

import java.util.Optional;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping("/allRoomsByPage")
    public Page<Room> getAllRoomsByPage(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> roomPage = roomRepository.findAll(pageable);
        return roomPage;
    }

    @GetMapping("/allRoomByPageByHotelId/{hotelId}")
    public Page<Room> getRoomsByHotelId(@PathVariable Integer hotelId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> byHotelId = roomRepository.getByHotel_Id(hotelId, pageable);
        return byHotelId;
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Integer id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()){
            return optionalRoom.get();
        }
        return new Room();
    }

    @PostMapping
    public String addRoom(@RequestBody RoomDto roomDto) {
        if (!roomDto.getNumber().isEmpty() && roomDto.getSize() != null && roomDto.getFloor() != null && roomDto.getHotelId() != null) {
            Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
            if (optionalHotel.isPresent()){
                Hotel hotel = optionalHotel.get();
                boolean byNumberAndHotelId = roomRepository.existsByNumberAndHotel_Id(roomDto.getNumber(), roomDto.getHotelId());
                if (!byNumberAndHotelId){
                    Room room = new Room();
                    room.setFloor(roomDto.getFloor());
                    room.setSize(roomDto.getSize());
                    room.setNumber(roomDto.getNumber());
                    room.setHotel(hotel);
                    roomRepository.save(room);
                    return "Room qo'shildi!";
                }else {
                    return "Kiritilgan numberli room ko'rsatilgan hotelda mavjud, boshqa number bering!";
                }
            }else {
                return "Kiritilgan id li hotel topilmadi!";
            }
        }
        return "Qatorlarni to'ldiring!";
    }

    @PutMapping("/{id}")
    public String editRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()){
            Room room = optionalRoom.get();
            if (!roomDto.getNumber().isEmpty() && roomDto.getSize() != null && roomDto.getFloor() != null && roomDto.getHotelId() != null) {
                Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
                if (optionalHotel.isPresent()){
                    Hotel hotel = optionalHotel.get();
                    boolean byNumberAndHotelId = roomRepository.existsByNumberAndHotel_Id(roomDto.getNumber(), roomDto.getHotelId());
                    if (!byNumberAndHotelId){
                        room.setFloor(roomDto.getFloor());
                        room.setSize(roomDto.getSize());
                        room.setNumber(room.getNumber());
                        room.setHotel(hotel);
                        roomRepository.save(room);
                        return "Room Taxrirlandi!";
                    }else {
                        return "Kiritilgan numberli room ko'rsatilgan hotelda mavjud, boshqa number bering!";
                    }
                }else {
                    return "Kiritilgan id li hotel topilmadi!";
                }
            }
        }
        return "Kiritilgan id li room topilmadi!";
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id){
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()){
            Room room = optionalRoom.get();
            roomRepository.delete(room);
            return "Room o'chirildi!";
        }
        return "Kiritilgan id li room topilmadi!";
    }

}
