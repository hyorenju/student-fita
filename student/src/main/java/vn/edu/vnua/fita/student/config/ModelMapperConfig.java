package vn.edu.vnua.fita.student.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.addMappings(new PropertyMap<Point, PointDTO>() {
//            @Override
//            protected void configure() {
//                map().setId(source.getId());
//            }
//        });
        return modelMapper;
    }
}
