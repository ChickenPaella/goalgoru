package com.gorugoru.api.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorugoru.api.component.auth.AuthProvider;
import com.gorugoru.api.domain.model.FoodNutri;
import com.gorugoru.api.domain.model.Nutri;
import com.gorugoru.api.domain.model.User;
import com.gorugoru.api.domain.repository.FoodNutriRepository;
import com.gorugoru.api.domain.repository.UserRepository;
import com.gorugoru.api.dto.SecUser;
import com.gorugoru.util.DateUtil;

@Service
public class UserService implements UserDetailsService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FoodNutriRepository foodNutriRepository;
	
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
	 * @throws Exception 
	 */
	public User regsistUserForSNS(AuthProvider authProvider, String authUID, String name, String birthDate,
			String email, String phone, String profileImage, String cardNumber) {
		
		User user = userRepository.findOneByAuthProviderAndAuthUID(authProvider.toString(), authUID);
		
		if(user == null){
			logger.info("insert New User");
			//insert
			String tmpId = "-temp-"+authProvider.toString()+"-"+authUID;
			return registUser(authProvider, authUID, tmpId, null, name, birthDate, email, phone, profileImage, cardNumber);
		}else{
			logger.info("modify exists User: "+user.toString());
			//update
			user.setName(name);
			user.setBirthDate(DateUtil.parseDate(birthDate));
			user.setEmail(email);
			user.setPhone(phone);
			user.setProfileImage(profileImage);
			userRepository.save(user);
		}
		
		return user;
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
			String phone, String profileImage, String cardNumber){
		return registUser(AuthProvider.NONE, null, id, pass, name, birthDate, email, phone, profileImage, cardNumber);
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
			String phone, String profileImage, String cardNumber){
		
		Date date = DateUtil.parseDate(birthDate);
		
		User user = new User(authProvider.toString(), authUID, id, pass, name, date, email, phone, profileImage, cardNumber);
		user.setCreated(new Date());
		user = userRepository.save(user);
		return user;
	}
	
	public User registCardNumber(User user, String cardNumber) {
		if(user.getCardNumber()==null){
			user.setCardNumber(cardNumber);
			user=userRepository.save(user);
			return user;
		}
		else return user;
		
	}
	
	public List<User> getUserList(){
		List<User> userList = (List<User>) userRepository.findAll();
		return userList;
	}
	
	public User managePoint(String userId, String foodName) {
		
		logger.info("managePoint()");
		
		User user = userRepository.findOneById(userId);
		FoodNutri food = foodNutriRepository.findOneByName(foodName);//FOOD 맵핑
		
		Nutri mainNutri = food.getMainNutri();
		
		if(mainNutri==Nutri.Carbohydrate ) {
			if(user.getPoint().getBadgeCarbo()<3) {
				user.getPoint().setBadgeCarbo(user.getPoint().getBadgeCarbo()+1);
			}
			else {
				logger.info("You can't collect more Carbo badge! (3/3)");
				logger.info("carbo: " + user.getPoint().getBadgeCarbo() + " protein: " + user.getPoint().getBadgeProtein() + " fat: " + user.getPoint().getBadgeFat() + " star: " + user.getPoint().getStar());
				return user;
			}
		}
		else if(mainNutri==Nutri.Protein) {
			if(user.getPoint().getBadgeProtein()<3) {
				user.getPoint().setBadgeProtein(user.getPoint().getBadgeProtein()+1);
			}
			else {
				logger.info("You can't collect more Protein badge! (3/3)");
				logger.info("carbo: " + user.getPoint().getBadgeCarbo() + " protein: " + user.getPoint().getBadgeProtein() + " fat: " + user.getPoint().getBadgeFat() + " star: " + user.getPoint().getStar());
				return user;
			}	
		} 
		else if(mainNutri==Nutri.Fat) {
			if(user.getPoint().getBadgeFat()<3) {
				user.getPoint().setBadgeFat(user.getPoint().getBadgeFat()+1);
			}
			else {
				logger.info("You can't collect more Fat badge! (3/3)");
				logger.info("carbo: " + user.getPoint().getBadgeCarbo() + " protein: " + user.getPoint().getBadgeProtein() + " fat: " + user.getPoint().getBadgeFat() + " star: " + user.getPoint().getStar());
				return user;
			}	
		}
		
		if(user.getPoint().getBadgeCarbo()==3 && user.getPoint().getBadgeProtein()==3 && user.getPoint().getBadgeFat()==3) {
			user.getPoint().setBadgeCarbo(0);
			user.getPoint().setBadgeProtein(0);
			user.getPoint().setBadgeFat(0);
			user.getPoint().setStar(user.getPoint().getStar()+1);
			logger.info("별 1개 증가!");
		}
		user=userRepository.save(user);
		logger.info("carbo: " + user.getPoint().getBadgeCarbo() + " protein: " + user.getPoint().getBadgeProtein() + " fat: " + user.getPoint().getBadgeFat() + " star: " + user.getPoint().getStar());
		return user;
	}
	
	/**
	 * lazy init 과 json 변환이 같이되려면, @Transactional 이 필요함. 고로, 서비스에서 처리
	 * @return
	 * @throws JsonProcessingException
	 */
	@Transactional
	public String getUserListJSON(Class<?> views) throws JsonProcessingException{
		List<User> userList = (List<User>) userRepository.findAll();
        return mapper.writerWithView(views).writeValueAsString(userList);
	}

	public boolean exists(long seq) {
		return userRepository.exists(seq);
	}

	public User getUserById(String id) {
		return userRepository.findOneById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = getUserById(username);
		
		if (user != null) {
			SecUser securityUser = new SecUser(user.getId(), "null", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
			detailsChecker.check(securityUser);
			return securityUser;
        } else {
            throw new UsernameNotFoundException("user with username '" + username + "' not found!");
        }
	}
}
