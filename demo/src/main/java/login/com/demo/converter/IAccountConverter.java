package login.com.demo.converter;

import login.com.demo.dto.AccountDTO;
import login.com.demo.entity.AccountEntity;

public interface IAccountConverter {

    AccountEntity toEntity(AccountDTO accountDTO);

    AccountDTO toDTO(AccountEntity accountEntity);
}
