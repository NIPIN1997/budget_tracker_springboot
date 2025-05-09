package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ProfileDao;
import com.example.demo.entity.Profile;

@Service
public class ProfileService {

	@Autowired
	private ProfileDao profileDao;
	
	public List<Profile> retrieveAllProfiles(){
		return profileDao.findAll();
	}

	public Profile addNewProfile(Profile profile) {
		Profile profile1= profileDao.save(profile);
		return profile1;
	}

	public Profile getProfileById(int profileId) {
		return profileDao.findById(profileId);
	}

	public Profile editProfile(Profile profile) {
		Profile profile1=profileDao.findById(profile.getId());
		profile1.setName(profile.getName());
		return profileDao.save(profile1);
	}

	public void deleteProfile(int profileId) {
		profileDao.deleteById(profileId);
	}
}
