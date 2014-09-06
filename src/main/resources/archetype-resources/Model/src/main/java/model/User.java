#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author WMS 用户实体，包含对用户的信息
 */
@Entity
@Table(name = "Users_Table")
public class User implements Serializable {
	
	private static final long serialVersionUID = -2654240922018804006L;

	@Id
	@Column(length = 20, nullable = false)
	private String id;// 账号

	@Column(length = 20, nullable = false)
	private String name;// 姓名

	@Column(length = 80, nullable = false)
	private String password;// 密码

	@Column(length = 80, nullable = false)
	private String address;// 住址

	@Temporal(TemporalType.DATE)
	private Date birthday;// 出生时间

	@Column(length = 30, nullable = false)
	private String email;// 电子邮件

	@Column(length = 30)
	private String phone;// 电话

	@Column(length = 15, nullable = false)
	private String ip;// 注册ip

	@Temporal(TemporalType.TIMESTAMP)
	private Date regtime;// 注册时间

	private boolean disable;// 是否被锁定

	private String salt;// 加密字符串

	@Column(length = 20)
	private String role;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getRegtime() {
		return regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
