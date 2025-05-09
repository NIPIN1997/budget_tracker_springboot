package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Profile;

@Repository
public interface ProfileDao extends JpaRepository<Profile, Integer> {

	Profile findById(int id);
}
