package com.cooklog.validate;

import com.cooklog.dto.BoardUpdateRequestDTO;
import com.cooklog.exception.board.OverContentLengthLimitException;
import com.cooklog.exception.board.OverTagCountLimitException;
import com.cooklog.exception.board.OverTagLengthLimitException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

public class BoardUpdateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BoardUpdateRequestDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoardUpdateRequestDTO request=(BoardUpdateRequestDTO) target;
        String content=request.getContent();
        List<String> tags = request.getTags();

        if(isOverContentLengthLimit(content)){
            throw new OverContentLengthLimitException();
        }
        if(isOverTagCountLimit(tags)){
            throw new OverTagCountLimitException();
        }
        if(isOverTagLengthLimit(tags)){
            throw new OverTagLengthLimitException();
        }
    }

    private boolean isOverContentLengthLimit(String content){
        return content.length()>500;
    }
    private boolean isOverTagCountLimit(List<String> tags){return tags.size()>10;}

    private boolean isOverTagLengthLimit(List<String> tags){
        for(String tag: tags){
            if(tag.length()>255){
                return true;
            }
        }
        return false;
    }
}
