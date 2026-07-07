package com.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authservice.model.OtpModel;

public interface OtpRepository extends JpaRepository<OtpModel,Long>{

}
