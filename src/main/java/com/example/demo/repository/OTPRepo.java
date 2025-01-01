package com.example.demo.repository;

import com.example.demo.models.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepo extends JpaRepository<OTP, Long> {

    boolean existsByOtpAndEmail(int otp, String email);
}
