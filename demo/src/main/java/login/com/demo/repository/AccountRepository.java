package login.com.demo.repository;

import login.com.demo.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findByUsernameAndPassword(String username, String password);

    // Kiểm tra tồn tại bằng username
    boolean existsByUsername(String username);
    AccountEntity findOneByUsername(String username);
}
