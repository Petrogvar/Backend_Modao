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

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "group_id")
  private Group group;

  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private List<Expense> expenseList;

  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private List<UserEvent> userEventList ;

  private Long userPayingId; // ->user
  private String usernamePaying; //del
  private Long userCreatorId; // ->user
  private String usernameCreator; //del

  private Integer price;
  private String eventName;
  private Integer status;
}