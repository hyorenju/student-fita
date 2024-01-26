package vn.edu.vnua.fita.student.service.student;

import com.google.cloud.storage.Blob;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.vnua.fita.student.common.FirebaseExpirationTimeConstant;
import vn.edu.vnua.fita.student.entity.Student;
import vn.edu.vnua.fita.student.entity.StudentStatus;
import vn.edu.vnua.fita.student.model.statistic.StudentStatistic;
import vn.edu.vnua.fita.student.repository.jparepo.StudentRepository;
import vn.edu.vnua.fita.student.request.ChangePasswordRequest;
import vn.edu.vnua.fita.student.request.admin.studentstatus.GetStudentStatusListRequest;
import vn.edu.vnua.fita.student.request.student.UpdateEmailRequest;
import vn.edu.vnua.fita.student.request.student.UpdatePhoneRequest;
import vn.edu.vnua.fita.student.request.student.UpdateStudentProfileRequest;
import vn.edu.vnua.fita.student.service.admin.file.FirebaseService;
import vn.edu.vnua.fita.student.service.admin.management.StudentStatusManager;
import vn.edu.vnua.fita.student.service.admin.statistic.StatisticService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final FirebaseService firebaseService;
    private final PasswordEncoder encoder;
    private final StatisticService statisticService;
    private final StudentStatusManager studentStatusService;
    private final String studentNotFound = "Không tìm thấy sinh viên";
    private final String mustBeGmail = "Vui lòng sử dụng tài khoản gmail phục vụ cho việc lấy lại mật khẩu";

    @Value("${firebase.storage.bucket}")
    private String bucketName;

    @Override
    public Student getProfile() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return studentRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(studentNotFound));
    }

    @Override
    public Student updateAvatar(MultipartFile file) throws IOException {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(studentNotFound));

        Blob blob = firebaseService.uploadImage(file, bucketName);

        student.setAvatar(blob
                .signUrl(FirebaseExpirationTimeConstant.EXPIRATION_TIME, TimeUnit.MILLISECONDS)
                .toString());

        return studentRepository.saveAndFlush(student);
    }

    @Override
    public Student updateProfile(UpdateStudentProfileRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(studentNotFound));
        student.setPhoneNumber2(request.getPhoneNumber2());
        student.setResidence(request.getResidence());
        student.setFatherName(request.getFatherName());
        student.setFatherPhoneNumber(request.getFatherPhoneNumber());
        student.setMotherName(request.getMotherName());
        student.setMotherPhoneNumber(request.getMotherPhoneNumber());
        return studentRepository.saveAndFlush(student);
    }

    @Override
    public Student changePassword(ChangePasswordRequest request) {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(studentNotFound));

        if(!encoder.matches(request.getCurrentPassword(), student.getPassword())){
            throw new RuntimeException("Mật khẩu hiện tại không chính xác");
        }
        if(encoder.matches(request.getNewPassword(), student.getPassword())){
            throw new RuntimeException("Mật khẩu mới không được trùng mật khẩu cũ");
        }
        if(!Objects.equals(request.getNewPassword(), request.getConfirmPassword())){
            throw new RuntimeException("Xác nhận mật khẩu không trùng khớp");
        }

        student.setPassword(encoder.encode(request.getNewPassword()));
        return studentRepository.saveAndFlush(student);
    }

    @Override
    public StudentStatistic getStatistic() {
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            Student student = studentRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(studentNotFound));

            return statisticService.getStudentStatistic(student.getId());
    }

    @Override
    public List<StudentStatus> getStatus() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(studentNotFound));

        GetStudentStatusListRequest request = new GetStudentStatusListRequest();
        GetStudentStatusListRequest.FilterCondition filterCondition = new GetStudentStatusListRequest.FilterCondition();
        filterCondition.setStatusId(null);
        filterCondition.setTermId(null);

        request.setStudentId(student.getId());
        request.setPage(1);
        request.setSize(Integer.MAX_VALUE);
        request.setFilter(filterCondition);

        Page<StudentStatus> page = studentStatusService.getStudentStatusList(request);
        return page.getContent().stream().toList();
    }

    @Override
    public Student updateEmail(UpdateEmailRequest request) {
        if(!request.getEmail().contains("@gmail")) {
            throw new RuntimeException(mustBeGmail);
        }

        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(studentNotFound));

        student.setEmail(request.getEmail());
        studentRepository.saveAndFlush(student);

        return student;
    }

    @Override
    public Student updatePhoneNumber2(UpdatePhoneRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Student student = studentRepository.findById(authentication.getPrincipal().toString()).orElseThrow(() -> new RuntimeException(studentNotFound));
        student.setPhoneNumber2(request.getPhoneNumber2());
        return studentRepository.saveAndFlush(student);
    }
}
