package com.goldnexusbackend.service;

import com.goldnexusbackend.entity.Res;
import com.goldnexusbackend.entity.User;
import com.goldnexusbackend.entity.UserDetail;
import com.goldnexusbackend.entity.VO;
import com.goldnexusbackend.mapper.UserMapper;
import com.goldnexusbackend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {


    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    Res res=new Res();

    @Transactional
    public Res register(VO vo) {
        log.info("用户进行注册请求");

        if (userMapper.selectUserByUsername(vo.getUsername())!=null) {
            log.info("用户名已存在，注册失败");

            res.setCode(500);
            res.setMsg("用户名已存在");
            res.setData(null);
            return res;
        }

        User user=new User();
        user.setUsername(vo.getUsername());
        user.setPassword(passwordEncoder.encode(vo.getPassword()));

        userMapper.insertUser(user);

        log.info("注册成功");

        res.setCode(200);
        res.setMsg("success");
        res.setData(null);
        return res;
    }

    @Transactional
    public Res login(VO vo) {

        log.info("用户进行登录请求");

        User user=userMapper.selectUserByUsername(vo.getUsername());
        if (user==null) {
            log.info("用户名不存在");

            res.setCode(500);
            res.setMsg("用户名不存在");
            res.setData(null);
            return res;
        }

        if (!passwordEncoder.matches(vo.getPassword(), user.getPassword())) {
            log.info("密码错误");

            res.setCode(500);
            res.setMsg("密码错误");
            res.setData(null);
            return res;
        }

        log.info("注册成功");

        res.setCode(200);
        res.setMsg("success");
        res.setData(jwtUtil.generateToken(user));
        return res;
    }

    @Transactional
    public Res UpdateUserDetail(UserDetail userDetail) {


        log.info("用户进行个人数据上传/更新请求");

        if (userMapper.selectUserDetailByUsername(userDetail.getUsername()) != null) {

            try {
                int i = userMapper.updateUserDetail(userDetail);
                if (i > 0) {
                    res.setCode(200);
                    res.setMsg("success");
                    res.setData(null);
                    log.info("用户信息更新成功");
                    return res;
                } else {
                    res.setCode(500);
                    res.setMsg("fail");
                    res.setData(null);
                    log.info("用户信息没有更新");
                    return res;
                }
            } catch (Exception e) {
                res.setCode(500);
                res.setMsg("用户信息更新失败");
                res.setData(null);
                return res;
            }
        }

        try {
            int i = userMapper.insertUserDetail(userDetail);
            if (i > 0) {
                res.setCode(200);
                res.setMsg("success");
                res.setData(null);
                log.info("用户信息已上传");
                return res;
            }
            else {
                res.setCode(500);
                res.setMsg("fail");
                res.setData(null);
                log.info("用户信息未上传");
                return res;
            }
        }catch (Exception e){
            res.setCode(500);
            res.setMsg("fail");
            res.setData(null);
            log.info("用户信息上传失败");
            return res;
        }
    }

}
