package com.communitydemo.service;

import com.communitydemo.dto.PaginationDTO;
import com.communitydemo.dto.QuestionDTO;
import com.communitydemo.mapper.QuestionMapper;
import com.communitydemo.mapper.UserMapper;
import com.communitydemo.model.Question;
import com.communitydemo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);

        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        if (questions != null) {
            for (Question question : questions) {
                if (question.getCreator() != null) {
                    User user = userMapper.findById(Integer.parseInt(question.getCreator().trim()));
                    QuestionDTO questionDTO = new QuestionDTO();
                    BeanUtils.copyProperties(question, questionDTO);
                    questionDTO.setUser(user);
                    questionDTOList.add(questionDTO);
                }
            }
            paginationDTO.setQuestions(questionDTOList);
        }
        return paginationDTO;
    }
}
