package sh.springcore.transaction;


import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionTestService {

    private final DataSource dataSource;
    private final TransactionTestRepository repository;

    @Transactional(readOnly = true)
    public List<MemberDto> getAllMembers() throws InterruptedException {
        printDataSourceInfo();

        System.out.println("================ Start ================");
        List<MemberDto> memberDtos = repository.findAll()
                .stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
        System.out.println("================ End ================");

        printDataSourceInfo();
        return memberDtos;
    }

    private void printDataSourceInfo() throws InterruptedException {

        Thread.sleep(1000);

        if (dataSource instanceof HikariDataSource hikariDataSource) {
            HikariPoolMXBean poolMXBean = hikariDataSource.getHikariPoolMXBean();

            int totalConnections = poolMXBean.getTotalConnections();
            int idleConnections = poolMXBean.getIdleConnections();
            int activeConnections = totalConnections - idleConnections;

            System.out.println("totalConnections = " + totalConnections);
            System.out.println("idleConnections = " + idleConnections);
            System.out.println("activeConnections = " + activeConnections);
        }
    }
}
