package com.example.TestTask.service;

import com.example.TestTask.Entity.UserEntity;
import com.example.TestTask.Exceptions.UserAlreadyExistException;
import com.example.TestTask.Exceptions.UserInvalidException;
import com.example.TestTask.Exceptions.UserNotFoundException;
import com.example.TestTask.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    private boolean createUserValid=true;
    private boolean updateUserValid=true;

    public UserEntity createUser(UserEntity user) throws UserAlreadyExistException, UserInvalidException {
        validatorCreateUser(user);
            if(createUserValid) {
                return userRepo.save(user);
            }else{
                return null;
            }

    }

    public UserEntity getUser(Long id) throws UserNotFoundException {
        try {
            UserEntity user = userRepo.findById(id).get();
            return user;
        }catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
    }
    public UserEntity updateFirstName(UserEntity user) throws UserNotFoundException, UserInvalidException {
        Long id = user.getId();
        validatorUpdateUser(user);
        UserEntity userToUpdate;

            if(updateUserValid) {
                userToUpdate = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
                userToUpdate.setFirstName(user.getFirstName());
                return userRepo.save(userToUpdate);
            }else{
                throw new UserInvalidException("invalid user");
            }
        }



    public UserEntity updateFullName(UserEntity user) throws UserNotFoundException, UserInvalidException {
        Long id = user.getId();
        validatorUpdateUser(user);
        UserEntity userToUpdate = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (updateUserValid) {
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setMiddleName(user.getMiddleName());
            userToUpdate.setLastName(user.getLastName());

            return userRepo.save(userToUpdate);
        } else {
            throw new UserInvalidException("invalid user");
        }
    }
    public UserEntity updateAll(UserEntity user) throws UserNotFoundException, UserInvalidException {
        Long id = user.getId();
        validatorUpdateUser(user);
        UserEntity userToUpdate = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (updateUserValid) {
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setMiddleName(user.getMiddleName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setLogin(user.getLogin());
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setBirthday(user.getBirthday());
            userToUpdate.setPhoneNumber(user.getPhoneNumber());
            userToUpdate.setAddress(user.getAddress());
            return userRepo.save(userToUpdate);
        } else {
            throw new UserInvalidException("invalid user");
        }
    }

    public Long deleteUser(Long id)throws UserNotFoundException{
        if(userRepo.findById(id).isPresent()){
            userRepo.deleteById(id);
            return id;
        }else{
            throw new UserNotFoundException("User not found");
        }

    }

    public List<UserEntity> getUsersByBirthdayRange(Date startDate, Date endDate) {
        return userRepo.findByBirthdayBetween(startDate, endDate);
    }

    private void validatorCreateUser(UserEntity user) throws UserInvalidException, UserAlreadyExistException {
        Long id = user.getId();
        Date userBirthday = user.getBirthday();
        Date currentDate = new Date();
        Calendar userCal = Calendar.getInstance();
        userCal.setTime(userBirthday);
        int userDay = userCal.get(Calendar.DAY_OF_MONTH);
        int userMonth = userCal.get(Calendar.MONTH);
        int userYear = userCal.get(Calendar.YEAR);

        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(currentDate);
        int currentDay = currentCal.get(Calendar.DAY_OF_MONTH);
        int currentMonth = currentCal.get(Calendar.MONTH);
        int currentYear = currentCal.get(Calendar.YEAR);

        int age = currentYear - userYear;

        if(id != null){
            createUserValid=false;
            throw new UserAlreadyExistException("User already exist");
        }if(!user.getEmail().contains("@")){
            createUserValid=false;
            throw new UserInvalidException("Invalid email address");
        } if (userYear > currentYear ||
                (userYear == currentYear && userMonth > currentMonth) ||
                (userYear == currentYear && userMonth == currentMonth && userDay >= currentDay)) {
            createUserValid=false;
            throw new UserInvalidException("Registration date >= date now");
        }if(age<18){
            createUserValid=false;
            throw new UserInvalidException("age<18");
        }
    }
    private void validatorUpdateUser(UserEntity user) throws UserInvalidException, UserNotFoundException {
        Long id = user.getId();
        Date userBirthday = user.getBirthday();
        Date currentDate = new Date();
        Calendar userCal = Calendar.getInstance();
        userCal.setTime(userBirthday);
        int userDay = userCal.get(Calendar.DAY_OF_MONTH);
        int userMonth = userCal.get(Calendar.MONTH);
        int userYear = userCal.get(Calendar.YEAR);

        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(currentDate);
        int currentDay = currentCal.get(Calendar.DAY_OF_MONTH);
        int currentMonth = currentCal.get(Calendar.MONTH);
        int currentYear = currentCal.get(Calendar.YEAR);

        int age = currentYear - userYear;

        if(id == null){
            updateUserValid=false;
            throw new UserNotFoundException("user not found");
        }if(!user.getEmail().contains("@")){
            updateUserValid=false;
            throw new UserInvalidException("Invalid email address");
        } if (userYear > currentYear ||
                (userYear == currentYear && userMonth > currentMonth) ||
                (userYear == currentYear && userMonth == currentMonth && userDay >= currentDay)) {
            updateUserValid=false;
            throw new UserInvalidException("Registration date >= date now");
        }if(age<18){
            updateUserValid=false;
            throw new UserInvalidException("age<18");
        }
        if(user.getFirstName()==null || user.getLastName()==null || user.getMiddleName()==null||
        user.getEmail()==null||user.getBirthday()==null||user.getAddress()==null){
            updateUserValid=false;
            throw new UserInvalidException("check the required parameters: firstName,middleName,lastName,email,birthday.address");

        }
    }




}
