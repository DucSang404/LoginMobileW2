package login.com.demo.converter;

import login.com.demo.dto.AccountDTO;
import login.com.demo.entity.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter implements IAccountConverter {

    public AccountEntity toEntity(AccountDTO accountDTO) {
        AccountEntity entity = new AccountEntity();
        entity.setUsername(accountDTO.getUsername());
        entity.setPassword(accountDTO.getPassword());
        entity.setId(accountDTO.getId());

        return entity;
    }

    public AccountDTO toDTO(AccountEntity accountEntity) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(accountEntity.getUsername());
        accountDTO.setPassword(accountEntity.getPassword());
        accountDTO.setId(accountEntity.getId());
        return accountDTO;
    }

}
