package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("""
            select b
            from Booking as b
            where b.booker.id = :bookerId
            order by b.startDate DESC""")
    List<Booking> findAllByBookerId(@Param("bookerId")Long bookerId);

    @Query("""
            select b
            from Booking as b
            where b.booker.id = :bookerId
            and CURRENT_TIMESTAMP between b.startDate and b.endDate
            order by b.startDate DESC""")
    List<Booking> findCurrentByBookerId(@Param("bookerId")Long bookerId);

    @Query("""
            select b
            from Booking as b
            where b.booker.id = :bookerId
            and b.endDate < CURRENT_TIMESTAMP
            order by b.startDate DESC""")
    List<Booking> findPastByBookerId(@Param("bookerId")Long bookerId);

    @Query("""
            select b
            from Booking as b
            where b.booker.id = :bookerId
            and b.startDate > CURRENT_TIMESTAMP
            order by b.startDate DESC""")
    List<Booking> findFutureByBookerId(@Param("bookerId")Long bookerId);

    @Query("""
            select b
            from Booking as b
            where b.booker.id = :bookerId
            and b.status = 'WAITING'
            order by b.startDate DESC""")
    List<Booking> findWaitingByBookerId(@Param("bookerId")Long bookerId);

    @Query("""
            select b
            from Booking as b
            where b.booker.id = :bookerId
            and b.status = 'REJECTED'
            order by b.startDate DESC""")
    List<Booking> findRejectedByBookerId(@Param("bookerId")Long bookerId);

    // ownerId
    @Query("""
            select b
            from Booking as b
            where b.item.owner.id = :ownerId
            order by b.startDate DESC""")
    List<Booking> findAllByOwnerId(@Param("ownerId")Long ownerId);

    @Query("""
            select b
            from Booking as b
            where b.item.owner.id = :ownerId
            and CURRENT_TIMESTAMP between b.startDate and b.endDate
            order by b.startDate DESC""")
    List<Booking> findCurrentByOwnerId(@Param("ownerId")Long ownerId);

    @Query("""
            select b
            from Booking as b
            where b.item.owner.id = :ownerId
            and b.endDate < CURRENT_TIMESTAMP
            order by b.startDate DESC""")
    List<Booking> findPastByOwnerId(@Param("ownerId")Long ownerId);

    @Query("""
            select b
            from Booking as b
            where b.item.owner.id = :ownerId
            and b.startDate > CURRENT_TIMESTAMP
            order by b.startDate DESC""")
    List<Booking> findFutureByOwnerId(@Param("ownerId")Long ownerId);

    @Query("""
            select b
            from Booking as b
            where b.item.owner.id = :ownerId
            and b.status = 'WAITING'
            order by b.startDate DESC""")
    List<Booking> findWaitingByOwnerId(@Param("ownerId")Long ownerId);

    @Query("""
            select b
            from Booking as b
            where b.item.owner.id = :ownerId
            and b.status = 'REJECTED'
            order by b.startDate DESC""")
    List<Booking> findRejectedByOwnerId(@Param("ownerId")Long ownerId);

}
