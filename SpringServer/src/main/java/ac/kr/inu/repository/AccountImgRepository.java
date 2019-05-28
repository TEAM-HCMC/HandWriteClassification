package ac.kr.inu.repository;

import ac.kr.inu.domain.Account;
import ac.kr.inu.domain.AccountImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountImgRepository extends JpaRepository<AccountImg, Long> {

    List<AccountImg> findByAccount(Account account);

    List<AccountImg> findByAccount_Id(Long id);

    void deleteAllByAccount_Id(Long id);

    void deleteById(Long id);
}
