package com.SpringProject.core.Entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String refreshToken;
  private String registrationToken;
  private Timestamp time;
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "userTo_id")
  User user;
}
