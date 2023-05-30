package com.SpringProject.core.Entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
  @Column(unique = true)
  private String uuid;
  private String phoneNumber;
  private String bank;
  private Integer idPicture;
  @Column(name = "created_at", nullable = false, updatable = false)
  private Timestamp createdAt;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<UserGroup> userGroupsList;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<UserEvent> userEventList;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<InvitationInGroup> invitationInGroupList;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<InvitationFriend> invitationFriendList;
  @ManyToMany
  private List<User> friends;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  Token token;



}
