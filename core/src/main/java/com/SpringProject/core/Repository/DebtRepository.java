package com.SpringProject.core.Repository;

import com.SpringProject.core.Entity.Debt;
import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebtRepository extends JpaRepository<Debt, Long> {
  Debt findByGroupAndUserFromAndUserTo (Group group, User userFrom, User userTo);
  List<Debt> findAllByGroupAndUserFrom (Group group, User userFrom);
}
