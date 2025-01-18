package login.com.demo.service;

import login.com.demo.dto.AccountDTO;
import login.com.demo.entity.AccountEntity;
import login.com.demo.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    AccountRepository accountRepository;

    public List<AccountDTO> findAll() {
        List<AccountEntity> entities = accountRepository.findAll();
        List<AccountDTO> dtos = new ArrayList<>();
        for(AccountEntity entity : entities) {
            AccountDTO dto = new AccountDTO();
            dto.setPassword(entity.getPassword());
            dto.setUsername(entity.getUsername());

            dtos.add(dto);
        }
        return dtos;
    }
}
