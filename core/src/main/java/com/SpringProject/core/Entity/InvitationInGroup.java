package com.SpringProject.core.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "InvitationInGroup")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvitationInGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;


  private String username;

  @JoinColumn(name = "userFrom_id")
  private Long userId;

  private String nameGroup;
  private Long groupId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "userTo_id")
  private User user;
}
