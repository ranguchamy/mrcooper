package com.mrcooper.userservice.repository;

import com.mrcooper.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

//    @Modifying
//    @Query("UPDATE User u SET u.firstName = :firstName, u.lastName = :lastName, u.gender = :gender, u.phoneNumber = :phoneNumber WHERE u.id = :userId")
//    User updateUser(@Param("userId") Long userId,
//                    @Param("firstName") String firstName,
//                    @Param("lastName") String lastName,
//                    @Param("gender") String gender,
//                    @Param("phoneNumber") String phoneNumber);
//
//    long countByUserType(String type);
//
//    @Transactional
//    @Modifying
//    @Query("UPDATE User u SET u.isEnabled = :enabled WHERE u.id = :userId")
//    void updateUserEnabledStatus(@Param("userId")Long userId, @Param("enabled") boolean enabled);
}
