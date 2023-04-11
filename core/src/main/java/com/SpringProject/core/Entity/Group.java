package com.SpringProject.core.Entity;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String groupName;
  private Integer typeGroup;
  @Column(unique=true)
  private String uuid;
  private String description;
  @Column(name = "created_at", nullable = false, updatable = false)
  private Timestamp createdAt;
  @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserGroup> userGroupList;

  @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Event> eventList;

}

