package shop.mtcoding.bankapp.model.history;

import java.util.List;

public interface HistoryRepository {
    public int insert(History history);

    public int updateById(History history);

    public int deleteById(int id);

    public List<History> findAll();

    public History findById(int id);

}
