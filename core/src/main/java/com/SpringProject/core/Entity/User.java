package com.SpringProject.core.Entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   private String username;
   private String login;
   private String password;
   private String phoneNumber;
   private String bank;
   private Integer idPicture;
   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
   private List<UserGroup> userGroupsList;

   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
   private List<UserEvent> userEventList;

   @ManyToMany
   private List<User> friends;

//   @OneToMany(mappedBy = "userTo", cascade = CascadeType.ALL)
//   private List<Debt> debtList;


}
