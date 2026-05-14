package com.codesync.repository;

import com.codesync.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    Optional<Room> findBySlug(String slug);
    boolean existsBySlug(String slug);

    @Query("SELECT r FROM Room r WHERE r.owner.username = :username")
    List<Room> findByOwnerUsername(String username);

    @Query("SELECT r FROM Room r JOIN r.members m WHERE m.user.username = :username")
    List<Room> findRoomsByMemberUsername(String username);
}
