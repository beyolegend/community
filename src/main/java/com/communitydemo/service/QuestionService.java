package com.communitydemo.service;

import com.communitydemo.dto.QuestionDTO;
import com.communitydemo.mapper.QuestionMapper;
import com.communitydemo.mapper.UserMapper;
import com.communitydemo.model.Question;
import com.communitydemo.model.User;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if (questions != null) {
            for (Question question : questions) {
                if (question.getCreator()!= null) {
                    User user = userMapper.findById(Integer.parseInt(question.getCreator().trim()));
                    QuestionDTO questionDTO = new QuestionDTO();
                    BeanUtils.copyProperties(question, questionDTO);
                    questionDTO.setUser(user);
                    questionDTOList.add(questionDTO);
                }
            }
        }
        return questionDTOList;
    }
}
