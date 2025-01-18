package login.com.demo.service;

import login.com.demo.converter.AccountConverter;
import login.com.demo.converter.IAccountConverter;
import login.com.demo.dto.AccountDTO;
import login.com.demo.entity.AccountEntity;
import login.com.demo.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    IAccountConverter accountConverter;

    public List<AccountDTO> findAll() {
        List<AccountEntity> entities = accountRepository.findAll();
        List<AccountDTO> dtos = new ArrayList<>();
        for(AccountEntity entity : entities) {
            dtos.add(accountConverter.toDTO(entity));
        }
        return dtos;
    }

    public AccountDTO findByUsernameAndPassword (String username, String password) {
        AccountEntity account = accountRepository.findByUsernameAndPassword(username, password);
        return (account != null) ? accountConverter.toDTO(account) : null;
    }
}
