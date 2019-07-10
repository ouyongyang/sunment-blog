package com.yang.sunment.service.security;



import com.yang.sunment.model.Role;
import com.yang.sunment.model.SecAccount;
import com.yang.sunment.repository.mybatis.SecAccountRepository;
import com.yang.sunment.service.SecAccountService;
import com.yang.sunment.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: OYY
 * @Date: 2018/11/11 19:11
 * Describe: 用户登录处理
 */
@Service
public class CustomUserServiceImpl implements UserDetailsService {

    @Autowired
    SecAccountRepository secAccountRepository;
    @Autowired
    SecAccountService userService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {

        SecAccount secAccount = secAccountRepository.findByPhone(phone);

        if(secAccount == null){
            throw  new UsernameNotFoundException("用户不存在");
        }
        //记录最后登录时间
        TimeUtil timeUtil = new TimeUtil();
        String lastLogintime = timeUtil.getFormatDateForSix();
        userService.updateLastLoginTime(secAccount.getUsername(), lastLogintime);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role : secAccount.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(secAccount.getUsername(), secAccount.getPassword(), authorities);
    }
}
