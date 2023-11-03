package vn.edu.vnua.fita.student.service.visitor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.vnua.fita.student.entity.Display;
import vn.edu.vnua.fita.student.repository.jparepo.DisplayRepository;

@Service
@RequiredArgsConstructor
public class DisplayServiceImpl implements DisplayService{
    private final DisplayRepository displayRepository;
    private final String displayNotFound = "Không tìm thấy hiển thị này";

    @Override
    public Display getDisplayById(Long id) {
        return displayRepository.findById(id).orElseThrow(() -> new RuntimeException(displayNotFound));
    }
}
