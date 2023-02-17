package shop.mtcoding.bankapp.model.history;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import shop.mtcoding.bankapp.dto.account.AccountDepositReqDto;
import shop.mtcoding.bankapp.dto.account.AccountWithdrawReqDto;

@Mapper
public interface HistoryRepository {

    public AccountWithdrawReqDto findByWithdraw();

    public AccountDepositReqDto findByDeposit();

    public List<History> findByAll();

    public int insert(History history);

    public int updateById(History history);

    public int deleteById(int id);

    public List<History> findAll();

    public History findById(int id);

}
