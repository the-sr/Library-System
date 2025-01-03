package library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import library.models.OTP;

@Repository
public interface OTPRepo extends JpaRepository<OTP, Long> {

    boolean existsByOtpAndEmail(int otp, String email);
}