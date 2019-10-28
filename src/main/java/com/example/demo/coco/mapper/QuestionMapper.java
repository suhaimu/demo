package com.example.demo.coco.mapper;

import com.example.demo.coco.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtmodified},#{creator},#{tag}")
    void create(Question question);
}
