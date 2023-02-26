package uz.pdp.lesson101.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson101.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    boolean existsByHotel_Id(Integer hotel_id);

    Page<Room> getByHotel_Id(Integer hotel_id, Pageable pageable);

    boolean existsByNumberAndHotel_Id(String number, Integer hotel_id);

}
