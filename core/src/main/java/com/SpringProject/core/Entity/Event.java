package com.SpringProject.core.Entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import liquibase.pro.packaged.L;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String event_name;
  //private Integer type_event;
  private  Long pat_pay_id;
  @ManyToOne (fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "group_id")
  private GroupsEntity group;
  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserEvent> user;


}
