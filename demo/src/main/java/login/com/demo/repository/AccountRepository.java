package login.com.demo.repository;

import login.com.demo.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findOneByUsernameAndPassword(String account, String password);
}
