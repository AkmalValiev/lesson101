package uz.pdp.lesson101.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.lesson101.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    boolean existsByName(String name);

}
