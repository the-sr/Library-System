package com.example.demo.repository;

import com.example.demo.utils.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailConfigRepo extends JpaRepository<EmailConfig, Long> {
}
