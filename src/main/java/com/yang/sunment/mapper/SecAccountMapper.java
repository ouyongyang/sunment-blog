package com.yang.sunment.mapper;

import com.yang.sunment.model.Categories;
import com.yang.sunment.model.SecAccount;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: OYY
 * @Date: 2019/1/7 15:52
 * Describe: 员工表操作数据库
 */
@Mapper
@Repository
public interface SecAccountMapper {

    @Select("select * from sec_account where phone=#{phone}")
    SecAccount findAccountByPhone(@Param("phone") String phone);

    @Select("select username from sec_account where id=#{id}")
    String findUsernameById(int id);

    @Insert("insert into sec_account(phone,username,password,gender,avatar_images_url) values(#{phone},#{username},#{password},#{gender},#{avatarImagesUrl})")
    void insert(SecAccount secAccount);

    @Select("select username from sec_account where phone=#{phone}")
    SecAccount findUsernameByPhone(@Param("phone") String phone);

    @Select("select * from sec_account where username=#{username}")
    SecAccount findUsernameByUsername(@Param("username") String username);

    @Insert("insert into sec_account_role(user_id, role_id) values (#{userId}, #{roleId})")
    void insertRole(@Param("userId") int userId, @Param("roleId") int roleId);

    @Select("select role_id from sec_account_role where user_id=#{userId}")
    List<Object> findRoleIdByUserId(@Param("userId") int userId);

    @Select("select id from sec_account where phone=#{phone}")
    int findUserIdByPhone(@Param("phone") String phone);

    @Update("update sec_account set password=#{password} where phone=#{phone}")
    void updatePassword(@Param("phone") String phone, @Param("password") String password);

    @Select("select phone from sec_account where username=#{username}")
    String findPhoneByUsername(@Param("username") String username);

    @Select("select id from sec_account where username=#{username}")
    int findIdByUsername(String username);

    @Update("update sec_account set last_logintime=#{lastLogintime} where phone=#{phone}")
    void updateLastLoginTime(@Param("phone") String phone, @Param("lastLogintime") String lastLogintime);

    @Update("update sec_account set avatar_images_url=#{avatarImgUrl} where id=#{id}")
    void updateAvatarImgUrlById(@Param("avatarImgUrl") String avatarImgUrl, @Param("id") int id);

    @Select("select avatar_images_url from sec_account where id=#{id}")
    String getHeadPortraitUrl(@Param("id") int id);

    @Select("select sa.id,sa.phone,sa.username,sa.`password`,sa.gender,sa.true_name AS trueName,sa.birthday,sa.personal_profile AS personalProfile,sa.email,sa.last_logintime As lastLogintime,sa.avatar_images_url\n" +
            "As avatarImagesUrl from sec_account sa where username=#{username}")
    SecAccount getAccountPersonalInfoByUsername(@Param("username") String username);

    @Update("update sec_account set username=#{secAccount.username},gender=#{secAccount.gender},true_name=#{secAccount.trueName},birthday=#{secAccount.birthday},email=#{secAccount.email},personal_profile=#{secAccount.personalProfile} where username=#{username}")
    void savePersonalDate(@Param("secAccount") SecAccount secAccount, @Param("username") String username);

    @Select("select count(*) from sec_account")
    int countUserNum();

    @Select("select id,phone,username,email,avatar_images_url As avatarImagesUrl,last_logintime As lastLogintime,type from sec_account order by id")
    List<SecAccount> getSecAccountManagement();

    @Select("select type from sec_account where id=#{id}")
    int selectById(@Param("id") int id);

    @Update("update sec_account set type=1 where id=#{id}")
    void agreeSecAccount(@Param("id") int id);

    @Update("update sec_account set type=0 where id=#{id}")
    void disAgreeSecAccount(@Param("id") int id);

    @Delete("delete from sec_account where id=#{id}")
    void deleteSecAccount(long id);
}
