package com.cooklog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooklog.dto.UserDTO;
import com.cooklog.model.User;
import com.cooklog.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDTO findUserById(Long id) {
		User user = userRepository.findById(id).orElse(null);
		if (user != null) {
			UserDTO dto = new UserDTO();
			dto.setIdx(user.getIdx());
			dto.setNickname(user.getNickname());
			dto.setEmail(user.getEmail());
			dto.setProfileImage(user.getProfileImage());
			return dto;
		}
		return null;
	}

	//구현 로직
}
