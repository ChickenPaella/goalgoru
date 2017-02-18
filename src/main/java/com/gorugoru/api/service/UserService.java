package com.gorugoru.api.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gorugoru.api.component.auth.AuthProvider;
import com.gorugoru.api.domain.model.User;
import com.gorugoru.api.domain.repository.UserRepository;
import com.gorugoru.util.DateUtil;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserRepository userRepository;
	
	/**
	 * 사용자등록 소셜로그인용
	 * @param authProvider
	 * @param authUID
	 * @param name
	 * @param birthDate
	 * @param email
	 * @param phone
	 * @param profileImage
	 * @return
	 */
	public User regsistUserForSNS(AuthProvider authProvider, String authUID, String name, String birthDate, String email,
			String phone, String profileImage){
		
		List<User> users = userRepository.findByAuthProviderAndAuthUID(authProvider.toString(), authUID);
		for(int i=0;i<users.size();i++){
			logger.info(users.toString());
		}
		String tmpId = "-temp-"+authProvider.toString()+"-"+authUID;
		return registUser(authProvider, authUID, tmpId, null, name, birthDate, email, phone, profileImage);
	}
	
	/**
	 * 사용자등록 일반가입
	 * @param id
	 * @param pass
	 * @param name
	 * @param birthDate
	 * @param email
	 * @param phone
	 * @param profileImage
	 * @return
	 */
	public User registUserForOwn(String id, String pass, String name, String birthDate, String email,
			String phone, String profileImage){
		return registUser(AuthProvider.NONE, null, id, pass, name, birthDate, email, phone, profileImage);
	}
	
	/**
	 * 사용자 등록
	 * @param authProvider
	 * @param authUID
	 * @param id
	 * @param pass
	 * @param name
	 * @param birthDate
	 * @param email
	 * @param phone
	 * @param profileImage
	 * @return
	 */
	public User registUser(AuthProvider authProvider, String authUID, String id, String pass, String name, String birthDate, String email,
			String phone, String profileImage){
		
		Date date = DateUtil.parseDate(birthDate);
		
		User user = new User(authProvider.toString(), authUID, id, pass, name, date, email, phone, profileImage);
		user = userRepository.save(user);
		return user;
	}
	
	public List<User> getUserList(){
		List<User> userList = (List<User>) userRepository.findAll();
		return userList;
	}

	public boolean isExistUser(long lastUserSeq) {
		return userRepository.findOne(lastUserSeq) != null;
	}
}
