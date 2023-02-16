package shop.mtcoding.bankapp.model.account;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import shop.mtcoding.bankapp.dto.account.AccountSaveReqDto;

@Mapper
public interface AccountRepository {
    public int insert(Account account);

    public int updateById(Account account);

    public int deleteById(int id);

    public List<Account> findAll();

    public Account findById(int id);

}
