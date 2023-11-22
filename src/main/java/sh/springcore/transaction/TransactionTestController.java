package sh.springcore.transaction;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class TransactionTestController {

    private final TransactionTestService testService;

    @GetMapping("/members")
    public ResponseEntity<?> getAllStudent() throws SQLException, InterruptedException {

        List<MemberDto> allStudents = testService.getAllMembers();

        return new ResponseEntity<>(allStudents, HttpStatus.OK);

    }
}
