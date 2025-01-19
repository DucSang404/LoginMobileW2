package login.com.demo.service;

import login.com.demo.converter.AccountConverter;
import login.com.demo.converter.IAccountConverter;
import login.com.demo.dto.AccountDTO;
import login.com.demo.dto.MessageDTO;
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

    public boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    public MessageDTO register(AccountDTO accountDTO) {
        AccountEntity accountEntity = accountConverter.toEntity(accountDTO);

        if (accountEntity == null) {
            return new MessageDTO("Dữ liệu tài khoản không hợp lệ!", false, "fail");
        }

        if (accountRepository.existsByUsername(accountDTO.getUsername())) {
            return new MessageDTO("Email đã tồn tại!", false, "fail");
        }

        try {
            accountEntity = accountRepository.save(accountEntity);
            return new MessageDTO("Tạo tài khoản thành công!", true, "success");
        } catch (Exception e) {
            return new MessageDTO("Tạo tài khoản thất bại: " + e.getMessage(), false, "fail");
        }
    }

    public boolean updatePassword(String email, String password) {
        AccountEntity entity = accountRepository.findOneByUsername(email);

        entity.setPassword(password);
        entity = accountRepository.save(entity);

        return true;
    }


}
