package ac.kr.inu.repository;

import ac.kr.inu.domain.Account;
import ac.kr.inu.domain.ComparedImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComparedImgRepository extends JpaRepository<ComparedImg, Long> {
    void deleteAllByAccount(Account account);

    List<ComparedImg> findByAccount(Account account);
}
